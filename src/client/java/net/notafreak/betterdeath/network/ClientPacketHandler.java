package net.notafreak.betterdeath.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.notafreak.betterdeath.BetterDeath;

public class ClientPacketHandler {
	public static final Identifier DEATH_ID = new Identifier(BetterDeath.MOD_ID, "death");

	public static void RegisterS2Cpackets() {
		ClientPlayNetworking.registerGlobalReceiver(DEATH_ID, S2CDeathNotifyPacket::receive);
	}
}
