package greymerk.roguelike.dungeon.rooms.prototype;

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
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsFire extends DungeonBase {


  public DungeonsFire(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public static void genFire(WorldEditor editor, ThemeBase theme, Coord origin) {

    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();


    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    BlockType.NETHERRACK.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.FIRE.getBrush().stroke(editor, cursor);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      start = new Coord(origin);
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end = new Coord(start);
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(editor, pillar, true, false);

      cursor = new Coord(origin);
      cursor.translate(dir);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor, true, false);
      cursor.translate(Cardinal.UP);
      BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 6);
      cursor.translate(dir, 3);

      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o, 2);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, c, true, false);
        c.translate(o);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, c, true, false);
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir, 2);

      if (!editor.isAirBlock(cursor)) {
        continue;
      }

      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      start.translate(dir, 2);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stair.setUpsideDown(true).setFacing(dir);
      RectSolid.newRect(start, end).fill(editor, stair, true, false);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 3);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.WEST, 2);
    end = new Coord(origin);
    end.translate(Cardinal.UP, 7);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.EAST, 2);

    RectSolid.newRect(start, end).fill(editor, wall, true, false);

  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = settings.getTheme();

    BlockBrush wall = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush pillar = theme.getPrimary().getPillar();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 8);
    start.translate(Cardinal.WEST, 8);
    start.translate(Cardinal.DOWN);
    end = new Coord(origin);
    end.translate(Cardinal.SOUTH, 8);
    end.translate(Cardinal.EAST, 8);
    end.translate(Cardinal.UP, 7);

    RectHollow.newRect(start, end).fill(editor, wall, false, true);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 8);
    start.translate(Cardinal.WEST, 8);
    end.translate(Cardinal.SOUTH, 8);
    end.translate(Cardinal.EAST, 8);
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getFloor(), false, true);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orth : dir.orthogonals()) {
        start = new Coord(origin);
        start.translate(dir, 7);
        start.translate(orth, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 6);
        RectSolid.newRect(start, end).fill(editor, pillar);

        cursor = new Coord(origin);
        cursor.translate(dir, 8);
        cursor.translate(orth);
        cursor.translate(Cardinal.UP, 2);
        stair.setUpsideDown(true).setFacing(orth.reverse()).stroke(editor, cursor, true, false);

        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(orth.reverse()).stroke(editor, cursor);

        start = new Coord(cursor);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, pillar);

        cursor.translate(dir.reverse());
        cursor.translate(orth);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

        start = new Coord(cursor);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, pillar);

        cursor.translate(dir);
        cursor.translate(orth);
        stair.setUpsideDown(true).setFacing(orth).stroke(editor, cursor);

        start = new Coord(cursor);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, pillar);

      }

      cursor = new Coord(origin);
      cursor.translate(dir, 6);
      cursor.translate(dir.antiClockwise(), 6);

      genFire(editor, theme, cursor);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.translate(dir, 6);
      RectSolid.newRect(start, end).fill(editor, wall);
      cursor.translate(dir.antiClockwise());
      wall.stroke(editor, cursor);

      start = new Coord(end);
      end.translate(Cardinal.UP, 2);
      end.translate(dir.reverse());
      RectSolid.newRect(start, end).fill(editor, wall);

      stair.setUpsideDown(true).setFacing(dir.reverse());

      cursor = new Coord(end);
      start = new Coord(cursor);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(editor, wall, true, false);

      start = new Coord(cursor);
      start.translate(Cardinal.DOWN);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(editor, stair, true, false);

      start = new Coord(cursor);
      start.translate(dir.reverse());
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(editor, stair, true, false);
    }


    return this;
  }

  public int getSize() {
    return 10;
  }

}