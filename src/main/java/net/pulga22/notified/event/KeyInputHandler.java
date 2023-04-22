package net.pulga22.notified.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.pulga22.notified.gui.ScreenWrappers;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_NOTIFIED = "key.category.notified.notified";
    public static final String KEY_OPEN_NOTIFICATIONS = "key.notified.open_notifications";

    public static KeyBinding openNotifications;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openNotifications.wasPressed()){
                ScreenWrappers.openNotiScreen(client.world, client.player);
            }
        });
    }

    public static void register(){
        openNotifications = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_NOTIFICATIONS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                KEY_CATEGORY_NOTIFIED
        ));

        registerKeyInputs();
    }
}
