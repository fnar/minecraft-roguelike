package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsFire extends DungeonBase {


  public DungeonsFire(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public static void genFire(WorldEditor editor, Random rand, ThemeBase theme, Coord origin) {

    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();


    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    BlockType.get(BlockType.NETHERRACK).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.get(BlockType.FIRE).set(editor, cursor);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      start = new Coord(origin);
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end = new Coord(start);
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, pillar, true, false);

      cursor = new Coord(origin);
      cursor.translate(dir);
      stair.setOrientation(dir, false).set(editor, rand, cursor, true, false);
      cursor.translate(Cardinal.UP);
      BlockType.get(BlockType.IRON_BAR).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir, true).set(editor, rand, cursor, true, false);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 6);
      cursor.translate(dir, 3);

      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o, 2);
        stair.setOrientation(dir, true).set(editor, rand, c, true, false);
        c.translate(o);
        stair.setOrientation(dir, true).set(editor, rand, c, true, false);
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
      stair.setOrientation(dir, true);
      RectSolid.fill(editor, rand, start, end, stair, true, false);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 3);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.WEST, 2);
    end = new Coord(origin);
    end.translate(Cardinal.UP, 7);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.EAST, 2);

    RectSolid.fill(editor, rand, start, end, wall, true, false);

  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = settings.getTheme();

    IBlockFactory wall = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory pillar = theme.getPrimary().getPillar();

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

    RectHollow.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 8);
    start.translate(Cardinal.WEST, 8);
    end.translate(Cardinal.SOUTH, 8);
    end.translate(Cardinal.EAST, 8);
    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor(), false, true);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orth : dir.orthogonal()) {
        start = new Coord(origin);
        start.translate(dir, 7);
        start.translate(orth, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 6);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor = new Coord(origin);
        cursor.translate(dir, 8);
        cursor.translate(orth);
        cursor.translate(Cardinal.UP, 2);
        stair.setOrientation(orth.reverse(), true).set(editor, rand, cursor, true, false);

        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);

        start = new Coord(cursor);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor.translate(dir.reverse());
        cursor.translate(orth);
        stair.setOrientation(dir.reverse(), true).set(editor, cursor);

        start = new Coord(cursor);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor.translate(dir);
        cursor.translate(orth);
        stair.setOrientation(orth, true).set(editor, cursor);

        start = new Coord(cursor);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);

      }

      cursor = new Coord(origin);
      cursor.translate(dir, 6);
      cursor.translate(dir.antiClockwise(), 6);

      genFire(editor, rand, theme, cursor);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.translate(dir, 6);
      RectSolid.fill(editor, rand, start, end, wall);
      cursor.translate(dir.antiClockwise());
      wall.set(editor, rand, cursor);

      start = new Coord(end);
      end.translate(Cardinal.UP, 2);
      end.translate(dir.reverse());
      RectSolid.fill(editor, rand, start, end, wall);

      stair.setOrientation(dir.reverse(), true);

      cursor = new Coord(end);
      start = new Coord(cursor);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.fill(editor, rand, start, end, wall, true, false);

      start = new Coord(cursor);
      start.translate(Cardinal.DOWN);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.fill(editor, rand, start, end, stair, true, false);

      start = new Coord(cursor);
      start.translate(dir.reverse());
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.fill(editor, rand, start, end, stair, true, false);
    }


    return this;
  }

  public int getSize() {
    return 10;
  }

}
