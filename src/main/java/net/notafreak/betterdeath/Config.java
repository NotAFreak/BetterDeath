package net.notafreak.betterdeath;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = BetterDeath.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
        public static ForgeConfigSpec.IntValue blackScreenDuration;

        public static void init(ForgeConfigSpec.Builder builder) {
            builder.push("General Settings");
            blackScreenDuration = builder
                    .comment("Duration of the black screen in ticks (20 ticks = 1 second)")
                    .defineInRange("blackScreenDuration", 100, 0, 6000);
            builder.pop();
        }
}
