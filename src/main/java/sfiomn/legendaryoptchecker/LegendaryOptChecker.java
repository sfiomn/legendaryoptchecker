package sfiomn.legendaryoptchecker;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sfiomn.legendaryoptchecker.config.Config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LegendaryOptChecker.MOD_ID)
public class LegendaryOptChecker
{
    public static final String MOD_ID = "legendaryoptchecker";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static Path modConfigPath = FMLPaths.CONFIGDIR.get();
    public static boolean optifineLoaded = false;

    public LegendaryOptChecker() {

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onModConfigEvent);

        Config.register();

        Config.Baked.bakeClient();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        try {
            Class.forName("net.optifine.Config");
            optifineLoaded = true;
        } catch (ClassNotFoundException e) {
            optifineLoaded = false;
        }

        if (!optifineLoaded)
            LOGGER.debug("Optifine is not loaded");
    }

    private void onModConfigEvent(final ModConfig.ModConfigEvent event)
    {
        final ModConfig config = event.getConfig();

        // Since client config is not shared, we want it to update instantly whenever it's saved
        if (config.getSpec() == Config.CLIENT_SPEC)
            Config.Baked.bakeClient();
    }
}
