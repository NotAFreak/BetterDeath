package net.notafreak.betterdeath;


import net.minecraft.world.level.GameType;
import net.notafreak.betterdeath.config.CommonConfig;

public class AffectedPlayerData {
    public int deathScreenTimer = 0; // In game ticks
    public GameType previousGameType = GameType.SURVIVAL; // Just default
    
    public AffectedPlayerData(GameType prevGameType) {
        if(prevGameType == null) {
            // Default
            previousGameType = GameType.SURVIVAL;
        } else {
            previousGameType = prevGameType;
        }
        deathScreenTimer = CommonConfig.deathScreenDuration.get();
    }
}
