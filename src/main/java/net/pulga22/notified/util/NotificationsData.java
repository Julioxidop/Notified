package net.pulga22.notified.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.notified.networking.ModMessages;

import java.util.*;

public class NotificationsData {

    public static void sendNotification(ServerPlayerEntity player){
        //ON SERVER -> Send a new notification to all players
        //Sets read to false, so the gui appears
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putBoolean("read", false);
        //Write buf with the notifications from the server-side file
        PacketByteBuf buf = PacketByteBufs.create();
        Map<String, String> map = new HashMap<>();
        map.put("title1", NotificationSaver.getTitle(1));
        map.put("message1", NotificationSaver.getMessage(1));
        map.put("title2", NotificationSaver.getTitle(2));
        map.put("message2", NotificationSaver.getMessage(2));
        map.put("title3", NotificationSaver.getTitle(3));
        map.put("message3", NotificationSaver.getMessage(3));
        buf.writeMap(map, PacketByteBuf::writeString, PacketByteBuf::writeString);
        //Sends packet to the client
        ServerPlayNetworking.send(player, ModMessages.RECEIVE_NOTIFICATION, buf);
    }

    public static void readNotification(ServerPlayerEntity player){
        //The player read the new notification
        NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
        nbt.putBoolean("read", true);
    }
}
