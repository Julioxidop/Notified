package net.pulga22.notified.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class ScreenWrappers {
    public static void openNotiScreen(World world, PlayerEntity player){
        MinecraftClient.getInstance().setScreen(new NotiScreen(Text.literal("notiScreen"), player));
    }

    public static void openSendNotiScreen(PlayerEntity player){
        MinecraftClient.getInstance().setScreen(new SendNotiScreen(Text.literal("sendNotiScreen"), player));
    }
}
