package net.notafreak.betterdeath;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class ClientDeathScreenHandler {
    public static Boolean deathScreenActive = false;
    public static float deathScreenRemainingTime = 0;

    public static void Register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            Render(context, tickDelta);
        });
        ClientTickEvents.END_CLIENT_TICK.register((MinecraftClient client) -> {
            ClientTick(client);
        });
    }

    private static void ClientTick(MinecraftClient client) {
        if(!deathScreenActive) return;

        deathScreenRemainingTime--;
        if(deathScreenRemainingTime <= 0) {
            deathScreenActive = false;
            deathScreenRemainingTime = 0;
        }
    }

    private static void Render(DrawContext context, float tickDelta) {
        if(!deathScreenActive) return;

        int color = Utils.ConstructColorHex(
            ClientModConfig.config.deathScreenR, 
            ClientModConfig.config.deathScreenG, 
            ClientModConfig.config.deathScreenB, 
            255
        );
        context.fill(0, 0, context.getScaledWindowWidth(), context.getScaledWindowHeight(), color);
    }

    public static void Trigger(float duration) {
        deathScreenRemainingTime = duration;
        deathScreenActive = true;
    }
}
