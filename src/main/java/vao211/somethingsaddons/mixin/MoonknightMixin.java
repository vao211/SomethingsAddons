package vao211.somethingsaddons.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

//=================
// For Marium's SoulWeaponry Moonknight Boss (Fallen Icon)
//=================

@Pseudo
@Mixin(targets = "net.soulsweaponry.entity.mobs.Moonknight")
public abstract class MoonknightMixin {

    @ModifyVariable(method = {"damage", "method_5643"}, at = @At("HEAD"), argsOnly = true, ordinal = 0, remap = false)
    private float somethingsaddons$capMoonknightRawDamage(float amount, DamageSource source) {

        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) || amount > 1000000.0f) {
            return amount;
        }

        LivingEntity entity = (LivingEntity) (Object) this;

        if (!SomethingsAddonsConfig.applyDmgGating) return amount;
        if (entity.getMaxHealth() < SomethingsAddonsConfig.hpToApplyDmgGating) return amount;

        float maxDamageLimit = (float) (entity.getMaxHealth() * SomethingsAddonsConfig.maxDamagePercentPerHit);

        if (amount > maxDamageLimit) {
            return maxDamageLimit;
        }

        return amount;
    }
}