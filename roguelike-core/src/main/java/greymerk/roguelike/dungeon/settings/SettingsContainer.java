package greymerk.roguelike.dungeon.settings;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.roguelike.settings.exception.SettingsNotFoundException;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

  private final Map<String, DungeonSettings> settingsByName = new HashMap<>();
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

  public SettingsContainer loadFiles() {
    File settingsDirectoryFile = new File(RogueConfig.CONFIG_DIRECTORY + "/settings");

    if (settingsDirectoryFile.exists() && !settingsDirectoryFile.isDirectory()) {
      throw new RuntimeException("Settings directory is a file");
    }

    if (!settingsDirectoryFile.exists()) {
      settingsDirectoryFile.mkdir();
    }

    Map<String, String> fileByName = collectSettingsFiles(settingsDirectoryFile);
    put(fileByName);
    return this;
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

  public void put(Map<String, String> dungeonSettingsJsonByFileName) {
    for (Map.Entry<String, String> entry : dungeonSettingsJsonByFileName.entrySet()) {
      String filePath = entry.getKey();
      String setting = entry.getValue();
      try {
        put(setting);
      } catch (Exception e) {
        throw new RuntimeException("Error in: " + filePath + ": " + e.getMessage());
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
    settingsByName.put(dungeonSettings.getName(), dungeonSettings);
  }

  public String getNamesStartingWith(String namespace) {
    return getStartingWith(namespace).stream()
        .map(DungeonSettings::getName)
        .collect(joining(" "));
  }

  public Collection<DungeonSettings> getStartingWith(String name) {
    return settingsByName.keySet().stream()
        .filter(s -> s.startsWith(name))
        .map(settingsByName::get)
        .collect(Collectors.toSet());
  }

  public Collection<DungeonSettings> getBuiltinSettings() {
    return settingsByName.entrySet().stream()
        .filter(this::isBuiltin)
        .map(Map.Entry::getValue)
        .collect(Collectors.toSet());
  }

  private boolean isBuiltin(Map.Entry<String, DungeonSettings> entry) {
    return entry.getKey().startsWith(BUILTIN_NAMESPACE);
  }

  public Collection<DungeonSettings> getCustomSettings() {
    return settingsByName.entrySet().stream()
        .filter(((Predicate<Map.Entry<String, DungeonSettings>>) this::isBuiltin).negate())
        .map(Map.Entry::getValue)
        .collect(Collectors.toSet());
  }

  public DungeonSettings get(String id) {
    if (!contains(id)) {
      throw new SettingsNotFoundException(id);
    }
    return settingsByName.get(id);
  }

  private boolean contains(String id) {
    return settingsByName.containsKey(id);
  }

  @Override
  public String toString() {
    return String.join(" ", settingsByName.keySet());
  }
}
