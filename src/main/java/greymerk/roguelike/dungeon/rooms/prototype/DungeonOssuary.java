package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Skull;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonOssuary extends DungeonBase {

  public DungeonOssuary(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {
    ITheme theme = settings.getTheme();
    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

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
    RectHollow.fill(editor, rand, start, end, walls, false, true);

    // entrance arches
    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir, 7);
      for (Cardinal o : dir.orthogonal()) {
        start = new Coord(cursor);
        start.translate(o, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 5);
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);

        start = new Coord(cursor);
        start.translate(o, 2);
        start.translate(Cardinal.UP, 2);
        start.translate(dir.reverse(), 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, walls);
        stair.setOrientation(dir.reverse(), true).set(editor, start);

        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);
        stair.setOrientation(dir.reverse(), true).set(editor, start);

        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);
        stair.setOrientation(dir.reverse(), true).set(editor, start);

        start = new Coord(cursor);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        start.translate(dir.antiClockwise());
        end.translate(dir.clockwise());
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, walls);
        start.translate(Cardinal.UP);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);
        start.translate(Cardinal.UP);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);

        Coord c = new Coord(cursor);
        c.translate(o);
        c.translate(Cardinal.UP, 2);
        stair.setOrientation(o.reverse(), true).set(editor, c);
        c.translate(dir.reverse());
        c.translate(Cardinal.UP);
        stair.setOrientation(dir.reverse(), true).set(editor, c);
        c.translate(dir.reverse());
        c.translate(Cardinal.UP);
        stair.setOrientation(dir.reverse(), true).set(editor, c);
        c.translate(dir.reverse());
      }

      Coord c = new Coord(origin);
      c.translate(dir, 7);
      c.translate(Cardinal.UP, 3);
      stair.setOrientation(dir.reverse(), true).set(editor, c);
      c.translate(dir.reverse());
      c.translate(Cardinal.UP);
      stair.setOrientation(dir.reverse(), true).set(editor, c);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(Cardinal.UP, 5);
      start = new Coord(cursor);
      start.translate(Cardinal.NORTH);
      start.translate(Cardinal.EAST);
      end = new Coord(cursor);
      end.translate(Cardinal.SOUTH);
      end.translate(Cardinal.WEST);
      RectSolid.fill(editor, rand, start, end, walls);
      air.set(editor, cursor);
      for (Cardinal d : Cardinal.directions) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setOrientation(d.reverse(), true).set(editor, c);
      }
    }

    // corner pillars
    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(dir, 6);
      start.translate(dir.antiClockwise(), 6);
      end = new Coord(start);
      end.translate(dir);
      end.translate(dir.antiClockwise());
      end.translate(Cardinal.UP, 6);
      RectSolid.fill(editor, rand, start, end, walls);
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
    RectSolid.fill(editor, rand, start, end, walls);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, air);
    air.set(editor, cursor);
    for (Cardinal d : Cardinal.directions) {
      Coord c = new Coord(cursor);
      c.translate(d);
      stair.setOrientation(d.reverse(), true).set(editor, c);
    }

    for (Cardinal dir : Cardinal.directions) {
      Cardinal[] orth = dir.orthogonal();
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir, 2);
      air.set(editor, cursor);
      for (Cardinal o : orth) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setOrientation(o.reverse(), true).set(editor, c);
      }
      cursor.translate(orth[0], 2);
      walls.set(editor, rand, cursor);
    }

    for (Cardinal dir : Cardinal.directions) {
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
      RectSolid.fill(editor, rand, start, end, walls);
      air.set(editor, cursor);
      for (Cardinal d : Cardinal.directions) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setOrientation(d.reverse(), true).set(editor, c);
      }

      for (Cardinal d : new Cardinal[]{dir, dir.antiClockwise()}) {
        cursor = new Coord(origin);
        cursor.translate(dir, 4);
        cursor.translate(dir.antiClockwise(), 4);
        cursor.translate(Cardinal.UP, 4);
        cursor.translate(d, 2);
        air.set(editor, cursor);
        for (Cardinal o : d.orthogonal()) {
          Coord c = new Coord(cursor);
          c.translate(o);
          stair.setOrientation(o.reverse(), true).set(editor, c);
        }

        start = new Coord(origin);
        start.translate(dir, 4);
        start.translate(dir.antiClockwise(), 4);
        start.translate(d, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, walls);
        start = new Coord(end);
        start.translate(d.orthogonal()[0]);
        end.translate(d.orthogonal()[1]);
        end.translate(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, walls);
        start.translate(d.reverse());
        end.translate(d.reverse());
        start.translate(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, walls);

        for (Cardinal o : d.orthogonal()) {
          cursor = new Coord(origin);
          cursor.translate(dir, 4);
          cursor.translate(dir.antiClockwise(), 4);
          cursor.translate(d, 3);
          cursor.translate(o);
          walls.set(editor, rand, cursor);
          cursor.translate(Cardinal.UP);
          skull(editor, rand, d.reverse(), cursor);
          cursor.translate(Cardinal.UP);
          walls.set(editor, rand, cursor);
          cursor.translate(Cardinal.UP);
          skull(editor, rand, d.reverse(), cursor);
          cursor.translate(Cardinal.UP);
          cursor.translate(d.reverse());
          stair.setOrientation(o.reverse(), true).set(editor, cursor);
        }
      }

      cursor.translate(dir, 2);
    }

    return this;
  }

  private void skull(WorldEditor editor, Random rand, Cardinal dir, Coord origin) {
    if (rand.nextInt(3) == 0) {
      return;
    }

    Coord cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    if (editor.isAirBlock(cursor)) {
      return;
    }

    if (rand.nextInt(15) == 0) {
      Skull.set(editor, rand, origin, dir, Skull.WITHER);
      return;
    }

    Skull.set(editor, rand, origin, dir, Skull.SKELETON);
  }

  @Override
  public int getSize() {
    return 10;
  }
}
