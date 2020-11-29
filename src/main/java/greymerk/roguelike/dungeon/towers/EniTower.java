package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class EniTower implements ITower {

  public EniTower() {
  }

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {

    MetaBlock air = BlockType.get(BlockType.AIR);

    IBlockFactory blocks = theme.getPrimary().getWall();

    IStair stair = theme.getPrimary().getStair();

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    int x = dungeon.getX();
    int z = dungeon.getZ();

    RectSolid.fill(editor, rand, new Coord(x - 4, floor.getY(), z - 4), new Coord(x + 4, floor.getY() + 3, z + 4), air);
    RectSolid.fill(editor, rand, new Coord(x - 3, floor.getY() + 4, z - 3), new Coord(x + 3, floor.getY() + 12, z + 3), air);
    RectSolid.fill(editor, rand, new Coord(x - 2, floor.getY() + 13, z - 2), new Coord(x + 2, floor.getY() + 21, z + 2), air);
    RectSolid.fill(editor, rand, new Coord(x - 3, floor.getY() + 22, z - 3), new Coord(x + 3, floor.getY() + 28, z + 3), air);

    Coord start;
    Coord end;
    Coord cursor;

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orth : dir.orthogonal()) {

        start = new Coord(floor);
        end = new Coord(start);
        end.translate(dir, 4);
        end.translate(orth, 2);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.translate(dir, 5);
        end = new Coord(start);
        start.translate(orth);
        end.translate(orth.reverse());
        end.translate(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.translate(dir, 4);
        start.translate(orth, 2);
        end = new Coord(start);
        end.translate(orth);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.translate(dir, 3);
        start.translate(orth, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, blocks);

        cursor = new Coord(floor);
        cursor.translate(dir, 5);
        cursor.translate(Cardinal.UP, 3);
        blocks.set(editor, rand, cursor);
        cursor.translate(orth);
        stair.setOrientation(orth, false).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orth);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(orth);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orth.reverse());
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);
        cursor.translate(dir);
        cursor.translate(orth.reverse());
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);

        // second section

        start = new Coord(floor);
        start.translate(Cardinal.UP, 4);
        start.translate(dir, 4);
        end = new Coord(start);
        start.translate(orth);
        end.translate(orth.reverse());
        end.translate(Cardinal.UP, 8);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.translate(Cardinal.UP, 4);
        start.translate(dir, 3);
        start.translate(orth, 2);
        end = new Coord(start);
        end.translate(orth);
        end.translate(Cardinal.UP, 8);
        RectSolid.fill(editor, rand, start, end, blocks);

        cursor = new Coord(floor);
        cursor.translate(Cardinal.UP, 13);
        cursor.translate(dir, 4);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(orth);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orth);
        stair.setOrientation(dir, false).set(editor, cursor);

        // section 3

        start = new Coord(floor);
        start.translate(Cardinal.UP, 13);
        start.translate(dir, 3);
        end = new Coord(start);
        start.translate(orth);
        end.translate(orth.reverse());
        end.translate(Cardinal.UP, 8);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.translate(Cardinal.UP, 13);
        start.translate(dir, 2);
        start.translate(orth, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 8);
        RectSolid.fill(editor, rand, start, end, blocks);

        // section 4

        start = new Coord(floor);
        start.translate(Cardinal.UP, 22);
        start.translate(dir, 4);
        end = new Coord(start);
        start.translate(orth, 2);
        end.translate(orth.reverse(), 2);
        end.translate(Cardinal.UP, 6);
        RectSolid.fill(editor, rand, start, end, blocks, true, false);

        start = new Coord(floor);
        start.translate(Cardinal.UP, 22);
        start.translate(dir, 3);
        start.translate(orth, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 6);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.translate(Cardinal.UP, 22);
        end = new Coord(start);
        end.translate(dir, 3);
        end.translate(orth, 2);
        RectSolid.fill(editor, rand, start, end, blocks);

        cursor = new Coord(floor);
        cursor.translate(Cardinal.UP, 20);
        cursor.translate(dir, 3);
        cursor.translate(orth, 2);
        stair.setOrientation(dir, true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        blocks.set(editor, rand, cursor);
        cursor.translate(dir);
        stair.setOrientation(dir, true).set(editor, cursor);

        // section 4 roof

        IStair roof = theme.getSecondary().getStair();
        start = new Coord(floor);
        start.translate(Cardinal.UP, 29);
        start.translate(dir, 3);
        end = new Coord(start);
        end.translate(dir, 2);
        RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());
        start.translate(orth);
        end.translate(orth);
        RectSolid.fill(editor, rand, start, end, roof.setOrientation(orth, false));
        start.translate(orth);
        end.translate(orth);
        start.translate(Cardinal.DOWN);
        end.translate(Cardinal.DOWN);
        RectSolid.fill(editor, rand, start, end, roof.setOrientation(orth, false));
        start.translate(orth);
        end.translate(orth);
        start.translate(Cardinal.DOWN);
        end.translate(Cardinal.DOWN);
        RectSolid.fill(editor, rand, start, end, roof.setOrientation(orth, false));
        cursor = new Coord(end);
        cursor.translate(orth.reverse());
        roof.setOrientation(orth.reverse(), true).set(editor, cursor);
        cursor.translate(orth.reverse());
        cursor.translate(Cardinal.UP);
        roof.setOrientation(orth.reverse(), true).set(editor, cursor);

        cursor.translate(dir.reverse(), 3);
        cursor.translate(orth);
        theme.getSecondary().getWall().set(editor, rand, cursor);

        // tower top
        start = new Coord(floor);
        start.translate(Cardinal.UP, 29);
        end = new Coord(start);
        start.translate(dir, 2);
        start.translate(orth);
        end.translate(dir, 2);
        end.translate(orth.reverse());
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, blocks);


        cursor = new Coord(floor);
        cursor.translate(Cardinal.UP, 33);
        cursor.translate(dir, 3);
        roof.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(orth);
        roof.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orth);
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.translate(orth.reverse());
        cursor.translate(Cardinal.UP);
        roof.setOrientation(orth, false).set(editor, cursor);
        cursor.translate(orth.reverse());
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.translate(Cardinal.UP);
        roof.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(dir.reverse());
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.translate(Cardinal.DOWN);
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.translate(orth);
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.translate(Cardinal.UP);
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.translate(Cardinal.UP);
        cursor.translate(orth.reverse());
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.translate(Cardinal.UP);
        roof.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(dir.reverse());
        theme.getSecondary().getWall().set(editor, rand, cursor);
        cursor.translate(Cardinal.UP);
        theme.getSecondary().getWall().set(editor, rand, cursor);
      }
    }

    // mid floors
    start = new Coord(floor);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);

    RectSolid.fill(editor, rand, start, end, blocks);
    start.translate(Cardinal.UP, 3);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, blocks);
    start.translate(Cardinal.UP, 3);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, blocks);


    for (Cardinal dir : Cardinal.DIRECTIONS) {

      // lower windows
      cursor = new Coord(floor);
      cursor.translate(dir, 4);
      cursor.translate(Cardinal.UP, 4);
      MetaBlock window = ColorBlock.get(ColorBlock.PANE, rand);
      for (int i = 0; i < 3; i++) {
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        window.set(editor, cursor);
        cursor.translate(Cardinal.UP);
        window.set(editor, cursor);
        cursor.translate(Cardinal.UP);
      }

      // floor before slit windows
      cursor.translate(dir.reverse(), 2);
      start = new Coord(cursor);
      start.translate(dir.antiClockwise());
      end = new Coord(cursor);
      end.translate(dir.clockwise());
      RectSolid.fill(editor, rand, start, end, blocks);

      // slit windows
      cursor = new Coord(floor);
      cursor.translate(Cardinal.UP, 14);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise());
      stair.setOrientation(dir.clockwise(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir.clockwise(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.clockwise());
      stair.setOrientation(dir.antiClockwise(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir.antiClockwise(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir.clockwise(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir.clockwise(), true).set(editor, cursor);
      cursor.translate(dir.clockwise());
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir.antiClockwise(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir.antiClockwise(), true).set(editor, cursor);

      // top windows

      cursor = new Coord(floor);
      cursor.translate(Cardinal.UP, 23);
      cursor.translate(dir, 4);
      window.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      window.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      window.set(editor, cursor);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir.antiClockwise());
      window.set(editor, cursor);
      cursor.translate(dir.clockwise(), 2);
      window.set(editor, cursor);

      // top ceiling
      cursor = new Coord(floor);
      cursor.translate(Cardinal.UP, 26);
      cursor.translate(dir, 3);
      start = new Coord(cursor);
      start.translate(dir.antiClockwise());
      end = new Coord(cursor);
      end.translate(dir.clockwise());
      RectSolid.fill(editor, rand, start, end, blocks);
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, blocks);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, blocks);

      cursor.translate(dir.reverse());
      cursor.translate(dir.antiClockwise(), 2);
      blocks.set(editor, rand, cursor);
    }

    start = new Coord(x - 4, 60, z - 4);
    end = new Coord(x + 4, floor.getY(), z + 4);
    RectSolid.fill(editor, rand, start, end, blocks);

    for (int i = (floor.getY() + 22); i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getPrimary().getPillar());
    }


    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(floor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir, 6);
      if (editor.isAirBlock(cursor)) {
        cursor = new Coord(floor);
        cursor.translate(Cardinal.UP);
        cursor.translate(dir, 5);
        theme.getPrimary().getDoor().generate(editor, cursor, dir, false);
        cursor.translate(dir);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP);
        end.translate(dir, 3);
        RectSolid.fill(editor, rand, start, end, air);
        break;
      }
    }


  }
}
