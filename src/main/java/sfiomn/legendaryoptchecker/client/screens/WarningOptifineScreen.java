package sfiomn.legendaryoptchecker.client.screens;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import sfiomn.legendaryoptchecker.config.Config;

public class WarningOptifineScreen extends ConfirmScreen {

    public WarningOptifineScreen() {
        super(getButtonEffect(),
                new TranslationTextComponent("screen.legendaryoptchecker.opt_not_found.title"),
                new TranslationTextComponent("screen.legendaryoptchecker.opt_not_found.text"),
                new TranslationTextComponent("screen.legendaryoptchecker.opt_not_found.open_link"),
                new TranslationTextComponent("menu.quit"));
    }

    private static BooleanConsumer getButtonEffect() {
        return (confirmButton) -> {
            if (confirmButton) {
                Util.getPlatform().openUri(Config.Baked.linkUrlForOptifine);
            } else {
                Minecraft.getInstance().close();
            }
        };
    }
}
