package vao211.somethingsaddons;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry; // Đừng quên import cái này
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;
import vao211.somethingsaddons.network.PickupLockPayload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Somethingsaddons implements ModInitializer {
    public static final String MOD_ID = "somethingsaddons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, SomethingsAddonsConfig.class);

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


        Path configDir = FabricLoader.getInstance().getConfigDir().resolve("SomethingsAddons");
        try {
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
        } catch (IOException e) {
            System.err.println("[SomethingsAddons] Không thể tạo thư mục config: " + e.getMessage());
        }
        LOGGER.info("Initializing Somethings Addons");
        SomethingsAddonsConfig.init("SomethingsAddons/config", SomethingsAddonsConfig.class);
    }
}