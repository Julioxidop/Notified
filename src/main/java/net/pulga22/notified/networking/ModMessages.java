package net.pulga22.notified.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.pulga22.notified.Notified;
import net.pulga22.notified.networking.packets.*;

public class ModMessages {

    //Identifiers of the packets
    public static final Identifier RECEIVE_NOTIFICATION = new Identifier(Notified.MOD_ID, "receive_notification");
    public static final Identifier SEND_NOTIFICATION_SCREEN = new Identifier(Notified.MOD_ID, "send_notification_screen");
    public static final Identifier NOTIFICATION_SENT = new Identifier(Notified.MOD_ID, "notification_sent");
    public static final Identifier JOINING = new Identifier(Notified.MOD_ID, "joining");
    public static final Identifier READ_NOTIFICATION = new Identifier(Notified.MOD_ID, "read_notification");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(NOTIFICATION_SENT, SendNotificationC2SPacket::send);
        ServerPlayNetworking.registerGlobalReceiver(JOINING, JoiningC2SPacket::send);
        ServerPlayNetworking.registerGlobalReceiver(READ_NOTIFICATION, ReadNotificationC2SPacket::send);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(RECEIVE_NOTIFICATION, ReceiveNotificationS2CPacket::send);
        ClientPlayNetworking.registerGlobalReceiver(SEND_NOTIFICATION_SCREEN, OpenSendNotificationScreenS2CPacket::send);
    }

}
