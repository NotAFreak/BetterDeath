package net.notafreak.betterdeath;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

public class AffectedPlayerData {
    public int deathScreenTimer = 0; // In game ticks
    public GameMode previousGameType = GameMode.SURVIVAL;
    public Vec3d respawnPos;
    
    public AffectedPlayerData(GameMode prevGameType) {
        if(prevGameType == null) {
            // Default
            previousGameType = GameMode.SURVIVAL;
        } else {
            previousGameType = prevGameType;
        }
        // deathScreenTimer = CommonConfig.deathScreenDuration.get();
    }
}
