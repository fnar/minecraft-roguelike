package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class EthoTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {

    BlockBrush primary = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getSecondary().getPillar();

    StairsBlock stair = theme.getSecondary().getStair();

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    Coord start = floor.copy();
    Coord end = start.copy();
    Coord cursor;

    int x = dungeon.getX();
    int z = dungeon.getZ();

    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP, 4);

    //WorldGenPrimitive.fillRectSolid(rand, start, end, air);

    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.WEST);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.EAST);
    end.translate(Cardinal.UP);

    RectHollow.newRect(start, end).fill(editor, primary);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orthogonals = dir.orthogonals();
      start = floor.copy();
      start.translate(dir, 3);
      start.translate(orthogonals[0], 3);
      end = start.copy();
      end.translate(Cardinal.UP, 6);

      RectSolid.newRect(start, end).fill(editor, pillar);

      for (Cardinal o : orthogonals) {
        start = floor.copy();
        start.translate(dir, 5);
        start.translate(o, 4);
        end = start.copy();
        end.translate(Cardinal.UP, 4);
        start.translate(Cardinal.DOWN, 10);
        RectSolid.newRect(start, end).fill(editor, pillar);

        end.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, end);

        end.translate(dir.reverse());
        end.translate(o.reverse());
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, end);
        end.translate(o.reverse());
        start = end.copy();
        start.translate(o.reverse(), 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(false).setFacing(dir));

        end.translate(dir.reverse());
        end.translate(Cardinal.UP);
        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(false).setFacing(dir));
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, end);

        start = floor.copy();
        start.translate(dir, 3);
        start.translate(Cardinal.UP, 4);
        end = start.copy();
        end.translate(o, 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(true).setFacing(dir.reverse()));
        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end = start.copy();
        end.translate(o, 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(true).setFacing(dir.reverse()));
        start.translate(Cardinal.UP);
        end.translate(Cardinal.UP);
        RectSolid.newRect(start, end).fill(editor, pillar);
        cursor = end.copy();
        start = end.copy();
        start.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, pillar);
        cursor.translate(o.reverse());
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 2);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        start.translate(Cardinal.UP);
        end = start.copy();
        end.translate(o.reverse(), 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(false).setFacing(dir));
        cursor = end.copy();
        cursor.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
        cursor.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);


      }


    }

    Cardinal front = Cardinal.NORTH;

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = floor.copy();
      cursor.translate(dir, 6);
      if (editor.isAirBlock(cursor)) {
        front = dir;
        break;
      }
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      if (dir == front) {

        for (Cardinal o : dir.orthogonals()) {
          cursor = floor.copy();
          cursor.translate(dir, 5);
          cursor.translate(o, 2);
          primary.stroke(editor, cursor);
          cursor.translate(o);
          stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
          cursor.translate(dir);
          stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
          cursor.translate(o.reverse());
          stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(dir.reverse());
          cursor.translate(Cardinal.UP);
          stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.translate(Cardinal.UP);
          stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
          cursor.translate(o.reverse());
          cursor.translate(Cardinal.UP);
          stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.translate(o.reverse());
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.translate(o.reverse());
          cursor.translate(Cardinal.UP);
          stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
        }

        // carve doorway
        Cardinal[] orthogonals = dir.orthogonals();
        cursor = floor.copy();
        cursor.translate(dir, 4);
        start = cursor.copy();
        end = start.copy();
        start.translate(orthogonals[0]);
        end.translate(Cardinal.UP, 2);
        end.translate(orthogonals[1]);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

        cursor = floor.copy();
        cursor.translate(dir, 6);
        cursor.translate(Cardinal.DOWN);
        step(editor, theme, dir, cursor);

        continue;
      }


      for (Cardinal o : dir.orthogonals()) {
        start = floor.copy();
        start.translate(Cardinal.UP, 4);
        start.translate(dir, 5);
        end = start.copy();
        start.translate(o, 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(false).setFacing(dir));
        start.translate(o);
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, start);
        start.translate(Cardinal.DOWN);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, start);
      }

    }


    for (int i = floor.getY() - 1; i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getPrimary().getPillar());
    }
  }

  private void step(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord origin) {

    if (editor.isOpaqueCubeBlock(origin)) {
      return;
    }

    Coord start;
    Coord end;

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush blocks = theme.getPrimary().getWall();

    Cardinal[] orthogonals = dir.orthogonals();

    start = origin.copy();
    end = origin.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.newRect(start, end).fill(editor, blocks);

    start = origin.copy();
    end = origin.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    stair.setUpsideDown(false).setFacing(dir);
    RectSolid.newRect(start, end).fill(editor, stair);

    origin.translate(Cardinal.DOWN);
    origin.translate(dir);
    step(editor, theme, dir, origin);
  }

}
