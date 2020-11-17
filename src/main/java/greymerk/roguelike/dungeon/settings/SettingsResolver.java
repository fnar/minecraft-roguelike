package greymerk.roguelike.dungeon.settings;

import net.minecraft.client.Minecraft;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public class SettingsResolver {

  private final SettingsContainer settingsContainer;

  public SettingsResolver(
      SettingsContainer settingsContainer
  ) {
    this.settingsContainer = settingsContainer;
  }

  public DungeonSettings getAnyCustomDungeonSettings(WorldEditor editor, Coord coord) {
    return chooseRandomCustomDungeonIfPossible(editor, coord)
        .orElseGet(() -> chooseOneBuiltinSettingAtRandom(editor, coord)
            .orElse(null));
  }

  public DungeonSettings getByName(String name) {
    try {
      SettingIdentifier id = new SettingIdentifier(name);
      if (settingsContainer.contains(id)) {
        DungeonSettings setting = new DungeonSettings(settingsContainer.get(id));
        DungeonSettings byName = processInheritance(setting);
        if (byName != null) {
          // todo: Remove SettingsBlank. This is data and should be treated like a constant instance
          // todo: also consider eliminating usage of the merge constructor here.
          // todo: also consider eliminating usage of the copy constructor here.
          return new DungeonSettings(new SettingsBlank(), byName);
        }
      }
      return null;
    } catch (Exception e) {
      Minecraft.getMinecraft().player.sendChatMessage(Arrays.toString(e.getStackTrace()));
      throw new RuntimeException(e);
//      throw new RuntimeException("Malformed Setting ID String: " + name);
    }
  }

  public DungeonSettings processInheritance(
      DungeonSettings dungeonSettings
  ) {
    return dungeonSettings.getInherits().stream()
        .peek(this::throwIfNotFound)
        .map(settingsContainer::get)
        .reduce(dungeonSettings, this::inherit);
  }

  private void throwIfNotFound(SettingIdentifier settingIdentifier) {
    if (!settingsContainer.contains(settingIdentifier)) {
      throw new RuntimeException("Setting not found: " + settingIdentifier.toString());
    }
  }

  private DungeonSettings inherit(DungeonSettings child, DungeonSettings parent) {
    return new DungeonSettings(processInheritance(parent), child);
  }

  private Optional<DungeonSettings> chooseOneBuiltinSettingAtRandom(WorldEditor editor, Coord coord) {
    if (!RogueConfig.getBoolean(RogueConfig.SPAWNBUILTIN)) {
      return empty();
    }
    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(getValidBuiltinSettings(editor, coord));

    if (settingsRandomizer.isEmpty()) {
      return empty();
    }
    Random random = editor.getRandom(coord);
    DungeonSettings randomSetting = settingsRandomizer.get(random);
    DungeonSettings builtin = processInheritance(randomSetting);
    return ofNullable(builtin);
  }

  private List<DungeonSettings> getValidBuiltinSettings(WorldEditor editor, Coord coord) {
    return filterValid(settingsContainer.getBuiltinSettings(), editor, coord);
  }

  private List<DungeonSettings> filterValid(Collection<DungeonSettings> builtinSettings, WorldEditor editor, Coord coord) {
    return builtinSettings.stream()
        .filter(isValid(editor, coord))
        .filter(DungeonSettings::isExclusive)
        .collect(Collectors.toList());
  }

  private Optional<DungeonSettings> chooseRandomCustomDungeonIfPossible(
      WorldEditor editor,
      Coord coord
  ) {
    List<DungeonSettings> validCustomSettings = filterValid(settingsContainer.getCustomSettings(), editor, coord);
    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(validCustomSettings);
    Random random = editor.getRandom(coord);
    return ofNullable(settingsRandomizer.get(random))
        .map(this::processInheritance);
  }

  private WeightedRandomizer<DungeonSettings> newWeightedRandomizer(List<DungeonSettings> dungeonSettings) {
    WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<>();
    dungeonSettings.stream()
        .map(setting -> new WeightedChoice<>(setting, setting.getSpawnCriteria().getWeight()))
        .forEach(settingsRandomizer::add);
    return settingsRandomizer;
  }

  private Predicate<DungeonSettings> isValid(WorldEditor editor, Coord pos) {
    return setting -> setting.isValid(editor, pos);
  }

  public String toString(String namespace) {
    return settingsContainer.getByNamespace(namespace).stream()
        .map(DungeonSettings::getId)
        .map(SettingIdentifier::toString)
        .collect(joining(" "));
  }

  @Override
  public String toString() {
    return settingsContainer.toString();
  }
}
