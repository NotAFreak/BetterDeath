package net.notafreak.betterdeath;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
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
    
    public static final Map<String, AffectedPlayerData> affectedPlayers = new HashMap<>(); // Username, data

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        String username = event.player.getName().getString();
        AffectedPlayerData data = affectedPlayers.get(username);
    
        if (data != null) {

            // Force the player to not be able to move
            if(!event.player.level().isClientSide()) {
                ServerPlayer player = ((ServerPlayer)event.player);
                player.setDeltaMovement(0, 0, 0);
                if(affectedPlayers.get(username).respawnPos != null) {
                    player.teleportTo(affectedPlayers.get(username).respawnPos.x, affectedPlayers.get(username).respawnPos.y, affectedPlayers.get(username).respawnPos.z);
                }
                player.setXRot(0);
                player.setYRot(player.getRespawnAngle());
                player.hurtMarked = true;
            }

            if (data.deathScreenTimer <= 0) {
                ServerPlayer player = null;
                if(!event.player.level().isClientSide()) {
                    player = ((ServerPlayer)event.player);

                    // Switch the player back to their previous gamemode
                    player.setGameMode(affectedPlayers.get(username).previousGameType);
                    BetterDeath.LOGGER.info("Reset to previous gamemode (" + affectedPlayers.get(username).previousGameType + ")");
                    affectedPlayers.remove(username);
                } else {
                    BetterDeath.LOGGER.info("death screen timer up but we're on the client");
                }

            }

            data.deathScreenTimer--;
        }
    }
    

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return; // Ensure the player instance exists

        String username = mc.player.getName().getString();
        AffectedPlayerData data = affectedPlayers.get(username);
        if (data != null) {
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
                ConstructColorHex(
                    ClientConfig.deathScreenR.get(),
                    ClientConfig.deathScreenG.get(),
                    ClientConfig.deathScreenB.get(),
                    255
                )
            );

            // Reset posing
            guiGraphics.pose().translate(0, 0, -9000);
            RenderSystem.disableBlend();

            // Pause sounds
            mc.getSoundManager().pause();
        } else {
            // Resume sounds if the death screen is not active
            mc.getSoundManager().resume();
        }
    }

    //get the position the player should spawn at and set them to spectator mode
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        affectedPlayers.get(event.getEntity().getName().getString()).respawnPos = event.getEntity().position();

        event.getEntity().getServer().getPlayerList().getPlayer(event.getEntity().getUUID()).setGameMode(GameType.SPECTATOR);
    }

    public static int ConstructColorHex(int R, int G, int B, int A) {
        R = Math.max(0, Math.min(255, R));
        G = Math.max(0, Math.min(255, G));
        B = Math.max(0, Math.min(255, B));
        A = Math.max(0, Math.min(255, A));
        return (A << 24) | (R << 16) | (G << 8) | B;
    }

    // Used for server
    public static void triggerDeathScreen(ServerPlayer player) {
        // Switch the player to spectator here
        GameType prevGameType = player.gameMode.getGameModeForPlayer();
        
        affectedPlayers.put(player.getName().getString(), new AffectedPlayerData(prevGameType));
        PacketHandler.sendToPlayer(new S2CdeathNotifyPacket(CommonConfig.deathScreenDuration.get()), player);
    }

    // Used for client
    public static void triggerDeathScreen(float duration) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) {
            return;
        }

        LocalPlayer lPlayer = mc.player;
        ServerPlayer sPlayer = lPlayer.getServer().getPlayerList().getPlayer(lPlayer.getUUID());
        GameType prevGameType = sPlayer.gameMode.getGameModeForPlayer();
        String username = lPlayer.getName().getString();
        AffectedPlayerData data = new AffectedPlayerData(prevGameType);
        
        data.deathScreenTimer = (int) duration;
        affectedPlayers.put(username, data);
    }
}