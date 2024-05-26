package sfiomn.legendaryoptchecker.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import sfiomn.legendaryoptchecker.LegendaryOptChecker;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

import static sfiomn.legendaryoptchecker.LegendaryOptChecker.MOD_ID;

public class Config {

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Config.Client CLIENT;

    static
    {
        final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Config.Client::new);
        CLIENT_SPEC = client.getRight();
        CLIENT = client.getLeft();
    }

    public static void register()
    {
        try
        {
            Files.createDirectory(LegendaryOptChecker.modConfigPath);
        }
        catch (FileAlreadyExistsException ignored) {}
        catch (IOException e)
        {
            LegendaryOptChecker.LOGGER.error("Failed to create Legendary Survival Overhaul config directories");
            e.printStackTrace();
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC,  MOD_ID + "-client.toml");

    }

    public static class Client {
        public final ForgeConfigSpec.ConfigValue<String> linkUrlForOptifine;

        Client(ForgeConfigSpec.Builder builder) {

            builder.comment(new String[]{" Options related to the Warning OptiFine screen."
            }).push("general");

            linkUrlForOptifine = builder
                    .comment(" Link to follow when clicking the link button")
                    .define("OptiFine link", "https://optifine.net/adloadx?f=OptiFine_1.16.5_HD_U_G8.jar");

            builder.pop();
        }
    }

    public static class Baked {

        // Client Config
        public static String linkUrlForOptifine;


        public static void bakeClient() {
            LegendaryOptChecker.LOGGER.debug("Load Client configuration from file");
            try {
                linkUrlForOptifine = CLIENT.linkUrlForOptifine.get();
            } catch (Exception e) {
                LegendaryOptChecker.LOGGER.warn("An exception was caused trying to load the client config.");
                e.printStackTrace();
            }
        }
    }
}
