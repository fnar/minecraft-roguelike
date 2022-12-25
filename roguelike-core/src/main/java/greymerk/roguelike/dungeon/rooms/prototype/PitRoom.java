package greymerk.roguelike.dungeon.rooms.prototype;

import com.google.common.collect.Lists;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.TorchBlock;

import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PitRoom extends BaseRoom {

  public PitRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    generateWalls(origin);
    generateFloor(origin);
    generateCeiling(origin);
    generateDoorways(origin, entrances, getSize()-1);
    generatePit(origin);
    generateTraps(origin, entrances);
    generateChest(origin, entrances);
    return this;
  }

  private void generateChest(Coord origin, List<Direction> entrances) {
    Coord chest = origin.copy()
        .north(random().nextBoolean() ? 2 : -2)
        .east(random().nextBoolean() ? 2 : -2);

    generateTrappableChest(chest, getEntrance(entrances));
  }

  protected void generateWalls(Coord at) {
    int size = getSize();

    Coord hollow = at.copy();
    Coord hollowNWCorner = hollow.copy().north(size - 2).west(size - 2);
    Coord hollowSECorner = hollow.copy().south(size - 2).east(size - 2).up(size);
    List<Coord> hollowCoords = RectSolid.newRect(hollowNWCorner, hollowSECorner).asList();
    SingleBlockBrush.AIR.fill(worldEditor, hollowCoords);

    Coord walls = at.copy().down();
    Coord wallsNWCorner = walls.copy().north(size - 1).west(size - 1);
    Coord wallsSECorner = walls.copy().south(size - 1).east(size - 1).up(size);
    List<Coord> wallsCoords = RectSolid.newRect(wallsNWCorner, wallsSECorner).asList();
    wallsCoords.removeAll(hollowCoords);
    walls().fill(worldEditor, wallsCoords, false, true);
  }

  protected void generateFloor(Coord at) {
    Coord floor = at.copy().down();
    Coord nwCorner = floor.copy().north(getSize()).west(getSize());
    Coord seCorner = floor.copy().south(getSize()).east(getSize());
    floors().fill(worldEditor, RectSolid.newRect(nwCorner, seCorner));
  }

  protected void generateCeiling(Coord at) {
    Coord ceiling = at.copy().up(getSize());
    Coord nwCorner = ceiling.copy().north(getSize()).west(getSize());
    Coord seCorner = ceiling.copy().south(getSize()).east(getSize());
    walls().fill(worldEditor, RectSolid.newRect(nwCorner, seCorner));
  }

  private void generatePit(Coord at) {
    Coord pit = at.copy().down();

    int layersDepth = random().nextInt(Dungeon.NUM_LAYERS - 1) + 1;
    int blocksDepth = layersDepth * Dungeon.VERTICAL_SPACING;
    int bottomOfPit = Math.max(Dungeon.BOTTOM_OF_WORLD_HEIGHT, pit.getY() - blocksDepth) + 1; // +1 here to align with levels

    Coord wallsCorner0 = pit.copy().north(2).west(2);
    Coord wallsCorner1 = pit.copy().south(2).east(2).setY(bottomOfPit);
    RectSolid wallsRect = RectSolid.newRect(wallsCorner0, wallsCorner1);
    walls().fill(worldEditor, wallsRect, false, true);

    Coord pitCorner0 = pit.copy().north().west();
    Coord pitCorner1 = pit.copy().south().east().setY(bottomOfPit);
    RectSolid pitRect = RectSolid.newRect(pitCorner0, pitCorner1);
    SingleBlockBrush.AIR.fill(worldEditor, pitRect);

    if (random().nextBoolean()) {
      int liquidBlocksDepth = random().nextInt(Dungeon.VERTICAL_SPACING - 3) + 3;
      Coord waterCorner0 = pit.copy().north().west().setY(bottomOfPit + liquidBlocksDepth);
      Coord waterCorner1 = pit.copy().south().east().setY(bottomOfPit);
      RectSolid waterRect = RectSolid.newRect(waterCorner0, waterCorner1);
      liquid().fill(worldEditor, waterRect);
    }
  }

  private void generateTraps(Coord origin, List<Direction> entrances) {
    List<Direction> trapDirections = Lists.newArrayList(Direction.CARDINAL);
    trapDirections.removeAll(entrances);
    trapDirections.forEach(dir -> setTrap(origin, dir));
  }

  private void setTrap(Coord at, Direction outward) {
    double trapChance = 0.20;
    if (random().nextDouble() >= trapChance) {
      return;
    }
    Coord start = at.copy().translate(outward, 3).translate(outward.left()).down();
    Coord end = at.copy().translate(outward, 6).translate(outward.right()).up(3);
    RectSolid trapRect = RectSolid.newRect(start, end);
    List<Coord> trapCoords = trapRect.asList();

    boolean isValidTrapLocation = trapCoords.stream().noneMatch(worldEditor::isAirBlock);
    if (!isValidTrapLocation) {
      return;
    }

    trapCoords.forEach(block -> walls().stroke(worldEditor, block));

    Coord cursor = at.copy().translate(outward, 2);
    BlockType.PRESSURE_PLATE_STONE.getBrush().stroke(worldEditor, cursor);

    cursor = cursor.translate(outward).down();
    TorchBlock.redstone().setFacing(outward).stroke(worldEditor, cursor);

    cursor.translate(outward);
    BlockType.REDSTONE_WIRE.getBrush().stroke(worldEditor, cursor);

    cursor.translate(outward).up();
    TorchBlock.redstone().extinguish().setFacing(Direction.UP).stroke(worldEditor, cursor);

    cursor.translate(outward.reverse()).up();
    BlockType.STICKY_PISTON.getBrush().setFacing(outward).stroke(worldEditor, cursor);
  }

  public int getSize() {
    return 4;
  }
}
