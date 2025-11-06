package net.notafreak.betterdeath;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.*;
import net.minecraft.entity.player.PlayerEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterDeath implements ModInitializer {
	public static final String MOD_ID = "betterdeath";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if(!(entity instanceof PlayerEntity)) return;
			PlayerEntity player = (PlayerEntity)entity;
			
		});
	}
}