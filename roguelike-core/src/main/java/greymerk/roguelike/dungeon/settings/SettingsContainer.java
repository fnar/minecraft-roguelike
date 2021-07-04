package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import greymerk.roguelike.dungeon.settings.base.SettingsBase;
import greymerk.roguelike.dungeon.settings.base.SettingsLayout;
import greymerk.roguelike.dungeon.settings.base.SettingsLootRules;
import greymerk.roguelike.dungeon.settings.base.SettingsRooms;
import greymerk.roguelike.dungeon.settings.base.SettingsSecrets;
import greymerk.roguelike.dungeon.settings.base.SettingsSegments;
import greymerk.roguelike.dungeon.settings.base.SettingsTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsDesertTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsForestTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsGrasslandTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsIceTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsJungleTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMesaTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMountainTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSwampTheme;

import static greymerk.roguelike.dungeon.settings.DungeonSettingsParser.parseJson;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class SettingsContainer {

  public static final String DEFAULT_NAMESPACE = "default";
  public static final String BUILTIN_NAMESPACE = "builtin";

  private final Map<String, Map<String, DungeonSettings>> settingsByNamespace = new HashMap<>();

  private SettingsContainer() {
    put(
        new SettingsRooms(),
        new SettingsSecrets(),
        new SettingsSegments(),
        new SettingsLayout(),
        new SettingsTheme(),
        new SettingsLootRules(),
        new SettingsBase(),

        new SettingsDesertTheme(),
        new SettingsGrasslandTheme(),
        new SettingsJungleTheme(),
        new SettingsSwampTheme(),
        new SettingsMountainTheme(),
        new SettingsForestTheme(),
        new SettingsMesaTheme(),
        new SettingsIceTheme()
    );
  }

  public SettingsContainer(DungeonSettings... dungeonSettings) {
    this();
    put(dungeonSettings);
  }

  public SettingsContainer(Map<String, String> dungeonSettingsJsonByFileName) throws Exception {
    this();
    put(dungeonSettingsJsonByFileName);
  }

  public void put(Map<String, String> dungeonSettingsJsonByFileName) throws Exception {
    for (Map.Entry<String, String> entry : dungeonSettingsJsonByFileName.entrySet()) {
      String filePath = entry.getKey();
      String setting = entry.getValue();
      try {
        put(setting);
      } catch (Exception e) {
        throw new Exception("Error in: " + filePath + ": " + e.getMessage(), e);
      }
    }
  }

  public void put(String dungeonSettingsJson) throws Exception {
    parseJson(dungeonSettingsJson).ifPresent(this::put);
  }

  public void put(DungeonSettings... dungeonSettings) {
    stream(dungeonSettings).forEach(this::put);
  }

  public void put(List<DungeonSettings> dungeonSettings) {
    dungeonSettings.forEach(this::put);
  }

  private void put(DungeonSettings dungeonSettings) {
    String namespace = dungeonSettings.getNamespace();

    if (!containsNamespace(namespace)) {
      settingsByNamespace.put(namespace, new HashMap<>());
    }

    settingsByNamespace.get(namespace).put(dungeonSettings.getName(), dungeonSettings);
  }

  public Collection<DungeonSettings> getByNamespace(String namespace) {
    if (containsNamespace(namespace)) {
      return settingsByNamespace.get(namespace).values();
    }
    return new ArrayList<>();
  }

  public Collection<DungeonSettings> getBuiltinSettings() {
    return settingsByNamespace.entrySet().stream()
        .filter(this::isBuiltIn)
        .map(Map.Entry::getValue)
        .map(Map::values)
        .flatMap(Collection::stream)
        .collect(toList());
  }

  private boolean isBuiltIn(Map.Entry<String, Map<String, DungeonSettings>> entry) {
    return BUILTIN_NAMESPACE.equals(entry.getKey());
  }

  public Collection<DungeonSettings> getCustomSettings() {
    return settingsByNamespace.entrySet().stream()
        .filter(((Predicate<Map.Entry<String, Map<String, DungeonSettings>>>) this::isBuiltIn).negate())
        .map(Map.Entry::getValue)
        .map(Map::values)
        .flatMap(Collection::stream)
        .collect(toList());
  }

  public DungeonSettings get(SettingIdentifier id) {
    if (!contains(id)) {
      throw new RuntimeException("Setting not found: " + id.toString());
    }
    return getNamespace(id).get(id.getName());
  }

  private boolean contains(SettingIdentifier id) {
    return containsNamespace(id.getNamespace()) && getNamespace(id).containsKey(id.getName());
  }

  private boolean containsNamespace(String namespace) {
    return settingsByNamespace.containsKey(namespace);
  }

  private Map<String, DungeonSettings> getNamespace(SettingIdentifier id) {
    return settingsByNamespace.get(id.getNamespace());
  }

  @Override
  public String toString() {
    return settingsByNamespace.values().stream()
        .map(Map::values)
        .flatMap(Collection::stream)
        .map(DungeonSettings::getId)
        .map(SettingIdentifier::toString)
        .collect(joining(" "));
  }
}
