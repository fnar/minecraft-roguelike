package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
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

    start = new Coord(main);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    start.translate(Cardinal.DOWN);
    end = new Coord(main);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP, 3);

    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orthogonal = dir.orthogonals();

      start = new Coord(main);
      start.translate(dir, 3);
      start.translate(orthogonal[0], 3);
      start.translate(Cardinal.DOWN);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);

      RectSolid.newRect(start, end).fill(editor, pillar);

      for (Cardinal o : orthogonal) {
        start = new Coord(main);
        start.translate(dir, 4);
        start.translate(o, 2);
        start.translate(Cardinal.DOWN);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);

        RectSolid.newRect(start, end).fill(editor, pillar);
        cursor = new Coord(end);
        cursor.translate(dir);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
        for (Cardinal d : orthogonal) {
          cursor = new Coord(end);
          cursor.translate(d);
          stair.setUpsideDown(true).setFacing(d).stroke(editor, cursor);
        }
      }
    }

    // second floor

    Coord secondFloor = new Coord(main);
    secondFloor.translate(Cardinal.UP, 4);

    start = new Coord(secondFloor);
    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    start.translate(Cardinal.DOWN);
    end = new Coord(secondFloor);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    end.translate(Cardinal.UP, 6);

    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orthogonal = dir.orthogonals();

      start = new Coord(secondFloor);
      start.translate(dir, 4);
      start.translate(Cardinal.UP);
      end = new Coord(start);
      start.translate(orthogonal[0]);
      end.translate(orthogonal[1]);
      end.translate(Cardinal.UP);
      RectSolid.newRect(start, end).fill(editor, glass);

      start = new Coord(secondFloor);
      start.translate(dir, 4);
      start.translate(Cardinal.DOWN);
      start.translate(orthogonal[0], 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 4);
      RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

      for (Cardinal o : orthogonal) {

        start = new Coord(secondFloor);
        start.translate(Cardinal.DOWN);
        start.translate(dir, 4);
        start.translate(o, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, pillar);

        start = new Coord(secondFloor);
        start.translate(Cardinal.DOWN);
        start.translate(dir, 5);
        start.translate(o, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        RectSolid.newRect(start, end).fill(editor, pillar);

        cursor = new Coord(end);
        cursor.translate(dir);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
        for (Cardinal d : orthogonal) {
          cursor = new Coord(end);
          cursor.translate(d);
          stair.setUpsideDown(true).setFacing(d).stroke(editor, cursor);
        }
      }
    }

    // third floor

    Coord thirdFloor = new Coord(secondFloor);
    thirdFloor.translate(Cardinal.UP, 7);

    start = new Coord(thirdFloor);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    start.translate(Cardinal.DOWN);
    end = new Coord(thirdFloor);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP, 4);

    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orthogonal = dir.orthogonals();

      cursor = new Coord(thirdFloor);
      cursor.translate(dir, 3);
      cursor.translate(Cardinal.UP);
      window(editor, theme, dir, cursor);

      start = new Coord(thirdFloor);
      start.translate(dir, 2);
      end = new Coord(start);
      end.translate(dir, 4);
      end.translate(Cardinal.DOWN);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = new Coord(thirdFloor);
      start.translate(dir, 5);
      start.translate(Cardinal.DOWN, 2);
      end = new Coord(start);
      start.translate(orthogonal[0]);
      end.translate(orthogonal[1]);
      end.translate(Cardinal.DOWN);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = new Coord(thirdFloor);
      start.translate(dir, 3);
      start.translate(orthogonal[0], 3);
      start.translate(Cardinal.DOWN, 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 5);
      RectSolid.newRect(start, end).fill(editor, pillar);

      cursor = new Coord(end);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

      cursor = new Coord(end);
      cursor.translate(orthogonal[0]);
      stair.setUpsideDown(true).setFacing(orthogonal[0]).stroke(editor, cursor);

      for (Cardinal o : orthogonal) {

        start = new Coord(thirdFloor);
        start.translate(dir, 4);
        start.translate(Cardinal.DOWN);
        start.translate(o, 3);
        end = new Coord(start);
        end.translate(o);
        end.translate(Cardinal.DOWN);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

        for (int i = 0; i < 4; ++i) {
          start = new Coord(thirdFloor);
          start.translate(dir, 4);
          start.translate(o, i + 1);
          start.translate(Cardinal.DOWN, i);
          end = new Coord(start);
          end.translate(dir, 2);
          stair.setUpsideDown(false).setFacing(o);
          RectSolid.newRect(start, end).fill(editor, stair);

          if (i < 3) {
            start = new Coord(thirdFloor);
            start.translate(dir, 4);
            start.translate(o, i + 1);
            start.translate(Cardinal.DOWN, i + 1);
            end = new Coord(start);
            end.translate(dir, 2);
            RectSolid.newRect(start, end).fill(editor, blocks);
          }

          start = new Coord(thirdFloor);
          start.translate(dir, 4);
          start.translate(o, 2);
          start.translate(Cardinal.DOWN, 3);
          end = new Coord(start);
          end.translate(dir, 2);
          RectSolid.newRect(start, end).fill(editor, blocks);

          cursor = new Coord(thirdFloor);
          cursor.translate(dir, 6);
          cursor.translate(o);
          cursor.translate(Cardinal.DOWN, 2);
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        }
      }
    }


    for (int i = thirdFloor.getY() - 1; i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(origin.getX(), i, origin.getZ()), theme.getPrimary().getStair(), theme.getPrimary().getPillar());
    }

    // attic

    Coord attic = new Coord(thirdFloor);
    attic.translate(Cardinal.UP, 5);

    start = new Coord(attic);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.WEST, 2);
    start.translate(Cardinal.DOWN);
    end = new Coord(attic);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.EAST, 2);
    end.translate(Cardinal.UP, 3);

    RectHollow.newRect(start, end).fill(editor, blocks);

    start = new Coord(attic);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.WEST);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.EAST);
    RectHollow.newRect(start, end).fill(editor, blocks);

    start = new Coord(attic);
    start.translate(Cardinal.UP, 5);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);
    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orthogonal = dir.orthogonals();

      cursor = new Coord(attic);
      cursor.translate(dir, 2);
      cursor.translate(Cardinal.UP);
      window(editor, theme, dir, cursor);

      stair.setUpsideDown(false).setFacing(dir);

      start = new Coord(attic);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(orthogonal[0], 3);
      end.translate(orthogonal[1], 3);

      RectSolid.newRect(start, end).fill(editor, stair);

      start = new Coord(attic);
      start.translate(dir, 4);
      start.translate(Cardinal.DOWN);
      end = new Coord(start);
      start.translate(orthogonal[0], 4);
      end.translate(orthogonal[1], 4);

      RectSolid.newRect(start, end).fill(editor, stair);

      start = new Coord(attic);
      start.translate(dir, 3);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(orthogonal[0], 3);
      end.translate(orthogonal[1], 3);

      RectSolid.newRect(start, end).fill(editor, stair);

      start = new Coord(attic);
      start.translate(dir, 2);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      start.translate(orthogonal[0], 2);
      end.translate(orthogonal[1], 2);

      RectSolid.newRect(start, end).fill(editor, stair);

    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(main);
      cursor.translate(dir, 4);
      if (editor.isAirBlock(cursor)) {
        cursor = new Coord(main);
        cursor.translate(dir, 3);
        theme.getPrimary().getDoor().setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP);
        end.translate(dir, 3);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

        cursor = new Coord(main);
        cursor.translate(dir, 4);
        cursor.translate(Cardinal.DOWN);
        step(editor, theme, dir, cursor);
        break;
      }
    }
  }

  private void window(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord origin) {

    Coord cursor;

    StairsBlock stair = theme.getPrimary().getStair();

    cursor = new Coord(origin);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
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

    Cardinal[] orthogonal = dir.orthogonals();

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.newRect(start, end).fill(editor, blocks);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    stair.setUpsideDown(false).setFacing(dir);
    RectSolid.newRect(start, end).fill(editor, stair);

    origin.translate(Cardinal.DOWN);
    origin.translate(dir);
    step(editor, theme, dir, origin);
  }
}
