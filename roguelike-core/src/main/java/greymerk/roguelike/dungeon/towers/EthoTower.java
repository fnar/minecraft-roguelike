package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class EthoTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, Theme theme, Coord dungeon) {

    BlockBrush primary = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getSecondary().getPillar();

    StairsBlock stair = theme.getSecondary().getStair();

    Coord floor = TowerType.getBaseCoord(editor, dungeon);

    Coord start = floor.copy();
    Coord end = start.copy();
    Coord cursor;

    int x = dungeon.getX();
    int z = dungeon.getZ();

    start.north(3);
    start.west(3);
    end.south(3);
    end.east(3);
    end.up(4);

    //WorldGenPrimitive.fillRectSolid(rand, start, end, air);

    start.north();
    start.west();
    start.down();
    end.south();
    end.east();
    end.up();

    RectHollow.newRect(start, end).fill(editor, primary);

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonals = dir.orthogonals();
      start = floor.copy();
      start.translate(dir, 3);
      start.translate(orthogonals[0], 3);
      end = start.copy();
      end.up(6);

      RectSolid.newRect(start, end).fill(editor, pillar);

      for (Direction o : orthogonals) {
        start = floor.copy();
        start.translate(dir, 5);
        start.translate(o, 4);
        end = start.copy();
        end.up(4);
        start.down(10);
        RectSolid.newRect(start, end).fill(editor, pillar);

        end.up();
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, end);

        end.translate(dir.reverse());
        end.translate(o.reverse());
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, end);
        end.translate(o.reverse());
        start = end.copy();
        start.translate(o.reverse(), 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(false).setFacing(dir));

        end.translate(dir.reverse());
        end.up();
        start.translate(dir.reverse());
        start.up();
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(false).setFacing(dir));
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, end);

        start = floor.copy();
        start.translate(dir, 3);
        start.up(4);
        end = start.copy();
        end.translate(o, 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(true).setFacing(dir.reverse()));
        start.translate(dir.reverse());
        start.up();
        end = start.copy();
        end.translate(o, 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(true).setFacing(dir.reverse()));
        start.up();
        end.up();
        RectSolid.newRect(start, end).fill(editor, pillar);
        cursor = end.copy();
        start = end.copy();
        start.up(3);
        RectSolid.newRect(start, end).fill(editor, pillar);
        cursor.translate(o.reverse());
        cursor.up();
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
        cursor.up(2);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        start.up();
        end = start.copy();
        end.translate(o.reverse(), 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(false).setFacing(dir));
        cursor = end.copy();
        cursor.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
        cursor.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
        cursor.up();
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);


      }


    }

    Direction front = Direction.NORTH;

    for (Direction dir : Direction.CARDINAL) {
      cursor = floor.copy();
      cursor.translate(dir, 6);
      if (editor.isAirBlock(cursor)) {
        front = dir;
        break;
      }
    }

    for (Direction dir : Direction.CARDINAL) {

      if (dir == front) {

        for (Direction o : dir.orthogonals()) {
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
          cursor.up();
          stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.up();
          stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
          cursor.translate(o.reverse());
          cursor.up();
          stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.translate(o.reverse());
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.translate(o.reverse());
          cursor.up();
          stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
        }

        // carve doorway
        Direction[] orthogonals = dir.orthogonals();
        cursor = floor.copy();
        cursor.translate(dir, 4);
        start = cursor.copy();
        end = start.copy();
        start.translate(orthogonals[0]);
        end.up(2);
        end.translate(orthogonals[1]);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

        cursor = floor.copy();
        cursor.translate(dir, 6);
        cursor.down();
        step(editor, theme, dir, cursor);

        continue;
      }


      for (Direction o : dir.orthogonals()) {
        start = floor.copy();
        start.up(4);
        start.translate(dir, 5);
        end = start.copy();
        start.translate(o, 2);
        RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(false).setFacing(dir));
        start.translate(o);
        stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, start);
        start.down();
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, start);
      }

    }


    for (int i = floor.getY() - 1; i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getPrimary().getPillar());
    }
  }

  private void step(WorldEditor editor, Theme theme, Direction dir, Coord origin) {

    if (editor.isOpaqueCubeBlock(origin)) {
      return;
    }

    Coord start;
    Coord end;

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush blocks = theme.getPrimary().getWall();

    Direction[] orthogonals = dir.orthogonals();

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

    origin.down();
    origin.translate(dir);
    step(editor, theme, dir, origin);
  }

}
