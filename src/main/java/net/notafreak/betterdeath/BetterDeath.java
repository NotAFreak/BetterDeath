package net.notafreak.betterdeath;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.notafreak.betterdeath.network.ServerPacketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterDeath implements ModInitializer {
	public static final String MOD_ID = "betterdeath";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// man i love how clean fabric's initialization and general code is.
	@Override
	public void onInitialize() {
		CommonModConfig.Register();
		CommonModConfig.Load();
		ServerPacketHandler.RegisterC2Spackets();
		ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStart);		
		ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);
		ServerLivingEntityEvents.AFTER_DEATH.register(this::onDeath);
	}

	private void onServerStart(MinecraftServer server) {
		if(CommonModConfig.config.forceImmediateRespawn) {
			server.getGameRules().get(GameRules.DO_IMMEDIATE_RESPAWN).set(true, server);
		}
	}
	private void onServerTick(MinecraftServer server) {
		for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
			ServerDeathScreenHandler.onPlayerTick(player);
		}
	}
	private void onDeath(LivingEntity entity, DamageSource source) {
		if(!(entity instanceof ServerPlayerEntity)) {
			if((entity instanceof PlayerEntity)) {
				LOGGER.warn("Player died but wasn't an instance of a server player entity!");
				return;
			}
			return;
		}
		ServerDeathScreenHandler.Trigger((ServerPlayerEntity)entity);
	}
}