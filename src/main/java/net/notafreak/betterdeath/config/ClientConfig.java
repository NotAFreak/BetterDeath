package net.notafreak.betterdeath.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec SPEC = null;

	public static ForgeConfigSpec.ConfigValue<Integer> deathScreenR;
	public static ForgeConfigSpec.ConfigValue<Integer> deathScreenG;
	public static ForgeConfigSpec.ConfigValue<Integer> deathScreenB;

	static {
		BUILDER.push("General Settings");
		
		deathScreenR = BUILDER.comment("Red value of the death overlay").defineInRange("Death screen R", 0, 0, 255);
		deathScreenG = BUILDER.comment("Green value of the death overlay").defineInRange("Death screen G", 0, 0, 255);
		deathScreenB = BUILDER.comment("Blue value of the death overlay").defineInRange("Death screen B", 0, 0, 255);
			
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
