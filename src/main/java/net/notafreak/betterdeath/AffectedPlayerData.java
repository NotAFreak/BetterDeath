package net.notafreak.betterdeath;


import net.notafreak.betterdeath.config.CommonConfig;

public class AffectedPlayerData {
    public int deathScreenTimer = 0; // In game ticks
    
    public AffectedPlayerData() {
        deathScreenTimer = CommonConfig.deathScreenDuration.get();
    }
}
