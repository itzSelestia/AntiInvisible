package com.yae.antiinvisible.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AntiinvisibleConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("antiinvisible.json");
    private static final int DEFAULT_INVISIBLE_OPACITY_PERCENT = 15;
    private static ConfigData config = new ConfigData(DEFAULT_INVISIBLE_OPACITY_PERCENT);

    private AntiinvisibleConfig() {
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                ConfigData loadedConfig = GSON.fromJson(reader, ConfigData.class);
                if (loadedConfig != null) {
                    config = loadedConfig;
                }
            } catch (IOException | JsonParseException exception) {
                config = new ConfigData(DEFAULT_INVISIBLE_OPACITY_PERCENT);
            }
        }

        config.invisibleOpacityPercent = clamp(config.invisibleOpacityPercent);
        save();
    }

    public static int getInvisibleEntityColor() {
        int alpha = Math.round(config.invisibleOpacityPercent * 255.0F / 100.0F);
        return (alpha << 24) | 0x00FFFFFF;
    }

    private static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException ignored) {
        }
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }

    private static final class ConfigData {
        private int invisibleOpacityPercent;

        private ConfigData(int invisibleOpacityPercent) {
            this.invisibleOpacityPercent = invisibleOpacityPercent;
        }
    }
}
