package itemreplacer.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public final class Screens {
    private Screens() {
    }

    public static Screen current() {
        return Minecraft.getInstance().screen;
    }

    public static void open(Screen screen) {
        Minecraft.getInstance().setScreen(screen);
    }
}
