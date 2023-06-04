package net.pulga22.notified.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pulga22.notified.networking.ModMessages;

import java.util.Collection;
import java.util.Objects;


public class SendNotification {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("notification").requires((source) -> source.hasPermissionLevel(2))
                .then(
                    CommandManager.literal("send").executes(SendNotification::run)
                )
                .then(
                    CommandManager.literal("clear").executes(SendNotification::clear)
                )
        );
    }

    private static int run(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        //send notification screen
        ServerPlayerEntity player = source.getSource().isExecutedByPlayer() ? source.getSource().getPlayer() : null;
        if (player != null) {
            ServerPlayNetworking.send(player, ModMessages.SEND_NOTIFICATION_SCREEN, PacketByteBufs.empty());
        } else {
            throw new SimpleCommandExceptionType(Text.literal("Player is null. Maybe this command is being called by a non player.")).create();
        }
        return 1;

    }

    private static int clear(CommandContext<ServerCommandSource> source) {
        MinecraftServer server = source.getSource().getServer();
        Collection<ServerPlayerEntity> allPlayers = PlayerLookup.all(server);
        allPlayers.forEach((player) -> {
            ServerPlayNetworking.send(player, ModMessages.CLEAR_NOTIFICATIONS, PacketByteBufs.empty());
        });

        Objects.requireNonNull(source.getSource().getPlayer()).sendMessage(Text.translatable
                ("notified.clear_notifications")
                .fillStyle(Style.EMPTY.withColor(Formatting.GRAY))
        );

        return 1;
    }



}
