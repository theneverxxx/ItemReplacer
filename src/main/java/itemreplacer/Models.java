package itemreplacer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Models {
    public static final String NONE = "None";
    public static final String NAMESPACE = "itemreplacer";

    public static final List<String> NUMBERS = List.of(
            NONE, "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14");

    public static final Map<String, String> SWORDS = new LinkedHashMap<>();
    public static final Map<String, String> SWORD_SKINS = new LinkedHashMap<>();
    public static final Map<String, String> TOTEMS = new LinkedHashMap<>();
    public static final Map<String, String> GOLDEN_APPLES = new LinkedHashMap<>();
    public static final Map<Integer, String> MISC_ITEMS = new LinkedHashMap<>();
    public static final Map<Integer, String> MISC_NAMES = new LinkedHashMap<>();

    public static final String WOODEN = "Деревянный";
    public static final String STONE = "Каменный";
    public static final String COPPER = "Медный";
    public static final String GOLDEN = "Золотой";
    public static final String IRON = "Железный";
    public static final String DIAMOND = "Алмазный";
    public static final String NETHERITE = "Незеритовый";
    public static final String MAGISTER = "Магистра";

    public static final Map<String, String> SWORD_TYPES = new LinkedHashMap<>();
    public static final List<String> SWORD_TYPE_OPTIONS;

    public static final String TOTEM_MODEL_ID = "minecraft:totem_of_undying";
    public static final String GOLDEN_APPLE_MODEL_ID = "minecraft:golden_apple";
    public static final String STICK_MODEL_ID = "minecraft:stick";

    static {
        for (int i = 1; i <= 14; i++) {
            SWORDS.put(String.valueOf(i), id("s" + i));
            TOTEMS.put(String.valueOf(i), id("totems/t" + i));
            GOLDEN_APPLES.put(String.valueOf(i), id("gapples/g" + i));
        }

        SWORD_SKINS.put(NONE, null);
        SWORD_SKINS.put("Меч Магистра", id("diamond_saber"));
        SWORD_SKINS.put("Тесак", id("golden_katana"));
        SWORD_SKINS.put("Кинжал смерти", id("death_dagger"));
        SWORD_SKINS.put("Золотая катана", id("cleaver"));
        SWORD_SKINS.put("Алмазная сабля", id("magister_sword"));

        SWORD_TYPES.put("minecraft:wooden_sword", WOODEN);
        SWORD_TYPES.put("minecraft:stone_sword", STONE);
        SWORD_TYPES.put("minecraft:copper_sword", COPPER);
        SWORD_TYPES.put("minecraft:golden_sword", GOLDEN);
        SWORD_TYPES.put("minecraft:iron_sword", IRON);
        SWORD_TYPES.put("minecraft:diamond_sword", DIAMOND);
        SWORD_TYPES.put("minecraft:netherite_sword", NETHERITE);
        SWORD_TYPE_OPTIONS = List.of(WOODEN, STONE, COPPER, GOLDEN, IRON, DIAMOND, NETHERITE, MAGISTER);

        addMisc(1, "Обычный ключ", "ares/misc/common_key");
        addMisc(2, "Редкий ключ", "ares/misc/rare_key");
        addMisc(3, "Уникальный ключ", "ares/misc/unique_key");
        addMisc(4, "Свиток Метеора", "ares/misc/1");
        addMisc(5, "Свиток Неуязвимости", "ares/misc/2");
        addMisc(6, "Свиток Гравитации", "ares/misc/3");
        addMisc(9, "Помощь Зевса", "ares/misc/6");
        addMisc(10, "Око Деймоса", "ares/misc/7");
        addMisc(11, "Транспортировщик", "ares/misc/8");
        addMisc(12, "Магнит", "ares/misc/10");
        addMisc(13, "Небесная нитка", "ares/misc/11");
        addMisc(19, "Кирка Гефеста", "ares/misc/17");
        addMisc(20, "Гнев Богов", "ares/misc/18");
        addMisc(21, "Чешуя Василиска", "ares/misc/19");
        addMisc(22, "Доспехи Ареса", "ares/misc/20");
        addMisc(24, "Крыло Василиска", "ares/misc/22");
        addMisc(25, "Защитный панцирь", "ares/misc/23");
    }

    private Models() {
    }

    private static String id(String path) {
        return NAMESPACE + ":itemreplacer/" + path;
    }

    private static void addMisc(int customModelData, String name, String path) {
        MISC_ITEMS.put(customModelData, id(path));
        MISC_NAMES.put(customModelData, name);
    }
}
