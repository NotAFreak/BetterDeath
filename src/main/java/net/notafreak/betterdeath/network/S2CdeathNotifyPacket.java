package net.notafreak.betterdeath.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.notafreak.betterdeath.BetterDeath;
import net.notafreak.betterdeath.DeathScreenHandler;
public class S2CdeathNotifyPacket {
    private final float ScreenLength;

    public S2CdeathNotifyPacket(float ScreenLength) {
        this.ScreenLength = ScreenLength;
    }
    public S2CdeathNotifyPacket(FriendlyByteBuf buffer) {
        this(buffer.readFloat());
    }
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.ScreenLength);
    }
    // This is handled on the client, when the server sends this packet to the client
    public void handle(Supplier<NetworkEvent.Context> context) {
        BetterDeath.LOGGER.debug("Client received that it should commit die!");
        context.get().enqueueWork(() -> {
            DeathScreenHandler.triggerDeathScreenClient(ScreenLength);
        });
        context.get().setPacketHandled(true);
    }
}
 