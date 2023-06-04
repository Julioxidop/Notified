package net.pulga22.notified.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.notified.Notified;
import net.pulga22.notified.handler.Notification;

public class ReceiveNotificationUtil {

    @Environment(EnvType.CLIENT)
    public static void handleNotification(MinecraftClient client, PacketByteBuf buf, boolean clear) {
        try {
            List<Notification> notifications = parseNotificationPacket(buf);

            //SYNC
            client.execute(()-> {
                NotificationSaver config = Notified.getInstance().getConfig();
                var notificationList = config.getConfig().getNotifications();

                int lastNotificationSize = notificationList.size();
                if(clear) notificationList.clear();
                notificationList.addAll(notifications);
                config.saveConfig();

                // Check if it has new notifications
                if(lastNotificationSize != notificationList.size())
                    ((IEntityDataSaver) client.player).getPersistentData().putBoolean("read", false);
            });
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    @Environment(EnvType.CLIENT)
    public static void clearNotifications(MinecraftClient client){
        client.execute(() -> {
            NotificationSaver config = Notified.getInstance().getConfig();
            List<Notification> notificationList = config.getConfig().getNotifications();
            notificationList.clear();
            config.saveConfig();
        });
    }

    public static List<Notification> parseNotificationPacket(PacketByteBuf buf) {
        int size = buf.readInt();
        List<Notification> notifications = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Notification notification = new Notification(buf.readString(), buf.readString());
            notifications.add(notification);
        }
        return notifications;
    }

    public static PacketByteBuf createNotificationBuffer(Notification ... notifications) {
        return createNotificationBuffer(Arrays.asList(notifications));
    }

    public static PacketByteBuf createNotificationBuffer(List<Notification> notifications) {
        PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(notifications.size());
        for (Notification notification : notifications) {
            buf.writeString(notification.getTitle());
            buf.writeString(notification.getMessage());
        }

        return buf;
    }
}
