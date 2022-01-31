package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.spawner.MobType;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class BlazeRoom extends BaseRoom {


  public BlazeRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public void genFire(Coord origin) {
    Coord start = origin.copy();
    Coord end = start.copy();
    end.up(2);
    RectSolid.newRect(start, end).fill(worldEditor, BlockType.LAVA_STILL.getBrush());

    for (Direction dir : Direction.CARDINAL) {

      start = origin.copy();
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end = start.copy();
      end.up(2);
      RectSolid.newRect(start, end).fill(worldEditor, pillars(), true, false);

      Coord cursor = origin.copy();
      cursor.translate(dir);
      stairs().setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor, true, false);
      cursor.up();
      BlockType.IRON_BAR.getBrush().stroke(worldEditor, cursor);
      cursor.up();
      stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor, true, false);

      cursor = origin.copy();
      cursor.up(6);
      cursor.translate(dir, 3);

      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o, 2);
        stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, c, true, false);
        c.translate(o);
        stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, c, true, false);
      }

      cursor = origin.copy();
      cursor.up();
      cursor.translate(dir, 2);

      if (!worldEditor.isAirBlock(cursor)) {
        continue;
      }

      start = origin.copy();
      start.up(3);
      start.translate(dir, 2);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      RectSolid.newRect(start, end).fill(worldEditor, stairs().setUpsideDown(true).setFacing(dir), true, false);
    }

    start = origin.copy();
    start.up(3);
    start.north(2);
    start.west(2);
    end = origin.copy();
    end.up(7);
    end.south(2);
    end.east(2);

    RectSolid.newRect(start, end).fill(worldEditor, walls(), true, false);

  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.north(8);
    start.west(8);
    start.down();
    end = origin.copy();
    end.south(8);
    end.east(8);
    end.up(7);
    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

    start = origin.copy();
    start.down();
    end = start.copy();
    start.north(8);
    start.west(8);
    end.south(8);
    end.east(8);
    RectSolid.newRect(start, end).fill(worldEditor, floors(), false, true);

    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonal : dir.orthogonals()) {
        start = origin.copy();
        start.translate(dir, 7);
        start.translate(orthogonal, 2);
        end = start.copy();
        end.up(6);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());

        cursor = origin.copy();
        cursor.translate(dir, 8);
        cursor.translate(orthogonal);
        cursor.up(2);
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor, true, false);

        cursor.translate(dir.reverse());
        cursor.up();
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);

        start = cursor.copy();
        start.up();
        end = start.copy();
        end.up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());

        cursor.translate(dir.reverse());
        cursor.translate(orthogonal);
        stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        start = cursor.copy();
        start.up();
        end = start.copy();
        end.up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());

        cursor.translate(dir);
        cursor.translate(orthogonal);
        stairs().setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);

        start = cursor.copy();
        start.up();
        end = start.copy();
        end.up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());

      }

      cursor = origin.copy();
      cursor.translate(dir, 6);
      cursor.translate(dir.antiClockwise(), 6);

      genFire(cursor);

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir);
      start = cursor.copy();
      end = cursor.copy();
      end.translate(dir, 6);
      RectSolid.newRect(start, end).fill(worldEditor, walls());
      cursor.translate(dir.antiClockwise());
      walls().stroke(worldEditor, cursor);

      start = end.copy();
      end.up(2);
      end.translate(dir.reverse());
      RectSolid.newRect(start, end).fill(worldEditor, walls());

      cursor = end.copy();
      start = cursor.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, walls(), true, false);

      start = cursor.copy();
      start.down();
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, stairs().setUpsideDown(true).setFacing(dir.reverse()), true, false);

      start = cursor.copy();
      start.translate(dir.reverse());
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, stairs().setUpsideDown(true).setFacing(dir.reverse()), true, false);
    }

    start = origin.copy();
    end = origin.copy();
    start.north(4);
    start.east(4);
    end.south(4);
    end.west(4);
    end.down(4);
    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

    start = origin.copy();
    start.down(2);
    end = start.copy();
    end.down();
    start.north(3);
    start.east(3);
    end.south(3);
    end.west(3);
    RectSolid.newRect(start, end).fill(worldEditor, liquid());

    cursor = origin.copy();
    cursor.up(4);
    start = cursor.copy();
    start.down();
    start.north();
    start.east();
    end = cursor.copy();
    end.up();
    end.south();
    end.west();
    RectSolid.newRect(start, end).fill(worldEditor, BlockType.OBSIDIAN.getBrush());
    generateSpawner(cursor, MobType.NETHER_MOBS);
    generateDoorways(origin, entrances);

    return this;
  }

  public int getSize() {
    return 10;
  }

}
