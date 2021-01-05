package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.TorchBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class RogueTower implements ITower {

  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {


    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    BlockBrush blocks = theme.getPrimary().getWall();

    StairsBlock stair = theme.getPrimary().getStair();

    Coord floor = Tower.getBaseCoord(editor, dungeon);
    int ground = floor.getY() - 1;
    int main = floor.getY() + 4;
    int roof = floor.getY() + 9;

    RectSolid.newRect(new Coord(x - 3, ground, z - 3), new Coord(x + 3, floor.getY() + 12, z + 3)).fill(editor, SingleBlockBrush.AIR);

    RectSolid.newRect(new Coord(x - 2, y + 10, z - 2), new Coord(x + 2, floor.getY() - 1, z + 2)).fill(editor, blocks, false, true);

    Coord start;
    Coord end;
    Coord cursor;

    RectSolid.newRect(new Coord(x - 3, main, z - 3), new Coord(x + 3, main, z + 3)).fill(editor, theme.getSecondary().getWall());
    RectSolid.newRect(new Coord(x - 3, roof, z - 3), new Coord(x + 3, roof, z + 3)).fill(editor, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonals : dir.orthogonals()) {
        // ground floor
        start = new Coord(floor);
        start.translate(Cardinal.DOWN, 1);
        start.translate(dir, 2);
        end = new Coord(start);
        end.translate(dir, 3);
        end.translate(orthogonals, 1);
        RectSolid.newRect(start, end).fill(editor, blocks);
        start.translate(orthogonals, 2);
        end.translate(dir.reverse(), 2);
        end.translate(orthogonals, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);

        cursor = new Coord(floor);
        cursor.translate(dir, 5);
        cursor.translate(orthogonals, 1);
        start = new Coord(cursor);
        end = new Coord(cursor);
        end.translate(dir.reverse(), 1);
        end.translate(Cardinal.UP, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);
        start = new Coord(end);
        start.translate(dir, 1);
        start.translate(orthogonals.reverse(), 1);
        RectSolid.newRect(start, end).fill(editor, blocks);
        cursor.translate(Cardinal.UP, 2);
        stair.setUpsideDown(false).setFacing(orthogonals);
        stair.stroke(editor, cursor);

        start = new Coord(floor);
        start.translate(dir, 4);
        end = new Coord(start);
        end.translate(Cardinal.UP, 9);
        end.translate(orthogonals, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(dir, 3);
        start.translate(orthogonals, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 9);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(dir, 4);
        end = new Coord(start);
        end.translate(dir, 1);
        end.translate(Cardinal.UP, 1);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

        cursor = new Coord(floor);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        cursor.translate(Cardinal.UP, 3);
        stair.setUpsideDown(true).setFacing(orthogonals.reverse());
        stair.stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 5);
        stair.setUpsideDown(true).setFacing(orthogonals.reverse());
        stair.stroke(editor, cursor);

        start = new Coord(floor);
        start.translate(dir, 4);
        start.translate(orthogonals, 3);
        start.translate(Cardinal.UP, 4);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, start);

        start.translate(Cardinal.UP, 1);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = new Coord(floor);
        start.translate(dir, 5);
        start.translate(Cardinal.UP, 4);
        stair.setUpsideDown(true).setFacing(dir);
        stair.stroke(editor, start);

        cursor = new Coord(start);
        cursor.translate(orthogonals, 1);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, cursor);

        start.translate(Cardinal.UP, 3);
        stair.setUpsideDown(true).setFacing(dir);
        stair.stroke(editor, start);

        cursor = new Coord(start);
        cursor.translate(orthogonals, 1);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, cursor);

        start.translate(Cardinal.UP, 1);
        end = new Coord(start);
        end.translate(orthogonals, 1);
        end.translate(Cardinal.UP, 1);
        RectSolid.newRect(start, end).fill(editor, blocks);

        cursor = new Coord(end);
        cursor.translate(orthogonals, 1);
        cursor.translate(Cardinal.DOWN, 1);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        cursor.translate(orthogonals, 1);
        stair.stroke(editor, cursor);

        cursor.translate(orthogonals.reverse(), 1);
        blocks.stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(orthogonals, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        addCrenellation(editor, cursor, blocks);

        cursor.translate(Cardinal.DOWN, 2);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(orthogonals, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(Cardinal.DOWN, 1);
        blocks.stroke(editor, cursor);

        cursor = new Coord(floor);
        cursor.translate(dir, 6);
        cursor.translate(Cardinal.UP, 9);

        stair.setUpsideDown(true).setFacing(dir);
        stair.stroke(editor, cursor);

        cursor.translate(orthogonals, 1);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, cursor);

        cursor.translate(orthogonals.reverse(), 1);
        cursor.translate(Cardinal.UP, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(orthogonals, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        addCrenellation(editor, cursor, blocks);

        cursor = new Coord(floor);
        cursor.translate(dir, 4);
        cursor.translate(Cardinal.UP, 5);
        SingleBlockBrush.AIR.stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        SingleBlockBrush.AIR.stroke(editor, cursor);
        cursor.translate(orthogonals, 2);
        BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
      }
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonals : dir.orthogonals()) {
        start = new Coord(x, ground, z);
        start.translate(dir, 4);
        end = new Coord(x, 60, z);
        end.translate(dir, 4);
        start.translate(orthogonals.reverse(), 2);
        end.translate(orthogonals, 2);

        RectSolid.newRect(start, end).fill(editor, blocks);
        start = new Coord(x, ground, z);
        start.translate(dir, 3);
        start.translate(orthogonals, 3);
        end = new Coord(x, 60, z);
        end.translate(dir, 3);
        end.translate(orthogonals, 3);
        RectSolid.newRect(start, end).fill(editor, blocks);

      }
    }

    for (int i = main; i >= y; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getPrimary().getPillar());
    }
  }


  private void addCrenellation(WorldEditor editor, Coord cursor, BlockBrush blocks) {

    blocks.stroke(editor, cursor);

    if (editor.isAirBlock(cursor)) {
      return;
    }

    cursor.translate(Cardinal.UP, 1);
    TorchBlock.torch().setFacing(Cardinal.UP).stroke(editor, cursor);
  }
}
