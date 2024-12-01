package net.notafreak.betterdeath.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec SPEC = null;

	public static ForgeConfigSpec.ConfigValue<Integer> deathScreenDuration;

	static {
		BUILDER.push("General Settings");
		deathScreenDuration = BUILDER
			.comment("Duration of the death screen in ticks (20 ticks = 1 second)")
			.defineInRange("deathScreenDuration", 100, 0, 6000);
			
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
