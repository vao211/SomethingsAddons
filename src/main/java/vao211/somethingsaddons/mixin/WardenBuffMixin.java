package vao211.somethingsaddons.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.Somethingsaddons;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

import java.util.List;

@Mixin(WardenEntity.class)
public abstract class WardenBuffMixin {
    @Unique private boolean somethingsaddons$statsInitialized = false;
    @Unique private int somethingsaddons$aliveTicks = 0;
    @Unique private int somethingsaddons$buffTimer = 0;
    @Unique private int somethingsaddons$buffCount = 0;
    @Unique private static final Identifier WARDEN_ARMOR_BUFF = Identifier.of(Somethingsaddons.MOD_ID, "warden_armor_buff");
    @Unique private static final Identifier WARDEN_DMG_BUFF = Identifier.of(Somethingsaddons.MOD_ID, "warden_dmg_buff");
    @Unique private static final Identifier WARDEN_SCALE_BUFF = Identifier.of(Somethingsaddons.MOD_ID, "warden_scale_buff");

    @Inject(method = "tick", at = @At("HEAD"))
    private void somethingsaddons$wardenEnrageTick(CallbackInfo ci) {
        WardenEntity warden = (WardenEntity) (Object) this;

        if (warden.getWorld().isClient() || warden.isDead() || !SomethingsAddonsConfig.enableWardenBuff) return;
        if (!this.somethingsaddons$statsInitialized) {
            EntityAttributeInstance maxHealthAttr = warden.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (maxHealthAttr != null && maxHealthAttr.getBaseValue() != SomethingsAddonsConfig.wardenBaseHealth) {
                float currentHealth = warden.getHealth();
                double oldMax = maxHealthAttr.getBaseValue();
                maxHealthAttr.setBaseValue(SomethingsAddonsConfig.wardenBaseHealth);
                warden.setHealth(currentHealth * (float) (SomethingsAddonsConfig.wardenBaseHealth / oldMax));
            }
            EntityAttributeInstance armorAttr = warden.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
            if (armorAttr != null && armorAttr.getBaseValue() != SomethingsAddonsConfig.wardenBaseArmor) {
                armorAttr.setBaseValue(SomethingsAddonsConfig.wardenBaseArmor);
            }

            this.somethingsaddons$statsInitialized = true;
        }

        this.somethingsaddons$aliveTicks++;
        if (this.somethingsaddons$aliveTicks <= 100) {
            return;
        }
        if (this.somethingsaddons$buffCount < SomethingsAddonsConfig.wardenMaxBuffTimes) {
            this.somethingsaddons$buffTimer++;

            int threshold = SomethingsAddonsConfig.wardenBuffTimeThreshold * 20;

            if (this.somethingsaddons$buffTimer >= threshold) {
                this.somethingsaddons$applyWardenBuff(warden);
                this.somethingsaddons$buffTimer = 0;
            }
        }
    }

    @Unique
    private void somethingsaddons$applyWardenBuff(WardenEntity warden) {
        this.somethingsaddons$buffCount++;

        float healAmount = warden.getMaxHealth() * 0.2f;
        warden.setHealth(Math.min(warden.getMaxHealth(), warden.getHealth() + healAmount));

        somethingsaddons$addPersistentModifier(warden, EntityAttributes.GENERIC_ARMOR, WARDEN_ARMOR_BUFF, 3.0 * this.somethingsaddons$buffCount);
        somethingsaddons$addPersistentModifier(warden, EntityAttributes.GENERIC_ATTACK_DAMAGE, WARDEN_DMG_BUFF, 2.0 * this.somethingsaddons$buffCount);

        double scaleIncrease = Math.min(1.0, 0.1 * this.somethingsaddons$buffCount);
        somethingsaddons$addPersistentModifier(warden, EntityAttributes.GENERIC_SCALE, WARDEN_SCALE_BUFF, scaleIncrease);

        ServerWorld world = (ServerWorld) warden.getWorld();

        world.spawnParticles(ParticleTypes.SONIC_BOOM, warden.getX(), warden.getBodyY(0.5), warden.getZ(), 1, 0, 0, 0, 0);
        world.playSound(null, warden.getBlockPos(), SoundEvents.ENTITY_WARDEN_AGITATED, SoundCategory.HOSTILE, 2.0f, 0.8f);

        Box aoeBox = warden.getBoundingBox().expand(8.0D);
        List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class, aoeBox, e -> e != warden && e.isAlive());

        for (LivingEntity target : targets) {
            double dx = warden.getX() - target.getX();
            double dz = warden.getZ() - target.getZ();
            target.takeKnockback(2.0, dx, dz);
            if (target instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.networkHandler.sendPacket(new net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket(serverPlayer));
            }

            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 3));
        }
    }

    @Unique
    private void somethingsaddons$addPersistentModifier(WardenEntity warden, net.minecraft.registry.entry.RegistryEntry<net.minecraft.entity.attribute.EntityAttribute> attribute, Identifier id, double value) {
        EntityAttributeInstance attrInstance = warden.getAttributeInstance(attribute);
        if (attrInstance != null) {
            attrInstance.removeModifier(id);
            attrInstance.addPersistentModifier(new EntityAttributeModifier(id, value, EntityAttributeModifier.Operation.ADD_VALUE));
        }
    }
}