package greymerk.roguelike.dungeon.base;

import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.block.spawner.SpawnerSettings;
import com.github.fnar.roguelike.worldgen.generatables.thresholds.Threshold;

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

  public static final int ENCASING_SIZE = 1;

  private final RoomSetting roomSetting;
  protected final LevelSettings levelSettings;
  protected final WorldEditor worldEditor;
  protected int size = 5;
  protected int height = Dungeon.VERTICAL_SPACING;
  protected int depth = 2;

  public BaseRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    this.roomSetting = roomSetting;
    this.levelSettings = levelSettings;
    this.worldEditor = worldEditor;
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    generateWalls(origin);
    generateFloor(origin);
    generateCeiling(origin);
    generateDecorations(origin);
    generateDoorways(origin, entrances);
    return this;
  }

  protected void generateWalls(Coord at) {
    walls().fill(worldEditor, at.copy().down(depth).newHollowRect(getWallDist()).withHeight(getHeight()));
  }

  protected void generateFloor(Coord at) {
    floors().fill(worldEditor, at.newRect(getWallDist()).down());
  }

  protected void generateCeiling(Coord at) {
    walls().fill(worldEditor, at.newRect(getWallDist()).up(getCeilingHeight()));
  }

  protected void generateDecorations(Coord origin) {

  }

  protected void generateDoorways(Coord origin, List<Direction> entrances) {
    generateDoorways(origin, entrances, getWallDist());
  }

  protected void generateDoorways(Coord origin, List<Direction> entrances, int distanceFromOrigin) {
    entrances.forEach(direction ->
        Threshold.generateDoorway(
            worldEditor,
            levelSettings,
            origin.copy().translate(direction, distanceFromOrigin),
            direction));
  }

  protected void generateSpawner(Coord spawnerLocation, MobType... defaultMobs) {
    int level = getLevel(spawnerLocation);
    generateSpawner(spawnerLocation, level, defaultMobs);
  }

  protected void generateSpawner(Coord spawnerLocation) {
    int level = getLevel(spawnerLocation);
    generateSpawner(spawnerLocation, level, MobType.COMMON_MOBS);
  }

  protected int getLevel(Coord coord) {
    return levelSettings.getLevel(coord);
  }

  private void generateSpawner(Coord spawnerLocation, int level, MobType... defaultMobs) {
    Spawner spawner = chooseSpawner(level, defaultMobs, random());
    generateSpawnerSafe(worldEditor, spawner, spawnerLocation, level);
  }

  private Spawner chooseSpawner(int level, MobType[] defaultMobs, Random random) {
    Optional<SpawnerSettings> roomSpawnerSettings = getSpawnerSettings(roomSetting.getSpawnerId(), level);
    SpawnerSettings levelSpawnerSettings = levelSettings.getSpawnerSettings();
    if (roomSpawnerSettings.isPresent()) {
      return roomSpawnerSettings.get().chooseOneAtRandom(random);
    }
    if (!levelSpawnerSettings.isEmpty()) {
      return levelSpawnerSettings.chooseOneAtRandom(random());
    }
    return MobType.asSpawner(defaultMobs.length > 0 ? defaultMobs : MobType.COMMON_MOBS);
  }

  private static Optional<SpawnerSettings> getSpawnerSettings(String spawnerId, int level) {
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
    SpawnerSettings spawnerSettings = dungeonSettings.getLevelSettings(level).getSpawnerSettings();
    if (spawnerSettings == null || spawnerSettings.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(spawnerSettings);
  }

  public static void generateSpawnerSafe(WorldEditor editor, Spawner spawner, Coord cursor, int level) {
    try {
      editor.generateSpawner(spawner, cursor, level);
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
        .withTrap(getLevel(cursor))
        .stroke(worldEditor, cursor);
  }

  private TreasureChest chest(Coord cursor, Direction facing, ChestType defaultChestType) {
    return new TreasureChest(cursor, worldEditor)
        .withChestType(getChestTypeOrUse(defaultChestType))
        .withFacing(facing);
  }

  protected void theFloorIsLava(Coord origin) {
    liquid().fill(worldEditor, RectSolid.newRect(
        origin.copy().north(getWallDist()).west(getWallDist()).down(),
        origin.copy().south(getWallDist()).east(getWallDist()).down(2)
    ), true, false);
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

  public int getSize() {
    return size;
  }

  protected final int getWallDist() {
    return getSize() - ENCASING_SIZE;
  }

  public final int getHeight() {
    // todo: break dependents on getHeight(), specifically NetherBrick room
    // todo: return getCeilingHeight() + depth + ENCASING_SIZE;
    return height;
  }

  protected final int getCeilingHeight() {
    // TODO: this relationship should be inverted.
    // Currently, if the depth is increased, the ceiling will fall.
    // Instead, if the depth is increased, the ceiling should remain where it is and the room height should increase.
    return getHeight() - depth - ENCASING_SIZE;
  }

  public boolean isValidLocation(Coord at, Direction facing) {
    int size = getSize();
    Coord cornerNW = at.copy().north(size).west(size).up(getHeight());
    Coord cornerSE = at.copy().south(size).east(size).down(2);
    RectHollow bounds = RectHollow.newRect(cornerNW, cornerSE);
    return bounds.asList().stream().allMatch(worldEditor::isSolidBlock);
  }

  @Override
  public int compareTo(BaseRoom other) {
    return getSize() - other.getSize();
  }

  protected RoomSetting getRoomSetting() {
    return roomSetting;
  }
}
