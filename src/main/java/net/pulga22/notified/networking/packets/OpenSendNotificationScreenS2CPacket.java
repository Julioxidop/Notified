package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.notified.gui.ScreenWrappers;

public class OpenSendNotificationScreenS2CPacket {
    public static void send(MinecraftClient client, ClientPlayNetworkHandler handler,
                            PacketByteBuf buf, PacketSender sender){
        //On client
        //Open SendNotiScren to the player
        client.execute(() -> {
            ScreenWrappers.openSendNotiScreen((PlayerEntity) client.player);
        });
    }
}
