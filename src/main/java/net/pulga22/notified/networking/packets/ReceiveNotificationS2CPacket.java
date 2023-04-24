package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.notified.util.IEntityDataSaver;
import net.pulga22.notified.util.NotificationSaver;

import java.util.Map;

public class ReceiveNotificationS2CPacket {
    public static void send(MinecraftClient client, ClientPlayNetworkHandler handler,
                            PacketByteBuf buf, PacketSender sender){
        //On client
        assert client.player != null;
        //Changes notifications client-sive files
        Map<String,String> map = buf.readMap(PacketByteBuf::readString, PacketByteBuf::readString);
        client.execute(()-> {
            NotificationSaver.setTitle(1, map.get("title1"));
            NotificationSaver.setMessage(1, map.get("message1"));
            NotificationSaver.setTitle(2, map.get("title2"));
            NotificationSaver.setMessage(2, map.get("message2"));
            NotificationSaver.setTitle(3, map.get("title3"));
            NotificationSaver.setMessage(3, map.get("message3"));
        });
        //New notification so sets read to false
        ((IEntityDataSaver) client.player).getPersistentData().putBoolean("read", false);
    }
}
