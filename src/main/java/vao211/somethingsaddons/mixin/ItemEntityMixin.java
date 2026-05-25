package vao211.somethingsaddons.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$blockItemPickup(PlayerEntity player, CallbackInfo ci) {
        if (player.getCommandTags().contains("SomethingsAddons_PickupLocked")) {
            ci.cancel();
        }
    }
}