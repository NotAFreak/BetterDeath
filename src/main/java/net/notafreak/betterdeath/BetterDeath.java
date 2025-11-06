package net.notafreak.betterdeath;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.player.PlayerEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterDeath implements ModInitializer {
	public static final String MOD_ID = "betterdeath";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommonModConfig.Register();
		CommonModConfig.Load();

		LOGGER.info("Hello Fabric world!");
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if(!(entity instanceof PlayerEntity)) return;
			PlayerEntity player = (PlayerEntity)entity;
			LOGGER.info(player.getEntityName() + " has died!");
		});
	}
}