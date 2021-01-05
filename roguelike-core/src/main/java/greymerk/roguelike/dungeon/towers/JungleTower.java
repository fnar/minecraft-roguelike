package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.Wood;
import com.github.srwaggon.roguelike.worldgen.block.decorative.VineBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class JungleTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {

    Coord origin = Tower.getBaseCoord(editor, dungeon);
    origin.translate(Cardinal.UP);
    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush grass = BlockType.GRASS.getBrush();

    Coord start;
    Coord end;
    Coord cursor;

    // lower pillars
    for (Cardinal dir : Cardinal.DIRECTIONS) {

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 3);
      cursor.translate(dir, 7);
      pillar(editor, theme, cursor);

      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o, 3);
        pillar(editor, theme, c);
        c.translate(dir);
        c.translate(Cardinal.UP);
        walls.stroke(editor, c);
        c.translate(Cardinal.UP);
        walls.stroke(editor, c);
        c.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
        c.translate(dir.reverse());
        walls.stroke(editor, c);
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 8);
      walls.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      walls.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      cursor.translate(dir.reverse());
      walls.stroke(editor, cursor);

      start = new Coord(origin);
      start.translate(dir, 2);
      start.translate(dir.antiClockwise(), 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      pillar.fill(editor, new RectSolid(start, end));
      cursor = new Coord(end);
      for (Cardinal d : new Cardinal[]{dir.reverse(), dir.clockwise()}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d).stroke(editor, c);
      }

      // corner pillar
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 6);
      cursor.translate(dir, 6);
      cursor.translate(dir.antiClockwise(), 6);
      editor.fillDown(new Coord(cursor), pillar);
      for (Cardinal d : new Cardinal[]{dir, dir.antiClockwise()}) {
        start = new Coord(cursor);
        start.translate(d);
        stair.setUpsideDown(false).setFacing(d).stroke(editor, start);
        start.translate(Cardinal.DOWN);
        end = new Coord(start);
        end.translate(Cardinal.DOWN, 2);
        walls.fill(editor, new RectSolid(start, end));
        end.translate(Cardinal.DOWN);
        stair.setUpsideDown(true).setFacing(d).stroke(editor, end);
      }
    }


    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 7);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.antiClockwise(), 5);
      end.translate(dir.clockwise(), 5);
      walls.fill(editor, new RectSolid(start, end));
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      grass.fill(editor, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      walls.fill(editor, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 6);
      pillar(editor, theme, cursor);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o, 3);
        pillar(editor, theme, c);
      }

      start = new Coord(origin);
      start.translate(Cardinal.UP, 5);
      start.translate(dir, 2);
      end = new Coord(start);
      end.translate(dir, 3);
      walls.fill(editor, new RectSolid(start, end));
      end.translate(dir.antiClockwise(), 3);
      start = new Coord(end);
      start.translate(dir.reverse(), 10);
      walls.fill(editor, new RectSolid(start, end));

      start = new Coord(origin);
      start.translate(Cardinal.UP, 6);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 2);
      end = new Coord(start);
      end.translate(dir.clockwise(), 8);
      end.translate(dir, 3);
      walls.fill(editor, new RectSolid(start, end));

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      start.translate(dir, 2);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise());
      end.translate(Cardinal.UP, 2);
      walls.fill(editor, new RectSolid(start, end));

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 2);
      stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));
    }

    // level 2 grass patches
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal o : dir.orthogonals()) {
        start = new Coord(origin);
        start.translate(Cardinal.UP, 6);
        start.translate(dir, 5);
        start.translate(o);
        end = new Coord(start);
        end.translate(o);
        end.translate(dir);
        grass.fill(editor, new RectSolid(start, end));
        start.translate(o, 3);
        end.translate(o, 3);
        grass.fill(editor, new RectSolid(start, end));
      }
    }

    // second floor pillars
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 9);
      cursor.translate(dir, 5);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o, 2);
        pillar(editor, theme, c);
        c.translate(dir);
        c.translate(Cardinal.UP);
        walls.stroke(editor, c);
        c.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
        c.translate(dir.reverse());
        walls.stroke(editor, c);
        c.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
      }
      cursor.translate(dir.antiClockwise(), 5);
      pillar(editor, theme, cursor);
      for (Cardinal d : new Cardinal[]{dir.antiClockwise(), dir}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        c.translate(Cardinal.UP);
        walls.stroke(editor, c);
        c.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(d).stroke(editor, c);
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 10);
      cursor.translate(dir, 2);
      cursor.translate(dir.antiClockwise(), 2);
      start = new Coord(cursor);
      end = new Coord(start);
      end.translate(Cardinal.DOWN, 3);
      pillar.fill(editor, new RectSolid(start, end));
      for (Cardinal d : new Cardinal[]{dir.clockwise(), dir.reverse()}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d).stroke(editor, c);
      }

      cursor.translate(Cardinal.DOWN);
      for (Cardinal d : new Cardinal[]{dir.antiClockwise(), dir}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d).stroke(editor, c);
        c.translate(Cardinal.UP);
        walls.stroke(editor, c);
        c.translate(d);
        walls.stroke(editor, c);
      }

    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = new Coord(origin);
      start.translate(Cardinal.UP, 10);
      start.translate(dir, 5);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 5);
      end.translate(dir.clockwise(), 4);
      walls.fill(editor, new RectSolid(start, end));

      start = new Coord(origin);
      start.translate(Cardinal.UP, 11);
      start.translate(dir, 2);
      end = new Coord(start);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise(), 4);
      end.translate(dir, 2);
      walls.fill(editor, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 11);
      cursor.translate(dir, 5);
      start = new Coord(cursor);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      grass.fill(editor, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 12);
      cursor.translate(dir, 3);
      start = new Coord(cursor);
      end = new Coord(start);
      end.translate(dir);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise(), 4);
      grass.fill(editor, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 12);
      cursor.translate(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      walls.fill(editor, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 11);
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      walls.stroke(editor, cursor);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(dir.antiClockwise(), 2);
      cursor.translate(Cardinal.UP, 15);
      pillar(editor, theme, cursor);
      for (Cardinal d : new Cardinal[]{dir, dir.antiClockwise()}) {
        Coord c = new Coord(cursor);
        c.translate(d);
        c.translate(Cardinal.UP);
        walls.stroke(editor, c);
        c.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(d).stroke(editor, c);
      }

      start = new Coord(origin);
      start.translate(Cardinal.UP, 16);
      start.translate(dir, 2);
      end = new Coord(start);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise(), 2);
      walls.fill(editor, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 17);
      cursor.translate(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      grass.fill(editor, new RectSolid(start, end));
      cursor.translate(dir.antiClockwise(), 2);
      walls.stroke(editor, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP, 17);
      end = new Coord(start);
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end.translate(dir.reverse());
      end.translate(dir.clockwise());
      walls.fill(editor, new RectSolid(start, end));
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      grass.fill(editor, new RectSolid(start, end));
    }

    start = new Coord(origin);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.EAST, 2);
    end = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.WEST, 2);
    walls.fill(editor, new RectSolid(start, end), false, true);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 12);
    start = new Coord(cursor.getX(), dungeon.getY(), cursor.getZ());
    end = new Coord(cursor);
    for (Coord c : new RectSolid(start, end)) {
      editor.spiralStairStep(rand, c, stair, pillar);
    }

    decorate(editor, rand, origin);
  }

  private void decorate(WorldEditor editor, Random rand, Coord origin) {
    List<Coord> spots = new ArrayList<>();
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      Coord cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 6);
      cursor.translate(dir, 7);
      for (Cardinal o : dir.orthogonals()) {
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
      for (Cardinal o : dir.orthogonals()) {
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
      for (Cardinal o : dir.orthogonals()) {
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
      for (Cardinal o : dir.orthogonals()) {
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
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      spots.addAll(new RectSolid(start, end).get());
    }

    for (Coord c : spots) {
      if (rand.nextBoolean()) {
        tree(editor, rand, c);
      }
    }

    Coord start = new Coord(origin)
        .translate(Cardinal.NORTH, 8)
        .translate(Cardinal.EAST, 8);

    Coord end = new Coord(origin)
        .translate(Cardinal.UP, 20)
        .translate(Cardinal.SOUTH, 8)
        .translate(Cardinal.WEST, 8);

    VineBlock.vine().fill(editor, new RectSolid(start, end));
  }

  private void tree(WorldEditor editor, Random rand, Coord origin) {

    Wood jungle = Wood.JUNGLE;
    BlockBrush leaves = jungle.getLeaves();
    BlockBrush log = jungle.getLog();

    Coord cursor = new Coord(origin);
    log.stroke(editor, cursor);
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      Coord c = new Coord(cursor);
      c.translate(dir);
      leafSpill(editor, rand, c, rand.nextInt(6));
    }
    if (rand.nextBoolean()) {
      cursor.translate(Cardinal.UP);
      log.stroke(editor, cursor);
      for (Cardinal dir : Cardinal.DIRECTIONS) {
        Coord c = new Coord(cursor);
        c.translate(dir);
        leaves.stroke(editor, c, true, false);
      }
    }
    if (rand.nextInt(3) == 0) {
      cursor.translate(Cardinal.UP);
      log.stroke(editor, cursor);
      for (Cardinal dir : Cardinal.DIRECTIONS) {
        Coord c = new Coord(cursor);
        c.translate(dir);
        leaves.stroke(editor, c, true, false);
      }
    }
    cursor.translate(Cardinal.UP);
    leaves.stroke(editor, cursor);
  }

  public void leafSpill(WorldEditor editor, Random rand, Coord origin, int count) {
    if (count < 0) {
      return;
    }
    BlockBrush leaves = Wood.JUNGLE.getLeaves();
    leaves.stroke(editor, origin);
    Coord cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    if (!editor.isOpaqueBlock(cursor)) {
      leaves.stroke(editor, origin);
      if (rand.nextBoolean()) {
        leafSpill(editor, rand, cursor, count - 1);
      }
      return;
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      if (editor.isOpaqueBlock(cursor)) {
        continue;
      }
      leaves.stroke(editor, origin);
      cursor.translate(Cardinal.DOWN);
      if (editor.isOpaqueBlock(cursor)) {
        continue;
      }
      leafSpill(editor, rand, cursor, count - 1);
    }
  }

  private void pillar(WorldEditor editor, ThemeBase theme, Coord origin) {

    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;

    editor.fillDown(new Coord(origin), pillar);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    }
  }

}
