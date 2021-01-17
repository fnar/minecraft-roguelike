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

import static com.google.common.collect.Sets.newHashSet;
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
    dungeonSettings.getOverrides().addAll(ofNullable(getOverrides()).orElse(newHashSet()));

    if (!dungeonSettings.getOverrides().contains(SettingsType.LOOTRULES)) {
      dungeonSettings.getLootRules().merge(toInherit.getLootRules());
      dungeonSettings.getLootTables().addAll(toInherit.getLootTables());
    }
    dungeonSettings.getLootRules().merge(getLootRules());
    dungeonSettings.getLootTables().addAll(getLootTables());

    dungeonSettings.getInherit().addAll(getInherit());

    dungeonSettings.exclusive = isExclusive();

    dungeonSettings.setTowerSettings(dungeonSettings.getTowerSettings(toInherit, this));

    IntStream.range(0, getMaxNumLevels())
        .forEach(level -> {
          LevelSettings parent = toInherit.getLevels().get(level);
          LevelSettings child = getLevels().get(level);
          LevelSettings levelSettings = new LevelSettings(parent, child, dungeonSettings.getOverrides());
          dungeonSettings.getLevels().put(level, levelSettings);
        });

    return dungeonSettings;
  }

  private TowerSettings getTowerSettings(DungeonSettings parent, DungeonSettings child) {
    if (getOverrides().contains(SettingsType.TOWER) && child.getTowerSettings() != null) {
      return new TowerSettings(child.getTowerSettings());
    } else if (parent.getTowerSettings() != null || child.getTowerSettings() != null) {
      return new TowerSettings(parent.getTowerSettings(), child.getTowerSettings());
    } else {
      return null;
    }
  }

  public DungeonSettings(DungeonSettings toCopy) {
    setTowerSettings(toCopy.getTowerSettings() != null ? new TowerSettings(toCopy.getTowerSettings()) : null);
    this.lootRules = toCopy.getLootRules();
    getLootTables().addAll(toCopy.getLootTables());

    getInherit().addAll(toCopy.getInherit());

    this.exclusive = toCopy.isExclusive();

    for (int i = 0; i < getMaxNumLevels(); ++i) {
      LevelSettings level = toCopy.getLevels().get(i);
      LevelSettings levelSettings = Optional.ofNullable(level).map(LevelSettings::new).orElse(new LevelSettings());
      getLevels().put(i, levelSettings);
    }

    if (toCopy.getOverrides() != null) {
      getOverrides().addAll(toCopy.getOverrides());
    }
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

  public List<SettingIdentifier> getInherits() {
    return getInherit() != null ? getInherit() : new ArrayList<>();
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
    return getSpawnCriteria().isValid(positionInfo);
  }

  public LevelSettings getLevelSettings(int level) {
    return getLevels().get(level);
  }

  public TowerSettings getTower() {
    if (getTowerSettings() == null) {
      return new TowerSettings(Tower.ROGUE, Theme.STONE);
    }

    return getTowerSettings();
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

  public TowerSettings getTowerSettings() {
    return towerSettings;
  }

  public void setTowerSettings(TowerSettings towerSettings) {
    this.towerSettings = towerSettings;
  }

  public List<SettingIdentifier> getInherit() {
    return inherit;
  }

  public Map<Integer, LevelSettings> getLevels() {
    return levels;
  }

  public SpawnCriteria getSpawnCriteria() {
    return spawnCriteria;
  }

  public List<LootTableRule> getLootTables() {
    return lootTables;
  }

}
