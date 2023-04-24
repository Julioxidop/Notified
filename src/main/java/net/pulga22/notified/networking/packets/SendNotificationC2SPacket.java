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
import java.util.Map;

public class SendNotificationC2SPacket {
    public static void send(MinecraftServer server, ServerPlayerEntity player,
                            ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        //On server
        //Changes notifications file server-side
            //Pop not3 so new not can be saved
        Map<String,String> map = buf.readMap(PacketByteBuf::readString, PacketByteBuf::readString);
        NotificationSaver.setTitle(3, NotificationSaver.getTitle(2));
        NotificationSaver.setMessage(3, NotificationSaver.getMessage(2));
        NotificationSaver.setTitle(2, NotificationSaver.getTitle(1));
        NotificationSaver.setMessage(2, NotificationSaver.getMessage(1));
        NotificationSaver.setTitle(1, map.get("title1"));
        NotificationSaver.setMessage(1, map.get("message1"));

        //Sends a new notification to all players
        Collection<ServerPlayerEntity> allPlayers = PlayerLookup.all(server);
        allPlayers.forEach(onePlayer -> {
            onePlayer.sendMessage(Text.literal("You have received a new notification!").fillStyle(Style.EMPTY.withBold(true).withColor(Formatting.GOLD)));
            BlockPos blockPos = onePlayer.getBlockPos();
            onePlayer.getWorld().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.AMBIENT, 1f, 1f);
            //Sends packet of the new notification to all players
            NotificationsData.sendNotification(onePlayer);
        });
    }
}
