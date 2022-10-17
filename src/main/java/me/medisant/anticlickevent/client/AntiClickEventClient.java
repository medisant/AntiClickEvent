package me.medisant.anticlickevent.client;

import me.medisant.anticlickevent.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class AntiClickEventClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.init();
    }
}
