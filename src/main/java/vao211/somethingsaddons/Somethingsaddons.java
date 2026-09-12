package vao211.somethingsaddons;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;
import vao211.somethingsaddons.entity.ModEntities;
import vao211.somethingsaddons.network.PickupLockPayload;

public class Somethingsaddons implements ModInitializer {
    public static final String MOD_ID = "somethingsaddons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static long lastWardenKillDay = -1;

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, SomethingsAddonsConfig.class);
        ModEntities.register();
        PayloadTypeRegistry.playC2S().register(PickupLockPayload.ID, PickupLockPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(PickupLockPayload.ID, PickupLockPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PickupLockPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                net.minecraft.server.network.ServerPlayerEntity player = context.player();
                String tag = "SomethingsAddons_PickupLocked";

                if (payload.isLocked()) {
                    if (!player.getCommandTags().contains(tag)) {
                        player.addCommandTag(tag);
                    }
                } else {
                    player.removeCommandTag(tag);
                }
            });
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            server.execute(() -> {
                ServerPlayerEntity player = handler.getPlayer();
                boolean currentLockedState = player.getCommandTags().contains("SomethingsAddons_PickupLocked");
                sender.sendPacket(new PickupLockPayload(currentLockedState));
            });
        });
    }
}