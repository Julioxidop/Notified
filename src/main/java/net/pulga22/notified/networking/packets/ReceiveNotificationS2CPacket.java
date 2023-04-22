package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.notified.util.IEntityDataSaver;

public class ReceiveNotificationS2CPacket {
    public static void send(MinecraftClient client, ClientPlayNetworkHandler handler,
                            PacketByteBuf buf, PacketSender sender){
        //Send the packet to the player
        assert client.player != null;
        String[] notificationParts  = buf.readString().split(";;;");
        ((IEntityDataSaver) client.player).getPersistentData().putString("title", notificationParts[0]);
        ((IEntityDataSaver) client.player).getPersistentData().putString("message", notificationParts[1]);
    }
}
