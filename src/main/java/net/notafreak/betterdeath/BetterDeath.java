package net.notafreak.betterdeath;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.notafreak.betterdeath.config.ClientConfig;
import net.notafreak.betterdeath.config.CommonConfig;
import net.notafreak.betterdeath.network.PacketHandler;
import net.notafreak.betterdeath.network.S2CdeathNotifyPacket;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BetterDeath.MODID)
public class BetterDeath {
    public static final String NAME = "Better Death";
    public static final String MODID = "betterdeath";
    public static final Logger LOGGER = LogUtils.getLogger();
    public BetterDeath(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, MODID + "-client.toml");
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, MODID + "-common.toml");
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            DeathScreenHandler.triggerDeathScreenServer(player);
        }
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        ServerLevel overworld = event.getServer().overworld();
        if (overworld != null) {
            if(CommonConfig.forceImmediateRespawn.get()) {
                overworld.getGameRules().getRule(GameRules.RULE_DO_IMMEDIATE_RESPAWN).set(true, event.getServer());
            }
        }
    }
}
