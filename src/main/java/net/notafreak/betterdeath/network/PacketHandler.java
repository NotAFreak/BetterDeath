package net.notafreak.betterdeath.network;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.notafreak.betterdeath.BetterDeath;
import net.notafreak.betterdeath.DeathScreenHandler;

public class PacketHandler {

    // Should be handled on the MAIN thread
    public static void handleOnClient(final S2CdeathNotifyPacket packet, final IPayloadContext context) {
        BetterDeath.LOGGER.debug("Client received that it should commit die!");
        DeathScreenHandler.triggerDeathScreenClient(packet.ScreenLength());
    }
    public static void handleOnServer(final S2CdeathNotifyPacket packet, final IPayloadContext context) {
        BetterDeath.LOGGER.debug("Server received death packet!?");
    }
}
