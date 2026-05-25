package vao211.somethingsaddons.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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
    }
}