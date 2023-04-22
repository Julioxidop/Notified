package net.pulga22.notified.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pulga22.notified.Notified;
import net.pulga22.notified.gui.widgets.CustomButtonWidget;
import net.pulga22.notified.networking.ModMessages;

public class SendNotiScreen extends Screen {

    private static final Identifier TEXTURE = new Identifier(Notified.MOD_ID, "textures/gui/send_notification_window.png");
    protected TextFieldWidget notificationTitleField;
    protected TextFieldWidget notificationMessageField;

    protected SendNotiScreen(Text title, PlayerEntity player) {
        super(title);
    }

    @Override
    protected void init() {
        int offset = -7;
        CustomButtonWidget buttonClose = CustomButtonWidget.builder(Text.literal("x"), (onPress) -> {
            this.close();
        }).dimensions((int)this.width/2 - 30 + offset,this.height/2 + 12,20,20).build();
        this.addDrawableChild(buttonClose);

        CustomButtonWidget buttonSend = CustomButtonWidget.builder(Text.literal("Send"), (onPress) -> {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(this.notificationTitleField.getText() + ";;;" + this.notificationMessageField.getText());
            ClientPlayNetworking.send(ModMessages.NOTIFICATION_SENT, buf);
            this.close();
        }).dimensions((int)this.width/2 - 5 + offset,this.height/2 + 12,50,20).build();
        this.addDrawableChild(buttonSend);

        this.notificationTitleField = new TextFieldWidget(this.textRenderer, this.width / 2 - 50, this.height/2 - 32, 100, 15, Text.literal("Title"));
        this.notificationTitleField.setMaxLength(16);
        this.addDrawableChild(this.notificationTitleField);

        this.notificationMessageField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height/2 - 32 + 22, 200, 15, Text.literal("Title"));
        this.notificationMessageField.setMaxLength(64);
        this.addDrawableChild(this.notificationMessageField);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int x = (int) client.getWindow().getScaledWidth() / 2;
        int y = (int) client.getWindow().getScaledHeight() / 2;
        int textureWidth = 256;
        int textureHeight = 100;


        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x - (textureWidth/2), y - (textureHeight/2),0,0,textureWidth,textureHeight,textureWidth,textureHeight);

        super.render(matrices, mouseX, mouseY, delta);
    }
}
