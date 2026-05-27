package vao211.somethingsaddons.mixin;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.registry.tag.DamageTypeTags;

@Mixin(value = LivingEntity.class, priority = 2000)
public abstract class LivingEntityMixin {
    @Unique private int somethingsaddons_lastDamageWindowTick = 0;
    @Unique private float somethingsaddons_damageTakenThisWindow = 0f;

    @Unique private int somethingsaddons_lastHealWindowTick = 0;
    @Unique private float somethingsaddons_healTakenThisWindow = 0f;
    @Unique private boolean somethingsaddons_bypassGating = false;

    @Inject(method = "damage", at = @At("RETURN"))
    private void somethingsaddons$resetBypassFlag(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.somethingsaddons_bypassGating = false;
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$catchKillCommand(DamageSource source,
                                                   float amount,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) || amount > 1000000.0f) {
            LivingEntity entity = (LivingEntity) (Object) this;
            if (SomethingsAddonsConfig.protectCreativePlayer && entity instanceof PlayerEntity player && player.isCreative()) {
                cir.setReturnValue(false);
                return;
            }

            this.somethingsaddons_bypassGating = true;
        }
    }

    @ModifyVariable(method = "setHealth", at = @At("HEAD"), argsOnly = true)
    private float somethingsaddons$gateHealth(float newHealth) {
        LivingEntity entity = (LivingEntity) (Object) this;

        float currentHealth = entity.getHealth();

        //Bypass for /kill command
        if (this.somethingsaddons_bypassGating) {
            return newHealth;
        }

        if (newHealth == currentHealth) return newHealth;

        float maxHealth = entity.getMaxHealth();
        if (maxHealth < SomethingsAddonsConfig.hpToApplyDmgGating) return newHealth;

        int currentTick = entity.age;
        // Case 1: Take Dmg
        if (newHealth < currentHealth) {
            if (!SomethingsAddonsConfig.applyDmgGating) return newHealth;

            float damageAttempted = currentHealth - newHealth;
            float maxDamageLimit = (float) (maxHealth * SomethingsAddonsConfig.maxDamagePercentPerHit);

            // Reset (5 tick)
            if (currentTick - this.somethingsaddons_lastDamageWindowTick > 5) {
                this.somethingsaddons_damageTakenThisWindow = 0f;
                this.somethingsaddons_lastDamageWindowTick = currentTick;
            }

            if (this.somethingsaddons_damageTakenThisWindow + damageAttempted > maxDamageLimit) {
                float allowedDamage = maxDamageLimit - this.somethingsaddons_damageTakenThisWindow;
                this.somethingsaddons_damageTakenThisWindow = maxDamageLimit;
                return currentHealth - Math.max(0f, allowedDamage);
            }

            this.somethingsaddons_damageTakenThisWindow += damageAttempted;
            return newHealth;
        }

        // Case 2: Heal
        if (entity.age >= 40) {
            if (newHealth > currentHealth) {
                if (!SomethingsAddonsConfig.applyHealGating) return newHealth;

                float healAttempted = newHealth - currentHealth;
                float maxHealLimit = (float) (maxHealth * SomethingsAddonsConfig.maxHealPercentPerSecond);

                // Reset (20 tick)
                if (currentTick - this.somethingsaddons_lastHealWindowTick > 20) {
                    this.somethingsaddons_healTakenThisWindow = 0f;
                    this.somethingsaddons_lastHealWindowTick = currentTick;
                }

                if (this.somethingsaddons_healTakenThisWindow + healAttempted > maxHealLimit) {
                    float allowedHeal = maxHealLimit - this.somethingsaddons_healTakenThisWindow;
                    this.somethingsaddons_healTakenThisWindow = maxHealLimit;
                    return currentHealth + Math.max(0f, allowedHeal);
                }
                this.somethingsaddons_healTakenThisWindow += healAttempted;
                return newHealth;
            }
        }
            return newHealth;
    }

    @Inject(method = "getAttributeValue", at = @At("RETURN"), cancellable = true)
    private void somethingsaddons$enforceBossKnockbackResistance(RegistryEntry<?> attribute, CallbackInfoReturnable<Double> cir) {
        if (!SomethingsAddonsConfig.applyBossKnockbackImmunity) return;

        if (attribute.equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ||
                attribute.equals(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE)) {
            LivingEntity entity = (LivingEntity) (Object) this;
            if (entity.getMaxHealth() >= SomethingsAddonsConfig.hpToApplyKnockbackImmunity) {
                cir.setReturnValue(Math.max(1.0, cir.getReturnValue()));
            }
        }
    }

    //Armor Piercing
    @Inject(method = "applyArmorToDamage", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$trueArmorPiercing(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (!SomethingsAddonsConfig.enableDangerBoss) return;

        if (source.getAttacker() instanceof WitherEntity wither) {

            if (wither.getHealth() <= SomethingsAddonsConfig.witherPhase2Threshold) {
                LivingEntity victim = (LivingEntity) (Object) this;
                float currentArmor = victim.getArmor();
                float piercing = (float) SomethingsAddonsConfig.witherArmorPiercing;
                float effectiveArmor = currentArmor - (currentArmor * piercing);
                if (effectiveArmor<0) effectiveArmor = 0;

                float minimumTrueDamage = amount * piercing * 3.0f;
                float finalDamage = (amount / (1.0f + effectiveArmor)) * 5.0f;
                finalDamage = Math.max(finalDamage, minimumTrueDamage);

                cir.setReturnValue(finalDamage);
            }
        }
    }

    //ENDER DRAGON
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "getMaxHealth", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$dynamicDragonMaxHealth(CallbackInfoReturnable<Float> cir) {
        if (SomethingsAddonsConfig.enableDangerDragon && (Object) this instanceof EnderDragonEntity) {
            cir.setReturnValue((float) SomethingsAddonsConfig.enderDragonBaseHealth);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "getArmor", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$dynamicDragonArmor(CallbackInfoReturnable<Integer> cir) {
        if (SomethingsAddonsConfig.enableDangerDragon && (Object) this instanceof EnderDragonEntity) {
            cir.setReturnValue((int) SomethingsAddonsConfig.enderDragonBaseArmor);
        }
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float somethingsaddons$modifyDragonBreathDamage(float amount, DamageSource source) {
        if (SomethingsAddonsConfig.enableDangerDragon && source.isOf(DamageTypes.DRAGON_BREATH)) {
            return (float) SomethingsAddonsConfig.dragonBreathDamage;
        }
        return amount;
    }

}