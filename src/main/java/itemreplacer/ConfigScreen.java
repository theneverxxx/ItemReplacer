package itemreplacer;

import com.mojang.serialization.Codec;
import itemreplacer.compat.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ConfigScreen extends OptionsSubScreen {
    public ConfigScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.translatable("itemreplacer.title"));
    }

    @Override
    protected void addOptions() {
        Config config = Config.get();

        this.list.addBig(OptionInstance.createBoolean("itemreplacer.enabled", config.enabled, v -> config.enabled = v));
        this.list.addSmall(
                cycle("itemreplacer.sword_model", Models.NUMBERS, config.swordModel, v -> config.swordModel = v),
                cycle("itemreplacer.sword_skin", new ArrayList<>(Models.SWORD_SKINS.keySet()), config.swordSkin,
                        v -> config.swordSkin = v)
        );
        this.list.addSmall(
                cycle("itemreplacer.totem", Models.NUMBERS, config.totemModel, v -> config.totemModel = v),
                cycle("itemreplacer.gapple", Models.NUMBERS, config.gappleModel, v -> config.gappleModel = v)
        );
        this.list.addBig(OptionInstance.createBoolean("itemreplacer.other_players", config.otherPlayers,
                v -> config.otherPlayers = v));
        this.list.addSmall(
                Button.builder(Component.translatable("itemreplacer.sword_types"), b -> Screens.open(
                        new ToggleListScreen(this, "itemreplacer.sword_types", Models.SWORD_TYPE_OPTIONS, config.swordTypes)
                )).build(),
                Button.builder(Component.translatable("itemreplacer.misc_items"), b -> Screens.open(
                        new ToggleListScreen(this, "itemreplacer.misc_items", Models.MISC_NAMES.values(), config.miscItems)
                )).build()
        );
    }

    @Override
    public void removed() {
        super.removed();
        Config.get().save();
    }

    private interface Setter {
        void set(String value);
    }

    private static OptionInstance<String> cycle(String key, List<String> values, String current, Setter setter) {
        return new OptionInstance<>(
                key,
                OptionInstance.noTooltip(),
                (caption, value) -> Component.translatable("options.generic_value", caption,
                        Models.NONE.equals(value) ? Component.translatable("options.off") : Component.literal(value)),
                new OptionInstance.Enum<>(values, Codec.STRING),
                values.contains(current) ? current : values.get(0),
                v -> setter.set(v)
        );
    }

    public static class ToggleListScreen extends OptionsSubScreen {
        private final List<String> names;
        private final Set<String> selected;

        public ToggleListScreen(Screen parent, String titleKey, Collection<String> names, Set<String> selected) {
            super(parent, Minecraft.getInstance().options, Component.translatable(titleKey));
            this.names = List.copyOf(names);
            this.selected = selected;
        }

        @Override
        protected void addOptions() {
            List<OptionInstance<?>> toggles = new ArrayList<>();
            for (String name : names) {
                toggles.add(OptionInstance.createBoolean(name, selected.contains(name), on -> {
                    if (on) {
                        selected.add(name);
                    } else {
                        selected.remove(name);
                    }
                }));
            }
            this.list.addSmall(toggles.toArray(new OptionInstance<?>[0]));
        }

        @Override
        public void removed() {
            super.removed();
            Config.get().save();
        }
    }
}
