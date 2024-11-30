package net.notafreak.betterdeath;

import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

@Mod.EventBusSubscriber(modid = "betterdeath", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlackScreenHandler {

    private static int blackScreenTimer = 0; // Ticks remaining for black screen
    private static final int BLACK_SCREEN_DURATION = 100; // Configurable duration in ticks

    @SubscribeEvent
    public static void onPlayerRespawn(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide && event.player.isAlive() && blackScreenTimer > 0) {
            blackScreenTimer--;
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (blackScreenTimer > 0) { // Your condition for rendering the overlay
            GuiGraphics guiGraphics = event.getGuiGraphics();
            RenderSystem.enableBlend(); // Enable blending for the overlay
            RenderSystem.defaultBlendFunc(); // Use default blend mode
            
            // Draw a black rectangle across the entire screen
            int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            guiGraphics.fill(0, 0, screenWidth, screenHeight, 0xAA000000); // 0xAA for semi-transparency
            
            RenderSystem.disableBlend(); // Disable blending when done
        }
    }

    public static void triggerBlackScreen() {
        blackScreenTimer = BLACK_SCREEN_DURATION;
    }
}
