package net.notafreak.betterdeath;

import net.fabricmc.api.ClientModInitializer;

public class BetterDeathClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientModConfig.Register();
		ClientModConfig.Load();

		ClientDeathScreenHandler.Register();
	}
}