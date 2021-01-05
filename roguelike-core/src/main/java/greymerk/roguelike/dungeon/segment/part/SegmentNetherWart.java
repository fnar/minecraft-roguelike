package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.Crop;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class SegmentNetherWart extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();
    BlockBrush wall = theme.getSecondary().getWall();

    Coord cursor;

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor = new Coord(origin);
    cursor.translate(dir, 5);


    cursor = new Coord(origin);
    cursor.translate(dir, 3);
    BlockType.FENCE_NETHER_BRICK.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    BlockType.FENCE_NETHER_BRICK.getBrush().stroke(editor, cursor);

    for (Cardinal orthogonals : dir.orthogonals()) {
      stair.setUpsideDown(true).setFacing(orthogonals.reverse());
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(orthogonals, 1);
      cursor.translate(Cardinal.UP, 1);
      stair.stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 1);
      wall.stroke(editor, cursor);
      cursor.translate(orthogonals.reverse(), 1);
      wall.stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN, 2);
      Crop.NETHER_WART.getBrush().stroke(editor, cursor);
      cursor.translate(orthogonals, 1);
      Crop.NETHER_WART.getBrush().stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN, 1);
      BlockType.SOUL_SAND.getBrush().stroke(editor, cursor);
      cursor.translate(orthogonals.reverse(), 1);
      BlockType.SOUL_SAND.getBrush().stroke(editor, cursor);
    }

  }

}
