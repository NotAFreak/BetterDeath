package net.notafreak.betterdeath;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

@Config(name = "betterdeath-client")
public class ClientModConfig implements ConfigData {
    public static ClientModConfig config;

    int deathScreenR = 0;
    int deathScreenG = 0;
    int deathScreenB = 0;

    public static void Register() {
        AutoConfig.register(ClientModConfig.class, Toml4jConfigSerializer::new);
    }
    public static ClientModConfig Load() {
        config = AutoConfig.getConfigHolder(ClientModConfig.class).getConfig();
        return config;
    }
}
