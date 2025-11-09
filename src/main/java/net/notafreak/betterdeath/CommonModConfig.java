package net.notafreak.betterdeath;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

@Config(name = "betterdeath-common")
public class CommonModConfig implements ConfigData {
    public static CommonModConfig config;

    // In Ticks
    int deathScreenDuration = 60;
    boolean forceImmediateRespawn = true;

    public static void Register() {
        AutoConfig.register(CommonModConfig.class, Toml4jConfigSerializer::new);
    }
    public static CommonModConfig Load() {
        config = AutoConfig.getConfigHolder(CommonModConfig.class).getConfig();
        return config;
    }
}
