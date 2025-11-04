package net.notafreak.betterdeath;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.notafreak.betterdeath.config.ClientConfig;
import net.notafreak.betterdeath.config.CommonConfig;
import net.notafreak.betterdeath.network.PacketHandler;
import net.notafreak.betterdeath.network.S2CdeathNotifyPacket;

@Mod.EventBusSubscriber(modid = "betterdeath", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DeathScreenHandler {
    // Should only be used by the server/host
    public static final Map<String, AffectedPlayerData> affectedPlayers = new HashMap<>(); // Username, data

    // Should only be used by the client for rendering. Again, ONLY BY THE CLIENT FOR RENDERING.
    public static Boolean deathScreenActive = false;
    public static float deathScreenRemainingTime = 0;

    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        String username = event.player.getName().getString();
        AffectedPlayerData data = affectedPlayers.get(username);
        if(data == null) return;

        // Force the player to not be able to move
        if(!event.player.level().isClientSide()) {
            ServerPlayer player = ((ServerPlayer)event.player);
            player.setDeltaMovement(0, 0, 0);
            if(data.respawnPos != null) {
                player.teleportTo(data.respawnPos.x, data.respawnPos.y, data.respawnPos.z);
            }
            player.setXRot(0);
            player.setYRot(player.getRespawnAngle());
        }
        if (data.deathScreenTimer <= 0) {
            ServerPlayer player = null;
            if(!event.player.level().isClientSide()) {
                player = ((ServerPlayer)event.player);
                // Switch the player back to their previous gamemode
                player.setGameMode(data.previousGameType);
                BetterDeath.LOGGER.info("Reset to previous gamemode (" + data.previousGameType + ")");
                affectedPlayers.remove(username);
            } else {
                BetterDeath.LOGGER.info("death screen timer up but we're on the client");
            }
        }

        data.deathScreenTimer--;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if(!deathScreenActive) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return; // Ensure the player instance exists

        if (deathScreenRemainingTime > 0) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            // Draw a black rectangle across the entire screen
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();
            // Try and render over everything
            guiGraphics.pose().translate(0, 0, 9000);
            guiGraphics.fill(
                0, 0,
                screenWidth, screenHeight,
                Utils.ConstructColorHex(
                    ClientConfig.deathScreenR.get(),
                    ClientConfig.deathScreenG.get(),
                    ClientConfig.deathScreenB.get(),
                    255
                )
            );

            // Reset posing
            guiGraphics.pose().translate(0, 0, -9000);
            RenderSystem.disableBlend();
            mc.getSoundManager().pause();
            // Divide by 20 to convert from frame time to ticks time
            deathScreenRemainingTime -= (event.getPartialTick() / 20.0f);
        } else {
            // Resume sounds if the death screen is not active
            mc.getSoundManager().resume();
            deathScreenRemainingTime = 0;
            deathScreenActive = false;
        }
    }

    //get the position the player should spawn at and set them to spectator mode
    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        String playerName = event.getEntity().getName().getString();
        UUID playerUuid = event.getEntity().getUUID();

        if(!affectedPlayers.containsKey(playerName)) {
            BetterDeath.LOGGER.info("Player respawn without being dead: " + playerName);
            return;
        }
        affectedPlayers.get(playerName).respawnPos = event.getEntity().position();
        // .get.get.get i love java standards
        event.getEntity().getServer().getPlayerList().getPlayer(playerUuid).setGameMode(GameType.SPECTATOR);
    }

    // Used by server / host
    // Switch the player to spectator here, and notify the client how to handle the death
    public static void triggerDeathScreenServer(ServerPlayer player) {
        GameType prevGameType = player.gameMode.getGameModeForPlayer();        
        affectedPlayers.put(player.getName().getString(), new AffectedPlayerData(prevGameType));
        PacketHandler.sendToPlayer(new S2CdeathNotifyPacket(CommonConfig.deathScreenDuration.get()), player);
        BetterDeath.LOGGER.info("Sent death packet to player: " + player.getName());
    }

    @OnlyIn(Dist.CLIENT)
    public static void triggerDeathScreenClient(float duration) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) return;
        if(deathScreenActive) return;

        deathScreenActive = true;
        deathScreenRemainingTime = duration;
        BetterDeath.LOGGER.info("Client triggered death screen with duration: " + duration);
    }
}