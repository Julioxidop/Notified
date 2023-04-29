package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.notified.Notified;
import net.pulga22.notified.handler.Notification;
import net.pulga22.notified.networking.ModMessages;
import net.pulga22.notified.util.NotificationSaver;
import net.pulga22.notified.util.ReceiveNotificationUtil;

import java.util.List;

public class JoiningC2SPacket {
    public static void send(MinecraftServer server, ServerPlayerEntity player,
                            ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        //ONLY SERVER!
        //To sync notifications files from server-side to client-side
        NotificationSaver config = Notified.getInstance().getConfig();
        List<Notification> notifications = config.getConfig().getNotifications().stream().toList();

        if(notifications.size() > 0) {
            PacketByteBuf buffer = ReceiveNotificationUtil.createNotificationBuffer(notifications);
            ServerPlayNetworking.send(player, ModMessages.JOINING_SYNC, buffer);
        }
    }
}
