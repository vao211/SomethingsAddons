package vao211.somethingsaddons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

@Mixin(LivingEntity.class)
public abstract class DamageAndHealingMixin {

    @Unique
    private int somethingsaddons$healingDebuffTimer = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void somethingsaddons$tickDebuff(CallbackInfo ci) {
        if (this.somethingsaddons$healingDebuffTimer > 0) {
            this.somethingsaddons$healingDebuffTimer--;
        }
    }

    @Unique
    private float somethingsaddons$capturedOriginalDamage = 0.0f;

    //CAPTURE DMG FROM SHIELD, SNOWBALL, ... (0 dmg)
    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"))
    private void somethingsaddons$captureOriginalDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        this.somethingsaddons$capturedOriginalDamage = amount;
    }

    //MIN DMG
    @Inject(method = "modifyAppliedDamage", at = @At("RETURN"), cancellable = true)
    private void somethingsaddons$enforceMinimumDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (!SomethingsAddonsConfig.enableDmgAndHealingControl) return;

        LivingEntity entity = (LivingEntity) (Object) this;
        Entity attacker = source.getAttacker();

        if (entity.getWorld().isClient()) return;
        if (this.somethingsaddons$capturedOriginalDamage <= 0.0f) return;

        //debuffed healing ----
        if (SomethingsAddonsConfig.enableAntiHealDebuff) {
            if (attacker instanceof LivingEntity livingAttacker && livingAttacker.getMaxHealth() >= 100.0f) {
                this.somethingsaddons$healingDebuffTimer = 100;
                if (entity instanceof PlayerEntity player) {
                    player.sendMessage(Text.literal("§c-50% Healing! (5s)"), true);
                }
            }
        }
        //-----

        float damageAfterVanilla = cir.getReturnValue();

        boolean isArmorBypassing = source.isIn(DamageTypeTags.BYPASSES_ARMOR);
        boolean isValidBossOrMagicAttack =
                source.isOf(DamageTypes.DRAGON_BREATH) ||
                        source.isOf(DamageTypes.WITHER) ||
                        source.isOf(DamageTypes.MAGIC) ||
                        source.isOf(DamageTypes.INDIRECT_MAGIC) ||
                        source.isOf(DamageTypes.SONIC_BOOM);

        if (!isArmorBypassing || isValidBossOrMagicAttack) {
            float minDamage;
            if (entity instanceof PlayerEntity) {
                minDamage = (float) SomethingsAddonsConfig.minDmgTakenForPlayer;
                if (SomethingsAddonsConfig.enableMinDmgScalingWithBossHp && attacker instanceof LivingEntity livingAttacker) {
                    float attackerHp = livingAttacker.getMaxHealth();

                    if (attackerHp >= 100.0f) {
                        float multiplier = attackerHp / 100.0f;
                        multiplier = Math.min(5.0f, multiplier);
                        minDamage *= multiplier;
                    }
                }
            } else {
                minDamage = (float) SomethingsAddonsConfig.minDmgTakenForMob;
            }

            if (minDamage > 0.0f && damageAfterVanilla < minDamage) {
                cir.setReturnValue(minDamage);
            }
        }
    }

    //MAX HEALING
    @ModifyVariable(method = "heal", at = @At("HEAD"), argsOnly = true)
    private float somethingsaddons$enforceMaxHealing(float amount) {
        if (!SomethingsAddonsConfig.enableDmgAndHealingControl || amount <= 0.0f) {
            return amount;
        }
        //debuffed healing -----
        if (SomethingsAddonsConfig.enableAntiHealDebuff && this.somethingsaddons$healingDebuffTimer > 0) {
            amount *= 0.5f;
        }
        //-----
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.getWorld().isClient()) return amount;

        float maxHealing = (entity instanceof PlayerEntity)
                ? (float) SomethingsAddonsConfig.maxHealingForPlayer
                : (float) SomethingsAddonsConfig.maxHealingForMob;

        if (maxHealing > 0.0f && amount > maxHealing) {
            return maxHealing;
        }

        return amount;
    }

    //DEBUFFED HEALING FOR ABSORBTION
    @ModifyVariable(method = "setAbsorptionAmount", at = @At("HEAD"), argsOnly = true)
    private float somethingsaddons$halveAbsorption(float newAmount) {
        if (!SomethingsAddonsConfig.enableDmgAndHealingControl) return newAmount;

        if (this.somethingsaddons$healingDebuffTimer > 0) {
            LivingEntity entity = (LivingEntity) (Object) this;
            float currentAmount = entity.getAbsorptionAmount();

            if (newAmount > currentAmount) {
                float gainedAmount = newAmount - currentAmount;
                return currentAmount + (gainedAmount * 0.5f);
            }
        }

        return newAmount;
    }
}