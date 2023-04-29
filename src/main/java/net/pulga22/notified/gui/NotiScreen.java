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
import net.pulga22.notified.handler.Notification;
import net.pulga22.notified.Notified;
import net.pulga22.notified.networking.ModMessages;
import net.pulga22.notified.util.IEntityDataSaver;
import net.pulga22.notified.util.NotificationSaver;

import java.util.List;

public class NotiScreen extends Screen {

    private static final Identifier TEXTURE = new Identifier(Notified.MOD_ID, "textures/gui/notification_window.png");
    private TextWidget titleText;
    private TextWidget currentIndexText;
    private MultilineTextWidget messageText;
    private CustomButtonWidget prevMsg;
    private CustomButtonWidget nxtMsg;
    private int currentIndex;

    private final List<Notification> notifications;
    private final int newReadColor;
    private final boolean newRead;

    protected NotiScreen(Text title, PlayerEntity player) {
        super(title);
        //Check if the player have a new notification
        newRead = !((IEntityDataSaver) player).getPersistentData().getBoolean("read");

        //Send packet to server-side informing that the player has read the notification
        ClientPlayNetworking.send(ModMessages.READ_NOTIFICATION, PacketByteBufs.empty());
        ((IEntityDataSaver) player).getPersistentData().putBoolean("read", true);

        //Get notifications from the client-side notifications file
        NotificationSaver saver = Notified.getInstance().getConfig();
        this.notifications = saver.getConfig().getNotifications();
        this.newReadColor = saver.getConfig().parseColor();

        this.currentIndex = 0;
    }

    @Override
    protected void init() {
        //Close button
        CustomButtonWidget closeButton = CustomButtonWidget.builder
            (Text.translatable("notified.notifications_screen.close"), (onPress) -> {
                this.close();
            }).dimensions((int)this.width/2 - 24,(int)this.height/2+55,50,20).build();
        this.addDrawableChild(closeButton);

        //Previous message button
        this.prevMsg = CustomButtonWidget.builder(Text.literal("◀"), (onPress) -> {
            changeTextMessages("left");
        }).dimensions((int)this.width/2 - 45,(int)this.height/2+31,20,20).build();
        this.addDrawableChild(this.prevMsg);

        //Next message button
        this.nxtMsg = CustomButtonWidget.builder(Text.literal("▶"), (onPress) -> {
            changeTextMessages("right");
        }).dimensions((int)this.width/2 + 27,(int)this.height/2+31,20,20).build();
        this.addDrawableChild(this.nxtMsg);

        //Title
        this.titleText = new TextWidget(this.width/2 - 25, this.height/2 - 51 , 
            50, 20, Text.literal(new String()), this.textRenderer);
        this.titleText.alignCenter();
        this.addDrawableChild(this.titleText);

        //Message
        this.messageText = new MultilineTextWidget(0, 0,
            Text.literal(new String()), this.textRenderer);
        this.messageText.setCentered(true);
        this.messageText.setMaxWidth(80);
        this.messageText.setMaxRows(5);
        centerMessageText();
        this.addDrawableChild(this.messageText);

        //Current index
        this.currentIndexText = new TextWidget(this.width/2 - 24, this.height/2 + 32,
            50, 20, Text.literal("0/0"), this.textRenderer);
        this.currentIndexText.alignCenter();
        this.addDrawableChild(this.currentIndexText);

        //If the message is new, set gold color
        if (this.newRead){
            this.titleText.setTextColor(this.newReadColor);
            this.messageText.setTextColor(this.newReadColor);
        }
        //Since always we appear in the first message, disable previous message button
        this.prevMsg.active = false;

        //If we only 1 message or none, disable next message button
        if (this.notifications.size() <= 1) this.nxtMsg.active = false;

        //Handle if we don't have any messages
        if (this.notifications.isEmpty()){
            this.titleText.setMessage(Text.literal(" "));
            this.messageText.setMessage(Text.literal("No messages"));
            this.currentIndexText.setMessage(Text.literal("0/0"));
            centerMessageText();
        } else {
            //If we have messages, initialize the message and text widgets
            changeTextWidgetsMessages(currentIndex);
            setCurrentIndexText();
        }
    }

    //Center the message text widget with the new text
    private void centerMessageText(){
        this.messageText.setPosition(this.width/2 - this.messageText.getWidth() / 2, this.height/2 - 24);
    }

    //Update current index text widget
    private void setCurrentIndexText(){
        this.currentIndexText.setMessage(Text.literal((this.currentIndex + 1)+"/"+this.notifications.size()));
    }

    //Change message
    private void changeTextMessages(String side){
        //Move to left (next)
        if (side.equals("left") && this.currentIndex > 0){
            this.nxtMsg.active = true;
            this.currentIndex--;
            changeTextWidgetsMessages(this.currentIndex);
            if (this.currentIndex == 0){
                this.prevMsg.active = false;
                if (this.newRead){
                    this.titleText.setTextColor(this.newReadColor);
                    this.messageText.setTextColor(this.newReadColor);
                }
            }
        //Move to right (previous)
        } else if (side.equals("right") && this.currentIndex < this.notifications.size() - 1) {
            this.prevMsg.active = true;
            this.currentIndex++;
            changeTextWidgetsMessages(this.currentIndex);
            this.titleText.setTextColor(0xffffff);
            this.messageText.setTextColor(0xffffff);

            if (this.currentIndex == this.notifications.size() - 1)
                this.nxtMsg.active = false;
        }
        //Update index
        setCurrentIndexText();
    }

    //Change title and message by the index
    private void changeTextWidgetsMessages(int index){
        Notification notification = this.notifications.get(index);
            this.messageText.setMessage(Text.literal(notification.getMessage()));
            this.titleText.setMessage(Text.literal(notification.getTitle()));
        centerMessageText();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int x = (int) client.getWindow().getScaledWidth() / 2;
        int y = (int) client.getWindow().getScaledHeight() / 2;
        int textureWidth = 128;
        int textureHeight = 184;


        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x - (textureWidth/2), y - (textureHeight/2),0,0,
            textureWidth,textureHeight,textureWidth,textureHeight);

        super.render(matrices, mouseX, mouseY, delta);
    }
}
