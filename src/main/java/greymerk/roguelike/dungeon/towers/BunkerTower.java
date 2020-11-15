package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class BunkerTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    Coord origin = Tower.getBaseCoord(editor, dungeon);
    origin.translate(Cardinal.UP);
    Coord cursor;
    Coord start;
    Coord end;

    IBlockFactory walls = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock window = ColorBlock.get(ColorBlock.PANE, DyeColor.GRAY);

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(Cardinal.DOWN);
    start.translate(Cardinal.NORTH, 5);
    start.translate(Cardinal.EAST, 5);
    end.translate(Cardinal.SOUTH, 5);
    end.translate(Cardinal.WEST, 5);
    end.translate(Cardinal.UP, 4);
    RectHollow.fill(editor, rand, start, end, walls, true, true);

    start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
    end = new Coord(origin);
    end.translate(Cardinal.DOWN);
    start.translate(Cardinal.NORTH, 5);
    start.translate(Cardinal.EAST, 5);
    end.translate(Cardinal.SOUTH, 5);
    end.translate(Cardinal.WEST, 5);
    RectSolid.fill(editor, rand, start, end, walls, true, true);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(dir, 5);
      end = new Coord(start);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      start = new Coord(start.getX(), dungeon.getY() + 10, start.getZ());
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, walls, true, true);
      end.translate(Cardinal.DOWN);
      end.translate(dir);
      start.translate(dir);
      RectSolid.fill(editor, rand, start, end, walls, true, true);
      end.translate(Cardinal.DOWN);
      end.translate(dir);
      start.translate(dir);
      RectSolid.fill(editor, rand, start, end, walls, true, true);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
      start.translate(dir, 6);
      start.translate(dir.antiClockwise(), 6);
      end = new Coord(origin);
      end.translate(dir, 6);
      end.translate(dir.antiClockwise(), 6);
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, walls, true, true);
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end.translate(Cardinal.DOWN);
      end.translate(dir);
      end.translate(dir.antiClockwise());
      RectSolid.fill(editor, rand, start, end, walls, true, true);


      for (Cardinal o : dir.orthogonal()) {
        start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
        start.translate(dir, 5);
        start.translate(o, 5);
        end = new Coord(origin);
        end.translate(dir, 5);
        end.translate(o, 5);
        end.translate(Cardinal.UP, 2);
        end.translate(o, 2);
        RectSolid.fill(editor, rand, start, end, walls, true, true);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      stair.setOrientation(dir, false);
      for (Cardinal o : dir.orthogonal()) {
        start = new Coord(origin);
        start.translate(dir, 6);
        start.translate(o, 6);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(o.reverse());
        RectSolid.fill(editor, rand, start, end, stair);
        start.translate(Cardinal.DOWN);
        start.translate(dir);
        start.translate(o);
        end = new Coord(start);
        end.translate(o.reverse(), 2);
        RectSolid.fill(editor, rand, start, end, stair);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 3);
      cursor.translate(dir, 6);
      stair.setOrientation(dir, false).set(editor, cursor);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setOrientation(dir, false).set(editor, c);
      }
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir);
      stair.setOrientation(dir, false).set(editor, cursor);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setOrientation(dir, false).set(editor, c);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 5);
      start = new Coord(cursor);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 5);
      end.translate(dir.clockwise(), 5);
      stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir, 4);
      stair.setOrientation(dir, false).set(editor, cursor);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        pillar.set(editor, rand, c);
        c.translate(o);
        stair.setOrientation(dir, false).set(editor, c);
        c.translate(o);
        pillar.set(editor, rand, c);
      }
      cursor.translate(Cardinal.UP);
      window.set(editor, cursor);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        pillar.set(editor, rand, c);
        c.translate(o);
        window.set(editor, c);
        c.translate(o);
        pillar.set(editor, rand, c);
      }
      cursor.translate(Cardinal.UP);
      start = new Coord(cursor);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      start.translate(dir.clockwise());
      end.translate(dir.antiClockwise());
      stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
      stair.setOrientation(dir.antiClockwise(), false).set(editor, start);
      stair.setOrientation(dir.clockwise(), false).set(editor, end);
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      start.translate(dir.clockwise());
      end.translate(dir.antiClockwise());
      stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
      stair.setOrientation(dir.antiClockwise(), false).set(editor, start);
      stair.setOrientation(dir.clockwise(), false).set(editor, end);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 8);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.EAST, 2);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.WEST, 2);
    RectSolid.fill(editor, rand, start, end, walls);
    cursor.translate(Cardinal.UP);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.EAST);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.WEST);
    RectSolid.fill(editor, rand, start, end, walls);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      start.translate(dir, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(dir, 4);
      start.translate(dir.antiClockwise(), 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);
    }

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(Cardinal.UP, 5);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, pillar);
    }

    for (Cardinal dir : Cardinal.directions) {
      Cardinal[] orth = dir.orthogonal();
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 2);
      cursor.translate(dir, 5);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      BlockType.get(BlockType.REDSTONE_BLOCK).set(editor, cursor);
      cursor.translate(dir.reverse());
      BlockType.get(BlockType.REDSTONE_LAMP_LIT).set(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      for (Cardinal o : orth) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setOrientation(dir.reverse(), true).set(editor, c);
        c.translate(dir);
        stair.setOrientation(o, true).set(editor, c);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(dir, 5);
      end = new Coord(start);
      end.translate(Cardinal.UP);
      end.translate(dir, 3);
      RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));

      cursor = new Coord(start);
      for (Cardinal o : dir.orthogonal()) {
        start = new Coord(cursor);
        start.translate(o, 2);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(o);
        stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
        start.translate(Cardinal.UP);
        end.translate(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, window);
        start.translate(Cardinal.DOWN, 2);
        end.translate(Cardinal.DOWN, 2);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, walls);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));
      }

      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setOrientation(o.reverse(), false).set(editor, c);
        c.translate(dir);
        stair.setOrientation(o.reverse(), false).set(editor, c);
      }


    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    start = new Coord(cursor.getX(), dungeon.getY(), cursor.getZ());
    end = new Coord(cursor);
    for (Coord c : new RectSolid(start, end)) {
      editor.spiralStairStep(rand, c, stair, pillar);
    }
  }
}
