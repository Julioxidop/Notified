package net.pulga22.notified.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class NotiScreenWrapper {
    public static void openGui(World world, PlayerEntity entity){
        MinecraftClient.getInstance().setScreen(new NotiScreen(Text.literal("test"), entity));
    }
}
