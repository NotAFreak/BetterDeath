package net.notafreak.betterdeath;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import net.notafreak.betterdeath.network.ServerPacketHandler;

public class ServerDeathScreenHandler {
    public static final Map<String, AffectedPlayerData> affectedPlayers = new HashMap<>();

    // This is only called by the server
    public static void onPlayerTick(ServerPlayerEntity player) {
        if(affectedPlayers.size() == 0) return;
        String username = player.getName().getString();
        AffectedPlayerData data = affectedPlayers.get(username);
        if(data == null) return;

        // Force the player to not be able to move
        if(data.respawnPos != null) {
            player.teleport(data.respawnPos.x, data.respawnPos.y, data.respawnPos.z);
        }
        data.deathScreenTimer--;
        if (data.deathScreenTimer <= 0) {
            player.changeGameMode(data.previousGameType);
            BetterDeath.LOGGER.info("Reset to previous gamemode (" + data.previousGameType + ")");
            affectedPlayers.remove(username);
        }
    }

    public static void Trigger(ServerPlayerEntity player) {
        AffectedPlayerData data = new AffectedPlayerData(player.interactionManager.getGameMode());
        data.respawnPos = player.getSpawnPointPosition().toCenterPos(); // This is under the idea that this code gets called AFTER the player dies
        data.deathScreenTimer = CommonModConfig.config.deathScreenDuration;
        affectedPlayers.put(player.getName().getString(), data);
        player.changeGameMode(GameMode.SPECTATOR);
        ServerPacketHandler.sendDeathNotify(player, CommonModConfig.config.deathScreenDuration);
    }
}
