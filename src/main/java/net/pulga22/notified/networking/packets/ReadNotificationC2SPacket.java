package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.notified.util.NotificationsData;

public class ReadNotificationC2SPacket {
    public static void send(MinecraftServer server, ServerPlayerEntity player,
                            ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        //Server
        NotificationsData.readNotification(player);
    }
}
