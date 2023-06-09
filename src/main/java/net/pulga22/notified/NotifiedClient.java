package net.pulga22.notified;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.pulga22.notified.command.CommandRegistries;
import net.pulga22.notified.event.KeyInputHandler;
import net.pulga22.notified.gui.hud.NewNotificationOverlay;
import net.pulga22.notified.networking.ModMessages;

public class NotifiedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModMessages.registerS2CPackets();
        CommandRegistries.register();

        //HUD & Event Register
        HudRenderCallback.EVENT.register(new NewNotificationOverlay());
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client)->{
            if (client.player != null){
                ClientPlayNetworking.send(ModMessages.JOINING, PacketByteBufs.empty());
            }
        });
    }
}
