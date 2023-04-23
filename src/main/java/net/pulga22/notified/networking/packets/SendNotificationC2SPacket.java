package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.notified.util.NotificationSaver;
import net.pulga22.notified.util.NotificationsData;

import java.util.Collection;

public class SendNotificationC2SPacket {
    public static void send(MinecraftServer server, ServerPlayerEntity player,
                            ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        //On server
        String[] notificationParts  = buf.readString().split(";;;");
        server.execute(()-> {
            NotificationSaver.setTitle(notificationParts[0]);
            NotificationSaver.setMessage(notificationParts[1]);
        });
        Collection<ServerPlayerEntity> allPlayers = PlayerLookup.all(server);
        allPlayers.forEach(onePlayer -> {
            NotificationsData.sendNotification(onePlayer, notificationParts[0], notificationParts[1]);
        });
    }
}
