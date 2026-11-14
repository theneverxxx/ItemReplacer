package itemreplacer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public final class ItemReplacer {
    public static final String MOD_ID = "itemreplacer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final String MAGISTER_NAME = "меч магистра ";
    private static final Set<String> MAGISTER_LEVELS = Set.of("i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x");

    private ItemReplacer() {
    }

    public static boolean shouldReplaceFor(Object owner) {
        if (owner == null || owner instanceof LocalPlayer || owner == Minecraft.getInstance().player) {
            return true;
        }
        return owner instanceof Player && Config.get().otherPlayers;
    }

    public static String resolve(ItemStack item, String modelId, Object owner) {
        Config config = Config.get();
        if (!config.enabled || modelId == null || !shouldReplaceFor(owner)) {
            return null;
        }

        String swordType = Models.SWORD_TYPES.get(modelId);
        boolean magister = config.swordTypes.contains(Models.MAGISTER);
        if (swordType != null) {
            boolean type = config.swordTypes.contains(swordType);
            boolean replace = type == magister ? type : magister == isMagisterSword(item);
            return replace ? chosenSwordModel(config) : null;
        }
        if (magister && (modelId.endsWith("_sword") || item.has(DataComponents.CUSTOM_NAME)) && isMagisterSword(item)) {
            return chosenSwordModel(config);
        }

        if (Models.TOTEM_MODEL_ID.equals(modelId)) {
            return Models.TOTEMS.get(config.totemModel);
        }
        if (Models.GOLDEN_APPLE_MODEL_ID.equals(modelId)) {
            return Models.GOLDEN_APPLES.get(config.gappleModel);
        }

        if (!Models.STICK_MODEL_ID.equals(modelId)) {
            return null;
        }
        CustomModelData data = item.get(DataComponents.CUSTOM_MODEL_DATA);
        if (data == null || data.floats().isEmpty()) {
            return null;
        }
        int customModelData = data.floats().get(0).intValue();
        String name = Models.MISC_NAMES.get(customModelData);
        if (name == null || !config.miscItems.contains(name)) {
            return null;
        }
        return Models.MISC_ITEMS.get(customModelData);
    }

    private static String chosenSwordModel(Config config) {
        String model = Models.SWORDS.get(config.swordModel);
        return model != null ? model : Models.SWORD_SKINS.get(config.swordSkin);
    }

    private static boolean isMagisterSword(ItemStack item) {
        return isMagisterSword(item.getHoverName().getString());
    }

    public static boolean isMagisterSword(String displayName) {
        if (displayName == null) {
            return false;
        }
        String name = plainName(displayName);
        if (!name.startsWith(MAGISTER_NAME)) {
            return false;
        }
        int start = MAGISTER_NAME.length();
        int end = start;
        while (end < name.length() && "ivx".indexOf(name.charAt(end)) >= 0) {
            end++;
        }
        return (end == name.length() || !Character.isLetterOrDigit(name.charAt(end)))
                && MAGISTER_LEVELS.contains(name.substring(start, end));
    }

    private static String plainName(String value) {
        StringBuilder out = new StringBuilder(value.length());
        boolean space = false;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '§') {
                i++;
            } else if (Character.isWhitespace(c) || Character.isSpaceChar(c)) {
                space = out.length() > 0;
            } else {
                if (space) {
                    out.append(' ');
                    space = false;
                }
                char lower = Character.toLowerCase(c);
                out.append(lower == 'і' ? 'i' : lower == 'х' ? 'x' : lower);
            }
        }
        return out.toString();
    }
}
