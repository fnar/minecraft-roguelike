package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedGlass;

public class WitchTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord origin) {

    BlockBrush blocks = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush glass = stainedGlass().setColor(DyeColor.BLACK);

    Coord main = Tower.getBaseCoord(editor, origin);

    Coord cursor;
    Coord start;
    Coord end;

    // main floor

    start = main.copy();
    start.translate(Direction.NORTH, 3);
    start.translate(Direction.WEST, 3);
    start.translate(Direction.DOWN);
    end = main.copy();
    end.translate(Direction.SOUTH, 3);
    end.translate(Direction.EAST, 3);
    end.translate(Direction.UP, 3);

    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonal = dir.orthogonals();

      start = main.copy();
      start.translate(dir, 3);
      start.translate(orthogonal[0], 3);
      start.translate(Direction.DOWN);
      end = start.copy();
      end.translate(Direction.UP, 3);

      RectSolid.newRect(start, end).fill(editor, pillar);

      for (Direction o : orthogonal) {
        start = main.copy();
        start.translate(dir, 4);
        start.translate(o, 2);
        start.translate(Direction.DOWN);
        end = start.copy();
        end.translate(Direction.UP, 3);

        RectSolid.newRect(start, end).fill(editor, pillar);
        cursor = end.copy();
        cursor.translate(dir);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
        for (Direction d : orthogonal) {
          cursor = end.copy();
          cursor.translate(d);
          stair.setUpsideDown(true).setFacing(d).stroke(editor, cursor);
        }
      }
    }

    // second floor

    Coord secondFloor = main.copy();
    secondFloor.translate(Direction.UP, 4);

    start = secondFloor.copy();
    start.translate(Direction.NORTH, 4);
    start.translate(Direction.WEST, 4);
    start.translate(Direction.DOWN);
    end = secondFloor.copy();
    end.translate(Direction.SOUTH, 4);
    end.translate(Direction.EAST, 4);
    end.translate(Direction.UP, 6);

    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonal = dir.orthogonals();

      start = secondFloor.copy();
      start.translate(dir, 4);
      start.translate(Direction.UP);
      end = start.copy();
      start.translate(orthogonal[0]);
      end.translate(orthogonal[1]);
      end.translate(Direction.UP);
      RectSolid.newRect(start, end).fill(editor, glass);

      start = secondFloor.copy();
      start.translate(dir, 4);
      start.translate(Direction.DOWN);
      start.translate(orthogonal[0], 4);
      end = start.copy();
      end.translate(Direction.UP, 4);
      RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

      for (Direction o : orthogonal) {

        start = secondFloor.copy();
        start.translate(Direction.DOWN);
        start.translate(dir, 4);
        start.translate(o, 3);
        end = start.copy();
        end.translate(Direction.UP, 3);
        RectSolid.newRect(start, end).fill(editor, pillar);

        start = secondFloor.copy();
        start.translate(Direction.DOWN);
        start.translate(dir, 5);
        start.translate(o, 2);
        end = start.copy();
        end.translate(Direction.UP, 4);
        RectSolid.newRect(start, end).fill(editor, pillar);

        cursor = end.copy();
        cursor.translate(dir);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
        for (Direction d : orthogonal) {
          cursor = end.copy();
          cursor.translate(d);
          stair.setUpsideDown(true).setFacing(d).stroke(editor, cursor);
        }
      }
    }

    // third floor

    Coord thirdFloor = secondFloor.copy();
    thirdFloor.translate(Direction.UP, 7);

    start = thirdFloor.copy();
    start.translate(Direction.NORTH, 3);
    start.translate(Direction.WEST, 3);
    start.translate(Direction.DOWN);
    end = thirdFloor.copy();
    end.translate(Direction.SOUTH, 3);
    end.translate(Direction.EAST, 3);
    end.translate(Direction.UP, 4);

    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonal = dir.orthogonals();

      cursor = thirdFloor.copy();
      cursor.translate(dir, 3);
      cursor.translate(Direction.UP);
      window(editor, theme, dir, cursor);

      start = thirdFloor.copy();
      start.translate(dir, 2);
      end = start.copy();
      end.translate(dir, 4);
      end.translate(Direction.DOWN);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = thirdFloor.copy();
      start.translate(dir, 5);
      start.translate(Direction.DOWN, 2);
      end = start.copy();
      start.translate(orthogonal[0]);
      end.translate(orthogonal[1]);
      end.translate(Direction.DOWN);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = thirdFloor.copy();
      start.translate(dir, 3);
      start.translate(orthogonal[0], 3);
      start.translate(Direction.DOWN, 2);
      end = start.copy();
      end.translate(Direction.UP, 5);
      RectSolid.newRect(start, end).fill(editor, pillar);

      cursor = end.copy();
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

      cursor = end.copy();
      cursor.translate(orthogonal[0]);
      stair.setUpsideDown(true).setFacing(orthogonal[0]).stroke(editor, cursor);

      for (Direction o : orthogonal) {

        start = thirdFloor.copy();
        start.translate(dir, 4);
        start.translate(Direction.DOWN);
        start.translate(o, 3);
        end = start.copy();
        end.translate(o);
        end.translate(Direction.DOWN);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

        for (int i = 0; i < 4; ++i) {
          start = thirdFloor.copy();
          start.translate(dir, 4);
          start.translate(o, i + 1);
          start.translate(Direction.DOWN, i);
          end = start.copy();
          end.translate(dir, 2);
          stair.setUpsideDown(false).setFacing(o);
          RectSolid.newRect(start, end).fill(editor, stair);

          if (i < 3) {
            start = thirdFloor.copy();
            start.translate(dir, 4);
            start.translate(o, i + 1);
            start.translate(Direction.DOWN, i + 1);
            end = start.copy();
            end.translate(dir, 2);
            RectSolid.newRect(start, end).fill(editor, blocks);
          }

          start = thirdFloor.copy();
          start.translate(dir, 4);
          start.translate(o, 2);
          start.translate(Direction.DOWN, 3);
          end = start.copy();
          end.translate(dir, 2);
          RectSolid.newRect(start, end).fill(editor, blocks);

          cursor = thirdFloor.copy();
          cursor.translate(dir, 6);
          cursor.translate(o);
          cursor.translate(Direction.DOWN, 2);
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        }
      }
    }


    for (int i = thirdFloor.getY() - 1; i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(origin.getX(), i, origin.getZ()), theme.getPrimary().getStair(), theme.getPrimary().getPillar());
    }

    // attic

    Coord attic = thirdFloor.copy();
    attic.translate(Direction.UP, 5);

    start = attic.copy();
    start.translate(Direction.NORTH, 2);
    start.translate(Direction.WEST, 2);
    start.translate(Direction.DOWN);
    end = attic.copy();
    end.translate(Direction.SOUTH, 2);
    end.translate(Direction.EAST, 2);
    end.translate(Direction.UP, 3);

    RectHollow.newRect(start, end).fill(editor, blocks);

    start = attic.copy();
    start.translate(Direction.UP, 4);
    end = start.copy();
    start.translate(Direction.NORTH);
    start.translate(Direction.WEST);
    end.translate(Direction.SOUTH);
    end.translate(Direction.EAST);
    RectHollow.newRect(start, end).fill(editor, blocks);

    start = attic.copy();
    start.translate(Direction.UP, 5);
    end = start.copy();
    end.translate(Direction.UP, 2);
    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonal = dir.orthogonals();

      cursor = attic.copy();
      cursor.translate(dir, 2);
      cursor.translate(Direction.UP);
      window(editor, theme, dir, cursor);

      stair.setUpsideDown(false).setFacing(dir);

      start = attic.copy();
      start.translate(dir, 3);
      end = start.copy();
      start.translate(orthogonal[0], 3);
      end.translate(orthogonal[1], 3);

      RectSolid.newRect(start, end).fill(editor, stair);

      start = attic.copy();
      start.translate(dir, 4);
      start.translate(Direction.DOWN);
      end = start.copy();
      start.translate(orthogonal[0], 4);
      end.translate(orthogonal[1], 4);

      RectSolid.newRect(start, end).fill(editor, stair);

      start = attic.copy();
      start.translate(dir, 3);
      start.translate(Direction.UP, 3);
      end = start.copy();
      start.translate(orthogonal[0], 3);
      end.translate(orthogonal[1], 3);

      RectSolid.newRect(start, end).fill(editor, stair);

      start = attic.copy();
      start.translate(dir, 2);
      start.translate(Direction.UP, 4);
      end = start.copy();
      start.translate(orthogonal[0], 2);
      end.translate(orthogonal[1], 2);

      RectSolid.newRect(start, end).fill(editor, stair);

    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = main.copy();
      cursor.translate(dir, 4);
      if (editor.isAirBlock(cursor)) {
        cursor = main.copy();
        cursor.translate(dir, 3);
        theme.getPrimary().getDoor().setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir);
        start = cursor.copy();
        end = start.copy();
        end.translate(Direction.UP);
        end.translate(dir, 3);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

        cursor = main.copy();
        cursor.translate(dir, 4);
        cursor.translate(Direction.DOWN);
        step(editor, theme, dir, cursor);
        break;
      }
    }
  }

  private void window(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {

    Coord cursor;

    StairsBlock stair = theme.getPrimary().getStair();

    cursor = origin.copy();
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Direction.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(Direction.UP);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }
  }

  private void step(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {

    if (editor.isOpaqueCubeBlock(origin)) {
      return;
    }

    Coord start;
    Coord end;

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush blocks = theme.getPrimary().getWall();

    Direction[] orthogonal = dir.orthogonals();

    start = origin.copy();
    end = origin.copy();
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.newRect(start, end).fill(editor, blocks);

    start = origin.copy();
    end = origin.copy();
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    stair.setUpsideDown(false).setFacing(dir);
    RectSolid.newRect(start, end).fill(editor, stair);

    origin.translate(Direction.DOWN);
    origin.translate(dir);
    step(editor, theme, dir, origin);
  }
}
