package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsSlime extends BaseRoom {

  public DungeonsSlime(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(new Coord(-8, -1, -8));
    end.translate(new Coord(8, 5, 8));
    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      corner(cursor);

      start = origin.copy();
      start.up(4);
      start.translate(dir, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 8);
      end.translate(dir.clockwise(), 8);
      RectSolid.newRect(start, end).fill(worldEditor, walls());
      start.translate(dir, 4);
      end.translate(dir, 4);
      RectSolid.newRect(start, end).fill(worldEditor, walls());

    }


    for (Direction dir : Direction.CARDINAL) {
      if (!entrances.contains(dir)) {
        start = origin.copy();
        start.translate(dir, 4);
        end = start.copy();
        end.translate(dir, 2);
        start.translate(dir.antiClockwise(), 3);
        end.translate(dir.clockwise(), 3);
        RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);
        start.down();
        end.down();
        RectSolid.newRect(start, end).fill(worldEditor, liquid());
        start.down();
        end.down();
        RectSolid.newRect(start, end).fill(worldEditor, walls());

        start = origin.copy();
        start.translate(dir, 3);
        end = start.copy();
        start.translate(dir.antiClockwise(), 2);
        end.translate(dir.clockwise(), 2);
        RectSolid.newRect(start, end).fill(worldEditor, bars);

        cursor = origin.copy();
        cursor.translate(dir, 7);
        walls().stroke(worldEditor, cursor);
        cursor.up(2);
        walls().stroke(worldEditor, cursor);
        cursor.down();
        cursor.translate(dir);
        liquid().stroke(worldEditor, cursor);
        for (Direction o : dir.orthogonals()) {
          cursor = origin.copy();
          cursor.translate(dir, 7);
          cursor.translate(o);
          stairs().setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
          cursor.up();
          walls().stroke(worldEditor, cursor);
          cursor.up();
          stairs().setUpsideDown(false).setFacing(o).stroke(worldEditor, cursor);

        }
      }
    }


    return this;
  }

  private void corner(Coord origin) {
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(new Coord(-1, -1, -1));
    end.translate(new Coord(1, -1, 1));
    RectSolid.newRect(start, end).fill(worldEditor, liquid());

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-1, -2, -1));
    end.translate(new Coord(1, -2, 1));
    RectSolid.newRect(start, end).fill(worldEditor, walls());

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(dir, 2);
      start.translate(dir.antiClockwise(), 2);
      end = start.copy();
      end.up(3);
      RectSolid.newRect(start, end).fill(worldEditor, pillars());

      for (Direction d : Direction.CARDINAL) {
        Coord cursor = end.copy();
        cursor.translate(d);
        stairs().setUpsideDown(true).setFacing(d).stroke(worldEditor, cursor, true, false);
      }

      start = origin.copy();
      start.translate(dir, 2);
      end = start.copy();
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      RectSolid.newRect(start, end).fill(worldEditor, bars);

    }
  }

  public int getSize() {
    return 8;
  }
}
