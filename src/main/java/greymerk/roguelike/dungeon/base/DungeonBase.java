package greymerk.roguelike.dungeon.base;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;
import lombok.EqualsAndHashCode;

import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

@EqualsAndHashCode
public abstract class DungeonBase implements Comparable<DungeonBase> {

  private RoomSetting roomSetting;

  public DungeonBase() {
  }

  public DungeonBase(RoomSetting roomSetting) {
    this.roomSetting = roomSetting;
  }

  public static List<Coord> chooseRandomLocations(Random random, int limit, List<Coord> spaces) {
    shuffle(spaces, random);

    return spaces.stream()
        .limit(limit)
        .collect(toList());
  }

  public abstract DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances);

  protected void generateSpawner(WorldEditor editor, Random rand, Coord spawnerLocation, int difficulty, SpawnerSettings levelSettingsSpawners, MobType... defaultMobs) {
    getSpawnerSettings(difficulty, defaultMobs, levelSettingsSpawners).generateSpawner(editor, rand, spawnerLocation, difficulty);
  }

  private SpawnerSettings getSpawnerSettings(int difficulty, MobType[] defaultMobs, SpawnerSettings levelSettingsSpawners) {
    String spawnerId = roomSetting.getSpawnerId();
    if (spawnerId != null) {
      DungeonSettings dungeonSettings = Dungeon.settingsResolver.getByName(spawnerId);
      if (dungeonSettings != null) {
        SpawnerSettings dungeonSettingsSpawners = dungeonSettings.getLevelSettings(difficulty).getSpawners();
        if (!dungeonSettingsSpawners.isEmpty()) {
          return dungeonSettingsSpawners;
        }
      }
    }
    return !levelSettingsSpawners.isEmpty()
        ? levelSettingsSpawners
        : MobType.newSpawnerSetting(defaultMobs.length > 0 ? defaultMobs : MobType.COMMON_MOBS);
  }

  public abstract int getSize();

  public boolean validLocation(WorldEditor editor, Cardinal dir, Coord pos) {

    int size = getSize();
    Coord start = new Coord(pos.getX() - size, pos.getY() - 2, pos.getZ() - size);
    Coord end = new Coord(pos.getX() + size, pos.getY() + 5, pos.getZ() + size);

    for (Coord cursor : new RectHollow(start, end)) {
      MetaBlock b = editor.getBlock(cursor);
      if (!b.getMaterial().isSolid()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int compareTo(DungeonBase other) {
    return getSize() - other.getSize();
  }

  protected RoomSetting getRoomSetting() {
    return roomSetting;
  }
}
