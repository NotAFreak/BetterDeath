package net.notafreak.betterdeath.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec SPEC = null;

	public static ForgeConfigSpec.ConfigValue<Integer> deathScreenDuration;
	public static ForgeConfigSpec.ConfigValue<Boolean> forceImmediateRespawn;

	static {
		BUILDER.push("General Settings");
		deathScreenDuration = BUILDER
			.comment("Duration of the death screen in ticks (20 ticks = 1 second). Defaults to 3 seconds.")
			.defineInRange("deathScreenDuration", 60, 0, 6000);
		
		forceImmediateRespawn = BUILDER
			.comment("Whether or not to force the gamerule to respawn immediately")
			.define("forceImmediateRespawn", true);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
