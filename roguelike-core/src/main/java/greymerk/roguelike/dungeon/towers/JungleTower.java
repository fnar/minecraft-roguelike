package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.decorative.VineBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.material.Wood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class JungleTower extends Tower {

  @Override
  public void generate(WorldEditor editor, Random rand, Theme theme, Coord dungeon) {

    Coord origin = TowerType.getBaseCoord(editor, dungeon);
    origin.up();
    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush grass = BlockType.GRASS.getBrush();

    Coord start;
    Coord end;
    Coord cursor;

    // lower pillars
    for (Direction dir : Direction.CARDINAL) {

      cursor = origin.copy();
      cursor.up(3);
      cursor.translate(dir, 7);
      pillar(editor, theme, cursor);

      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o, 3);
        pillar(editor, theme, c);
        c.translate(dir);
        c.up();
        walls.stroke(editor, c);
        c.up();
        walls.stroke(editor, c);
        c.up();
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
        c.translate(dir.reverse());
        walls.stroke(editor, c);
      }

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir, 8);
      walls.stroke(editor, cursor);
      cursor.up();
      walls.stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      cursor.translate(dir.reverse());
      walls.stroke(editor, cursor);

      start = origin.copy();
      start.translate(dir, 2);
      start.translate(dir.antiClockwise(), 2);
      end = start.copy();
      end.up(3);
      pillar.fill(editor, new RectSolid(start, end));
      cursor = end.copy();
      for (Direction d : new Direction[]{dir.reverse(), dir.clockwise()}) {
        Coord c = cursor.copy();
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d).stroke(editor, c);
      }

      // corner pillar
      cursor = origin.copy();
      cursor.up(6);
      cursor.translate(dir, 6);
      cursor.translate(dir.antiClockwise(), 6);
      editor.fillDown(cursor.copy(), pillar);
      for (Direction d : new Direction[]{dir, dir.antiClockwise()}) {
        start = cursor.copy();
        start.translate(d);
        stair.setUpsideDown(false).setFacing(d).stroke(editor, start);
        start.down();
        end = start.copy();
        end.down(2);
        walls.fill(editor, new RectSolid(start, end));
        end.down();
        stair.setUpsideDown(true).setFacing(d).stroke(editor, end);
      }
    }


    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir, 7);
      start = cursor.copy();
      end = cursor.copy();
      start.translate(dir.antiClockwise(), 5);
      end.translate(dir.clockwise(), 5);
      walls.fill(editor, new RectSolid(start, end));
      start.up();
      end.up();
      grass.fill(editor, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      walls.fill(editor, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir, 6);
      pillar(editor, theme, cursor);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o, 3);
        pillar(editor, theme, c);
      }

      start = origin.copy();
      start.up(5);
      start.translate(dir, 2);
      end = start.copy();
      end.translate(dir, 3);
      walls.fill(editor, new RectSolid(start, end));
      end.translate(dir.antiClockwise(), 3);
      start = end.copy();
      start.translate(dir.reverse(), 10);
      walls.fill(editor, new RectSolid(start, end));

      start = origin.copy();
      start.up(6);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 2);
      end = start.copy();
      end.translate(dir.clockwise(), 8);
      end.translate(dir, 3);
      walls.fill(editor, new RectSolid(start, end));

      start = origin.copy();
      start.up(4);
      start.translate(dir, 2);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise());
      end.up(2);
      walls.fill(editor, new RectSolid(start, end));

      start = origin.copy();
      start.up(4);
      start.translate(dir, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 2);
      stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));
    }

    // level 2 grass patches
    for (Direction dir : Direction.CARDINAL) {
      for (Direction o : dir.orthogonals()) {
        start = origin.copy();
        start.up(6);
        start.translate(dir, 5);
        start.translate(o);
        end = start.copy();
        end.translate(o);
        end.translate(dir);
        grass.fill(editor, new RectSolid(start, end));
        start.translate(o, 3);
        end.translate(o, 3);
        grass.fill(editor, new RectSolid(start, end));
      }
    }

    // second floor pillars
    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.up(9);
      cursor.translate(dir, 5);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o, 2);
        pillar(editor, theme, c);
        c.translate(dir);
        c.up();
        walls.stroke(editor, c);
        c.up();
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
        c.translate(dir.reverse());
        walls.stroke(editor, c);
        c.up();
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
      }
      cursor.translate(dir.antiClockwise(), 5);
      pillar(editor, theme, cursor);
      for (Direction d : new Direction[]{dir.antiClockwise(), dir}) {
        Coord c = cursor.copy();
        c.translate(d);
        c.up();
        walls.stroke(editor, c);
        c.up();
        stair.setUpsideDown(false).setFacing(d).stroke(editor, c);
      }

      cursor = origin.copy();
      cursor.up(10);
      cursor.translate(dir, 2);
      cursor.translate(dir.antiClockwise(), 2);
      start = cursor.copy();
      end = start.copy();
      end.down(3);
      pillar.fill(editor, new RectSolid(start, end));
      for (Direction d : new Direction[]{dir.clockwise(), dir.reverse()}) {
        Coord c = cursor.copy();
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d).stroke(editor, c);
      }

      cursor.down();
      for (Direction d : new Direction[]{dir.antiClockwise(), dir}) {
        Coord c = cursor.copy();
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d).stroke(editor, c);
        c.up();
        walls.stroke(editor, c);
        c.translate(d);
        walls.stroke(editor, c);
      }

    }

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.up(10);
      start.translate(dir, 5);
      end = start.copy();
      start.translate(dir.antiClockwise(), 5);
      end.translate(dir.clockwise(), 4);
      walls.fill(editor, new RectSolid(start, end));

      start = origin.copy();
      start.up(11);
      start.translate(dir, 2);
      end = start.copy();
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise(), 4);
      end.translate(dir, 2);
      walls.fill(editor, new RectSolid(start, end));

      cursor = origin.copy();
      cursor.up(11);
      cursor.translate(dir, 5);
      start = cursor.copy();
      end = start.copy();
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      grass.fill(editor, new RectSolid(start, end));

      cursor = origin.copy();
      cursor.up(12);
      cursor.translate(dir, 3);
      start = cursor.copy();
      end = start.copy();
      end.translate(dir);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise(), 4);
      grass.fill(editor, new RectSolid(start, end));
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.up(12);
      cursor.translate(dir, 2);
      start = cursor.copy();
      end = cursor.copy();
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      walls.fill(editor, new RectSolid(start, end));

      cursor = origin.copy();
      cursor.up(11);
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      walls.stroke(editor, cursor);
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(dir.antiClockwise(), 2);
      cursor.up(15);
      pillar(editor, theme, cursor);
      for (Direction d : new Direction[]{dir, dir.antiClockwise()}) {
        Coord c = cursor.copy();
        c.translate(d);
        c.up();
        walls.stroke(editor, c);
        c.up();
        stair.setUpsideDown(false).setFacing(d).stroke(editor, c);
      }

      start = origin.copy();
      start.up(16);
      start.translate(dir, 2);
      end = start.copy();
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise(), 2);
      walls.fill(editor, new RectSolid(start, end));

      cursor = origin.copy();
      cursor.up(17);
      cursor.translate(dir, 2);
      start = cursor.copy();
      end = cursor.copy();
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      grass.fill(editor, new RectSolid(start, end));
      cursor.translate(dir.antiClockwise(), 2);
      walls.stroke(editor, cursor);

      start = origin.copy();
      start.up(17);
      end = start.copy();
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end.translate(dir.reverse());
      end.translate(dir.clockwise());
      walls.fill(editor, new RectSolid(start, end));
      start.up();
      end.up();
      grass.fill(editor, new RectSolid(start, end));
    }

    start = origin.copy();
    start.north(2);
    start.east(2);
    end = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
    end.south(2);
    end.west(2);
    walls.fill(editor, new RectSolid(start, end), false, true);

    cursor = origin.copy();
    cursor.up(12);
    start = new Coord(cursor.getX(), dungeon.getY(), cursor.getZ());
    end = cursor.copy();
    for (Coord c : new RectSolid(start, end)) {
      editor.spiralStairStep(rand, c, stair, pillar);
    }

    decorate(editor, rand, origin);
  }

  private void decorate(WorldEditor editor, Random rand, Coord origin) {
    List<Coord> spots = new ArrayList<>();
    for (Direction dir : Direction.CARDINAL) {
      Coord cursor = origin.copy();
      cursor.up(6);
      cursor.translate(dir, 7);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        spots.add(c.copy());
        c.translate(o);
        spots.add(c.copy());
        c.translate(o, 2);
        spots.add(c.copy());
        c.translate(o);
        spots.add(c.copy());
      }

      cursor = origin.copy();
      cursor.up(12);
      cursor.translate(dir, 5);
      spots.add(cursor.copy());
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        spots.add(c.copy());
        c.translate(o, 2);
        spots.add(c.copy());
        c.translate(o);
        spots.add(c.copy());
      }

      cursor = origin.copy();
      cursor.up(13);
      cursor.translate(dir, 4);
      spots.add(cursor.copy());
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        spots.add(c.copy());
        c.translate(o, 2);
        spots.add(c.copy());
        c.translate(o);
        spots.add(c.copy());
      }

      cursor = origin.copy();
      cursor.up(18);
      cursor.translate(dir, 2);
      spots.add(cursor.copy());
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        spots.add(c.copy());
      }

      cursor = origin.copy();
      cursor.up(19);
      Coord start = cursor.copy();
      Coord end = cursor.copy();
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

    Coord start = origin.copy()
        .north(8)
        .east(8);

    Coord end = origin.copy()
        .up(20)
        .south(8)
        .west(8);

    VineBlock.vine().fill(editor, new RectSolid(start, end));
  }

  private void tree(WorldEditor editor, Random rand, Coord origin) {

    Wood jungle = Wood.JUNGLE;
    BlockBrush leaves = jungle.getLeaves();
    BlockBrush log = jungle.getLog();

    Coord cursor = origin.copy();
    log.stroke(editor, cursor);
    for (Direction dir : Direction.CARDINAL) {
      Coord c = cursor.copy();
      c.translate(dir);
      leafSpill(editor, rand, c, rand.nextInt(6));
    }
    if (rand.nextBoolean()) {
      cursor.up();
      log.stroke(editor, cursor);
      for (Direction dir : Direction.CARDINAL) {
        Coord c = cursor.copy();
        c.translate(dir);
        leaves.stroke(editor, c, true, false);
      }
    }
    if (rand.nextInt(3) == 0) {
      cursor.up();
      log.stroke(editor, cursor);
      for (Direction dir : Direction.CARDINAL) {
        Coord c = cursor.copy();
        c.translate(dir);
        leaves.stroke(editor, c, true, false);
      }
    }
    cursor.up();
    leaves.stroke(editor, cursor);
  }

  public void leafSpill(WorldEditor editor, Random rand, Coord origin, int count) {
    if (count < 0) {
      return;
    }
    BlockBrush leaves = Wood.JUNGLE.getLeaves();
    leaves.stroke(editor, origin);
    Coord cursor = origin.copy();
    cursor.down();
    if (!editor.isOpaqueBlock(cursor)) {
      leaves.stroke(editor, origin);
      if (rand.nextBoolean()) {
        leafSpill(editor, rand, cursor, count - 1);
      }
      return;
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir);
      if (editor.isOpaqueBlock(cursor)) {
        continue;
      }
      leaves.stroke(editor, origin);
      cursor.down();
      if (editor.isOpaqueBlock(cursor)) {
        continue;
      }
      leafSpill(editor, rand, cursor, count - 1);
    }
  }

  private void pillar(WorldEditor editor, Theme theme, Coord origin) {

    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;

    editor.fillDown(origin.copy(), pillar);

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    }
  }

}
