package greymerk.roguelike.dungeon.settings;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.segment.ISegmentGenerator;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;
import lombok.ToString;

import static greymerk.roguelike.dungeon.settings.SettingsType.ROOMS;
import static greymerk.roguelike.dungeon.settings.SettingsType.SECRETS;
import static greymerk.roguelike.dungeon.settings.SettingsType.THEMES;
import static java.util.Optional.ofNullable;

@ToString
public class LevelSettings {

  private static final int NUM_ROOMS = 12;
  private static final int LEVEL_RANGE = 80;
  private static final int MINIMUM_SCATTER = 12;

  private int numRooms = NUM_ROOMS;
  private int range = LEVEL_RANGE;
  private int scatter = MINIMUM_SCATTER;
  private int levelDifficulty = -1;
  private RoomsSetting rooms = new RoomsSetting();
  private SecretsSetting secrets = new SecretsSetting();
  private ThemeBase theme;
  private SegmentGenerator segments;
  private SpawnerSettings spawners = new SpawnerSettings();
  private LevelGenerator generator;
  private Set<Filter> filters = new HashSet<>();

  public LevelSettings() {
  }

  public LevelSettings(LevelSettings toCopy) {
    init(toCopy);
  }

  public LevelSettings(LevelSettings parent, LevelSettings child, Set<SettingsType> overrides) {
    if (parent == null && child == null) {
      return;
    }

    if (parent == null) {
      init(child);
      return;
    }

    if (child == null) {
      init(parent);
      return;
    }

    numRooms = child.numRooms != parent.numRooms
        && child.numRooms != NUM_ROOMS
        ? child.numRooms : parent.numRooms;

    range = child.range != parent.range
        && child.range != LEVEL_RANGE
        ? child.range : parent.range;

    setScatter(child.scatter != parent.scatter && child.scatter != MINIMUM_SCATTER
        ? child.scatter : parent.scatter);

    levelDifficulty = (parent.levelDifficulty != child.levelDifficulty
        && child.levelDifficulty != -1) || parent.levelDifficulty == -1
        ? child.levelDifficulty : parent.levelDifficulty;

    if (overrides.contains(ROOMS)) {
      rooms = new RoomsSetting(child.rooms);
    } else {
      rooms = new RoomsSetting(parent.rooms, child.rooms);
    }

    if (overrides.contains(SECRETS)) {
      secrets = new SecretsSetting(child.secrets);
    } else {
      secrets = new SecretsSetting(parent.secrets, child.secrets);
    }

    setTheme(inherit(parent, child, overrides));

    if (parent.segments != null || child.segments != null) {
      segments = child.segments == null ? new SegmentGenerator(parent.segments) : new SegmentGenerator(child.segments);
    }

    spawners = new SpawnerSettings(parent.spawners, child.spawners);
    generator = child.generator == null ? parent.generator : child.generator;

    filters.addAll(parent.filters);
    filters.addAll(child.filters);
  }

  private ThemeBase inherit(LevelSettings parent, LevelSettings child, Set<SettingsType> overrides) {
    boolean isChildThemeAbsent = child.theme == null;
    boolean isParentThemeAbsent = parent.theme == null;
    if (isChildThemeAbsent && isParentThemeAbsent) {
      return null;
    }
    if (isChildThemeAbsent) {
      return parent.theme;
    }
    if (isParentThemeAbsent) {
      return child.theme;
    }
    if (overrides.contains(THEMES)) {
      return child.theme;
    }
    return ThemeBase.inherit(parent.theme, child.theme);
  }

  private void init(LevelSettings toCopy) {
    numRooms = toCopy.numRooms;
    range = toCopy.range;
    scatter = toCopy.scatter;
    levelDifficulty = toCopy.levelDifficulty;
    rooms = toCopy.rooms != null ? new RoomsSetting(toCopy.rooms) : null;
    secrets = toCopy.secrets != null ? new SecretsSetting(toCopy.secrets) : null;
    theme = toCopy.theme;
    segments = toCopy.segments != null ? new SegmentGenerator(toCopy.segments) : null;
    spawners = new SpawnerSettings(toCopy.spawners);
    filters = Sets.newHashSet(toCopy.filters);
    generator = toCopy.generator;
  }

  public LevelGenerator getGenerator() {
    return generator != null ? generator : LevelGenerator.CLASSIC;
  }

  public void setGenerator(LevelGenerator type) {
    generator = type;
  }

  public int getScatter() {
    return scatter;
  }

  public void setScatter(int scatter) {
    this.scatter = Math.max(MINIMUM_SCATTER, scatter);
  }

  public int getNumRooms() {
    return numRooms;
  }

  public void setNumRooms(int num) {
    numRooms = num;
  }

  public int getDifficulty(Coord pos) {

    if (levelDifficulty == -1) {
      return Dungeon.getLevel(pos.getY());
    }

    return levelDifficulty;
  }

  public void setDifficulty(int num) {
    levelDifficulty = num;
  }

  public RoomsSetting getRooms() {
    return rooms != null ? rooms : new RoomsSetting();
  }

  public void setRooms(RoomsSetting rooms) {
    this.rooms = rooms;
  }

  public SecretsSetting getSecrets() {
    return secrets != null ? secrets : new SecretsSetting();
  }

  public void setSecrets(SecretsSetting secrets) {
    this.secrets = secrets;
  }

  public ISegmentGenerator getSegments() {
    return segments != null ? segments : new SegmentGenerator();
  }

  public void setSegments(SegmentGenerator segments) {
    this.segments = segments;
  }

  public ThemeBase getTheme() {
    // return theme;
    // todo: not rely on this class to provide default as it's an inverted dependency
    return theme != null ? theme : Theme.STONE.getThemeBase();
  }

  public void setTheme(ThemeBase theme) {
    this.theme = theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme.getThemeBase();
  }

  public SpawnerSettings getSpawners() {
    return spawners;
  }

  public int getRange() {
    return range;
  }

  public void setRange(int range) {
    this.range = range;
  }

  public List<Filter> getFilters() {
    return new ArrayList<>(filters);
  }

  public void addFilter(Filter filter) {
    filters.add(filter);
  }


  @Override
  public boolean equals(Object o) {
    // I think this is only for tests, which means the behaviour isn't being tested, just the state
    LevelSettings other = (LevelSettings) o;
    if (other.generator != generator) {
      return false;
    }
    if (!secrets.equals(other.secrets)) {
      return false;
    }
    return rooms.equals(other.rooms);
  }
}
