package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedGlassPane;

public class BunkerTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {
    Coord origin = Tower.getBaseCoord(editor, dungeon);
    origin.translate(Cardinal.UP);
    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush window = stainedGlassPane().setColor(DyeColor.GRAY);

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(Cardinal.DOWN);
    start.translate(Cardinal.NORTH, 5);
    start.translate(Cardinal.EAST, 5);
    end.translate(Cardinal.SOUTH, 5);
    end.translate(Cardinal.WEST, 5);
    end.translate(Cardinal.UP, 4);
    RectHollow.fill(editor, start, end, walls, true, true);

    start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
    end = new Coord(origin);
    end.translate(Cardinal.DOWN);
    start.translate(Cardinal.NORTH, 5);
    start.translate(Cardinal.EAST, 5);
    end.translate(Cardinal.SOUTH, 5);
    end.translate(Cardinal.WEST, 5);
    RectSolid.newRect(start, end).fill(editor, walls, true, true);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = new Coord(origin);
      start.translate(dir, 5);
      end = new Coord(start);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      start = new Coord(start.getX(), dungeon.getY() + 10, start.getZ());
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, walls, true, true);
      end.translate(Cardinal.DOWN);
      end.translate(dir);
      start.translate(dir);
      RectSolid.newRect(start, end).fill(editor, walls, true, true);
      end.translate(Cardinal.DOWN);
      end.translate(dir);
      start.translate(dir);
      RectSolid.newRect(start, end).fill(editor, walls, true, true);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
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
      RectSolid.newRect(start, end).fill(editor, walls, true, true);
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end.translate(Cardinal.DOWN);
      end.translate(dir);
      end.translate(dir.antiClockwise());
      RectSolid.newRect(start, end).fill(editor, walls, true, true);


      for (Cardinal o : dir.orthogonals()) {
        start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
        start.translate(dir, 5);
        start.translate(o, 5);
        end = new Coord(origin);
        end.translate(dir, 5);
        end.translate(o, 5);
        end.translate(Cardinal.UP, 2);
        end.translate(o, 2);
        RectSolid.newRect(start, end).fill(editor, walls, true, true);
      }
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      stair.setUpsideDown(false).setFacing(dir);
      for (Cardinal o : dir.orthogonals()) {
        start = new Coord(origin);
        start.translate(dir, 6);
        start.translate(o, 6);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(o.reverse());
        RectSolid.newRect(start, end).fill(editor, stair);
        start.translate(Cardinal.DOWN);
        start.translate(dir);
        start.translate(o);
        end = new Coord(start);
        end.translate(o.reverse(), 2);
        RectSolid.newRect(start, end).fill(editor, stair);
      }
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 3);
      cursor.translate(dir, 6);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
      }
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
      }
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 5);
      start = new Coord(cursor);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 5);
      end.translate(dir.clockwise(), 5);
      stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir, 4);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        pillar.stroke(editor, c);
        c.translate(o);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
        c.translate(o);
        pillar.stroke(editor, c);
      }
      cursor.translate(Cardinal.UP);
      window.stroke(editor, cursor);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        pillar.stroke(editor, c);
        c.translate(o);
        window.stroke(editor, c);
        c.translate(o);
        pillar.stroke(editor, c);
      }
      cursor.translate(Cardinal.UP);
      start = new Coord(cursor);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      start.translate(dir.clockwise());
      end.translate(dir.antiClockwise());
      stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(editor, start);
      stair.setUpsideDown(false).setFacing(dir.clockwise()).stroke(editor, end);
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      start.translate(dir.clockwise());
      end.translate(dir.antiClockwise());
      stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(editor, start);
      stair.setUpsideDown(false).setFacing(dir.clockwise()).stroke(editor, end);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 8);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.EAST, 2);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.WEST, 2);
    RectSolid.newRect(start, end).fill(editor, walls);
    cursor.translate(Cardinal.UP);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.EAST);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.WEST);
    RectSolid.newRect(start, end).fill(editor, walls);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      start.translate(dir, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = new Coord(origin);
      start.translate(dir, 4);
      start.translate(dir.antiClockwise(), 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, pillar);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = new Coord(origin);
      start.translate(Cardinal.UP, 5);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(editor, pillar);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      Cardinal[] orthogonals = dir.orthogonals();
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 2);
      cursor.translate(dir, 5);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      BlockType.REDSTONE_BLOCK.getBrush().stroke(editor, cursor);
      cursor.translate(dir.reverse());
      BlockType.REDSTONE_LAMP_LIT.getBrush().stroke(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      for (Cardinal o : orthogonals) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, c);
        c.translate(dir);
        stair.setUpsideDown(true).setFacing(o).stroke(editor, c);
      }
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = new Coord(origin);
      start.translate(dir, 5);
      end = new Coord(start);
      end.translate(Cardinal.UP);
      end.translate(dir, 3);
      RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

      cursor = new Coord(start);
      for (Cardinal o : dir.orthogonals()) {
        start = new Coord(cursor);
        start.translate(o, 2);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(o);
        stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
        start.translate(Cardinal.UP);
        end.translate(Cardinal.UP);
        RectSolid.newRect(start, end).fill(editor, window);
        start.translate(Cardinal.DOWN, 2);
        end.translate(Cardinal.DOWN, 2);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(editor, walls);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        stair.setUpsideDown(false).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
      }

      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, c);
        c.translate(dir);
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, c);
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
