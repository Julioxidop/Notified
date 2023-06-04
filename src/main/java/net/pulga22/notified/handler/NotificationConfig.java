package net.pulga22.notified.handler;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class NotificationConfig {
    private List<Notification> notifications;
    private String unreadNotificationColor;

    public NotificationConfig() {
        notifications = new ArrayList<>();
        unreadNotificationColor = "#ffbf40";
    }

    public int parseColor() {
        return Integer.parseInt(unreadNotificationColor.startsWith("#")
            ? unreadNotificationColor.substring(1) : unreadNotificationColor, 16);
    }
}