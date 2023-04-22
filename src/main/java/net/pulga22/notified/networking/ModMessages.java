package net.pulga22.notified.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.pulga22.notified.Notified;
import net.pulga22.notified.networking.packets.ReceiveNotificationS2CPacket;
import net.pulga22.notified.networking.packets.SendNotificationC2SPacket;
import net.pulga22.notified.networking.packets.OpenSendNotificationScreenS2CPacket;

public class ModMessages {

    //Identifiers of the packets
    public static final Identifier RECEIVE_NOTIFICATION = new Identifier(Notified.MOD_ID, "receive_notification");
    public static final Identifier SEND_NOTIFICATION_SCREEN = new Identifier(Notified.MOD_ID, "send_notification_screen");
    public static final Identifier NOTIFICATION_SENT = new Identifier(Notified.MOD_ID, "notification_sent");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(NOTIFICATION_SENT, SendNotificationC2SPacket::send);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(RECEIVE_NOTIFICATION, ReceiveNotificationS2CPacket::send);
        ClientPlayNetworking.registerGlobalReceiver(SEND_NOTIFICATION_SCREEN, OpenSendNotificationScreenS2CPacket::send);
    }

}
