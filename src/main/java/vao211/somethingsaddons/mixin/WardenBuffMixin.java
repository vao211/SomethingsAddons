package vao211.somethingsaddons.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.particle.ParticleTypes;
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

        warden.heal(warden.getMaxHealth() * 0.2f);

        somethingsaddons$addPersistentModifier(warden, EntityAttributes.GENERIC_ARMOR, WARDEN_ARMOR_BUFF, 3.0 * this.somethingsaddons$buffCount);
        somethingsaddons$addPersistentModifier(warden, EntityAttributes.GENERIC_ATTACK_DAMAGE, WARDEN_DMG_BUFF, 2.0 * this.somethingsaddons$buffCount);

        double scaleIncrease = Math.min(1.0, 0.1 * this.somethingsaddons$buffCount);
        somethingsaddons$addPersistentModifier(warden, EntityAttributes.GENERIC_SCALE, WARDEN_SCALE_BUFF, scaleIncrease);

        ServerWorld world = (ServerWorld) warden.getWorld();

        world.spawnParticles(ParticleTypes.SONIC_BOOM, warden.getX(), warden.getBodyY(0.5), warden.getZ(), 1, 0, 0, 0, 0);
        world.playSound(null, warden.getBlockPos(), SoundEvents.ENTITY_WARDEN_AGITATED, SoundCategory.HOSTILE, 2.0f, 0.8f);

        Box aoeBox = warden.getBoundingBox().expand(3.0D);
        List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class, aoeBox, e -> e != warden && e.isAlive());

        for (LivingEntity target : targets) {
            double dx = target.getX() - warden.getX();
            double dz = target.getZ() - warden.getZ();
            target.takeKnockback(3, -dx, -dz);

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