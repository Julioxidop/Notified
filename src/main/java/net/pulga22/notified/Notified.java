package net.pulga22.notified;

import net.fabricmc.api.ModInitializer;

import net.pulga22.notified.command.CommandRegistries;
import net.pulga22.notified.networking.ModMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Notified implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("notified");
	public static final String MOD_ID = "notified";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModMessages.registerC2SPackets();
		CommandRegistries.register();
		LOGGER.info("Hello Fabric world!");
	}
}