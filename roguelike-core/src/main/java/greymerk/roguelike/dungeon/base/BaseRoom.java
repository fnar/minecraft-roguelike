package greymerk.roguelike.dungeon.base;

import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.block.spawner.SpawnerSettings;
import com.github.fnar.minecraft.worldgen.generatables.thresholds.Doorways;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;

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

  public abstract BaseRoom generate(Coord origin, List<Direction> entrances);

  protected void generateDoorways(Coord origin, List<Direction> entrances) {
    generateDoorways(origin, entrances, getSize() - 2);
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
    int difficulty = getDifficulty(spawnerLocation);
    generateSpawner(spawnerLocation, difficulty, defaultMobs);
  }

  protected void generateSpawner(Coord spawnerLocation) {
    int difficulty = getDifficulty(spawnerLocation);
    generateSpawner(spawnerLocation, difficulty, MobType.COMMON_MOBS);
  }

  protected int getDifficulty(Coord coord) {
    return levelSettings.getDifficulty(coord);
  }

  private void generateSpawner(Coord spawnerLocation, int difficulty, MobType... defaultMobs) {
    Spawner spawner = chooseSpawner(difficulty, defaultMobs, random());
    generateSpawnerSafe(worldEditor, spawner, spawnerLocation, difficulty);
  }

  private Spawner chooseSpawner(int difficulty, MobType[] defaultMobs, Random random) {
    Optional<SpawnerSettings> roomSpawnerSettings = getSpawnerSettings(roomSetting.getSpawnerId(), difficulty);
    SpawnerSettings levelSpawnerSettings = levelSettings.getSpawnerSettings();
    if (roomSpawnerSettings.isPresent()) {
      return roomSpawnerSettings.get().chooseOneAtRandom(random);
    }
    if (!levelSpawnerSettings.isEmpty()) {
      return levelSpawnerSettings.chooseOneAtRandom(random());
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

  protected ChestType getChestTypeOrUse(ChestType defaultChestType) {
    return getRoomSetting().getChestType().orElse(defaultChestType);
  }

  protected void generateChests(List<Coord> chestLocations, Direction facing) {
    generateChests(chestLocations, facing, ChestType.COMMON_TREASURES);
  }

  protected void generateChests(List<Coord> chestLocations, Direction facing, ChestType... defaultChestTypes) {
    chestLocations.forEach(chestLocation ->
        generateChest(chestLocation, facing, defaultChestTypes));
  }

  protected void generateChest(Coord cursor, Direction facing, ChestType... defaultChestType) {
    generateChest(cursor, facing, ChestType.chooseRandomAmong(random(), defaultChestType));
  }

  protected void generateChest(Coord coord) {
    generateChest(coord, Direction.randomCardinal(worldEditor.getRandom()));
  }

  protected void generateChest(Coord cursor, Direction facing) {
    generateChest(cursor, facing, ChestType.chooseRandomAmong(random(), ChestType.COMMON_TREASURES));
  }

  protected Optional<TreasureChest> generateChest(Coord coord, Direction facing, ChestType defaultChestType) {
    return chest(coord, facing, defaultChestType)
        .stroke(worldEditor, coord);
  }

  protected void generateTrappedChest(Coord cursor, Direction facing, ChestType... defaultChestType) {
    generateTrappedChest(cursor, facing, ChestType.chooseRandomAmong(random(), defaultChestType));
  }

  protected Optional<TreasureChest> generateTrappedChest(Coord cursor, Direction facing, ChestType defaultChestType) {
    return chest(cursor, facing, defaultChestType)
        .withTrap(true)
        .stroke(worldEditor, cursor);
  }

  protected List<Optional<TreasureChest>> generateTrappableChests(List<Coord> chestLocations, Direction facing) {
    return generateTrappableChests(chestLocations, facing, ChestType.COMMON_TREASURES);
  }

  protected List<Optional<TreasureChest>> generateTrappableChests(List<Coord> chestLocations, Direction facing, ChestType... defaultChestTypes) {
    return chestLocations.stream()
        .map(chestLocation -> generateTrappableChest(chestLocation, facing, defaultChestTypes))
        .collect(Collectors.toList());
  }

  protected Optional<TreasureChest> generateTrappableChest(Coord cursor, Direction facing) {
    return generateTrappableChest(cursor, facing, ChestType.COMMON_TREASURES);
  }

  protected Optional<TreasureChest> generateTrappableChest(Coord cursor, Direction facing, ChestType... defaultChestType) {
    return generateTrappableChest(cursor, facing, ChestType.chooseRandomAmong(random(), defaultChestType));
  }

  protected Optional<TreasureChest> generateTrappableChest(Coord cursor, Direction facing, ChestType defaultChestType) {
    return chest(cursor, facing, defaultChestType)
        .withTrapBasedOnDifficulty(getDifficulty(cursor))
        .stroke(worldEditor, cursor);
  }

  private TreasureChest chest(Coord cursor, Direction facing, ChestType defaultChestType) {
    return new TreasureChest(cursor, worldEditor)
        .withChestType(getChestTypeOrUse(defaultChestType))
        .withFacing(facing);
  }

  protected void theFloorIsLava(Coord origin, Direction front) {
    RectSolid.newRect(
        origin.copy().translate(front, getSize() - 1).translate(front.left(), getSize() - 1).down(),
        origin.copy().translate(front.back(), getSize() - 1).translate(front.right(), getSize() - 1).down(2)
    ).fill(worldEditor, liquid(), true, false);
  }

  protected Theme theme() {
    return levelSettings.getTheme();
  }

  protected BlockBrush floors() {
    return theme().getPrimary().getFloor();
  }

  protected BlockBrush lights() {
    return theme().getPrimary().getLightBlock();
  }

  protected BlockBrush liquid() {
    return theme().getPrimary().getLiquid();
  }

  protected BlockBrush pillars() {
    return theme().getPrimary().getPillar();
  }

  protected StairsBlock stairs() {
    return theme().getPrimary().getStair();
  }

  protected BlockBrush walls() {
    return theme().getPrimary().getWall();
  }

  protected BlockBrush secondaryPillars() {
    return theme().getSecondary().getPillar();
  }

  protected StairsBlock secondaryStairs() {
    return theme().getSecondary().getStair();
  }

  protected BlockBrush secondaryWalls() {
    return theme().getSecondary().getWall();
  }

  protected BlockBrush secondaryFloors() {
    return theme().getSecondary().getFloor();
  }

  protected Random random() {
    return worldEditor.getRandom();
  }

  protected Direction getEntrance(List<Direction> entrances) {
    return entrances.get(0);
  }

  protected Coord generateChestLocation(Coord origin) {
    Direction dir0 = Direction.randomCardinal(random());
    Direction dir1 = dir0.orthogonals()[random().nextBoolean() ? 0 : 1];
    return origin.copy()
        .translate(dir0, 3)
        .translate(dir1, 2);
  }

  public abstract int getSize();

  public boolean isValidLocation(Direction dir, Coord pos) {

    int size = getSize();
    Coord start = new Coord(pos.getX() - size, pos.getY() - 2, pos.getZ() - size);
    Coord end = new Coord(pos.getX() + size, pos.getY() + 5, pos.getZ() + size);

    for (Coord cursor : new RectHollow(start, end)) {
      if (!worldEditor.isSolidBlock(cursor)) {
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
