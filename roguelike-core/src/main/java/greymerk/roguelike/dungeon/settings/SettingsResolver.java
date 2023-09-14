package greymerk.roguelike.dungeon.settings;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class SettingsResolver {

  // TODO: Convert to instance
  public static SettingsResolver instance;

  private final SettingsContainer settingsContainer;

  public SettingsResolver(SettingsContainer settingsContainer) {
    this.settingsContainer = settingsContainer;
  }

  public Optional<DungeonSettings> chooseDungeonSetting(WorldEditor editor, Coord coord) {
    Optional<DungeonSettings> customDungeon = chooseRandomCustomDungeonIfPossible(editor, coord);
    return customDungeon.isPresent()
        ? customDungeon
        : chooseOneBuiltinSettingAtRandom(editor, coord);
  }

  public DungeonSettings getByName(String name) {
    try {
      SettingIdentifier id = new SettingIdentifier(name);
      DungeonSettings dungeonSettings = settingsContainer.get(id);
      return processInheritance(dungeonSettings);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public DungeonSettings processInheritance(DungeonSettings dungeonSettings) {
    DungeonSettings accumulatedInheritedSettings = dungeonSettings.getInherit().stream()
        .map(settingsContainer::get)
        .map(this::processInheritance)
        .reduce(new DungeonSettings(), (accumulation, toInherit) -> toInherit.inherit(accumulation));

    return dungeonSettings.inherit(accumulatedInheritedSettings);
  }

  private Optional<DungeonSettings> chooseOneBuiltinSettingAtRandom(WorldEditor editor, Coord coord) {
    if (!RogueConfig.SPAWNBUILTIN.getBoolean()) {
      return empty();
    }
    return chooseRandomValid(editor, coord, settingsContainer.getBuiltinSettings());
  }

  private Optional<DungeonSettings> chooseRandomCustomDungeonIfPossible(WorldEditor editor, Coord coord) {
    return chooseRandomValid(editor, coord, settingsContainer.getCustomSettings());
  }

  private Optional<DungeonSettings> chooseRandomValid(WorldEditor editor, Coord coord, Collection<DungeonSettings> settings) {
    List<DungeonSettings> inflatedSettings = settings.stream()
        .map(this::processInheritance)
        .filter(isValid(editor, coord))
        .collect(toList());
    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(inflatedSettings);
    return ofNullable(settingsRandomizer.get(editor.getRandom()));
  }

  private Predicate<DungeonSettings> isValid(WorldEditor editor, Coord coord) {
    return setting -> setting.isExclusive() && setting.isValid(editor, coord);
  }

  private WeightedRandomizer<DungeonSettings> newWeightedRandomizer(List<DungeonSettings> dungeonSettings) {
    WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<>();
    dungeonSettings.stream()
        .map(setting -> new WeightedChoice<>(setting, setting.getCriteria().getWeight()))
        .forEach(settingsRandomizer::add);
    return settingsRandomizer;
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
