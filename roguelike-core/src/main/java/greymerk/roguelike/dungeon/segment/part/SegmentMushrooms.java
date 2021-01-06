package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMushrooms extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction wallDirection, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();

    BlockWeightedRandom mushrooms = new BlockWeightedRandom();
    mushrooms.addBlock(BlockType.RED_MUSHROOM.getBrush(), 3);
    mushrooms.addBlock(BlockType.BROWN_MUSHROOM.getBrush(), 3);
    mushrooms.addBlock(SingleBlockBrush.AIR, 10);

    Coord cursor;
    Coord start;
    Coord end;

    Direction[] orthogonals = wallDirection.orthogonals();
    start = origin.copy();
    start.translate(wallDirection, 2);
    end = start.copy();
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.translate(Direction.UP, 1);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(Direction.DOWN, 1);
    end.translate(Direction.DOWN, 2);

    RectSolid.newRect(start, end).fill(editor, BlockType.MYCELIUM.getBrush());
    start.translate(Direction.UP, 1);
    end.translate(Direction.UP, 1);
    RectSolid.newRect(start, end).fill(editor, mushrooms);

    for (Direction d : orthogonals) {
      cursor = origin.copy();
      cursor.translate(wallDirection, 2);
      cursor.translate(d, 1);
      cursor.translate(Direction.UP, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }

  }
}
