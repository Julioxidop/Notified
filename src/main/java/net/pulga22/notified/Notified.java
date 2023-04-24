package net.pulga22.notified;

import net.fabricmc.api.ModInitializer;

import net.pulga22.notified.command.CommandRegistries;
import net.pulga22.notified.networking.ModMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Notified implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("notified");
	public static final String MOD_ID = "notified";

	@Override
	public void onInitialize() {
		//Register packets
		ModMessages.registerC2SPackets();
		//Register commands
		CommandRegistries.register();
		LOGGER.info("You are going to be notified! :)");
	}

}

