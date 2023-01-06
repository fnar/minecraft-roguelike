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
    this.size = 4;
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    super.generate(origin, entrances);

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

  private void generatePit(Coord at) {
    Coord pit = at.copy().down();

    int layersDepth = random().nextInt(Dungeon.NUM_LAYERS - 1) + 1;
    int blocksDepth = layersDepth * Dungeon.VERTICAL_SPACING;
    int bottomOfPit = Math.max(Dungeon.BOTTOM_OF_WORLD_HEIGHT, pit.getY() - blocksDepth) + 1; // +1 here to align with levels

    Coord wallsCorner0 = pit.copy().north(2).west(2);
    Coord wallsCorner1 = pit.copy().south(2).east(2).setY(bottomOfPit);
    RectSolid wallsRect = RectSolid.newRect(wallsCorner0, wallsCorner1);
    primaryWallBrush().fill(worldEditor, wallsRect, false, true);

    Coord pitCorner0 = pit.copy().north().west();
    Coord pitCorner1 = pit.copy().south().east().setY(bottomOfPit);
    RectSolid pitRect = RectSolid.newRect(pitCorner0, pitCorner1);
    SingleBlockBrush.AIR.fill(worldEditor, pitRect);

    if (random().nextBoolean()) {
      int liquidBlocksDepth = random().nextInt(Dungeon.VERTICAL_SPACING - 3) + 3;
      Coord waterCorner0 = pit.copy().north().west().setY(bottomOfPit + liquidBlocksDepth);
      Coord waterCorner1 = pit.copy().south().east().setY(bottomOfPit);
      RectSolid waterRect = RectSolid.newRect(waterCorner0, waterCorner1);
      primaryLiquidBrush().fill(worldEditor, waterRect);
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

    trapCoords.forEach(block -> primaryWallBrush().stroke(worldEditor, block));

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

}
