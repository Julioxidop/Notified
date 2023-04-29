package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.notified.networking.ModMessages;
import net.pulga22.notified.util.NotificationSaver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JoiningC2SPacket {
    public static void send(MinecraftServer server, ServerPlayerEntity player,
                            ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        //ONLY SERVER!
        //To sync notifications files from server-side to client-side
        List<String> titles = NotificationSaver.getTitles();
        List<String> messages = NotificationSaver.getMessages();
        AtomicInteger index = new AtomicInteger();
        index.set(0);



        if (titles != null && messages != null){
            ServerPlayNetworking.send(player, ModMessages.JOINING_CLEAR, PacketByteBufs.empty());
            titles.forEach((title) -> {
                PacketByteBuf newBuf = PacketByteBufs.create();
                Map<String, String> map = new HashMap<>();
                map.put("title", title);
                map.put("message", messages.get(index.get()));
                index.getAndIncrement();
                newBuf.writeMap(map, PacketByteBuf::writeString, PacketByteBuf::writeString);
                ServerPlayNetworking.send(player, ModMessages.JOINING_SYNC, newBuf);
            });

        }




    }
}
