package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.material.Crop;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class SegmentNetherWart extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();
    BlockBrush wall = theme.getSecondary().getWall();

    Coord cursor;

    cursor = origin.copy();
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up(1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor = origin.copy();
    cursor.translate(dir, 5);


    cursor = origin.copy();
    cursor.translate(dir, 3);
    BlockType.FENCE_NETHER_BRICK.getBrush().stroke(editor, cursor);
    cursor.up(1);
    BlockType.FENCE_NETHER_BRICK.getBrush().stroke(editor, cursor);

    for (Direction orthogonals : dir.orthogonals()) {
      stair.setUpsideDown(true).setFacing(orthogonals.reverse());
      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(orthogonals, 1);
      cursor.up(1);
      stair.stroke(editor, cursor);
      cursor.up(1);
      wall.stroke(editor, cursor);
      cursor.translate(orthogonals.reverse(), 1);
      wall.stroke(editor, cursor);
      cursor.down(2);
      Crop.NETHER_WART.getBrush().stroke(editor, cursor);
      cursor.translate(orthogonals, 1);
      Crop.NETHER_WART.getBrush().stroke(editor, cursor);
      cursor.down();
      BlockType.SOUL_SAND.getBrush().stroke(editor, cursor);
      cursor.translate(orthogonals.reverse(), 1);
      BlockType.SOUL_SAND.getBrush().stroke(editor, cursor);
    }

  }

}
