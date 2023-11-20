package greymerk.roguelike.dungeon.settings;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.roguelike.settings.exception.SettingsNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

  private final SettingsContainer settingsContainer;

  public static SettingsResolver getInstance(ModLoader modLoader) {
    return new SettingsResolver(new SettingsContainer(modLoader).loadFiles());
  }

  public SettingsResolver(SettingsContainer settingsContainer) {
    this.settingsContainer = settingsContainer;
  }

  public DungeonSettings resolve(String settingName) {
    DungeonSettings dungeonSettings = settingsContainer.get(new SettingIdentifier(settingName));
    DungeonSettings inflatedDungeonSettings = processInheritance(dungeonSettings);
    return ofNullable(inflatedDungeonSettings).orElseThrow(() -> new SettingsNotFoundException(settingName));
  }

  public Optional<DungeonSettings> chooseRandom(WorldEditor editor, Coord coord) {
    return Optional.ofNullable(chooseRandomCustom(editor, coord)
        .orElse(chooseRandomBuiltin(editor, coord)
            .orElse(null)));
  }

  public DungeonSettings processInheritance(DungeonSettings dungeonSettings) {
    DungeonSettings accumulatedInheritedSettings = dungeonSettings.getInherit().stream()
        .map(settingsContainer::get)
        .map(this::processInheritance)
        .reduce(new DungeonSettings(), (accumulation, toInherit) -> toInherit.inherit(accumulation));

    return dungeonSettings.inherit(accumulatedInheritedSettings);
  }

  private Optional<DungeonSettings> chooseRandomBuiltin(WorldEditor editor, Coord coord) {
    if (!RogueConfig.SPAWNBUILTIN.getBoolean()) {
      return empty();
    }
    return chooseRandomValid(editor, coord, settingsContainer.getBuiltinSettings());
  }

  private Optional<DungeonSettings> chooseRandomCustom(WorldEditor editor, Coord coord) {
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

  public Set<SettingIdentifier> getAllSettingIdentifiers() {
    return settingsContainer.getAllSettingIdentifiers();
  }
}
