package net.notafreak.betterdeath.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.notafreak.betterdeath.BetterDeath;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
        new ResourceLocation(BetterDeath.MODID, "main")
    )
    .serverAcceptedVersions((version) -> true)
    .clientAcceptedVersions((version) -> true)
    .networkProtocolVersion(() -> PROTOCOL_VERSION)
    .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(S2CdeathNotifyPacket.class, 0, NetworkDirection.PLAY_TO_CLIENT)
            .encoder(S2CdeathNotifyPacket::encode)
            .decoder(S2CdeathNotifyPacket::new)
            .consumerMainThread(S2CdeathNotifyPacket::handle)
            .add();
        //    .encoder(S2CdeathNotifyPacket::encode, PacketDistributor.PLAYER);
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(),msg);
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}
