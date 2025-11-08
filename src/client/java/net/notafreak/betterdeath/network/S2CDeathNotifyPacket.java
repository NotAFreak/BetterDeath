package net.notafreak.betterdeath.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.notafreak.betterdeath.ClientDeathScreenHandler;


public final class S2CDeathNotifyPacket {
    private final float screenLength;
    public S2CDeathNotifyPacket(float screenLength) {
        this.screenLength = screenLength;
    }
    public S2CDeathNotifyPacket(PacketByteBuf buf) {
        this(buf.readFloat());
    }
    public float screenLength() {
        return screenLength;
    }
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.screenLength);
    }

    // Received by the client
    public static void receive(
        MinecraftClient client, 
        ClientPlayNetworkHandler handler, 
        PacketByteBuf buf, 
        PacketSender sender
    ) {
        ClientDeathScreenHandler.Trigger(buf.readFloat());
    }
}