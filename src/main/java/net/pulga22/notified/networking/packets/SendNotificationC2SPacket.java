package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.pulga22.notified.Notified;
import net.pulga22.notified.NotifiedPhysicalServer;
import net.pulga22.notified.handler.Notification;
import net.pulga22.notified.networking.ModMessages;
import net.pulga22.notified.util.IEntityDataSaver;
import net.pulga22.notified.util.NotificationSaver;
import net.pulga22.notified.util.ReceiveNotificationUtil;

import java.util.Collection;
import java.util.List;

public class SendNotificationC2SPacket {
    public static void send(MinecraftServer server, ServerPlayerEntity player,
                            ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        //On server
        //Changes notifications file server-side
        //Pop not3 so new not can be saved
        List<Notification> notifications = ReceiveNotificationUtil.parseNotificationPacket(buf);
        PacketByteBuf newBuf = ReceiveNotificationUtil.createNotificationBuffer(notifications);

        //Sends a new notification to all players
        if (NotifiedPhysicalServer.getInstance() != null){
            NotificationSaver saver = Notified.getInstance().getConfig();
            Collection<ServerPlayerEntity> allPlayers = PlayerLookup.all(server);
            saver.getConfig().getNotifications().addAll(notifications);
            saver.saveConfig();

            allPlayers.forEach(onePlayer -> {

                onePlayer.sendMessage(Text.translatable("notified.new_notification_received")
                    .fillStyle(Style.EMPTY.withColor(saver.getConfig().parseColor())));

                onePlayer.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.AMBIENT, 1f, 1f);

                //Sends packet of the new notification to all players
                NbtCompound nbt = ((IEntityDataSaver) onePlayer).getPersistentData();
                    nbt.putBoolean("read", false);
                ServerPlayNetworking.send(onePlayer, ModMessages.RECEIVE_NOTIFICATION, newBuf);
            });
        }

    }
}
