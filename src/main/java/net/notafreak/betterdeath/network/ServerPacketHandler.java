package net.notafreak.betterdeath.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.notafreak.betterdeath.BetterDeath;

public class ServerPacketHandler {
	public static final Identifier DEATH_ID = new Identifier(BetterDeath.MOD_ID, "death");
	
	public static void RegisterC2Spackets() {

	}

	public static void sendDeathNotify(ServerPlayerEntity player, float screenLength) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeFloat(screenLength);
		ServerPlayNetworking.send(player, DEATH_ID, buf);
        BetterDeath.LOGGER.info("Sent packet to player: " + player.getName().getString());
	}
}
