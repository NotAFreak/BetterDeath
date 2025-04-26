package net.notafreak.betterdeath;



import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.notafreak.betterdeath.config.ClientConfig;
import net.notafreak.betterdeath.config.CommonConfig;

@Mod(BetterDeath.MODID)
public class BetterDeath {
    public static final String NAME = "Better Death";
    public static final String MODID = "betterdeath";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BetterDeath() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, MODID + "-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, MODID + "-common.toml");
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        // we do fuck all lol
    }
    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            LOGGER.info("these are thrilling times, the player has died");
            DeathScreenHandler.triggerDeathScreen(player);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        ServerLevel overworld = event.getServer().overworld();

        if (overworld != null) {
            // Set the instant respawn gamerule
            if(CommonConfig.forceImmediateRespawn.get()) {
                overworld.getGameRules().getRule(GameRules.RULE_DO_IMMEDIATE_RESPAWN).set(true, event.getServer());
            }
        }
    }
}
