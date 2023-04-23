package net.pulga22.notified;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.pulga22.notified.util.NotificationSaver;

public class NotifiedPhysicalServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        NotificationSaver.configFile();
    }
}
