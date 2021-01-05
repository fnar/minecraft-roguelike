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
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedGlassPane;

public class EniTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {

    BlockBrush blocks = theme.getPrimary().getWall();

    StairsBlock stair = theme.getPrimary().getStair();

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    int x = dungeon.getX();
    int z = dungeon.getZ();

    RectSolid.newRect(new Coord(x - 4, floor.getY(), z - 4), new Coord(x + 4, floor.getY() + 3, z + 4)).fill(editor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 3, floor.getY() + 4, z - 3), new Coord(x + 3, floor.getY() + 12, z + 3)).fill(editor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 2, floor.getY() + 13, z - 2), new Coord(x + 2, floor.getY() + 21, z + 2)).fill(editor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 3, floor.getY() + 22, z - 3), new Coord(x + 3, floor.getY() + 28, z + 3)).fill(editor, SingleBlockBrush.AIR);

    Coord start;
    Coord end;
    Coord cursor;

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonals : dir.orthogonals()) {

        start = new Coord(floor);
        end = new Coord(start);
        end.translate(dir, 4);
        end.translate(orthogonals, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(dir, 5);
        end = new Coord(start);
        start.translate(orthogonals);
        end.translate(orthogonals.reverse());
        end.translate(Cardinal.UP, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(dir, 4);
        start.translate(orthogonals, 2);
        end = new Coord(start);
        end.translate(orthogonals);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(dir, 3);
        start.translate(orthogonals, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, blocks);

        cursor = new Coord(floor);
        cursor.translate(dir, 5);
        cursor.translate(Cardinal.UP, 3);
        blocks.stroke(editor, cursor);
        cursor.translate(orthogonals);
        stair.setUpsideDown(false).setFacing(orthogonals).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orthogonals);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(orthogonals);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orthogonals.reverse());
        stair.setUpsideDown(true).setFacing(orthogonals.reverse()).stroke(editor, cursor);
        cursor.translate(dir);
        cursor.translate(orthogonals.reverse());
        stair.setUpsideDown(true).setFacing(orthogonals.reverse()).stroke(editor, cursor);

        // second section

        start = new Coord(floor);
        start.translate(Cardinal.UP, 4);
        start.translate(dir, 4);
        end = new Coord(start);
        start.translate(orthogonals);
        end.translate(orthogonals.reverse());
        end.translate(Cardinal.UP, 8);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(Cardinal.UP, 4);
        start.translate(dir, 3);
        start.translate(orthogonals, 2);
        end = new Coord(start);
        end.translate(orthogonals);
        end.translate(Cardinal.UP, 8);
        RectSolid.newRect(start, end).fill(editor, blocks);

        cursor = new Coord(floor);
        cursor.translate(Cardinal.UP, 13);
        cursor.translate(dir, 4);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(orthogonals);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orthogonals);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);

        // section 3

        start = new Coord(floor);
        start.translate(Cardinal.UP, 13);
        start.translate(dir, 3);
        end = new Coord(start);
        start.translate(orthogonals);
        end.translate(orthogonals.reverse());
        end.translate(Cardinal.UP, 8);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(Cardinal.UP, 13);
        start.translate(dir, 2);
        start.translate(orthogonals, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 8);
        RectSolid.newRect(start, end).fill(editor, blocks);

        // section 4

        start = new Coord(floor);
        start.translate(Cardinal.UP, 22);
        start.translate(dir, 4);
        end = new Coord(start);
        start.translate(orthogonals, 2);
        end.translate(orthogonals.reverse(), 2);
        end.translate(Cardinal.UP, 6);
        RectSolid.newRect(start, end).fill(editor, blocks, true, false);

        start = new Coord(floor);
        start.translate(Cardinal.UP, 22);
        start.translate(dir, 3);
        start.translate(orthogonals, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 6);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(Cardinal.UP, 22);
        end = new Coord(start);
        end.translate(dir, 3);
        end.translate(orthogonals, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);

        cursor = new Coord(floor);
        cursor.translate(Cardinal.UP, 20);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        blocks.stroke(editor, cursor);
        cursor.translate(dir);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

        // section 4 roof

        StairsBlock roof = theme.getSecondary().getStair();
        start = new Coord(floor);
        start.translate(Cardinal.UP, 29);
        start.translate(dir, 3);
        end = new Coord(start);
        end.translate(dir, 2);
        RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall());
        start.translate(orthogonals);
        end.translate(orthogonals);
        RectSolid.newRect(start, end).fill(editor, roof.setUpsideDown(false).setFacing(orthogonals));
        start.translate(orthogonals);
        end.translate(orthogonals);
        start.translate(Cardinal.DOWN);
        end.translate(Cardinal.DOWN);
        RectSolid.newRect(start, end).fill(editor, roof.setUpsideDown(false).setFacing(orthogonals));
        start.translate(orthogonals);
        end.translate(orthogonals);
        start.translate(Cardinal.DOWN);
        end.translate(Cardinal.DOWN);
        RectSolid.newRect(start, end).fill(editor, roof.setUpsideDown(false).setFacing(orthogonals));
        cursor = new Coord(end);
        cursor.translate(orthogonals.reverse());
        roof.setUpsideDown(true).setFacing(orthogonals.reverse()).stroke(editor, cursor);
        cursor.translate(orthogonals.reverse());
        cursor.translate(Cardinal.UP);
        roof.setUpsideDown(true).setFacing(orthogonals.reverse()).stroke(editor, cursor);

        cursor.translate(dir.reverse(), 3);
        cursor.translate(orthogonals);
        theme.getSecondary().getWall().stroke(editor, cursor);

        // tower top
        start = new Coord(floor);
        start.translate(Cardinal.UP, 29);
        end = new Coord(start);
        start.translate(dir, 2);
        start.translate(orthogonals);
        end.translate(dir, 2);
        end.translate(orthogonals.reverse());
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, blocks);


        cursor = new Coord(floor);
        cursor.translate(Cardinal.UP, 33);
        cursor.translate(dir, 3);
        roof.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(orthogonals);
        roof.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orthogonals);
        theme.getSecondary().getWall().stroke(editor, cursor);
        cursor.translate(orthogonals.reverse());
        cursor.translate(Cardinal.UP);
        roof.setUpsideDown(false).setFacing(orthogonals).stroke(editor, cursor);
        cursor.translate(orthogonals.reverse());
        theme.getSecondary().getWall().stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        roof.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        theme.getSecondary().getWall().stroke(editor, cursor);
        cursor.translate(Cardinal.DOWN);
        theme.getSecondary().getWall().stroke(editor, cursor);
        cursor.translate(orthogonals);
        theme.getSecondary().getWall().stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        theme.getSecondary().getWall().stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        cursor.translate(orthogonals.reverse());
        theme.getSecondary().getWall().stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        roof.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        theme.getSecondary().getWall().stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        theme.getSecondary().getWall().stroke(editor, cursor);
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

    RectSolid.newRect(start, end).fill(editor, blocks);
    start.translate(Cardinal.UP, 3);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(editor, blocks);
    start.translate(Cardinal.UP, 3);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(editor, blocks);


    for (Cardinal dir : Cardinal.DIRECTIONS) {

      // lower windows
      cursor = new Coord(floor);
      cursor.translate(dir, 4);
      cursor.translate(Cardinal.UP, 4);
      BlockBrush window = stainedGlassPane().setColor(DyeColor.chooseRandom(rand));
      for (int i = 0; i < 3; i++) {
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        window.stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        window.stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
      }

      // floor before slit windows
      cursor.translate(dir.reverse(), 2);
      start = new Coord(cursor);
      start.translate(dir.antiClockwise());
      end = new Coord(cursor);
      end.translate(dir.clockwise());
      RectSolid.newRect(start, end).fill(editor, blocks);

      // slit windows
      cursor = new Coord(floor);
      cursor.translate(Cardinal.UP, 14);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise());
      stair.setUpsideDown(false).setFacing(dir.clockwise()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.clockwise());
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(dir.clockwise()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
      cursor.translate(dir.clockwise());
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);

      // top windows

      cursor = new Coord(floor);
      cursor.translate(Cardinal.UP, 23);
      cursor.translate(dir, 4);
      window.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      window.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      window.stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir.antiClockwise());
      window.stroke(editor, cursor);
      cursor.translate(dir.clockwise(), 2);
      window.stroke(editor, cursor);

      // top ceiling
      cursor = new Coord(floor);
      cursor.translate(Cardinal.UP, 26);
      cursor.translate(dir, 3);
      start = new Coord(cursor);
      start.translate(dir.antiClockwise());
      end = new Coord(cursor);
      end.translate(dir.clockwise());
      RectSolid.newRect(start, end).fill(editor, blocks);
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.newRect(start, end).fill(editor, blocks);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.newRect(start, end).fill(editor, blocks);

      cursor.translate(dir.reverse());
      cursor.translate(dir.antiClockwise(), 2);
      blocks.stroke(editor, cursor);
    }

    start = new Coord(x - 4, 60, z - 4);
    end = new Coord(x + 4, floor.getY(), z + 4);
    RectSolid.newRect(start, end).fill(editor, blocks);

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
        theme.getPrimary().getDoor().setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP);
        end.translate(dir, 3);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
        break;
      }
    }


  }
}
