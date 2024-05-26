package sfiomn.legendaryoptchecker.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendaryoptchecker.LegendaryOptChecker;
import sfiomn.legendaryoptchecker.client.screens.WarningOptifineScreen;


@Mod.EventBusSubscriber(modid = LegendaryOptChecker.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    public static boolean hasOpened = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!LegendaryOptChecker.optifineLoaded && !hasOpened) {
                Minecraft.getInstance().setScreen(new WarningOptifineScreen((confirmButton) -> {
                    if (confirmButton) {
                        Util.getPlatform().openUri("https://www.optifine.net/downloads");
                    } else {
                        Minecraft.getInstance().close();
                    }

                }, new TranslationTextComponent("screen.legendaryoptchecker.opt_not_found.title"),
                        new TranslationTextComponent("screen.legendaryoptchecker.opt_not_found.text"),
                        new TranslationTextComponent("screen.legendaryoptchecker.opt_not_found.open_link"),
                        new TranslationTextComponent("menu.quit")));
                hasOpened = true;
            }
        }
    }
}
