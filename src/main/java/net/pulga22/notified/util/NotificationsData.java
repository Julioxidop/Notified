package net.pulga22.notified.util;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class NotificationsData {

    public static void readNotification(ServerPlayerEntity player){
        //The player read the new notification
        NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
        nbt.putBoolean("read", true);
    }
}
