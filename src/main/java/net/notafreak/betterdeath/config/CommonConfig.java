package net.notafreak.betterdeath.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.notafreak.betterdeath.BetterDeath;

public class CommonConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec SPEC = null;

	public static ForgeConfigSpec.ConfigValue<Integer> blackScreenDuration;

	static {
		BUILDER.push("General Settings");
		blackScreenDuration = BUILDER
			.comment("Duration of the black screen in ticks (20 ticks = 1 second)")
			.defineInRange("blackScreenDuration", 100, 0, 6000);
			
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
