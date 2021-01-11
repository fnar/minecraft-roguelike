package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.stainedGlassPane;

public class BunkerTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {
    Coord origin = Tower.getBaseCoord(editor, dungeon);
    origin.translate(Direction.UP);
    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush window = stainedGlassPane().setColor(DyeColor.GRAY);

    start = origin.copy();
    end = start.copy();
    start.translate(Direction.DOWN);
    start.translate(Direction.NORTH, 5);
    start.translate(Direction.EAST, 5);
    end.translate(Direction.SOUTH, 5);
    end.translate(Direction.WEST, 5);
    end.translate(Direction.UP, 4);
    RectHollow.newRect(start, end).fill(editor, walls);

    start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
    end = origin.copy();
    end.translate(Direction.DOWN);
    start.translate(Direction.NORTH, 5);
    start.translate(Direction.EAST, 5);
    end.translate(Direction.SOUTH, 5);
    end.translate(Direction.WEST, 5);
    RectSolid.newRect(start, end).fill(editor, walls);

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(dir, 5);
      end = start.copy();
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      start = new Coord(start.getX(), dungeon.getY() + 10, start.getZ());
      end.translate(Direction.UP, 3);
      RectSolid.newRect(start, end).fill(editor, walls);
      end.translate(Direction.DOWN);
      end.translate(dir);
      start.translate(dir);
      RectSolid.newRect(start, end).fill(editor, walls);
      end.translate(Direction.DOWN);
      end.translate(dir);
      start.translate(dir);
      RectSolid.newRect(start, end).fill(editor, walls);
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
      start.translate(dir, 6);
      start.translate(dir.antiClockwise(), 6);
      end = origin.copy();
      end.translate(dir, 6);
      end.translate(dir.antiClockwise(), 6);
      end.translate(Direction.UP, 2);
      RectSolid.newRect(start, end).fill(editor, walls);
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end.translate(Direction.DOWN);
      end.translate(dir);
      end.translate(dir.antiClockwise());
      RectSolid.newRect(start, end).fill(editor, walls);


      for (Direction o : dir.orthogonals()) {
        start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
        start.translate(dir, 5);
        start.translate(o, 5);
        end = origin.copy();
        end.translate(dir, 5);
        end.translate(o, 5);
        end.translate(Direction.UP, 2);
        end.translate(o, 2);
        RectSolid.newRect(start, end).fill(editor, walls);
      }
    }

    for (Direction dir : Direction.CARDINAL) {
      stair.setUpsideDown(false).setFacing(dir);
      for (Direction o : dir.orthogonals()) {
        start = origin.copy();
        start.translate(dir, 6);
        start.translate(o, 6);
        start.translate(Direction.UP, 3);
        end = start.copy();
        end.translate(o.reverse());
        RectSolid.newRect(start, end).fill(editor, stair);
        start.translate(Direction.DOWN);
        start.translate(dir);
        start.translate(o);
        end = start.copy();
        end.translate(o.reverse(), 2);
        RectSolid.newRect(start, end).fill(editor, stair);
      }
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(Direction.UP, 3);
      cursor.translate(dir, 6);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
      }
      cursor.translate(Direction.DOWN);
      cursor.translate(dir);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
      }
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(Direction.UP, 4);
      cursor.translate(dir, 5);
      start = cursor.copy();
      end = start.copy();
      start.translate(dir.antiClockwise(), 5);
      end.translate(dir.clockwise(), 5);
      stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(Direction.UP, 5);
      cursor.translate(dir, 4);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        pillar.stroke(editor, c);
        c.translate(o);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
        c.translate(o);
        pillar.stroke(editor, c);
      }
      cursor.translate(Direction.UP);
      window.stroke(editor, cursor);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        pillar.stroke(editor, c);
        c.translate(o);
        window.stroke(editor, c);
        c.translate(o);
        pillar.stroke(editor, c);
      }
      cursor.translate(Direction.UP);
      start = cursor.copy();
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
      start.translate(Direction.UP);
      end.translate(Direction.UP);
      start.translate(dir.clockwise());
      end.translate(dir.antiClockwise());
      stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(editor, start);
      stair.setUpsideDown(false).setFacing(dir.clockwise()).stroke(editor, end);
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      start.translate(Direction.UP);
      end.translate(Direction.UP);
      start.translate(dir.clockwise());
      end.translate(dir.antiClockwise());
      stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(editor, start);
      stair.setUpsideDown(false).setFacing(dir.clockwise()).stroke(editor, end);
    }

    cursor = origin.copy();
    cursor.translate(Direction.UP, 8);
    start = cursor.copy();
    end = cursor.copy();
    start.translate(Direction.NORTH, 2);
    start.translate(Direction.EAST, 2);
    end.translate(Direction.SOUTH, 2);
    end.translate(Direction.WEST, 2);
    RectSolid.newRect(start, end).fill(editor, walls);
    cursor.translate(Direction.UP);
    start = cursor.copy();
    end = cursor.copy();
    start.translate(Direction.NORTH);
    start.translate(Direction.EAST);
    end.translate(Direction.SOUTH);
    end.translate(Direction.WEST);
    RectSolid.newRect(start, end).fill(editor, walls);

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(Direction.UP, 3);
      start.translate(dir, 4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    }

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(dir, 4);
      start.translate(dir.antiClockwise(), 4);
      end = start.copy();
      end.translate(Direction.UP, 3);
      RectSolid.newRect(start, end).fill(editor, pillar);
    }

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(Direction.UP, 5);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end = start.copy();
      end.translate(Direction.UP, 2);
      RectSolid.newRect(start, end).fill(editor, pillar);
    }

    for (Direction dir : Direction.CARDINAL) {
      Direction[] orthogonals = dir.orthogonals();
      cursor = origin.copy();
      cursor.translate(Direction.UP, 2);
      cursor.translate(dir, 5);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Direction.UP);
      BlockType.REDSTONE_BLOCK.getBrush().stroke(editor, cursor);
      cursor.translate(dir.reverse());
      BlockType.REDSTONE_LAMP_LIT.getBrush().stroke(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      for (Direction o : orthogonals) {
        Coord c = cursor.copy();
        c.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, c);
        c.translate(dir);
        stair.setUpsideDown(true).setFacing(o).stroke(editor, c);
      }
    }

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(dir, 5);
      end = start.copy();
      end.translate(Direction.UP);
      end.translate(dir, 3);
      RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

      cursor = start.copy();
      for (Direction o : dir.orthogonals()) {
        start = cursor.copy();
        start.translate(o, 2);
        start.translate(Direction.UP);
        end = start.copy();
        end.translate(o);
        stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
        start.translate(Direction.UP);
        end.translate(Direction.UP);
        RectSolid.newRect(start, end).fill(editor, window);
        start.translate(Direction.DOWN, 2);
        end.translate(Direction.DOWN, 2);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(editor, walls);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        stair.setUpsideDown(false).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
      }

      cursor = origin.copy();
      cursor.translate(dir, 3);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, c);
        c.translate(dir);
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, c);
      }


    }

    cursor = origin.copy();
    cursor.translate(Direction.UP, 4);
    start = new Coord(cursor.getX(), dungeon.getY(), cursor.getZ());
    end = cursor.copy();
    for (Coord c : new RectSolid(start, end)) {
      editor.spiralStairStep(rand, c, stair, pillar);
    }
  }
}
