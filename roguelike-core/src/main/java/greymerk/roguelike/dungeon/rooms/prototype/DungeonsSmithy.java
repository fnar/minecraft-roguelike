package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.AnvilBlock;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.List;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsSmithy extends BaseRoom {

  public DungeonsSmithy(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(origin, entrances.stream().map(Direction::reverse).collect(Collectors.toList()));
    return this;
  }

  private void generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(Coord origin, List<Direction> entrances) {
    Direction entranceDirection = getEntrance(entrances);

    clearBoxes(entranceDirection, origin);

    Coord cursor = origin.copy();
    cursor.translate(entranceDirection, 6);
    sideRoom(entranceDirection, cursor);
    anvilRoom(entranceDirection, cursor);

    cursor = origin.copy();
    cursor.translate(entranceDirection.reverse(), 6);
    sideRoom(entranceDirection, cursor);

    cursor = origin.copy();
    cursor.translate(entranceDirection.reverse(), 9);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    mainRoom(entranceDirection, origin);
  }

  private void sideRoom(Direction entranceDirection, Coord origin) {
    Coord cursor;
    for (Direction side : entranceDirection.orthogonals()) {

      Coord start = origin.copy();
      start.up(3);
      Coord end = start.copy();
      start.translate(side, 2);
      start.translate(entranceDirection.reverse(), 2);
      end.translate(side, 3);
      end.translate(entranceDirection, 2);
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

      start.translate(entranceDirection);
      end = start.copy();
      end.translate(entranceDirection, 2);
      primaryStairBrush().setUpsideDown(true).setFacing(side.reverse()).fill(worldEditor, RectSolid.newRect(start, end));

      for (Direction o : side.orthogonals()) {
        start = origin.copy();
        start.translate(side, 3);
        start.translate(o, 2);
        end = start.copy();
        end.up(2);
        primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));

        cursor = end.copy();
        cursor.translate(side.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(side.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        cursor.translate(side.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(side.reverse()).stroke(worldEditor, cursor);

        cursor = end.copy();
        cursor.translate(o.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      }
    }

    cursor = origin.copy();
    cursor.up(4);
    overheadLight(cursor);
  }

  private void clearBoxes(Direction entranceDirection, Coord origin) {
    // end room
    Coord cursor = origin.copy();
    cursor.translate(entranceDirection, 6);

    Coord start = cursor.copy();
    start.down();
    start.translate(entranceDirection, 3);
    start.translate(entranceDirection.antiClockwise(), 4);

    Coord end = cursor.copy();
    end.up(4);
    end.translate(entranceDirection.reverse(), 3);
    end.translate(entranceDirection.clockwise(), 4);

    RectHollow.newRect(start, end).fill(worldEditor, primaryWallBrush());

    // entrance
    cursor = origin.copy();
    cursor.translate(entranceDirection.reverse(), 6);

    start = cursor.copy();
    start.down();
    start.translate(entranceDirection, 3);
    start.translate(entranceDirection.antiClockwise(), 4);

    end = cursor.copy();
    end.up(4);
    end.translate(entranceDirection.reverse(), 3);
    end.translate(entranceDirection.clockwise(), 4);

    RectHollow.newRect(start, end).fill(worldEditor, primaryWallBrush());

    // middle

    start = origin.copy();
    start.down();
    start.translate(entranceDirection.antiClockwise(), 6);
    start.translate(entranceDirection.reverse(), 4);

    end = origin.copy();
    end.up(6);
    end.translate(entranceDirection.clockwise(), 6);
    end.translate(entranceDirection, 4);

    RectHollow.newRect(start, end).fill(worldEditor, primaryWallBrush(), false, true);

  }

  private void mainRoom(Direction entranceDirection, Coord origin) {
    Coord start = origin.copy();
    start.translate(entranceDirection, 3);
    start.up(4);
    Coord end = start.copy();
    start.translate(entranceDirection.antiClockwise(), 5);
    end.translate(entranceDirection.clockwise(), 5);
    end.up();
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
    start.translate(entranceDirection.reverse(), 6);
    end.translate(entranceDirection.reverse(), 6);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    Coord cursor;
    for (Direction side : entranceDirection.orthogonals()) {
      for (Direction o : side.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(side, 2);
        cursor.translate(o, 3);
        mainPillar(o, cursor);
        cursor.translate(side, 3);
        mainPillar(o, cursor);
      }
    }

    smelterSide(entranceDirection.antiClockwise(), origin);
    fireplace(entranceDirection.clockwise(), origin);

    cursor = origin.copy();
    cursor.up(6);
    overheadLight(cursor);

  }

  private void mainPillar(Direction entranceDirection, Coord origin) {
    Coord start = origin.copy();
    Coord end = origin.copy();
    end.up(3);
    primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));
    Coord cursor = end.copy();
    cursor.translate(entranceDirection.antiClockwise());
    primaryStairBrush().setUpsideDown(true).setFacing(entranceDirection.antiClockwise()).stroke(worldEditor, cursor);
    cursor = end.copy();
    cursor.translate(entranceDirection.clockwise());
    primaryStairBrush().setUpsideDown(true).setFacing(entranceDirection.clockwise()).stroke(worldEditor, cursor);
    cursor = end.copy();
    cursor.translate(entranceDirection.reverse());
    primaryStairBrush().setUpsideDown(true).setFacing(entranceDirection.reverse()).stroke(worldEditor, cursor);
    cursor.up();
    primaryWallBrush().stroke(worldEditor, cursor);
    cursor.translate(entranceDirection.reverse());
    primaryStairBrush().setUpsideDown(true).setFacing(entranceDirection.reverse()).stroke(worldEditor, cursor);
    cursor.translate(entranceDirection.reverse());
    cursor.up();
    start = cursor.copy();
    end = cursor.copy();
    end.translate(entranceDirection, 2);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
    cursor = end.copy();
    cursor.translate(entranceDirection.antiClockwise());
    primaryStairBrush().setUpsideDown(true).setFacing(entranceDirection.antiClockwise()).stroke(worldEditor, cursor);
    cursor = end.copy();
    cursor.translate(entranceDirection.clockwise());
    primaryStairBrush().setUpsideDown(true).setFacing(entranceDirection.clockwise()).stroke(worldEditor, cursor);
  }


  private void smelterSide(Direction entranceDirection, Coord origin) {
    Coord start = origin.copy();
    start.translate(entranceDirection, 5);
    Coord end = start.copy();
    start.translate(entranceDirection.antiClockwise(), 2);
    end.translate(entranceDirection.clockwise(), 2);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
    start.translate(entranceDirection.reverse());
    end.translate(entranceDirection.reverse());
    primaryStairBrush().setUpsideDown(false).setFacing(entranceDirection.reverse()).fill(worldEditor, RectSolid.newRect(start, end));


    for (Direction o : entranceDirection.orthogonals()) {
      Coord cursor = origin.copy();
      cursor.translate(entranceDirection, 3);
      cursor.translate(o);
      smelter(entranceDirection, cursor);

      cursor.translate(o, 2);
      primaryWallBrush().stroke(worldEditor, cursor);
      cursor.translate(entranceDirection);
      primaryWallBrush().stroke(worldEditor, cursor);
    }
  }

  private void smelter(Direction entranceDirection, Coord origin) {
    Coord cursor = origin.copy();
    generateChest(cursor, entranceDirection, ChestType.EMPTY);

    cursor.translate(entranceDirection, 2);
    cursor.up(2);
    generateChest(cursor, entranceDirection, ChestType.EMPTY);

    cursor.up();
    cursor.translate(entranceDirection.reverse());
    generateChest(cursor, entranceDirection, ChestType.EMPTY);

    cursor = origin.copy();
    cursor.up();
    cursor.translate(entranceDirection);
    BlockType.FURNACE.getBrush().setFacing(entranceDirection).stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.translate(entranceDirection);
    BlockType.HOPPER.getBrush().setFacing(entranceDirection).stroke(worldEditor, cursor);

    cursor.translate(entranceDirection);
    cursor.up();
    BlockType.HOPPER.getBrush().setFacing(entranceDirection).stroke(worldEditor, cursor);

    cursor.translate(entranceDirection.reverse());
    cursor.up();
    BlockType.HOPPER.getBrush().setFacing(Direction.DOWN).stroke(worldEditor, cursor);
  }

  private void fireplace(Direction entranceDirection, Coord origin) {

    StairsBlock stair = StairsBlock.brick();
    BlockBrush brick = BlockType.BRICK.getBrush();
    BlockBrush brickSlab = SlabBlock.brick();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord start = origin.copy();
    start.translate(entranceDirection, 4);
    Coord end = start.copy();
    start.down();
    start.translate(entranceDirection.antiClockwise());
    end.translate(entranceDirection.clockwise());
    end.translate(entranceDirection, 2);
    end.up(5);

    brick.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(entranceDirection, 5);
    end = start.copy();
    end.up(5);
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));
    Coord cursor = origin.copy();
    cursor.up();
    cursor.translate(entranceDirection, 4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    for (Direction side : entranceDirection.orthogonals()) {

      cursor = origin.copy();
      cursor.translate(entranceDirection, 4);
      cursor.translate(side);

      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(worldEditor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(side.reverse()).stroke(worldEditor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(side).stroke(worldEditor, cursor);
      cursor.up();
      bars.stroke(worldEditor, cursor);
      cursor.up();
      bars.stroke(worldEditor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(side).stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(entranceDirection, 3);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(entranceDirection.reverse()).stroke(worldEditor, cursor);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(entranceDirection.reverse()).stroke(worldEditor, cursor);
      cursor.translate(side);
      brick.stroke(worldEditor, cursor);
      cursor.translate(entranceDirection);
      brick.stroke(worldEditor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(worldEditor, cursor);
      cursor.translate(entranceDirection.reverse());
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(entranceDirection, 4);
      cursor.translate(side, 2);
      brick.stroke(worldEditor, cursor);
      cursor.translate(entranceDirection);
      brick.stroke(worldEditor, cursor);
      cursor.up();
      brick.stroke(worldEditor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(entranceDirection.reverse()).stroke(worldEditor, cursor);
      cursor.down();
      cursor.translate(entranceDirection.reverse());
      stair.setUpsideDown(false).setFacing(entranceDirection.reverse()).stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(entranceDirection, 3);
      cursor.up(5);
      stair.setUpsideDown(true).setFacing(entranceDirection.reverse()).stroke(worldEditor, cursor);

    }

    BlockBrush netherrack = BlockType.NETHERRACK.getBrush();
    BlockBrush fire = BlockType.FIRE.getBrush();

    start = origin.copy();
    start.translate(entranceDirection, 5);
    start.down();
    end = start.copy();
    start.translate(entranceDirection.antiClockwise());
    end.translate(entranceDirection.clockwise());
    netherrack.fill(worldEditor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    fire.fill(worldEditor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.translate(entranceDirection, 3);
    brickSlab.stroke(worldEditor, cursor);
    cursor.translate(entranceDirection);
    brickSlab.stroke(worldEditor, cursor);

  }

  private void anvilRoom(Direction entranceDirection, Coord origin) {
    BlockBrush anvil = AnvilBlock.anvil().setFacing(entranceDirection.antiClockwise());
    Coord cursor = origin.copy();
    cursor.translate(entranceDirection);
    anvil.stroke(worldEditor, cursor);

    Coord start = origin.copy();
    start.translate(entranceDirection.clockwise(), 2);
    Coord end = start.copy();
    start.translate(entranceDirection, 2);
    end.translate(entranceDirection.reverse(), 2);
    primaryStairBrush().setUpsideDown(false).setFacing(entranceDirection.antiClockwise()).fill(worldEditor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.translate(entranceDirection.clockwise(), 3);
    primaryWallBrush().stroke(worldEditor, cursor);
    cursor.translate(entranceDirection);
    BlockType.WATER_FLOWING.getBrush().stroke(worldEditor, cursor);
    cursor.translate(entranceDirection.reverse(), 2);
    BlockType.LAVA_FLOWING.getBrush().stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.translate(entranceDirection.antiClockwise(), 3);
    start = cursor.copy();
    end = start.copy();
    start.translate(entranceDirection);
    end.translate(entranceDirection.reverse());
    primaryStairBrush().setUpsideDown(true).setFacing(entranceDirection.clockwise()).fill(worldEditor, RectSolid.newRect(start, end));
    cursor.up();

    generateChest(cursor, entranceDirection.antiClockwise(), ChestType.SMITH);
  }


  private void overheadLight(Coord origin) {
    SingleBlockBrush.AIR.stroke(worldEditor, origin);

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir);
      primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(dir.antiClockwise());
      primaryStairBrush().setUpsideDown(true).stroke(worldEditor, cursor);
    }

    cursor = origin.copy();
    cursor.up(2);
    BlockType.REDSTONE_BLOCK.getBrush().stroke(worldEditor, cursor);
    cursor.down();
    BlockType.REDSTONE_LAMP_LIT.getBrush().stroke(worldEditor, cursor);
  }

  public int getSize() {
    return 9;
  }

}
