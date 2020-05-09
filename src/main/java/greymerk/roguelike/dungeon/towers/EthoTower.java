package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class EthoTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    IBlockFactory primary = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getSecondary().getPillar();

    IStair stair = theme.getSecondary().getStair();

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    Coord start = new Coord(floor);
    Coord end = new Coord(start);
    Coord cursor;

    int x = dungeon.getX();
    int z = dungeon.getZ();

    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP, 4);

    //WorldGenPrimitive.fillRectSolid(rand, start, end, air, true, true);

    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.WEST);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.EAST);
    end.translate(Cardinal.UP);

    RectHollow.fill(editor, rand, start, end, primary, true, true);

    for (Cardinal dir : Cardinal.directions) {

      Cardinal[] orth = dir.orthogonal();
      start = new Coord(floor);
      start.translate(dir, 3);
      start.translate(orth[0], 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 6);

      RectSolid.fill(editor, rand, start, end, pillar, true, true);

      for (Cardinal o : orth) {
        start = new Coord(floor);
        start.translate(dir, 5);
        start.translate(o, 4);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        start.translate(Cardinal.DOWN, 10);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);

        end.translate(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, end);

        end.translate(dir.reverse());
        end.translate(o.reverse());
        stair.setOrientation(o.reverse(), false).set(editor, end);
        end.translate(o.reverse());
        start = new Coord(end);
        start.translate(o.reverse(), 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir, false), true, true);

        end.translate(dir.reverse());
        end.translate(Cardinal.UP);
        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir, false), true, true);
        stair.setOrientation(o.reverse(), false).set(editor, end);

        start = new Coord(floor);
        start.translate(dir, 3);
        start.translate(Cardinal.UP, 4);
        end = new Coord(start);
        end.translate(o, 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir.reverse(), true), true, true);
        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(o, 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir.reverse(), true), true, true);
        start.translate(Cardinal.UP);
        end.translate(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);
        cursor = new Coord(end);
        start = new Coord(end);
        start.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);
        cursor.translate(o.reverse());
        cursor.translate(Cardinal.UP);
        stair.setOrientation(o.reverse(), false).set(editor, cursor);
        cursor.translate(Cardinal.UP, 2);
        stair.setOrientation(o.reverse(), true).set(editor, cursor);
        start.translate(Cardinal.UP);
        end = new Coord(start);
        end.translate(o.reverse(), 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir, false), true, true);
        cursor = new Coord(end);
        cursor.translate(dir.reverse());
        stair.setOrientation(dir.reverse(), true).set(editor, cursor);
        cursor.translate(o);
        stair.setOrientation(dir.reverse(), true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(o.reverse());
        stair.setOrientation(dir, false).set(editor, cursor);


      }


    }

    Cardinal front = Cardinal.NORTH;

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(floor);
      cursor.translate(dir, 6);
      if (editor.isAirBlock(cursor)) {
        front = dir;
        break;
      }
    }

    for (Cardinal dir : Cardinal.directions) {

      if (dir == front) {

        for (Cardinal o : dir.orthogonal()) {
          cursor = new Coord(floor);
          cursor.translate(dir, 5);
          cursor.translate(o, 2);
          primary.set(editor, rand, cursor);
          cursor.translate(o);
          stair.setOrientation(o, false).set(editor, cursor);
          cursor.translate(dir);
          stair.setOrientation(o, false).set(editor, cursor);
          cursor.translate(o.reverse());
          stair.setOrientation(dir, false).set(editor, cursor);
          cursor.translate(dir.reverse());
          cursor.translate(Cardinal.UP);
          stair.setOrientation(o.reverse(), false).set(editor, cursor);
          cursor.translate(Cardinal.UP);
          stair.setOrientation(dir, false).set(editor, cursor);
          cursor.translate(o);
          stair.setOrientation(o, false).set(editor, cursor);
          cursor.translate(o.reverse());
          cursor.translate(Cardinal.UP);
          stair.setOrientation(o.reverse(), false).set(editor, cursor);
          cursor.translate(o.reverse());
          stair.setOrientation(o.reverse(), true).set(editor, cursor);
          cursor.translate(o.reverse());
          cursor.translate(Cardinal.UP);
          stair.setOrientation(dir, false).set(editor, cursor);
          cursor.translate(o);
          stair.setOrientation(dir, false).set(editor, cursor);
          cursor.translate(o);
          stair.setOrientation(o, false).set(editor, cursor);
        }

        // carve doorway
        Cardinal[] orth = dir.orthogonal();
        cursor = new Coord(floor);
        cursor.translate(dir, 4);
        start = new Coord(cursor);
        end = new Coord(start);
        start.translate(orth[0]);
        end.translate(Cardinal.UP, 2);
        end.translate(orth[1]);
        RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR), true, true);

        cursor = new Coord(floor);
        cursor.translate(dir, 6);
        cursor.translate(Cardinal.DOWN);
        step(editor, rand, theme, dir, cursor);

        continue;
      }


      for (Cardinal o : dir.orthogonal()) {
        start = new Coord(floor);
        start.translate(Cardinal.UP, 4);
        start.translate(dir, 5);
        end = new Coord(start);
        start.translate(o, 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir, false), true, true);
        start.translate(o);
        stair.setOrientation(o.reverse(), false).set(editor, start);
        start.translate(Cardinal.DOWN);
        stair.setOrientation(o.reverse(), true).set(editor, start);
      }

    }


    for (int i = floor.getY() - 1; i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getPrimary().getPillar());
    }
  }

  private void step(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    if (editor.getBlock(origin).isOpaqueCube()) {
      return;
    }

    Coord start;
    Coord end;

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory blocks = theme.getPrimary().getWall();

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.fill(editor, rand, start, end, blocks, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(orth[0]);
    end.translate(orth[1]);
    stair.setOrientation(dir, false);
    RectSolid.fill(editor, rand, start, end, stair, true, true);

    origin.translate(Cardinal.DOWN);
    origin.translate(dir);
    step(editor, rand, theme, dir, origin);
  }

}
