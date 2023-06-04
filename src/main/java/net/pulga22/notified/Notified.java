package net.pulga22.notified;

import net.fabricmc.api.ModInitializer;

import net.pulga22.notified.command.CommandRegistries;
import net.pulga22.notified.networking.ModMessages;
import net.pulga22.notified.util.NotificationSaver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

import java.util.Random;

public class Notified implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("notified");
	public static final String MOD_ID = "notified";
	private static final String[] MESSAGES = {
			"You are ready to been notified!",
			"You have 42 new notifications...",
			"Notify the world you are amazing!",
			"I see notifications everywhere..."
	};

	//Notified config file
    @Getter private NotificationSaver config;
	@Getter private static Notified instance;

	@Override
	public void onInitialize() {
		instance = this;
		this.config = new NotificationSaver();

		// Register basic packets
		ModMessages.registerC2SPackets();
		CommandRegistries.register();
		LOGGER.info("[NOTIFIED] " + MESSAGES[getRandom()]);
	}

	private int getRandom(){
		return new Random().nextInt(Notified.MESSAGES.length);
	}
}

