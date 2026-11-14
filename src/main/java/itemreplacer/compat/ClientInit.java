package itemreplacer.compat;

import com.mojang.blaze3d.platform.InputConstants;
import itemreplacer.Config;
import itemreplacer.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Config.get();
        KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("itemreplacer", "main"));
        KeyMapping open = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.itemreplacer.open", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, category));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (open.consumeClick()) {
                if (Screens.current() == null) {
                    Screens.open(new ConfigScreen(null));
                }
            }
        });
    }
}
