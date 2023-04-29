package net.pulga22.notified;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.pulga22.notified.util.NotificationSaver;

public class NotifiedPhysicalServer implements DedicatedServerModInitializer {
    public static boolean dedicated = false;

    @Override
    public void onInitializeServer() {
        NotificationSaver.configFile();
        dedicated = true;
    }
}
