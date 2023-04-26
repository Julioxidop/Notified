package net.pulga22.notified.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.MultilineTextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pulga22.notified.gui.widgets.CustomButtonWidget;
import net.pulga22.notified.Notified;
import net.pulga22.notified.networking.ModMessages;
import net.pulga22.notified.util.IEntityDataSaver;
import net.pulga22.notified.util.NotificationSaver;

public class NotiScreen extends Screen {

        private static final Identifier TEXTURE = new Identifier(Notified.MOD_ID, "textures/gui/notification_window.png");
    private TextWidget titleText;
    private MultilineTextWidget messageText;
    private CustomButtonWidget prevMsg;
    private CustomButtonWidget nxtMsg;
    private int currentIndex;
    private final String title1;
    private final String message1;
    private final String title2;
    private final String message2;
    private final String title3;
    private final String message3;
    private boolean newRead;

    protected NotiScreen(Text title, PlayerEntity player) {
        super(title);
        //Check if the player have a new notification
        newRead = !((IEntityDataSaver) player).getPersistentData().getBoolean("read");

        //Send packet to server-side informing that the player has read the notification
        ClientPlayNetworking.send(ModMessages.READ_NOTIFICATION, PacketByteBufs.empty());
        ((IEntityDataSaver) player).getPersistentData().putBoolean("read", true);

        //Get notifications from the client-side notifications file
        this.title1 = NotificationSaver.getTitle(1);
        this.message1 = NotificationSaver.getMessage(1);
        this.title2 = NotificationSaver.getTitle(2);
        this.message2 = NotificationSaver.getMessage(2);
        this.title3 = NotificationSaver.getTitle(3);
        this.message3 = NotificationSaver.getMessage(3);
        this.currentIndex = 1;
    }

    @Override
    protected void init() {
        CustomButtonWidget button = CustomButtonWidget.builder(Text.translatable("notified.notifications_screen.close"), (onPress) -> {
            this.close();
        }).dimensions((int)this.width/2 - 24,(int)this.height/2+42,50,20).build();
        this.addDrawableChild(button);
        this.prevMsg = CustomButtonWidget.builder(Text.literal("◀"), (onPress) -> {
            changeTextMessages("left");
        }).dimensions((int)this.width/2 - 45,(int)this.height/2+42,20,20).build();
        this.addDrawableChild(this.prevMsg);
        this.nxtMsg = CustomButtonWidget.builder(Text.literal("▶"), (onPress) -> {
            changeTextMessages("right");
        }).dimensions((int)this.width/2 + 27,(int)this.height/2+42,20,20).build();
        this.addDrawableChild(this.nxtMsg);

        this.titleText = new TextWidget(this.width/2 - 25, this.height/2 - 39, 50, 20, Text.literal(this.title1), this.textRenderer);
        this.titleText.alignCenter();
        this.addDrawableChild(this.titleText);

        this.messageText = new MultilineTextWidget(0, 0, Text.literal(this.message1), this.textRenderer);
        this.messageText.setCentered(true);
        this.messageText.setMaxWidth(80);
        this.messageText.setMaxRows(5);
        this.messageText.setPosition(this.width/2 - this.messageText.getWidth() / 2, this.height/2 - 12);
        this.addDrawableChild(this.messageText);

        if (this.newRead){
            this.titleText.setTextColor(0xffbf40);
            this.messageText.setTextColor(0xffbf40);
        }
        this.prevMsg.active = false;
    }

    private void changeTextMessages(String side){
        //Move to left (next)
        if (side.equals("left") && this.currentIndex > 1){
            this.nxtMsg.active = true;
            this.currentIndex--;
            changeTextWidgetsMessages(this.currentIndex);
            if (this.currentIndex == 1){
                this.prevMsg.active = false;
                if (this.newRead){
                    this.titleText.setTextColor(0xffbf40);
                    this.messageText.setTextColor(0xffbf40);
                }
            }
        //Move to right (previous)
        } else if (side.equals("right") && this.currentIndex < 3) {
            this.prevMsg.active = true;
            this.currentIndex++;
            changeTextWidgetsMessages(this.currentIndex);
            this.titleText.setTextColor(0xffffff);
            this.messageText.setTextColor(0xffffff);
            if (this.currentIndex == 3){
                this.nxtMsg.active = false;
            }
        }
    }

    private void changeTextWidgetsMessages(int index){
        switch (index){
            case 1 -> {
                this.titleText.setMessage(Text.literal(this.title1));
                this.messageText.setMessage(Text.literal(this.message1));
                this.messageText.setPosition(this.width/2 - this.messageText.getWidth() / 2, this.height/2 - 12);
            }
            case 2 -> {
                this.titleText.setMessage(Text.literal(this.title2));
                this.messageText.setMessage(Text.literal(this.message2));
                this.messageText.setPosition(this.width/2 - this.messageText.getWidth() / 2, this.height/2 - 12);
            }
            case 3 -> {
                this.titleText.setMessage(Text.literal(this.title3));
                this.messageText.setMessage(Text.literal(this.message3));
                this.messageText.setPosition(this.width/2 - this.messageText.getWidth() / 2, this.height/2 - 12);
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int x = (int) client.getWindow().getScaledWidth() / 2;
        int y = (int) client.getWindow().getScaledHeight() / 2;
        int textureWidth = 128;
        int textureHeight = 160;


        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x - (textureWidth/2), y - (textureHeight/2),0,0,textureWidth,textureHeight,textureWidth,textureHeight);

        super.render(matrices, mouseX, mouseY, delta);
    }

}
