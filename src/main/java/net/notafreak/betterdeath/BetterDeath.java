package net.notafreak.betterdeath;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.notafreak.betterdeath.network.ServerPacketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterDeath implements ModInitializer {
	public static final String MOD_ID = "betterdeath";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommonModConfig.Register();
		CommonModConfig.Load();
		ServerPacketHandler.RegisterC2Spackets();
		
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if(!(entity instanceof ServerPlayerEntity)) {
				if((entity instanceof PlayerEntity)) {
					LOGGER.warn("Player died but wasn't an instance of a server player entity!");
					return;
				}
				return;
			}
			ServerPlayerEntity player = (ServerPlayerEntity)entity;
			ServerPacketHandler.sendDeathNotify(player, CommonModConfig.config.deathScreenDuration);
		});
	}
}