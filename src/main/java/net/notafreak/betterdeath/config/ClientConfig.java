package net.notafreak.betterdeath.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.notafreak.betterdeath.BetterDeath;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = BetterDeath.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
