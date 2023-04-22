package net.pulga22.notified.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.notified.networking.ModMessages;

public class NotificationsData {

    public static void addNotification(IEntityDataSaver player, String title, String message){
        NbtCompound nbt = player.getPersistentData();
        nbt.putString("title", title);
        nbt.putString("message", message);
    }

    //Send the packet to a player
    public static void sendNotification(ServerPlayerEntity player, String title, String message){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(title + ";;;" + message);
        addNotification((IEntityDataSaver) player, title, message);
        ServerPlayNetworking.send(player, ModMessages.RECEIVE_NOTIFICATION, buf);
    }
}
