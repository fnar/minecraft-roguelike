package greymerk.roguelike.dungeon.base;

import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.block.spawner.SpawnerSettings;
import com.github.fnar.roguelike.worldgen.generatables.thresholds.Threshold;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class BaseRoom {

  private final RoomSetting roomSetting;
  protected final LevelSettings levelSettings;
  protected final WorldEditor worldEditor;
  // wallsDist is how far out to go to draw the walls
  protected int wallDist = 9;
  // depth is how far down to move to draw the floor
  protected int depth = 1;
  // ceiling height is the distance from the center origin to ceiling
  protected int ceilingHeight = Dungeon.VERTICAL_SPACING - depth - 1;

  public BaseRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    this.roomSetting = roomSetting;
    this.levelSettings = levelSettings;
    this.worldEditor = worldEditor;
  }

  public BaseRoom generate(Coord at, List<Direction> entrances) {
    generateWalls(at, entrances);
    generateFloor(at, entrances);
    generateCeiling(at, entrances);
    generateDecorations(at, entrances);
    generateDoorways(at, entrances);
    return this;
  }

  protected void generateWalls(Coord at, List<Direction> entrances) {
    IShape wallsRect = at.newHollowRect(getWallDist())
        .withHeight(getHeight())
        .down(getDepth());
    primaryWallBrush().fill(worldEditor, wallsRect, false, true);
  }

  protected void generateFloor(Coord at, List<Direction> entrances) {
    primaryFloorBrush().fill(worldEditor, at.newRect(getWallDist()).down(getDepth()));
  }

  protected void generateCeiling(Coord at, List<Direction> entrances) {
    primaryWallBrush().fill(worldEditor, at.newRect(getWallDist()).up(getCeilingHeight()));
  }

  protected void generateDecorations(Coord at, List<Direction> entrances) {

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
    MobType[] mobTypes = defaultMobs.length > 0 ? defaultMobs : MobType.COMMON_MOBS;
    Spawner spawner = chooseSpawner(mobTypes);
    worldEditor.generateSpawner(spawner, spawnerLocation);
  }

  private Spawner chooseSpawner(MobType[] mobTypes) {
    Optional<SpawnerSettings> roomSpawnerSettings = getSpawnerSettings(roomSetting.getSpawnerId());
    if (roomSpawnerSettings.isPresent()) {
      SpawnerSettings spawnerSettings = roomSpawnerSettings.get();
      if (!spawnerSettings.isEmpty()) {
        return spawnerSettings.chooseOneAtRandom(random());
      }
    }
    SpawnerSettings levelSpawnerSettings = levelSettings.getSpawnerSettings();
    if (!levelSpawnerSettings.isEmpty()) {
      return levelSpawnerSettings.chooseOneAtRandom(random());
    }
    return MobType.asSpawner(mobTypes);
  }

  private Optional<SpawnerSettings> getSpawnerSettings(String spawnerId) {
    try {
      if (spawnerId != null) {
        return Optional.ofNullable(SettingsResolver.instance.getByName(spawnerId))
            .map(dungeonSettings -> dungeonSettings.getLevelSettings(levelSettings.getLevel()))
            .map(LevelSettings::getSpawnerSettings);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  protected ChestType getChestTypeOrUse(ChestType defaultChestType) {
    return getRoomSetting().getChestType().orElse(defaultChestType);
  }

  protected void theFloorIsLava(Coord origin) {
    primaryLiquidBrush().fill(worldEditor, RectSolid.newRect(
        origin.copy().north(getWallDist()).west(getWallDist()).down(),
        origin.copy().south(getWallDist()).east(getWallDist()).down(depth)
    ), true, false);
  }

  protected Theme theme() {
    return levelSettings.getTheme();
  }

  protected BlockSet primaryTheme() {
    return theme().getPrimary();
  }

  protected BlockSet secondaryTheme() {
    return theme().getSecondary();
  }

  protected BlockBrush primaryFloorBrush() {
    return primaryTheme().getFloor();
  }

  protected BlockBrush primaryLightBrush() {
    return primaryTheme().getLightBlock();
  }

  protected BlockBrush primaryLiquidBrush() {
    return primaryTheme().getLiquid();
  }

  protected BlockBrush primaryPillarBrush() {
    return primaryTheme().getPillar();
  }

  protected StairsBlock primaryStairBrush() {
    return primaryTheme().getStair();
  }

  protected BlockBrush primaryWallBrush() {
    return primaryTheme().getWall();
  }

  protected BlockBrush secondaryFloorBrush() {
    return secondaryTheme().getFloor();
  }

  protected BlockBrush secondaryLightBrush() {
    return secondaryTheme().getLightBlock();
  }

  protected BlockBrush secondaryLiquidBrush() {
    return secondaryTheme().getLiquid();
  }

  protected BlockBrush secondaryPillarBrush() {
    return secondaryTheme().getPillar();
  }

  protected StairsBlock secondaryStairBrush() {
    return secondaryTheme().getStair();
  }

  protected BlockBrush secondaryWallBrush() {
    return secondaryTheme().getWall();
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

  public final int getSize() {
    // the 1 here is the center origin of the room
    return 1 + getWallDist();
  }

  protected final int getWallDist() {
    return wallDist;
  }

  public final int getHeight() {
    int depth = getDepth();
    int ceilingHeight = getCeilingHeight();
    return depth + (depth > 0 ? 1 : 0) +
        ceilingHeight + (ceilingHeight > 0 ? 1 : 0);
  }

  protected final int getCeilingHeight() {
    return ceilingHeight;
  }

  protected final int getDepth() {
    return depth;
  }

  public boolean isValidLocation(Coord at, Direction facing) {
    return at.newHollowRect(getWallDist())
        .withHeight(getHeight())
        .down(getDepth())
        .asList().stream()
        .allMatch(worldEditor::isSolidBlock);
  }

  protected RoomSetting getRoomSetting() {
    return roomSetting;
  }
}
