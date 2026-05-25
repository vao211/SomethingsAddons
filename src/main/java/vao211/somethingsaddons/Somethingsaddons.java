package vao211.somethingsaddons;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Somethingsaddons implements ModInitializer {
    public static final String MOD_ID = "somethingsaddons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, SomethingsAddonsConfig.class);
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
