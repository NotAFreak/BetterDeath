package net.notafreak.betterdeath;



import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.notafreak.betterdeath.config.ClientConfig;
import net.notafreak.betterdeath.config.CommonConfig;

import org.slf4j.Logger;

@Mod(BetterDeath.MODID)
public class BetterDeath {
    public static final String NAME = "Better Death";
    public static final String MODID = "betterdeath";
    private static final Logger LOGGER = LogUtils.getLogger();

    public BetterDeath()
    {
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
    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            event.setCanceled(true);
            LOGGER.info("these are thrilling times, the player has fucking died");

            // Reset health and other attributes
            player.setHealth(20.0F);
            player.clearFire();
            player.removeAllEffects();
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(0);
            
            BlockPos pos = player.getRespawnPosition();
            if(pos == null) {
                pos = player.serverLevel().getSharedSpawnPos();
            }
            Vec3 respawnPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            player.teleportTo(player.serverLevel(), respawnPos.x, respawnPos.y, respawnPos.z + 1, 0, 0);
            LOGGER.info("respawned player");
            BlackScreenHandler.triggerBlackScreen(player);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
}
