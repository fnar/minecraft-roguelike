package greymerk.roguelike.dungeon.towers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Vine;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class JungleTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    Coord origin = Tower.getBaseCoord(editor, dungeon);
    origin.translate(Cardinal.UP);
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock grass = BlockType.get(BlockType.GRASS);

    Coord start;
    Coord end;
    Coord cursor;

    // lower pillars
    for (Cardinal dir : Cardinal.directions) {

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 3);
      cursor.translate(dir, 7);
      pillar(editor, rand, theme, cursor);

      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o, 3);
        pillar(editor, rand, theme, c);
        c.translate(dir);
        c.translate(Cardinal.UP);
        walls.set(editor, rand, c);
        c.translate(Cardinal.UP);
        walls.set(editor, rand, c);
        c.translate(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, c);
        c.translate(dir.reverse());
        walls.set(editor, rand, c);
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 8);
      walls.set(editor, rand, cursor);
      cursor.translate(Cardinal.UP);
      walls.set(editor, rand, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir, false).set(editor, cursor);
      cursor.translate(dir.reverse());
      walls.set(editor, rand, cursor);

      start = new Coord(origin);
      start.translate(dir, 2);
      start.translate(dir.left(), 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      pillar.fill(editor, rand, new RectSolid(start, end));
      cursor = new Coord(end);
      for (Cardinal d : new Cardinal[]{dir.reverse(), dir.right()}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setOrientation(d, true).set(editor, c);
      }

      // corner pillar
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 6);
      cursor.translate(dir, 6);
      cursor.translate(dir.left(), 6);
      editor.fillDown(rand, new Coord(cursor), pillar);
      for (Cardinal d : new Cardinal[]{dir, dir.left()}) {
        start = new Coord(cursor);
        start.translate(d);
        stair.setOrientation(d, false).set(editor, start);
        start.translate(Cardinal.DOWN);
        end = new Coord(start);
        end.translate(Cardinal.DOWN, 2);
        walls.fill(editor, rand, new RectSolid(start, end));
        end.translate(Cardinal.DOWN);
        stair.setOrientation(d, true).set(editor, end);
      }
    }


    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 7);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.left(), 5);
      end.translate(dir.right(), 5);
      walls.fill(editor, rand, new RectSolid(start, end));
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      grass.fill(editor, rand, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      walls.fill(editor, rand, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 6);
      pillar(editor, rand, theme, cursor);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o, 3);
        pillar(editor, rand, theme, c);
      }

      start = new Coord(origin);
      start.translate(Cardinal.UP, 5);
      start.translate(dir, 2);
      end = new Coord(start);
      end.translate(dir, 3);
      walls.fill(editor, rand, new RectSolid(start, end));
      end.translate(dir.left(), 3);
      start = new Coord(end);
      start.translate(dir.reverse(), 10);
      walls.fill(editor, rand, new RectSolid(start, end));

      start = new Coord(origin);
      start.translate(Cardinal.UP, 6);
      start.translate(dir, 3);
      start.translate(dir.left(), 2);
      end = new Coord(start);
      end.translate(dir.right(), 8);
      end.translate(dir, 3);
      walls.fill(editor, rand, new RectSolid(start, end));

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      start.translate(dir, 2);
      end = new Coord(start);
      start.translate(dir.left(), 2);
      end.translate(dir.right());
      end.translate(Cardinal.UP, 2);
      walls.fill(editor, rand, new RectSolid(start, end));

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(dir.left(), 3);
      end.translate(dir.right(), 2);
      stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    }

    // level 2 grass patches
    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal o : dir.orthogonal()) {
        start = new Coord(origin);
        start.translate(Cardinal.UP, 6);
        start.translate(dir, 5);
        start.translate(o);
        end = new Coord(start);
        end.translate(o);
        end.translate(dir);
        grass.fill(editor, rand, new RectSolid(start, end));
        start.translate(o, 3);
        end.translate(o, 3);
        grass.fill(editor, rand, new RectSolid(start, end));
      }
    }

    // second floor pillars
    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 9);
      cursor.translate(dir, 5);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o, 2);
        pillar(editor, rand, theme, c);
        c.translate(dir);
        c.translate(Cardinal.UP);
        walls.set(editor, rand, c);
        c.translate(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, c);
        c.translate(dir.reverse());
        walls.set(editor, rand, c);
        c.translate(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, c);
      }
      cursor.translate(dir.left(), 5);
      pillar(editor, rand, theme, cursor);
      for (Cardinal d : new Cardinal[]{dir.left(), dir}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        c.translate(Cardinal.UP);
        walls.set(editor, rand, c);
        c.translate(Cardinal.UP);
        stair.setOrientation(d, false).set(editor, c);
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 10);
      cursor.translate(dir, 2);
      cursor.translate(dir.left(), 2);
      start = new Coord(cursor);
      end = new Coord(start);
      end.translate(Cardinal.DOWN, 3);
      pillar.fill(editor, rand, new RectSolid(start, end));
      for (Cardinal d : new Cardinal[]{dir.right(), dir.reverse()}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setOrientation(d, true).set(editor, c);
      }

      cursor.translate(Cardinal.DOWN);
      for (Cardinal d : new Cardinal[]{dir.left(), dir}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setOrientation(d, true).set(editor, c);
        c.translate(Cardinal.UP);
        walls.set(editor, rand, c);
        c.translate(d);
        walls.set(editor, rand, c);
      }

    }

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(Cardinal.UP, 10);
      start.translate(dir, 5);
      end = new Coord(start);
      start.translate(dir.left(), 5);
      end.translate(dir.right(), 4);
      walls.fill(editor, rand, new RectSolid(start, end));

      start = new Coord(origin);
      start.translate(Cardinal.UP, 11);
      start.translate(dir, 2);
      end = new Coord(start);
      start.translate(dir.left());
      end.translate(dir.right(), 4);
      end.translate(dir, 2);
      walls.fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 11);
      cursor.translate(dir, 5);
      start = new Coord(cursor);
      end = new Coord(start);
      start.translate(dir.left(), 4);
      end.translate(dir.right(), 4);
      grass.fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 12);
      cursor.translate(dir, 3);
      start = new Coord(cursor);
      end = new Coord(start);
      end.translate(dir);
      start.translate(dir.left());
      end.translate(dir.right(), 4);
      grass.fill(editor, rand, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 12);
      cursor.translate(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.left(), 4);
      end.translate(dir.right(), 4);
      walls.fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 11);
      cursor.translate(dir, 5);
      cursor.translate(dir.left(), 5);
      walls.set(editor, rand, cursor);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(dir.left(), 2);
      cursor.translate(Cardinal.UP, 15);
      pillar(editor, rand, theme, cursor);
      for (Cardinal d : new Cardinal[]{dir, dir.left()}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        c.translate(Cardinal.UP);
        walls.set(editor, rand, c);
        c.translate(Cardinal.UP);
        stair.setOrientation(d, false).set(editor, c);
      }

      start = new Coord(origin);
      start.translate(Cardinal.UP, 16);
      start.translate(dir, 2);
      end = new Coord(start);
      start.translate(dir.left());
      end.translate(dir.right(), 2);
      walls.fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 17);
      cursor.translate(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.left());
      end.translate(dir.right());
      grass.fill(editor, rand, new RectSolid(start, end));
      cursor.translate(dir.left(), 2);
      walls.set(editor, rand, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP, 17);
      end = new Coord(start);
      start.translate(dir);
      start.translate(dir.left());
      end.translate(dir.reverse());
      end.translate(dir.right());
      walls.fill(editor, rand, new RectSolid(start, end));
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      grass.fill(editor, rand, new RectSolid(start, end));
    }

    start = new Coord(origin);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.EAST, 2);
    end = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.WEST, 2);
    walls.fill(editor, rand, new RectSolid(start, end), false, true);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 12);
    start = new Coord(cursor.getX(), dungeon.getY(), cursor.getZ());
    end = new Coord(cursor);
    for (Coord c : new RectSolid(start, end)) {
      editor.spiralStairStep(rand, c, stair, pillar);
    }

    decorate(editor, rand, theme, origin);
  }

  private void decorate(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
    List<Coord> spots = new ArrayList<Coord>();
    for (Cardinal dir : Cardinal.directions) {
      Coord cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 6);
      cursor.translate(dir, 7);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        spots.add(new Coord(c));
        c.translate(o);
        spots.add(new Coord(c));
        c.translate(o, 2);
        spots.add(new Coord(c));
        c.translate(o);
        spots.add(new Coord(c));
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 12);
      cursor.translate(dir, 5);
      spots.add(new Coord(cursor));
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        spots.add(new Coord(c));
        c.translate(o, 2);
        spots.add(new Coord(c));
        c.translate(o);
        spots.add(new Coord(c));
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 13);
      cursor.translate(dir, 4);
      spots.add(new Coord(cursor));
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        spots.add(new Coord(c));
        c.translate(o, 2);
        spots.add(new Coord(c));
        c.translate(o);
        spots.add(new Coord(c));
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 18);
      cursor.translate(dir, 2);
      spots.add(new Coord(cursor));
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        spots.add(new Coord(c));
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 19);
      Coord start = new Coord(cursor);
      Coord end = new Coord(cursor);
      start.translate(dir);
      end.translate(dir.reverse());
      start.translate(dir.left());
      end.translate(dir.right());
      spots.addAll(new RectSolid(start, end).get());
    }

    for (Coord c : spots) {
      if (rand.nextBoolean()) {
        tree(editor, rand, theme, c);
      }
    }

    Coord start = new Coord(origin);
    Coord end = new Coord(origin);
    end.translate(Cardinal.UP, 20);
    start.translate(Cardinal.NORTH, 8);
    start.translate(Cardinal.EAST, 8);
    end.translate(Cardinal.SOUTH, 8);
    end.translate(Cardinal.WEST, 8);
    Vine.fill(editor, start, end);
  }

  private void tree(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    MetaBlock leaves = Leaves.get(Wood.JUNGLE, false);

    Coord cursor = new Coord(origin);
    Log.getLog(Wood.JUNGLE).set(editor, cursor);
    for (Cardinal dir : Cardinal.directions) {
      Coord c = new Coord(cursor);
      c.translate(dir);
      leafSpill(editor, rand, theme, c, rand.nextInt(6));
    }
    if (rand.nextBoolean()) {
      cursor.translate(Cardinal.UP);
      Log.getLog(Wood.JUNGLE).set(editor, cursor);
      for (Cardinal dir : Cardinal.directions) {
        Coord c = new Coord(cursor);
        c.translate(dir);
        leaves.set(editor, rand, c, true, false);
      }
    }
    if (rand.nextInt(3) == 0) {
      cursor.translate(Cardinal.UP);
      Log.getLog(Wood.JUNGLE).set(editor, cursor);
      for (Cardinal dir : Cardinal.directions) {
        Coord c = new Coord(cursor);
        c.translate(dir);
        leaves.set(editor, rand, c, true, false);
      }
    }
    cursor.translate(Cardinal.UP);
    leaves.set(editor, cursor);
  }

  public void leafSpill(IWorldEditor editor, Random rand, ITheme theme, Coord origin, int count) {
    if (count < 0) {
      return;
    }
    MetaBlock leaves = Leaves.get(Wood.JUNGLE, false);
    leaves.set(editor, origin);
    Coord cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    if (!editor.getBlock(cursor).getMaterial().isOpaque()) {
      leaves.set(editor, origin);
      if (rand.nextBoolean()) {
        leafSpill(editor, rand, theme, cursor, count - 1);
      }
      return;
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      if (editor.getBlock(cursor).getMaterial().isOpaque()) {
        continue;
      }
      leaves.set(editor, origin);
      cursor.translate(Cardinal.DOWN);
      if (editor.getBlock(cursor).getMaterial().isOpaque()) {
        continue;
      }
      leafSpill(editor, rand, theme, cursor, count - 1);
    }
  }

  private void pillar(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;

    editor.fillDown(rand, new Coord(origin), pillar);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, cursor);
    }
  }

}
