package net.notafreak.betterdeath.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.notafreak.betterdeath.BetterDeath;

@EventBusSubscriber(modid = BetterDeath.MODID)
public record S2CdeathNotifyPacket(float ScreenLength) implements CustomPacketPayload {
    public static final String PROTOCOL_VERSION = "1";
    public static final CustomPacketPayload.Type<S2CdeathNotifyPacket> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(BetterDeath.MODID, "main")
            );

    public static final StreamCodec<ByteBuf, S2CdeathNotifyPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            S2CdeathNotifyPacket::ScreenLength,
            S2CdeathNotifyPacket::new
    );

    @SubscribeEvent // on the mod event bus
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION)
                .executesOn(HandlerThread.MAIN);
        registrar.playToClient(
                S2CdeathNotifyPacket.TYPE,
                S2CdeathNotifyPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        PacketHandler::handleOnClient,
                        PacketHandler::handleOnServer
                )
        );
        BetterDeath.LOGGER.info("Registered packet!");
    }
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
 