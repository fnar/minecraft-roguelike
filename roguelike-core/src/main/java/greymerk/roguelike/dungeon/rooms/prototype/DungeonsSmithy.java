package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.AnvilBlock;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.List;

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

    Direction dir = getEntrance(entrances);

    clearBoxes(dir, origin);

    Coord cursor = origin.copy();
    cursor.translate(dir, 6);
    sideRoom(dir, cursor);
    anvilRoom(dir, cursor);

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 6);
    sideRoom(dir, cursor);

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 9);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    mainRoom(dir, origin);

    return this;
  }

  private void sideRoom(Direction dir, Coord origin) {
    Coord cursor;
    for (Direction side : dir.orthogonals()) {

      Coord start = origin.copy();
      start.up(3);
      Coord end = start.copy();
      start.translate(side, 2);
      start.translate(dir.reverse(), 2);
      end.translate(side, 3);
      end.translate(dir, 2);
      RectSolid.newRect(start, end).fill(worldEditor, walls());

      start.translate(dir);
      end = start.copy();
      end.translate(dir, 2);
      RectSolid.newRect(start, end).fill(worldEditor, stairs().setUpsideDown(true).setFacing(side.reverse()));

      for (Direction o : side.orthogonals()) {
        start = origin.copy();
        start.translate(side, 3);
        start.translate(o, 2);
        end = start.copy();
        end.up(2);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());

        cursor = end.copy();
        cursor.translate(side.reverse());
        stairs().setUpsideDown(true).setFacing(side.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        cursor.translate(side.reverse());
        stairs().setUpsideDown(true).setFacing(side.reverse()).stroke(worldEditor, cursor);

        cursor = end.copy();
        cursor.translate(o.reverse());
        stairs().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      }
    }

    cursor = origin.copy();
    cursor.up(4);
    overheadLight(cursor);
  }

  private void clearBoxes(Direction dir, Coord origin) {
    // end room
    Coord cursor = origin.copy();
    cursor.translate(dir, 6);

    Coord start = cursor.copy();
    start.down();
    start.translate(dir, 3);
    start.translate(dir.antiClockwise(), 4);

    Coord end = cursor.copy();
    end.up(4);
    end.translate(dir.reverse(), 3);
    end.translate(dir.clockwise(), 4);

    RectHollow.newRect(start, end).fill(worldEditor, walls());

    // entrance
    cursor = origin.copy();
    cursor.translate(dir.reverse(), 6);

    start = cursor.copy();
    start.down();
    start.translate(dir, 3);
    start.translate(dir.antiClockwise(), 4);

    end = cursor.copy();
    end.up(4);
    end.translate(dir.reverse(), 3);
    end.translate(dir.clockwise(), 4);

    RectHollow.newRect(start, end).fill(worldEditor, walls());

    // middle

    start = origin.copy();
    start.down();
    start.translate(dir.antiClockwise(), 6);
    start.translate(dir.reverse(), 4);

    end = origin.copy();
    end.up(6);
    end.translate(dir.clockwise(), 6);
    end.translate(dir, 4);

    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

  }

  private void mainRoom(Direction dir, Coord origin) {
    Coord start = origin.copy();
    start.translate(dir, 3);
    start.up(4);
    Coord end = start.copy();
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    end.up();
    RectSolid.newRect(start, end).fill(worldEditor, walls());
    start.translate(dir.reverse(), 6);
    end.translate(dir.reverse(), 6);
    RectSolid.newRect(start, end).fill(worldEditor, walls());

    Coord cursor;
    for (Direction side : dir.orthogonals()) {
      for (Direction o : side.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(side, 2);
        cursor.translate(o, 3);
        mainPillar(o, cursor);
        cursor.translate(side, 3);
        mainPillar(o, cursor);
      }
    }

    smelterSide(dir.antiClockwise(), origin);
    fireplace(dir.clockwise(), origin);

    cursor = origin.copy();
    cursor.up(6);
    overheadLight(cursor);

  }

  private void mainPillar(Direction dir, Coord origin) {
    Coord start = origin.copy();
    Coord end = origin.copy();
    end.up(3);
    RectSolid.newRect(start, end).fill(worldEditor, pillars());
    Coord cursor = end.copy();
    cursor.translate(dir.antiClockwise());
    stairs().setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(worldEditor, cursor);
    cursor = end.copy();
    cursor.translate(dir.clockwise());
    stairs().setUpsideDown(true).setFacing(dir.clockwise()).stroke(worldEditor, cursor);
    cursor = end.copy();
    cursor.translate(dir.reverse());
    stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.up();
    walls().stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    cursor.up();
    start = cursor.copy();
    end = cursor.copy();
    end.translate(dir, 2);
    RectSolid.newRect(start, end).fill(worldEditor, walls());
    cursor = end.copy();
    cursor.translate(dir.antiClockwise());
    stairs().setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(worldEditor, cursor);
    cursor = end.copy();
    cursor.translate(dir.clockwise());
    stairs().setUpsideDown(true).setFacing(dir.clockwise()).stroke(worldEditor, cursor);
  }


  private void smelterSide(Direction dir, Coord origin) {
    Coord start = origin.copy();
    start.translate(dir, 5);
    Coord end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(worldEditor, walls());
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    RectSolid.newRect(start, end).fill(worldEditor, stairs().setUpsideDown(false).setFacing(dir.reverse()));


    for (Direction o : dir.orthogonals()) {
      Coord cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.translate(o);
      smelter(dir, cursor);

      cursor.translate(o, 2);
      walls().stroke(worldEditor, cursor);
      cursor.translate(dir);
      walls().stroke(worldEditor, cursor);
    }
  }

  private void smelter(Direction dir, Coord origin) {
    Coord cursor = origin.copy();
    generateChest(cursor, dir, ChestType.EMPTY);

    cursor.translate(dir, 2);
    cursor.up(2);
    generateChest(cursor, dir, ChestType.EMPTY);

    cursor.up();
    cursor.translate(dir.reverse());
    generateChest(cursor, dir, ChestType.EMPTY);

    cursor = origin.copy();
    cursor.up();
    cursor.translate(dir);
    BlockType.FURNACE.getBrush().setFacing(dir).stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.translate(dir);
    BlockType.HOPPER.getBrush().setFacing(dir).stroke(worldEditor, cursor);

    cursor.translate(dir);
    cursor.up();
    BlockType.HOPPER.getBrush().setFacing(dir).stroke(worldEditor, cursor);

    cursor.translate(dir.reverse());
    cursor.up();
    BlockType.HOPPER.getBrush().setFacing(Direction.DOWN).stroke(worldEditor, cursor);
  }

  private void fireplace(Direction dir, Coord origin) {

    StairsBlock stair = StairsBlock.brick();
    BlockBrush brick = BlockType.BRICK.getBrush();
    BlockBrush brickSlab = SlabBlock.brick();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord start = origin.copy();
    start.translate(dir, 4);
    Coord end = start.copy();
    start.down();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(dir, 2);
    end.up(5);

    RectSolid.newRect(start, end).fill(worldEditor, brick);

    start = origin.copy();
    start.translate(dir, 5);
    end = start.copy();
    end.up(5);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);
    Coord cursor = origin.copy();
    cursor.up();
    cursor.translate(dir, 4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    for (Direction side : dir.orthogonals()) {

      cursor = origin.copy();
      cursor.translate(dir, 4);
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
      cursor.translate(dir, 3);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(side);
      brick.stroke(worldEditor, cursor);
      cursor.translate(dir);
      brick.stroke(worldEditor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(worldEditor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 4);
      cursor.translate(side, 2);
      brick.stroke(worldEditor, cursor);
      cursor.translate(dir);
      brick.stroke(worldEditor, cursor);
      cursor.up();
      brick.stroke(worldEditor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.down();
      cursor.translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.up(5);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

    }

    BlockBrush netherrack = BlockType.NETHERRACK.getBrush();
    BlockBrush fire = BlockType.FIRE.getBrush();

    start = origin.copy();
    start.translate(dir, 5);
    start.down();
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(worldEditor, netherrack);
    start.up();
    end.up();
    RectSolid.newRect(start, end).fill(worldEditor, fire);

    cursor = origin.copy();
    cursor.translate(dir, 3);
    brickSlab.stroke(worldEditor, cursor);
    cursor.translate(dir);
    brickSlab.stroke(worldEditor, cursor);

  }

  private void anvilRoom(Direction dir, Coord origin) {
    BlockBrush anvil = AnvilBlock.anvil().setFacing(dir.antiClockwise());
    Coord cursor = origin.copy();
    cursor.translate(dir);
    anvil.stroke(worldEditor, cursor);

    Coord start = origin.copy();
    start.translate(dir.clockwise(), 2);
    Coord end = start.copy();
    start.translate(dir, 2);
    end.translate(dir.reverse(), 2);
    stairs().setUpsideDown(false).setFacing(dir.antiClockwise());
    RectSolid.newRect(start, end).fill(worldEditor, stairs());

    cursor = origin.copy();
    cursor.translate(dir.clockwise(), 3);
    walls().stroke(worldEditor, cursor);
    cursor.translate(dir);
    BlockType.WATER_FLOWING.getBrush().stroke(worldEditor, cursor);
    cursor.translate(dir.reverse(), 2);
    BlockType.LAVA_FLOWING.getBrush().stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.translate(dir.antiClockwise(), 3);
    start = cursor.copy();
    end = start.copy();
    start.translate(dir);
    end.translate(dir.reverse());
    stairs().setUpsideDown(true).setFacing(dir.clockwise());
    RectSolid.newRect(start, end).fill(worldEditor, stairs());
    cursor.up();

    generateChest(cursor, dir.antiClockwise(), ChestType.SMITH);
  }


  private void overheadLight(Coord origin) {
    SingleBlockBrush.AIR.stroke(worldEditor, origin);

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir);
      stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(dir.antiClockwise());
      stairs().stroke(worldEditor, cursor);
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
