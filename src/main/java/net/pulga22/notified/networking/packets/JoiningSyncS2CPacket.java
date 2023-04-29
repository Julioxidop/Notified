package net.pulga22.notified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.notified.util.ReceiveNotificationUtil;

public class JoiningSyncS2CPacket {

    public static void send(MinecraftClient client, ClientPlayNetworkHandler handler,
                            PacketByteBuf buf, PacketSender sender){
        //On client
        ReceiveNotificationUtil.handleNotification(client, buf, true);
    }

}
