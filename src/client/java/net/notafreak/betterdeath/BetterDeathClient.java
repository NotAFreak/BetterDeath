package net.notafreak.betterdeath;

import net.fabricmc.api.ClientModInitializer;
import net.notafreak.betterdeath.network.ClientPacketHandler;

public class BetterDeathClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientModConfig.Register();
		ClientModConfig.Load();

		ClientDeathScreenHandler.Register();
		ClientPacketHandler.RegisterS2Cpackets();
	}
}