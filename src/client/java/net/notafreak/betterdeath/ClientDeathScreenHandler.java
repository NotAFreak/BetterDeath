package net.notafreak.betterdeath;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

public class ClientDeathScreenHandler {
    public static Boolean deathScreenActive = false;
    public static float deathScreenRemainingTime = 0;

    public static void Register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            Render(context, tickDelta);
        });
    }

    private static void Render(DrawContext context, float tickDelta) {
        if(!deathScreenActive) return;

        int color = Utils.ConstructColorHex(
            ClientModConfig.config.deathScreenR, 
            ClientModConfig.config.deathScreenG, 
            ClientModConfig.config.deathScreenB, 
            100
        );
        context.fill(0, 0, 100, 100, color);
        
        deathScreenRemainingTime -= tickDelta;
        if(deathScreenRemainingTime <= 0) {
            deathScreenActive = false;
            deathScreenRemainingTime = 0;
        }
    }
}
