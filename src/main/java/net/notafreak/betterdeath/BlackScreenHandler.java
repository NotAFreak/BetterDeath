package net.notafreak.betterdeath;

import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.notafreak.betterdeath.config.ClientConfig;
import net.notafreak.betterdeath.config.CommonConfig;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;

@Mod.EventBusSubscriber(modid = "betterdeath", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlackScreenHandler {

    private static int blackScreenTimer = 0; // Ticks remaining

    @SubscribeEvent
    public static void onPlayerRespawn(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide && event.player.isAlive() && blackScreenTimer > 0) {
            blackScreenTimer--;
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (blackScreenTimer > 0) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            
            // Draw a black rectangle across the entire screen
            int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            guiGraphics.fill(0, 0, screenWidth, screenHeight, ConstructColorHex(ClientConfig.deathScreenR.get(), ClientConfig.deathScreenG.get(), ClientConfig.deathScreenB.get(), 255)); // 0xAA for semi-transparency
            
            
            RenderSystem.disableBlend();
        }
    }

    // Construct color hexcode following ARGB
    public static int ConstructColorHex(int R, int G, int B, int A) {
        // guys this is so fucked
        R = Math.max(0, Math.min(255, R));
        G = Math.max(0, Math.min(255, G));
        B = Math.max(0, Math.min(255, B));
        A = Math.max(0, Math.min(255, A));

        // Combine the ARGB components into a single integer
        return (A << 24) | (R << 16) | (G << 8) | B;
    } 

    public static void triggerBlackScreen(ServerPlayer player) {
        blackScreenTimer = CommonConfig.blackScreenDuration.get();
    }
}
