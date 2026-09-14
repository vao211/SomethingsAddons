package vao211.somethingsaddons.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.Somethingsaddons;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

@Mixin(PlayerEntity.class)
public abstract class ArmorWeightMixin extends LivingEntity {

    protected ArmorWeightMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique private static final Identifier ARMOR_WEIGHT_SPEED = Identifier.of(Somethingsaddons.MOD_ID, "armor_weight_speed");
    @Unique private static final Identifier ARMOR_WEIGHT_KB = Identifier.of(Somethingsaddons.MOD_ID, "armor_weight_kb");

    @Inject(method = "tick", at = @At("HEAD"))
    private void somethingsaddons$applyArmorWeight(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getWorld().isClient() || !SomethingsAddonsConfig.enableArmorWeight) return;

        if (player.age % 20 != 0) return;

        double armor = player.getAttributeValue(EntityAttributes.GENERIC_ARMOR);

        double targetPenalty = 0.0;
        if (armor > 20.0) {
            double extraArmor = armor - 20.0;
            targetPenalty = Math.min(0.20, extraArmor * 0.02);
        }

        EntityAttributeInstance speedAttr = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeInstance kbAttr = player.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);

        if (speedAttr != null && kbAttr != null) {
            EntityAttributeModifier currentSpeedMod = speedAttr.getModifier(ARMOR_WEIGHT_SPEED);

            double currentPenalty = (currentSpeedMod != null) ? -currentSpeedMod.value() : 0.0;

            if (currentPenalty != targetPenalty) {
                speedAttr.removeModifier(ARMOR_WEIGHT_SPEED);
                kbAttr.removeModifier(ARMOR_WEIGHT_KB);

                if (targetPenalty > 0.0) {
                    speedAttr.addPersistentModifier(new EntityAttributeModifier(
                            ARMOR_WEIGHT_SPEED, -targetPenalty, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                    kbAttr.addPersistentModifier(new EntityAttributeModifier(
                            ARMOR_WEIGHT_KB, targetPenalty, EntityAttributeModifier.Operation.ADD_VALUE));
                }
            }
        }
    }
}