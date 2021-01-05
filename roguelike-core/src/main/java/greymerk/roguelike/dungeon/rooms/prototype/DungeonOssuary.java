package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import com.github.srwaggon.roguelike.worldgen.block.decorative.Skull;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonOssuary extends DungeonBase {

  public DungeonOssuary(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {
    ThemeBase theme = settings.getTheme();
    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.DOWN);
    start.translate(Cardinal.NORTH, 8);
    start.translate(Cardinal.EAST, 8);
    end.translate(Cardinal.SOUTH, 8);
    end.translate(Cardinal.WEST, 8);
    end.translate(Cardinal.UP, 6);
    RectHollow.newRect(start, end).fill(editor, walls, false, true);

    // entrance arches
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir, 7);
      for (Cardinal o : dir.orthogonals()) {
        start = new Coord(cursor);
        start.translate(o, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 5);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(editor, walls);

        start = new Coord(cursor);
        start.translate(o, 2);
        start.translate(Cardinal.UP, 2);
        start.translate(dir.reverse(), 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, walls);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, start);

        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(editor, walls);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, start);

        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(editor, walls);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, start);

        start = new Coord(cursor);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        start.translate(dir.antiClockwise());
        end.translate(dir.clockwise());
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, walls);
        start.translate(Cardinal.UP);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(editor, walls);
        start.translate(Cardinal.UP);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(editor, walls);

        Coord c = new Coord(cursor);
        c.translate(o);
        c.translate(Cardinal.UP, 2);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
        c.translate(dir.reverse());
        c.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, c);
        c.translate(dir.reverse());
        c.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, c);
        c.translate(dir.reverse());
      }

      Coord c = new Coord(origin);
      c.translate(dir, 7);
      c.translate(Cardinal.UP, 3);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, c);
      c.translate(dir.reverse());
      c.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, c);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(Cardinal.UP, 5);
      start = new Coord(cursor);
      start.translate(Cardinal.NORTH);
      start.translate(Cardinal.EAST);
      end = new Coord(cursor);
      end.translate(Cardinal.SOUTH);
      end.translate(Cardinal.WEST);
      RectSolid.newRect(start, end).fill(editor, walls);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      for (Cardinal d : Cardinal.DIRECTIONS) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d.reverse()).stroke(editor, c);
      }
    }

    // corner pillars
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = new Coord(origin);
      start.translate(dir, 6);
      start.translate(dir.antiClockwise(), 6);
      end = new Coord(start);
      end.translate(dir);
      end.translate(dir.antiClockwise());
      end.translate(Cardinal.UP, 6);
      RectSolid.newRect(start, end).fill(editor, walls);
    }

    // central ceiling
    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 6);
    start = new Coord(cursor);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.EAST, 2);
    end = new Coord(cursor);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.WEST, 2);
    RectSolid.newRect(start, end).fill(editor, walls);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    for (Cardinal d : Cardinal.DIRECTIONS) {
      Coord c = new Coord(cursor);
      c.translate(d);
      stair.setUpsideDown(true).setFacing(d.reverse()).stroke(editor, c);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      Cardinal[] orthogonal = dir.orthogonals();
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir, 2);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      for (Cardinal o : orthogonal) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
      }
      cursor.translate(orthogonal[0], 2);
      walls.stroke(editor, cursor);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(dir.antiClockwise(), 4);
      cursor.translate(Cardinal.UP, 5);
      start = new Coord(cursor);
      start.translate(Cardinal.NORTH);
      start.translate(Cardinal.EAST);
      end = new Coord(cursor);
      end.translate(Cardinal.SOUTH);
      end.translate(Cardinal.WEST);
      RectSolid.newRect(start, end).fill(editor, walls);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      for (Cardinal d : Cardinal.DIRECTIONS) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d.reverse()).stroke(editor, c);
      }

      for (Cardinal d : new Cardinal[]{dir, dir.antiClockwise()}) {
        cursor = new Coord(origin);
        cursor.translate(dir, 4);
        cursor.translate(dir.antiClockwise(), 4);
        cursor.translate(Cardinal.UP, 4);
        cursor.translate(d, 2);
        SingleBlockBrush.AIR.stroke(editor, cursor);
        for (Cardinal o : d.orthogonals()) {
          Coord c = new Coord(cursor);
          c.translate(o);
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
        }

        start = new Coord(origin);
        start.translate(dir, 4);
        start.translate(dir.antiClockwise(), 4);
        start.translate(d, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        RectSolid.newRect(start, end).fill(editor, walls);
        start = new Coord(end);
        start.translate(d.orthogonals()[0]);
        end.translate(d.orthogonals()[1]);
        end.translate(Cardinal.UP, 2);
        RectSolid.newRect(start, end).fill(editor, walls);
        start.translate(d.reverse());
        end.translate(d.reverse());
        start.translate(Cardinal.UP);
        RectSolid.newRect(start, end).fill(editor, walls);

        for (Cardinal o : d.orthogonals()) {
          cursor = new Coord(origin);
          cursor.translate(dir, 4);
          cursor.translate(dir.antiClockwise(), 4);
          cursor.translate(d, 3);
          cursor.translate(o);
          walls.stroke(editor, cursor);
          cursor.translate(Cardinal.UP);
          skull(editor, d.reverse(), cursor);
          cursor.translate(Cardinal.UP);
          walls.stroke(editor, cursor);
          cursor.translate(Cardinal.UP);
          skull(editor, d.reverse(), cursor);
          cursor.translate(Cardinal.UP);
          cursor.translate(d.reverse());
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        }
      }

      cursor.translate(dir, 2);
    }

    return this;
  }

  private void skull(WorldEditor editor, Cardinal dir, Coord origin) {
    if (editor.getRandom().nextInt(3) == 0) {
      return;
    }

    Coord cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    if (editor.isAirBlock(cursor)) {
      return;
    }

    Skull skull = editor.getRandom().nextInt(15) == 0 ? Skull.WITHER : Skull.SKELETON;
    editor.setSkull(editor, origin, dir, skull);
  }

  @Override
  public int getSize() {
    return 10;
  }
}
