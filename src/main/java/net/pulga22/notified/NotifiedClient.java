package net.pulga22.notified;

import net.fabricmc.api.ClientModInitializer;
import net.pulga22.notified.event.KeyInputHandler;

public class NotifiedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
