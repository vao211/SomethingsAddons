package vao211.somethingsaddons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherEntity.class)
public abstract class WitherEntityMixin {

    //Stats manager and phase changing
    @Inject(method = "mobTick", at = @At("HEAD"))
    private void somethingsaddons$updateDangerWitherStats(CallbackInfo ci) {
        if (!SomethingsAddonsConfig.enableDangerBoss) return;

        WitherEntity wither = (WitherEntity) (Object) this;

        //Update MaxHP
        EntityAttributeInstance maxHealthAttr = wither.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (maxHealthAttr != null && maxHealthAttr.getBaseValue() != SomethingsAddonsConfig.witherBaseHealth) {
            maxHealthAttr.setBaseValue(SomethingsAddonsConfig.witherBaseHealth);
            wither.setHealth(wither.getMaxHealth());
        }

        boolean isPhase2 = wither.getHealth() <= SomethingsAddonsConfig.witherPhase2Threshold;

        double targetArmor = isPhase2 ? SomethingsAddonsConfig.witherPhase2Armor : SomethingsAddonsConfig.witherBaseArmor;
        double targetDamage = isPhase2 ? SomethingsAddonsConfig.witherPhase2Damage : SomethingsAddonsConfig.witherBaseDamage;

        EntityAttributeInstance armorAttr = wither.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if (armorAttr != null && armorAttr.getBaseValue() != targetArmor) {
            armorAttr.setBaseValue(targetArmor);
        }

        EntityAttributeInstance dmgAttr = wither.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (dmgAttr != null && dmgAttr.getBaseValue() != targetDamage) {
            dmgAttr.setBaseValue(targetDamage);
        }
    }

    //Protect block breaking from Wither
    @Redirect(
            method = "mobTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z")
    )
    private boolean somethingsaddons$redirectWitherBlockBreak(World world,
                                                              BlockPos pos,
                                                              boolean originalDrop,
                                                              Entity breakingEntity) {
        boolean shouldDrop = originalDrop;
        if (SomethingsAddonsConfig.enableDangerBoss && SomethingsAddonsConfig.witherNoBlockDrops) {
            shouldDrop = false;
        }
        return world.breakBlock(pos, shouldDrop, breakingEntity);
    }
}