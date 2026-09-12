package vao211.somethingsaddons.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.Somethingsaddons;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

@Mixin(LivingEntity.class)
public abstract class DropLimitMixin {

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$limitWardenLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (SomethingsAddonsConfig.limitWardenDropsPerDay && entity instanceof WardenEntity) {

            long currentDay = entity.getWorld().getTimeOfDay() / 24000L;

            if (Somethingsaddons.lastWardenKillDay == currentDay) {
                ci.cancel();
            } else {
                Somethingsaddons.lastWardenKillDay = currentDay;
            }
        }
    }
}