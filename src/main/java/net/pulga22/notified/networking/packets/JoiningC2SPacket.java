package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.notified.util.NotificationSaver;
import net.pulga22.notified.util.NotificationsData;

public class JoiningC2SPacket {
    public static void send(MinecraftServer server, ServerPlayerEntity player,
                            ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        //ONLY SERVER!
        NotificationsData.sendNotification(player, NotificationSaver.getTitle(), NotificationSaver.getMessage());
    }
}
