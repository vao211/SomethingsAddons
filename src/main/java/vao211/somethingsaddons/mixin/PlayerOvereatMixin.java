package vao211.somethingsaddons.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

@Mixin(PlayerEntity.class)
public abstract class PlayerOvereatMixin {

    @Inject(method = "canConsume", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$allowEatingWhenFull(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir) {
        if (!SomethingsAddonsConfig.enableOvereating) return;

        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getAbilities().invulnerable) return;

        float currentSaturation = player.getHungerManager().getSaturationLevel();
        int currentFoodLevel = player.getHungerManager().getFoodLevel();

        if (currentSaturation < currentFoodLevel) {
            cir.setReturnValue(true);
        }
    }
}