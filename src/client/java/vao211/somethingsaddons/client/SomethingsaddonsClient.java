package vao211.somethingsaddons.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import vao211.somethingsaddons.client.renderer.CustomDragonFireballEntityRenderer;
import vao211.somethingsaddons.entity.ModEntities;
import vao211.somethingsaddons.network.PickupLockPayload;

public class SomethingsaddonsClient implements ClientModInitializer {
    public static boolean isPickupLocked = false;
    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(PickupLockPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                isPickupLocked = payload.isLocked();
            });
        });
        EntityRendererRegistry.register(ModEntities.CUSTOM_DRAGON_FIREBALL, CustomDragonFireballEntityRenderer::new);
    }
}