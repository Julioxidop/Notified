package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
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
            onePlayer.sendMessage(Text.literal("You have received a new notification!").fillStyle(Style.EMPTY.withBold(true).withColor(Formatting.GOLD)));
            BlockPos blockPos = onePlayer.getBlockPos();
            onePlayer.getWorld().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.AMBIENT, 1f, 1f);
            NotificationsData.sendNotification(onePlayer, notificationParts[0], notificationParts[1]);
        });
    }
}
