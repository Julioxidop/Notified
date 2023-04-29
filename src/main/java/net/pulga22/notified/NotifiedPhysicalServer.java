package net.pulga22.notified;

import lombok.Getter;
import net.fabricmc.api.DedicatedServerModInitializer;

public class NotifiedPhysicalServer implements DedicatedServerModInitializer {
    @Getter private static NotifiedPhysicalServer instance;

    @Override
    public void onInitializeServer() {
        instance = this;
    }
}
