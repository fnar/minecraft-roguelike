package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.roguelike.worldgen.generatables.SpiralStaircase;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedGlassPane;

public class EniTower extends Tower {

  public EniTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord dungeon) {

    StairsBlock stair = getPrimaryStair();

    Coord floor = TowerType.getBaseCoord(editor, dungeon);

    int x = dungeon.getX();
    int z = dungeon.getZ();

    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(new Coord(x - 4, floor.getY(), z - 4), new Coord(x + 4, floor.getY() + 3, z + 4)));
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(new Coord(x - 3, floor.getY() + 4, z - 3), new Coord(x + 3, floor.getY() + 12, z + 3)));
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(new Coord(x - 2, floor.getY() + 13, z - 2), new Coord(x + 2, floor.getY() + 21, z + 2)));
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(new Coord(x - 3, floor.getY() + 22, z - 3), new Coord(x + 3, floor.getY() + 28, z + 3)));

    Coord start;
    Coord end;
    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonals : dir.orthogonals()) {

        start = floor.copy();
        end = start.copy();
        end.translate(dir, 4);
        end.translate(orthogonals, 2);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        start = floor.copy();
        start.translate(dir, 5);
        end = start.copy();
        start.translate(orthogonals);
        end.translate(orthogonals.reverse());
        end.up(2);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        start = floor.copy();
        start.translate(dir, 4);
        start.translate(orthogonals, 2);
        end = start.copy();
        end.translate(orthogonals);
        end.up(3);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        start = floor.copy();
        start.translate(dir, 3);
        start.translate(orthogonals, 3);
        end = start.copy();
        end.up(3);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        cursor = floor.copy();
        cursor.translate(dir, 5);
        cursor.up(3);
        getPrimaryWall().stroke(editor, cursor);
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

        start = floor.copy();
        start.up(4);
        start.translate(dir, 4);
        end = start.copy();
        start.translate(orthogonals);
        end.translate(orthogonals.reverse());
        end.up(8);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        start = floor.copy();
        start.up(4);
        start.translate(dir, 3);
        start.translate(orthogonals, 2);
        end = start.copy();
        end.translate(orthogonals);
        end.up(8);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        cursor = floor.copy();
        cursor.up(13);
        cursor.translate(dir, 4);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(orthogonals);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orthogonals);
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);

        // section 3

        start = floor.copy();
        start.up(13);
        start.translate(dir, 3);
        end = start.copy();
        start.translate(orthogonals);
        end.translate(orthogonals.reverse());
        end.up(8);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        start = floor.copy();
        start.up(13);
        start.translate(dir, 2);
        start.translate(orthogonals, 2);
        end = start.copy();
        end.up(8);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        // section 4

        start = floor.copy();
        start.up(22);
        start.translate(dir, 4);
        end = start.copy();
        start.translate(orthogonals, 2);
        end.translate(orthogonals.reverse(), 2);
        end.up(6);
        RectSolid.newRect(start, end).fill(editor, getPrimaryWall(), true, false);

        start = floor.copy();
        start.up(22);
        start.translate(dir, 3);
        start.translate(orthogonals, 2);
        end = start.copy();
        end.up(6);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        start = floor.copy();
        start.up(22);
        end = start.copy();
        end.translate(dir, 3);
        end.translate(orthogonals, 2);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

        cursor = floor.copy();
        cursor.up(20);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
        cursor.up();
        getPrimaryWall().stroke(editor, cursor);
        cursor.translate(dir);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

        // section 4 roof

        StairsBlock roof = getSecondaryStair();
        start = floor.copy();
        start.up(29);
        start.translate(dir, 3);
        end = start.copy();
        end.translate(dir, 2);
        getSecondaryWall().fill(editor, RectSolid.newRect(start, end));
        start.translate(orthogonals);
        end.translate(orthogonals);
        roof.setUpsideDown(false).setFacing(orthogonals).fill(editor, RectSolid.newRect(start, end));
        start.translate(orthogonals);
        end.translate(orthogonals);
        start.down();
        end.down();
        roof.setUpsideDown(false).setFacing(orthogonals).fill(editor, RectSolid.newRect(start, end));
        start.translate(orthogonals);
        end.translate(orthogonals);
        start.down();
        end.down();
        roof.setUpsideDown(false).setFacing(orthogonals).fill(editor, RectSolid.newRect(start, end));
        cursor = end.copy();
        cursor.translate(orthogonals.reverse());
        roof.setUpsideDown(true).setFacing(orthogonals.reverse()).stroke(editor, cursor);
        cursor.translate(orthogonals.reverse());
        cursor.up();
        roof.setUpsideDown(true).setFacing(orthogonals.reverse()).stroke(editor, cursor);

        cursor.translate(dir.reverse(), 3);
        cursor.translate(orthogonals);
        getSecondaryWall().stroke(editor, cursor);

        // tower top
        start = floor.copy();
        start.up(29);
        end = start.copy();
        start.translate(dir, 2);
        start.translate(orthogonals);
        end.translate(dir, 2);
        end.translate(orthogonals.reverse());
        end.up(3);
        getPrimaryWall().fill(editor, RectSolid.newRect(start, end));


        cursor = floor.copy();
        cursor.up(33);
        cursor.translate(dir, 3);
        roof.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(orthogonals);
        roof.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(orthogonals);
        getSecondaryWall().stroke(editor, cursor);
        cursor.translate(orthogonals.reverse());
        cursor.up();
        roof.setUpsideDown(false).setFacing(orthogonals).stroke(editor, cursor);
        cursor.translate(orthogonals.reverse());
        getSecondaryWall().stroke(editor, cursor);
        cursor.up();
        roof.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        getSecondaryWall().stroke(editor, cursor);
        cursor.down();
        getSecondaryWall().stroke(editor, cursor);
        cursor.translate(orthogonals);
        getSecondaryWall().stroke(editor, cursor);
        cursor.up();
        getSecondaryWall().stroke(editor, cursor);
        cursor.up();
        cursor.translate(orthogonals.reverse());
        getSecondaryWall().stroke(editor, cursor);
        cursor.up();
        roof.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir.reverse());
        getSecondaryWall().stroke(editor, cursor);
        cursor.up();
        getSecondaryWall().stroke(editor, cursor);
      }
    }

    // mid floors
    start = floor.copy();
    start.up(4);
    end = start.copy();
    start.north(3);
    start.east(3);
    end.south(3);
    end.west(3);

    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
    start.up(3);
    end.up(3);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
    start.up(3);
    end.up(3);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));


    for (Direction dir : Direction.CARDINAL) {

      // lower windows
      cursor = floor.copy();
      cursor.translate(dir, 4);
      cursor.up(4);
      BlockBrush window = stainedGlassPane().setColor(DyeColor.chooseRandom(editor.getRandom()));
      for (int i = 0; i < 3; i++) {
        stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.up();
        window.stroke(editor, cursor);
        cursor.up();
        window.stroke(editor, cursor);
        cursor.up();
      }

      // floor before slit windows
      cursor.translate(dir.reverse(), 2);
      start = cursor.copy();
      start.translate(dir.antiClockwise());
      end = cursor.copy();
      end.translate(dir.clockwise());
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      // slit windows
      cursor = floor.copy();
      cursor.up(14);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise());
      stair.setUpsideDown(false).setFacing(dir.clockwise()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
      cursor.up();
      cursor.translate(dir.clockwise());
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(dir.clockwise()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
      cursor.translate(dir.clockwise());
      cursor.up();
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);

      // top windows

      cursor = floor.copy();
      cursor.up(23);
      cursor.translate(dir, 4);
      window.stroke(editor, cursor);
      cursor.up();
      window.stroke(editor, cursor);
      cursor.up();
      window.stroke(editor, cursor);
      cursor.down();
      cursor.translate(dir.antiClockwise());
      window.stroke(editor, cursor);
      cursor.translate(dir.clockwise(), 2);
      window.stroke(editor, cursor);

      // top ceiling
      cursor = floor.copy();
      cursor.up(26);
      cursor.translate(dir, 3);
      start = cursor.copy();
      start.translate(dir.antiClockwise());
      end = cursor.copy();
      end.translate(dir.clockwise());
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      start.up();
      end.up();
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
      start.up();
      end.up();
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      cursor.translate(dir.reverse());
      cursor.translate(dir.antiClockwise(), 2);
      getPrimaryWall().stroke(editor, cursor);
    }

    start = new Coord(x - 4, 60, z - 4);
    end = new Coord(x + 4, floor.getY(), z + 4);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    Coord topStep = new Coord(floor).up(23);
    SpiralStaircase.newStaircase(editor).withHeight(topStep.getY() - dungeon.getY()).withStairs(getPrimaryStair()).withPillar(getPrimaryWall()).generate(dungeon);

    for (Direction dir : Direction.CARDINAL) {
      cursor = floor.copy();
      cursor.up();
      cursor.translate(dir, 6);
      if (editor.isAirBlock(cursor)) {
        cursor = floor.copy();
        cursor.up();
        cursor.translate(dir, 5);
        getPrimaryDoor().setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir);
        start = cursor.copy();
        end = start.copy();
        end.up();
        end.translate(dir, 3);
        SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));
        break;
      }
    }
  }

}
