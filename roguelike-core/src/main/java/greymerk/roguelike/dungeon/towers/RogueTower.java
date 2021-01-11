package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.TorchBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
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

    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonals : dir.orthogonals()) {
        // ground floor
        start = floor.copy();
        start.translate(Direction.DOWN, 1);
        start.translate(dir, 2);
        end = start.copy();
        end.translate(dir, 3);
        end.translate(orthogonals, 1);
        RectSolid.newRect(start, end).fill(editor, blocks);
        start.translate(orthogonals, 2);
        end.translate(dir.reverse(), 2);
        end.translate(orthogonals, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);

        cursor = floor.copy();
        cursor.translate(dir, 5);
        cursor.translate(orthogonals, 1);
        start = cursor.copy();
        end = cursor.copy();
        end.translate(dir.reverse(), 1);
        end.translate(Direction.UP, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);
        start = end.copy();
        start.translate(dir, 1);
        start.translate(orthogonals.reverse(), 1);
        RectSolid.newRect(start, end).fill(editor, blocks);
        cursor.translate(Direction.UP, 2);
        stair.setUpsideDown(false).setFacing(orthogonals);
        stair.stroke(editor, cursor);

        start = floor.copy();
        start.translate(dir, 4);
        end = start.copy();
        end.translate(Direction.UP, 9);
        end.translate(orthogonals, 2);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = floor.copy();
        start.translate(dir, 3);
        start.translate(orthogonals, 3);
        end = start.copy();
        end.translate(Direction.UP, 9);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = floor.copy();
        start.translate(dir, 4);
        end = start.copy();
        end.translate(dir, 1);
        end.translate(Direction.UP, 1);
        RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

        cursor = floor.copy();
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        cursor.translate(Direction.UP, 3);
        stair.setUpsideDown(true).setFacing(orthogonals.reverse());
        stair.stroke(editor, cursor);
        cursor.translate(Direction.UP, 5);
        stair.setUpsideDown(true).setFacing(orthogonals.reverse());
        stair.stroke(editor, cursor);

        start = floor.copy();
        start.translate(dir, 4);
        start.translate(orthogonals, 3);
        start.translate(Direction.UP, 4);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, start);

        start.translate(Direction.UP, 1);
        end = start.copy();
        end.translate(Direction.UP, 4);
        RectSolid.newRect(start, end).fill(editor, blocks);

        start = floor.copy();
        start.translate(dir, 5);
        start.translate(Direction.UP, 4);
        stair.setUpsideDown(true).setFacing(dir);
        stair.stroke(editor, start);

        cursor = start.copy();
        cursor.translate(orthogonals, 1);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, cursor);

        start.translate(Direction.UP, 3);
        stair.setUpsideDown(true).setFacing(dir);
        stair.stroke(editor, start);

        cursor = start.copy();
        cursor.translate(orthogonals, 1);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, cursor);

        start.translate(Direction.UP, 1);
        end = start.copy();
        end.translate(orthogonals, 1);
        end.translate(Direction.UP, 1);
        RectSolid.newRect(start, end).fill(editor, blocks);

        cursor = end.copy();
        cursor.translate(orthogonals, 1);
        cursor.translate(Direction.DOWN, 1);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, cursor);
        cursor.translate(Direction.UP, 1);
        cursor.translate(orthogonals, 1);
        stair.stroke(editor, cursor);

        cursor.translate(orthogonals.reverse(), 1);
        blocks.stroke(editor, cursor);
        cursor.translate(Direction.UP, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(orthogonals, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(Direction.UP, 1);
        addCrenellation(editor, cursor, blocks);

        cursor.translate(Direction.DOWN, 2);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(orthogonals, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(Direction.DOWN, 1);
        blocks.stroke(editor, cursor);

        cursor = floor.copy();
        cursor.translate(dir, 6);
        cursor.translate(Direction.UP, 9);

        stair.setUpsideDown(true).setFacing(dir);
        stair.stroke(editor, cursor);

        cursor.translate(orthogonals, 1);
        stair.setUpsideDown(true).setFacing(orthogonals);
        stair.stroke(editor, cursor);

        cursor.translate(orthogonals.reverse(), 1);
        cursor.translate(Direction.UP, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(orthogonals, 1);
        blocks.stroke(editor, cursor);
        cursor.translate(Direction.UP, 1);
        addCrenellation(editor, cursor, blocks);

        cursor = floor.copy();
        cursor.translate(dir, 4);
        cursor.translate(Direction.UP, 5);
        SingleBlockBrush.AIR.stroke(editor, cursor);
        cursor.translate(Direction.UP, 1);
        SingleBlockBrush.AIR.stroke(editor, cursor);
        cursor.translate(orthogonals, 2);
        BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
      }
    }

    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonals : dir.orthogonals()) {
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

    cursor.translate(Direction.UP, 1);
    TorchBlock.torch().setFacing(Direction.UP).stroke(editor, cursor);
  }
}
