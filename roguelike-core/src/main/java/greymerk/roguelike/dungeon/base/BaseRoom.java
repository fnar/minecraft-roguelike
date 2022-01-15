package greymerk.roguelike.dungeon.base;

import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.block.spawner.SpawnerSettings;
import com.github.fnar.minecraft.worldgen.generatables.Doorways;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import lombok.EqualsAndHashCode;

import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

@EqualsAndHashCode
public abstract class BaseRoom implements Comparable<BaseRoom> {

  private final RoomSetting roomSetting;
  protected final LevelSettings levelSettings;
  protected final WorldEditor worldEditor;

  public BaseRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    this.roomSetting = roomSetting;
    this.levelSettings = levelSettings;
    this.worldEditor = worldEditor;
  }

  public List<Coord> chooseRandomLocations(int limit, List<Coord> spaces) {
    shuffle(spaces, worldEditor.getRandom());

    return spaces.stream()
        .limit(limit)
        .collect(toList());
  }

  public abstract BaseRoom generate(Coord origin, List<Direction> entrances);

  protected void generateDoorways(Coord origin, List<Direction> entrances) {
    generateDoorways(origin, entrances, getSize()-2);
  }

  protected void generateDoorways(Coord origin, List<Direction> entrances, int distanceFromOrigin) {
    entrances.forEach(direction ->
        Doorways.generateDoorway(
            worldEditor,
            levelSettings,
            origin.copy().translate(direction, distanceFromOrigin),
            direction));
  }

  protected void generateSpawner(Coord spawnerLocation, MobType... defaultMobs) {
    int difficulty = levelSettings.getDifficulty(spawnerLocation);
    generateSpawner(spawnerLocation, difficulty, defaultMobs);
  }

  private void generateSpawner(Coord spawnerLocation, int difficulty, MobType... defaultMobs) {
    Spawner spawner = chooseSpawner(difficulty, defaultMobs, worldEditor.getRandom());
    generateSpawnerSafe(worldEditor, spawner, spawnerLocation, difficulty);
  }

  private Spawner chooseSpawner(int difficulty, MobType[] defaultMobs, Random random) {
    Optional<SpawnerSettings> roomSpawnerSettings = getSpawnerSettings(roomSetting.getSpawnerId(), difficulty);
    SpawnerSettings levelSpawnerSettings = levelSettings.getSpawnerSettings();
    if (roomSpawnerSettings.isPresent()) {
      return roomSpawnerSettings.get().chooseOneAtRandom(random);
    }
    if (!levelSpawnerSettings.isEmpty()) {
      return levelSpawnerSettings.chooseOneAtRandom(worldEditor.getRandom());
    }
    return MobType.asSpawner(defaultMobs.length > 0 ? defaultMobs : MobType.COMMON_MOBS);
  }

  private static Optional<SpawnerSettings> getSpawnerSettings(String spawnerId, int difficulty) {
    if (spawnerId == null) {
      return Optional.empty();
    }
    DungeonSettings dungeonSettings = null;
    try {
      dungeonSettings = Dungeon.settingsResolver.getByName(spawnerId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (dungeonSettings == null) {
      return Optional.empty();
    }
    SpawnerSettings spawnerSettings = dungeonSettings.getLevelSettings(difficulty).getSpawnerSettings();
    if (spawnerSettings == null || spawnerSettings.isEmpty()) {
       return Optional.empty();
    }
    return Optional.of(spawnerSettings);
  }

  public static void generateSpawnerSafe(WorldEditor editor, Spawner spawner, Coord cursor, int difficulty) {
    try {
      editor.generateSpawner(spawner, cursor, difficulty);
    } catch (Exception e) {
      throw new RuntimeException("Tried to spawn empty spawner", e);
    }
  }

  public abstract int getSize();

  public boolean validLocation(WorldEditor editor, Direction dir, Coord pos) {

    int size = getSize();
    Coord start = new Coord(pos.getX() - size, pos.getY() - 2, pos.getZ() - size);
    Coord end = new Coord(pos.getX() + size, pos.getY() + 5, pos.getZ() + size);

    for (Coord cursor : new RectHollow(start, end)) {
      if (!editor.isSolidBlock(cursor)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int compareTo(BaseRoom other) {
    return getSize() - other.getSize();
  }

  protected RoomSetting getRoomSetting() {
    return roomSetting;
  }
}
