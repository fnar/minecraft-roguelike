package greymerk.roguelike.dungeon.settings;

import com.google.common.collect.Sets;

import com.github.fnar.minecraft.block.spawner.SpawnerSettings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.Themes;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.filter.Filter;
import lombok.ToString;

import static greymerk.roguelike.dungeon.settings.SettingsType.ROOMS;
import static greymerk.roguelike.dungeon.settings.SettingsType.SECRETS;
import static greymerk.roguelike.dungeon.settings.SettingsType.THEMES;

@ToString
public class LevelSettings {

  private static final int NUM_ROOMS = 12;
  private static final int LEVEL_RANGE = 80;
  private static final int MINIMUM_SCATTER = 12;
  private static final int DEFAULT_LEVEL = -1;

  private int numRooms = NUM_ROOMS;
  private int range = LEVEL_RANGE;
  private int scatter = MINIMUM_SCATTER;
  private int level = DEFAULT_LEVEL;
  private RoomsSetting rooms = new RoomsSetting();
  private SecretsSetting secrets = new SecretsSetting();
  private Theme theme;
  private SegmentGenerator segments = new SegmentGenerator();
  private SpawnerSettings spawners = new SpawnerSettings();
  private LevelGenerator generator;
  private Set<Filter> filters = new HashSet<>();

  public LevelSettings() {
  }

  public LevelSettings(LevelSettings toCopy) {
    init(toCopy);
  }

  public LevelSettings inherit(LevelSettings parent, Set<SettingsType> overrides) {
    numRooms = numRooms != parent.numRooms && numRooms != NUM_ROOMS
        ? numRooms
        : parent.numRooms;

    range = range != parent.range && range != LEVEL_RANGE
        ? range
        : parent.range;

    setScatter(scatter != parent.scatter && scatter != MINIMUM_SCATTER
        ? scatter
        : parent.scatter);

    level = (parent.level != level && level != -1) || parent.level == -1
        ? level
        : parent.level;

    rooms = overrides.contains(ROOMS) ? new RoomsSetting(rooms) : rooms.inherit(parent.rooms);

    setSecrets(overrides.contains(SECRETS) ? new SecretsSetting(secrets) : new SecretsSetting(parent.secrets, secrets));

    setTheme(chooseTheme(parent, overrides));

    segments = segments.inherit(parent.segments);

    spawners = new SpawnerSettings(parent.spawners, spawners);
    generator = generator == null ? parent.generator : generator;

    filters.addAll(parent.filters);
    return this;
  }

  private Theme chooseTheme(LevelSettings parent, Set<SettingsType> overrides) {
    if (overrides.contains(THEMES) || parent.theme == null) {
      return this.theme;
    }
    if (this.theme == null) {
      return parent.theme;
    }
    return Theme.inherit(parent.theme, this.theme);
  }

  private void init(LevelSettings toCopy) {
    numRooms = toCopy.numRooms;
    range = toCopy.range;
    scatter = toCopy.scatter;
    level = toCopy.level;
    rooms = toCopy.rooms != null ? new RoomsSetting(toCopy.rooms) : null;
    secrets = toCopy.secrets != null ? new SecretsSetting(toCopy.secrets) : null;
    theme = toCopy.theme;
    segments.add(toCopy.segments);
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

  public int getLevel(Coord pos) {
    if (level == -1) {
      level = Dungeon.getLevel(pos.getY());
    }
    return level;
  }

  public void setLevel(int num) {
    level = num;
  }

  public RoomsSetting getRooms() {
    return rooms;
  }

  public void setRooms(RoomsSetting rooms) {
    this.rooms = rooms;
  }

  public SecretsSetting getSecrets() {
    return secrets;
  }

  public void setSecrets(SecretsSetting secrets) {
    this.secrets = secrets;
  }

  public SegmentGenerator getSegments() {
    return segments;
  }

  public void setSegments(SegmentGenerator segments) {
    this.segments = segments;
  }

  public Theme getTheme() {
    // todo: not rely on this class to provide default as it's an inverted dependency
    return theme != null ? theme : Themes.STONE.getThemeBase();
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  public void setTheme(Themes theme) {
    this.theme = theme.getThemeBase();
  }

  public SpawnerSettings getSpawnerSettings() {
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
