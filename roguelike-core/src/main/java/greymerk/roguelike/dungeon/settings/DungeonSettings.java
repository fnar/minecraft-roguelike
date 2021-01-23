package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.PositionInfo;
import greymerk.roguelike.worldgen.WorldEditor;

import static greymerk.roguelike.dungeon.settings.SettingsContainer.DEFAULT_NAMESPACE;
import static java.util.Optional.ofNullable;


public class DungeonSettings {

  private static final int MAX_NUM_LEVELS = 5;
  private SettingIdentifier id;
  private final List<SettingIdentifier> inherit = new ArrayList<>();
  private boolean exclusive;
  private TowerSettings towerSettings;
  private final Map<Integer, LevelSettings> levels = new HashMap<>();
  private SpawnCriteria spawnCriteria = new SpawnCriteria();
  private LootRuleManager lootRules = new LootRuleManager();
  private final List<LootTableRule> lootTables = new ArrayList<>();
  private final Set<SettingsType> overrides = new HashSet<>();

  public DungeonSettings() {
  }

  public DungeonSettings(String id) {
    this(new SettingIdentifier(id));
  }

  public DungeonSettings(SettingIdentifier id) {
    setId(id);
  }

  public DungeonSettings inherit(DungeonSettings toInherit) {
    DungeonSettings dungeonSettings = new DungeonSettings();
    dungeonSettings.id = id;
//    dungeonSettings.inherit.addAll(getInherit());
//    dungeonSettings.overrides.addAll(ofNullable(getOverrides()).orElse(newHashSet()));
    dungeonSettings.exclusive = isExclusive();
    dungeonSettings.lootRules.merge(lootRules);
    dungeonSettings.lootTables.addAll(lootTables);
    if (!overrides.contains(SettingsType.LOOTRULES)) {
      dungeonSettings.lootRules.merge(toInherit.lootRules);
      dungeonSettings.lootTables.addAll(toInherit.lootTables);
    }
    dungeonSettings.towerSettings = dungeonSettings.getTowerSettings(toInherit, this);
    dungeonSettings.spawnCriteria = this.spawnCriteria.inherit(toInherit.spawnCriteria);
    IntStream.range(0, getMaxNumLevels())
        .forEach(level -> {
          LevelSettings parent = toInherit.levels.get(level);
          LevelSettings child = levels.get(level);
          LevelSettings levelSettings = new LevelSettings(parent, child, dungeonSettings.overrides);
          dungeonSettings.levels.put(level, levelSettings);
        });

    return dungeonSettings;
  }

  private TowerSettings getTowerSettings(DungeonSettings parent, DungeonSettings child) {
    if (getOverrides().contains(SettingsType.TOWER) && child.towerSettings != null) {
      return new TowerSettings(child.towerSettings);
    } else if (parent.towerSettings != null || child.towerSettings != null) {
      return new TowerSettings(parent.towerSettings, child.towerSettings);
    } else {
      return null;
    }
  }

  public DungeonSettings(DungeonSettings toCopy) {
    id = toCopy.id;
    inherit.addAll(toCopy.inherit);
    overrides.addAll(toCopy.overrides);
    exclusive = toCopy.exclusive;
    lootRules.merge(toCopy.lootRules);
    lootTables.addAll(toCopy.lootTables);
    for (int i = 0; i < getMaxNumLevels(); ++i) {
      LevelSettings level = toCopy.levels.get(i);
      LevelSettings levelSettings = Optional.ofNullable(level).map(LevelSettings::new).orElse(new LevelSettings());
      levels.put(i, levelSettings);
    }
    spawnCriteria = new SpawnCriteria(toCopy.spawnCriteria);
    towerSettings = toCopy.towerSettings != null ? new TowerSettings(toCopy.towerSettings) : null;
  }

  public static int getMaxNumLevels() {
    return MAX_NUM_LEVELS;
  }

  public SettingIdentifier getId() {
    return id;
  }

  public void setId(SettingIdentifier id) {
    this.id = id;
  }

  public String getNamespace() {
    return ofNullable(getId().getNamespace()).orElse(DEFAULT_NAMESPACE);
  }

  public String getName() {
    return getId().getName();
  }

  public void setSpawnCriteria(SpawnCriteria spawnCriteria) {
    this.spawnCriteria = spawnCriteria;
  }

  public boolean isValid(WorldEditor editor, Coord pos) {
    PositionInfo positionInfo = editor.getInfo(pos);
    return getCriteria().isValid(positionInfo);
  }

  public LevelSettings getLevelSettings(int level) {
    return getLevelSettings().get(level);
  }

  public TowerSettings getTower() {
    if (towerSettings == null) {
      return new TowerSettings(Tower.ROGUE, Theme.STONE);
    }

    return towerSettings;
  }

  public int getNumLevels() {
    return getMaxNumLevels();
  }

  public Set<SettingsType> getOverrides() {
    return overrides;
  }

  public boolean isExclusive() {
    return exclusive;
  }

  public void processLoot(TreasureManager treasure) {
    getLootRules().process(treasure);
    getLootTables().forEach(table -> table.process(treasure));
  }

  public LootRuleManager getLootRules() {
    return lootRules;
  }

  public void setExclusive(boolean exclusive) {
    this.exclusive = exclusive;
  }

  public void setTowerSettings(TowerSettings towerSettings) {
    this.towerSettings = towerSettings;
  }

  public List<SettingIdentifier> getInherit() {
    return inherit;
  }

  public Map<Integer, LevelSettings> getLevelSettings() {
    return levels;
  }

  public SpawnCriteria getCriteria() {
    return spawnCriteria;
  }

  public List<LootTableRule> getLootTables() {
    return lootTables;
  }

}
