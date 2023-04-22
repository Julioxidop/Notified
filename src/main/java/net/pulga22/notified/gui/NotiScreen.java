package net.pulga22.notified.gui;

import com.mojang.blaze3d.systems.RenderSystem;
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
import net.pulga22.notified.util.IEntityDataSaver;

public class NotiScreen extends Screen {

    private static final Identifier TEXTURE = new Identifier(Notified.MOD_ID, "textures/gui/notification_window.png");
    private TextWidget titleText;
    private MultilineTextWidget messageText;
    private String title;
    private String message;

    protected NotiScreen(Text title, PlayerEntity player) {
        super(title);
        this.title = ((IEntityDataSaver) player).getPersistentData().getString("title");
        this.message = ((IEntityDataSaver) player).getPersistentData().getString("message");
    }

    @Override
    protected void init() {
        CustomButtonWidget button = CustomButtonWidget.builder(Text.literal("Close"), (onPress) -> {
            this.close();
        }).dimensions((int)this.width/2 - 25,(int)this.height/2+42,50,20).build();
        this.addDrawableChild(button);

        this.titleText = new TextWidget(this.width/2 - 25, this.height/2 - 39, 50, 20, Text.literal(this.title), this.textRenderer);
        this.titleText.alignCenter();
        this.addDrawableChild(this.titleText);

        this.messageText = new MultilineTextWidget(0, 0, Text.literal(this.message), this.textRenderer);
        this.messageText.setCentered(true);
        this.messageText.setMaxWidth(80);
        this.messageText.setMaxRows(5);
        this.messageText.setPosition(this.width/2 - this.messageText.getWidth() / 2, this.height/2 - 12);
        this.addDrawableChild(this.messageText);
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
