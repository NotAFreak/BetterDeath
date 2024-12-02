package net.notafreak.betterdeath;

import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.notafreak.betterdeath.config.ClientConfig;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;

@Mod.EventBusSubscriber(modid = "betterdeath", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DeathScreenHandler {

    public static final Map<String, AffectedPlayerData> affectedPlayers = new HashMap<>(); // Username, data

    @SubscribeEvent
    public static void onPlayerRespawn(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide()) return; // Only process on the server side
        String username = event.player.getName().getString();
        AffectedPlayerData data = affectedPlayers.get(username);
        if (data != null) {
            data.deathScreenTimer--;
            if (data.deathScreenTimer <= 0) {
                affectedPlayers.remove(username); // Remove the player if timer ends
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return; // Ensure the player instance exists

        String username = mc.player.getName().getString();
        AffectedPlayerData data = affectedPlayers.get(username);
        if (data != null && data.deathScreenTimer > 0) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            // Draw a black rectangle across the entire screen
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();
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

    public static int ConstructColorHex(int R, int G, int B, int A) {
        R = Math.max(0, Math.min(255, R));
        G = Math.max(0, Math.min(255, G));
        B = Math.max(0, Math.min(255, B));
        A = Math.max(0, Math.min(255, A));
        return (A << 24) | (R << 16) | (G << 8) | B;
    }

    public static void triggerDeathScreen(ServerPlayer player) {
        affectedPlayers.put(player.getName().getString(), new AffectedPlayerData());
    }

    public static class AffectedPlayerData {
        public int deathScreenTimer = 200; // Adjust this for desired screen duration (200 ticks = 10 seconds)
    }
}
