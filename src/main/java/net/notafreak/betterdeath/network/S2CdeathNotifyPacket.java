package net.notafreak.betterdeath.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketByteBuf;
import net.notafreak.betterdeath.BetterDeath;

public class S2CdeathNotifyPacket {
    private final float ScreenLength;

    public S2CdeathNotifyPacket(float ScreenLength) {
        this.ScreenLength = ScreenLength;
    }
    // i only guessed that FriendlyByteBuf = PacketByteBuf, i was writing this code offline
    public S2CdeathNotifyPacket(PacketByteBuf buffer) {
        this(buffer.readFloat());
    }
    public void Encode(PacketByteBuf buffer) {
        buffer.writeFloat(this.ScreenLength);
    }
    // Only handled on the client
    public void Handle() {
        BetterDeath.LOGGER.debug("Client received that it should commit die!");
    }
}
