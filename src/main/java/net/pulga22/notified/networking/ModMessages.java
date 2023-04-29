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
    public static final Identifier JOINING_SYNC = new Identifier(Notified.MOD_ID, "joining_sync");
    public static final Identifier JOINING_CLEAR = new Identifier(Notified.MOD_ID, "joining_clear");
    public static final Identifier READ_NOTIFICATION = new Identifier(Notified.MOD_ID, "read_notification");

    public static void registerC2SPackets(){
        //Packet to send a notification
        ServerPlayNetworking.registerGlobalReceiver(NOTIFICATION_SENT, SendNotificationC2SPacket::send);
        //Sync packets when joining
        ServerPlayNetworking.registerGlobalReceiver(JOINING, JoiningC2SPacket::send);
        //When a people read a notification
        ServerPlayNetworking.registerGlobalReceiver(READ_NOTIFICATION, ReadNotificationC2SPacket::send);
    }

    public static void registerS2CPackets(){
        //Receive a notification, update client notifications.json
        ClientPlayNetworking.registerGlobalReceiver(RECEIVE_NOTIFICATION, ReceiveNotificationS2CPacket::send);
        //Open screen to send a new notification
        ClientPlayNetworking.registerGlobalReceiver(SEND_NOTIFICATION_SCREEN, OpenSendNotificationScreenS2CPacket::send);
        //Sync data from server to client
        ClientPlayNetworking.registerGlobalReceiver(JOINING_SYNC, JoiningSyncS2CPacket::send);
        //Clean client notifications
        ClientPlayNetworking.registerGlobalReceiver(JOINING_CLEAR, JoiningClearS2CPacket::send);
    }

}
