package net.pulga22.notified;

import net.fabricmc.api.ClientModInitializer;
import net.pulga22.notified.command.CommandRegistries;
import net.pulga22.notified.event.KeyInputHandler;
import net.pulga22.notified.networking.ModMessages;

public class NotifiedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModMessages.registerS2CPackets();

    }
}
