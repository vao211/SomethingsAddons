package vao211.somethingsaddons.client.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.client.SomethingsaddonsClient;
import vao211.somethingsaddons.network.PickupLockPayload;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {

    // ĐÃ XÓA HOÀN TOÀN @Unique isPickupLocked Ở ĐÂY

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void somethingsaddons$addPickupLockButton(CallbackInfo ci) {
        int buttonX = this.x + 130;
        int buttonY = this.y + 60;
        int width = 20;
        int height = 20;

        Text buttonText = SomethingsaddonsClient.isPickupLocked ?
                Text.translatable("somethingsaddons.gui.pickup_lock.on") :
                Text.translatable("somethingsaddons.gui.pickup_lock.off");

        ButtonWidget lockButton = ButtonWidget.builder(buttonText, (button) -> {
                    SomethingsaddonsClient.isPickupLocked = !SomethingsaddonsClient.isPickupLocked;
                    ClientPlayNetworking.send(new PickupLockPayload(SomethingsaddonsClient.isPickupLocked));
                    button.setMessage(SomethingsaddonsClient.isPickupLocked ?
                            Text.translatable("somethingsaddons.gui.pickup_lock.on") :
                            Text.translatable("somethingsaddons.gui.pickup_lock.off"));
                })
                .dimensions(buttonX, buttonY, width, height)
                .build();
        this.addDrawableChild(lockButton);
    }
}