package itemreplacer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Config instance;

    public boolean enabled = true;
    public boolean otherPlayers = false;

    public String swordModel = Models.NONE;
    public String swordSkin = Models.NONE;
    public Set<String> swordTypes = new LinkedHashSet<>(List.of(Models.NETHERITE));

    public String totemModel = Models.NONE;
    public String gappleModel = Models.NONE;

    public Set<String> miscItems = new LinkedHashSet<>(Models.MISC_NAMES.values());

    public static Config get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve("itemreplacer.json");
    }

    private static Config load() {
        Path path = path();
        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                Config config = GSON.fromJson(reader, Config.class);
                if (config != null) {
                    config.fix();
                    return config;
                }
            } catch (Exception e) {
                ItemReplacer.LOGGER.warn("Could not read {}, using defaults", path, e);
            }
        }
        Config config = new Config();
        config.save();
        return config;
    }

    private void fix() {
        if (!Models.NUMBERS.contains(swordModel)) swordModel = Models.NONE;
        if (!Models.SWORD_SKINS.containsKey(swordSkin)) swordSkin = Models.NONE;
        if (!Models.NUMBERS.contains(totemModel)) totemModel = Models.NONE;
        if (!Models.NUMBERS.contains(gappleModel)) gappleModel = Models.NONE;
        swordTypes = swordTypes == null ? new LinkedHashSet<>() : new LinkedHashSet<>(swordTypes);
        miscItems = miscItems == null ? new LinkedHashSet<>() : new LinkedHashSet<>(miscItems);
    }

    public void save() {
        Path path = path();
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            ItemReplacer.LOGGER.warn("Could not save {}", path, e);
        }
    }
}
