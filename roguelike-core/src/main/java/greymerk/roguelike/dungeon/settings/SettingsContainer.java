package greymerk.roguelike.dungeon.settings;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import com.github.fnar.forge.ModLoader;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinBaseSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinLayoutSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinLootSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinRoomsSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinSecretsSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinSegmentsSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinThemeSettings;
import greymerk.roguelike.dungeon.settings.builtin.dungeon.BuiltinDesertDungeonSettings;
import greymerk.roguelike.dungeon.settings.builtin.dungeon.BuiltinForestDungeonSettings;
import greymerk.roguelike.dungeon.settings.builtin.dungeon.BuiltinIceDungeonSettings;
import greymerk.roguelike.dungeon.settings.builtin.dungeon.BuiltinJungleDungeonSettings;
import greymerk.roguelike.dungeon.settings.builtin.dungeon.BuiltinMesaDungeonSettings;
import greymerk.roguelike.dungeon.settings.builtin.dungeon.BuiltinMountainDungeonSettings;
import greymerk.roguelike.dungeon.settings.builtin.dungeon.BuiltinPlainsDungeonSettings;
import greymerk.roguelike.dungeon.settings.builtin.dungeon.BuiltinSwampDungeonSettings;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class SettingsContainer {

  public static final String DEFAULT_NAMESPACE = "default";
  public static final String BUILTIN_NAMESPACE = "builtin";

  private final Map<String, Map<String, DungeonSettings>> settingsByNamespace = new HashMap<>();
  private final DungeonSettingsParser dungeonSettingsParser;

  public SettingsContainer(ModLoader modLoader) {
    put(
        new BuiltinRoomsSettings(),
        new BuiltinSecretsSettings(),
        new BuiltinSegmentsSettings(),
        new BuiltinLayoutSettings(),
        new BuiltinThemeSettings(),
        new BuiltinLootSettings(),
        new BuiltinBaseSettings(),

        new BuiltinDesertDungeonSettings(),
        new BuiltinPlainsDungeonSettings(),
        new BuiltinJungleDungeonSettings(),
        new BuiltinSwampDungeonSettings(),
        new BuiltinMountainDungeonSettings(),
        BuiltinForestDungeonSettings.create(),
        new BuiltinMesaDungeonSettings(),
        new BuiltinIceDungeonSettings()
    );

    this.dungeonSettingsParser = new DungeonSettingsParser(modLoader);
  }

  // TODO: move call to constructor ?
  public void loadFiles() throws Exception {
    File settingsDirectoryFile = new File(RogueConfig.CONFIG_DIRECTORY + "/settings");

    if (settingsDirectoryFile.exists() && !settingsDirectoryFile.isDirectory()) {
      throw new Exception("Settings directory is a file");
    }

    if (!settingsDirectoryFile.exists()) {
      settingsDirectoryFile.mkdir();
    }

    Map<String, String> fileByName = collectSettingsFiles(settingsDirectoryFile);
    put(fileByName);
  }

  private Map<String, String> collectSettingsFiles(File settingsDirectory) {
    List<File> files = listFilesRecursively(settingsDirectory);
    return mapContentByFilename(files);
  }

  private static List<File> listFilesRecursively(File settingsDirectory) {
    File[] files = settingsDirectory.listFiles();
    return ofNullable(files).isPresent()
        ? newArrayList(files).stream()
        .flatMap(file -> file.isDirectory() ? listFilesRecursively(file).stream() : Lists.newArrayList(file).stream())
        .filter(file -> FilenameUtils.getExtension(file.getName()).equals("json"))
        .collect(toList())
        : emptyList();
  }

  private static Map<String, String> mapContentByFilename(List<File> files) {
    return files.stream()
        .collect(toMap(File::getAbsolutePath, SettingsContainer::getFileContent));
  }

  private static String getFileContent(File file) {
    try {
      return Files.toString(file, Charsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Error reading file : " + file.getName());
    }
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
    dungeonSettingsParser.parseJson(dungeonSettingsJson).ifPresent(this::put);
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
