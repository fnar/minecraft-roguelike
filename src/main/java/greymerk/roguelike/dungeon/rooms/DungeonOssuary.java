package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Skull;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonOssuary extends DungeonBase {

  public DungeonOssuary(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public IDungeonRoom generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {
    ITheme theme = settings.getTheme();
    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.DOWN);
    start.add(Cardinal.NORTH, 8);
    start.add(Cardinal.EAST, 8);
    end.add(Cardinal.SOUTH, 8);
    end.add(Cardinal.WEST, 8);
    end.add(Cardinal.UP, 6);
    RectHollow.fill(editor, rand, start, end, walls, false, true);

    // entrance arches
    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir, 7);
      for (Cardinal o : dir.orthogonal()) {
        start = new Coord(cursor);
        start.add(o, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 5);
        end.add(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);

        start = new Coord(cursor);
        start.add(o, 2);
        start.add(Cardinal.UP, 2);
        start.add(dir.reverse(), 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, walls);
        stair.setOrientation(dir.reverse(), true).set(editor, start);

        start.add(dir.reverse());
        start.add(Cardinal.UP);
        end.add(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);
        stair.setOrientation(dir.reverse(), true).set(editor, start);

        start.add(dir.reverse());
        start.add(Cardinal.UP);
        end.add(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);
        stair.setOrientation(dir.reverse(), true).set(editor, start);

        start = new Coord(cursor);
        start.add(Cardinal.UP, 3);
        end = new Coord(start);
        start.add(dir.left());
        end.add(dir.right());
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, walls);
        start.add(Cardinal.UP);
        start.add(dir.reverse());
        end.add(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);
        start.add(Cardinal.UP);
        start.add(dir.reverse());
        end.add(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);

        Coord c = new Coord(cursor);
        c.add(o);
        c.add(Cardinal.UP, 2);
        stair.setOrientation(o.reverse(), true).set(editor, c);
        c.add(dir.reverse());
        c.add(Cardinal.UP);
        stair.setOrientation(dir.reverse(), true).set(editor, c);
        c.add(dir.reverse());
        c.add(Cardinal.UP);
        stair.setOrientation(dir.reverse(), true).set(editor, c);
        c.add(dir.reverse());
      }

      Coord c = new Coord(origin);
      c.add(dir, 7);
      c.add(Cardinal.UP, 3);
      stair.setOrientation(dir.reverse(), true).set(editor, c);
      c.add(dir.reverse());
      c.add(Cardinal.UP);
      stair.setOrientation(dir.reverse(), true).set(editor, c);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir, 4);
      cursor.add(Cardinal.UP, 5);
      start = new Coord(cursor);
      start.add(Cardinal.NORTH);
      start.add(Cardinal.EAST);
      end = new Coord(cursor);
      end.add(Cardinal.SOUTH);
      end.add(Cardinal.WEST);
      RectSolid.fill(editor, rand, start, end, walls);
      air.set(editor, cursor);
      for (Cardinal d : Cardinal.directions) {
        Coord c = new Coord(cursor);
        c.add(d);
        stair.setOrientation(d.reverse(), true).set(editor, c);
      }
    }

    // corner pillars
    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(dir, 6);
      start.add(dir.left(), 6);
      end = new Coord(start);
      end.add(dir);
      end.add(dir.left());
      end.add(Cardinal.UP, 6);
      RectSolid.fill(editor, rand, start, end, walls);
    }

    // central ceiling
    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 6);
    start = new Coord(cursor);
    start.add(Cardinal.NORTH, 2);
    start.add(Cardinal.EAST, 2);
    end = new Coord(cursor);
    end.add(Cardinal.SOUTH, 2);
    end.add(Cardinal.WEST, 2);
    RectSolid.fill(editor, rand, start, end, walls);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, air);
    air.set(editor, cursor);
    for (Cardinal d : Cardinal.directions) {
      Coord c = new Coord(cursor);
      c.add(d);
      stair.setOrientation(d.reverse(), true).set(editor, c);
    }

    for (Cardinal dir : Cardinal.directions) {
      Cardinal[] orth = dir.orthogonal();
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 5);
      cursor.add(dir, 2);
      air.set(editor, cursor);
      for (Cardinal o : orth) {
        Coord c = new Coord(cursor);
        c.add(o);
        stair.setOrientation(o.reverse(), true).set(editor, c);
      }
      cursor.add(orth[0], 2);
      walls.set(editor, rand, cursor);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir, 4);
      cursor.add(dir.left(), 4);
      cursor.add(Cardinal.UP, 5);
      start = new Coord(cursor);
      start.add(Cardinal.NORTH);
      start.add(Cardinal.EAST);
      end = new Coord(cursor);
      end.add(Cardinal.SOUTH);
      end.add(Cardinal.WEST);
      RectSolid.fill(editor, rand, start, end, walls);
      air.set(editor, cursor);
      for (Cardinal d : Cardinal.directions) {
        Coord c = new Coord(cursor);
        c.add(d);
        stair.setOrientation(d.reverse(), true).set(editor, c);
      }

      for (Cardinal d : new Cardinal[]{dir, dir.left()}) {
        cursor = new Coord(origin);
        cursor.add(dir, 4);
        cursor.add(dir.left(), 4);
        cursor.add(Cardinal.UP, 4);
        cursor.add(d, 2);
        air.set(editor, cursor);
        for (Cardinal o : d.orthogonal()) {
          Coord c = new Coord(cursor);
          c.add(o);
          stair.setOrientation(o.reverse(), true).set(editor, c);
        }

        start = new Coord(origin);
        start.add(dir, 4);
        start.add(dir.left(), 4);
        start.add(d, 3);
        end = new Coord(start);
        end.add(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, walls);
        start = new Coord(end);
        start.add(d.orthogonal()[0]);
        end.add(d.orthogonal()[1]);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, walls);
        start.add(d.reverse());
        end.add(d.reverse());
        start.add(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, walls);

        for (Cardinal o : d.orthogonal()) {
          cursor = new Coord(origin);
          cursor.add(dir, 4);
          cursor.add(dir.left(), 4);
          cursor.add(d, 3);
          cursor.add(o);
          walls.set(editor, rand, cursor);
          cursor.add(Cardinal.UP);
          skull(editor, rand, d.reverse(), cursor);
          cursor.add(Cardinal.UP);
          walls.set(editor, rand, cursor);
          cursor.add(Cardinal.UP);
          skull(editor, rand, d.reverse(), cursor);
          cursor.add(Cardinal.UP);
          cursor.add(d.reverse());
          stair.setOrientation(o.reverse(), true).set(editor, cursor);
        }
      }

      cursor.add(dir, 2);
    }

    return this;
  }

  private void skull(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {
    if (rand.nextInt(3) == 0) {
      return;
    }

    Coord cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
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
