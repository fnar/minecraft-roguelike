package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WitchTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord origin) {

    IBlockFactory blocks = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);
    MetaBlock glass = ColorBlock.get(ColorBlock.GLASS, DyeColor.BLACK);

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

    RectHollow.fill(editor, rand, start, end, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orth = dir.orthogonal();

      start = new Coord(main);
      start.translate(dir, 3);
      start.translate(orth[0], 3);
      start.translate(Cardinal.DOWN);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);

      RectSolid.fill(editor, rand, start, end, pillar);

      for (Cardinal o : orth) {
        start = new Coord(main);
        start.translate(dir, 4);
        start.translate(o, 2);
        start.translate(Cardinal.DOWN);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);

        RectSolid.fill(editor, rand, start, end, pillar);
        cursor = new Coord(end);
        cursor.translate(dir);
        stair.setOrientation(dir, true).set(editor, cursor);
        for (Cardinal d : orth) {
          cursor = new Coord(end);
          cursor.translate(d);
          stair.setOrientation(d, true).set(editor, cursor);
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

    RectHollow.fill(editor, rand, start, end, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orth = dir.orthogonal();

      start = new Coord(secondFloor);
      start.translate(dir, 4);
      start.translate(Cardinal.UP);
      end = new Coord(start);
      start.translate(orth[0]);
      end.translate(orth[1]);
      end.translate(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, glass);

      start = new Coord(secondFloor);
      start.translate(dir, 4);
      start.translate(Cardinal.DOWN);
      start.translate(orth[0], 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 4);
      RectSolid.fill(editor, rand, start, end, air);

      for (Cardinal o : orth) {

        start = new Coord(secondFloor);
        start.translate(Cardinal.DOWN);
        start.translate(dir, 4);
        start.translate(o, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);

        start = new Coord(secondFloor);
        start.translate(Cardinal.DOWN);
        start.translate(dir, 5);
        start.translate(o, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor = new Coord(end);
        cursor.translate(dir);
        stair.setOrientation(dir, true).set(editor, cursor);
        for (Cardinal d : orth) {
          cursor = new Coord(end);
          cursor.translate(d);
          stair.setOrientation(d, true).set(editor, cursor);
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

    RectHollow.fill(editor, rand, start, end, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orth = dir.orthogonal();

      cursor = new Coord(thirdFloor);
      cursor.translate(dir, 3);
      cursor.translate(Cardinal.UP);
      window(editor, rand, theme, dir, cursor);

      start = new Coord(thirdFloor);
      start.translate(dir, 2);
      end = new Coord(start);
      end.translate(dir, 4);
      end.translate(Cardinal.DOWN);
      RectSolid.fill(editor, rand, start, end, blocks);

      start = new Coord(thirdFloor);
      start.translate(dir, 5);
      start.translate(Cardinal.DOWN, 2);
      end = new Coord(start);
      start.translate(orth[0]);
      end.translate(orth[1]);
      end.translate(Cardinal.DOWN);
      RectSolid.fill(editor, rand, start, end, blocks);

      start = new Coord(thirdFloor);
      start.translate(dir, 3);
      start.translate(orth[0], 3);
      start.translate(Cardinal.DOWN, 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 5);
      RectSolid.fill(editor, rand, start, end, pillar);

      cursor = new Coord(end);
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, cursor);

      cursor = new Coord(end);
      cursor.translate(orth[0]);
      stair.setOrientation(orth[0], true).set(editor, cursor);

      for (Cardinal o : orth) {

        start = new Coord(thirdFloor);
        start.translate(dir, 4);
        start.translate(Cardinal.DOWN);
        start.translate(o, 3);
        end = new Coord(start);
        end.translate(o);
        end.translate(Cardinal.DOWN);
        RectSolid.fill(editor, rand, start, end, air);

        for (int i = 0; i < 4; ++i) {
          start = new Coord(thirdFloor);
          start.translate(dir, 4);
          start.translate(o, i + 1);
          start.translate(Cardinal.DOWN, i);
          end = new Coord(start);
          end.translate(dir, 2);
          stair.setOrientation(o, false);
          RectSolid.fill(editor, rand, start, end, stair);

          if (i < 3) {
            start = new Coord(thirdFloor);
            start.translate(dir, 4);
            start.translate(o, i + 1);
            start.translate(Cardinal.DOWN, i + 1);
            end = new Coord(start);
            end.translate(dir, 2);
            RectSolid.fill(editor, rand, start, end, blocks);
          }

          start = new Coord(thirdFloor);
          start.translate(dir, 4);
          start.translate(o, 2);
          start.translate(Cardinal.DOWN, 3);
          end = new Coord(start);
          end.translate(dir, 2);
          RectSolid.fill(editor, rand, start, end, blocks);

          cursor = new Coord(thirdFloor);
          cursor.translate(dir, 6);
          cursor.translate(o);
          cursor.translate(Cardinal.DOWN, 2);
          stair.setOrientation(o.reverse(), true).set(editor, cursor);
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

    RectHollow.fill(editor, rand, start, end, blocks);

    start = new Coord(attic);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.WEST);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.EAST);
    RectHollow.fill(editor, rand, start, end, blocks);

    start = new Coord(attic);
    start.translate(Cardinal.UP, 5);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);
    RectHollow.fill(editor, rand, start, end, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orth = dir.orthogonal();

      cursor = new Coord(attic);
      cursor.translate(dir, 2);
      cursor.translate(Cardinal.UP);
      window(editor, rand, theme, dir, cursor);

      stair.setOrientation(dir, false);

      start = new Coord(attic);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(orth[0], 3);
      end.translate(orth[1], 3);

      RectSolid.fill(editor, rand, start, end, stair);

      start = new Coord(attic);
      start.translate(dir, 4);
      start.translate(Cardinal.DOWN);
      end = new Coord(start);
      start.translate(orth[0], 4);
      end.translate(orth[1], 4);

      RectSolid.fill(editor, rand, start, end, stair);

      start = new Coord(attic);
      start.translate(dir, 3);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(orth[0], 3);
      end.translate(orth[1], 3);

      RectSolid.fill(editor, rand, start, end, stair);

      start = new Coord(attic);
      start.translate(dir, 2);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      start.translate(orth[0], 2);
      end.translate(orth[1], 2);

      RectSolid.fill(editor, rand, start, end, stair);

    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(main);
      cursor.translate(dir, 4);
      if (editor.isAirBlock(cursor)) {
        cursor = new Coord(main);
        cursor.translate(dir, 3);
        theme.getPrimary().getDoor().generate(editor, cursor, dir);
        cursor.translate(dir);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP);
        end.translate(dir, 3);
        RectSolid.fill(editor, rand, start, end, air);

        cursor = new Coord(main);
        cursor.translate(dir, 4);
        cursor.translate(Cardinal.DOWN);
        step(editor, rand, theme, dir, cursor);
        break;
      }
    }


  }

  private void window(WorldEditor editor, Random rand, ThemeBase theme, Cardinal dir, Coord origin) {

    Coord cursor;

    IStair stair = theme.getPrimary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    cursor = new Coord(origin);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    air.set(editor, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(o);
      stair.setOrientation(o.reverse(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
    }
  }

  private void step(WorldEditor editor, Random rand, ThemeBase theme, Cardinal dir, Coord origin) {

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
    RectSolid.fill(editor, rand, start, end, blocks);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(orth[0]);
    end.translate(orth[1]);
    stair.setOrientation(dir, false);
    RectSolid.fill(editor, rand, start, end, stair);

    origin.translate(Cardinal.DOWN);
    origin.translate(dir);
    step(editor, rand, theme, dir, origin);
  }
}
