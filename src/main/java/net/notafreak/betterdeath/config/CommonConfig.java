package net.notafreak.betterdeath.config;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.notafreak.betterdeath.BetterDeath;

public class CommonConfig {
	public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
	public static ModConfigSpec SPEC = null;

	public static ModConfigSpec.ConfigValue<Integer> deathScreenDuration;
	public static ModConfigSpec.ConfigValue<Boolean> forceImmediateRespawn;

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
