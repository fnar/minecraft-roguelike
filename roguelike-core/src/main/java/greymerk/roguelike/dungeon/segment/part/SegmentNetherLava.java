package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentNetherLava extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    StairsBlock stair = getSecondaryStairs(theme);

    Coord cursor = origin.copy();
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up(1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor = origin.copy();
    cursor.translate(dir, 5);
    boolean isAir = editor.isAirBlock(cursor);
    BlockBrush wall = getSecondaryWall(theme);

    for (Direction orthogonals : dir.orthogonals()) {
      Coord start = origin.copy();
      start.translate(dir, 3);
      Coord end = start.copy();
      start.translate(orthogonals, 1);
      start.up(2);
      end.down();
      if (!isAir) {
        SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));
        BlockType.LAVA_FLOWING.getBrush().stroke(editor, start);
        start.translate(orthogonals.reverse(), 1);
        BlockType.LAVA_FLOWING.getBrush().stroke(editor, start);
      }

      cursor = origin.copy();
      cursor.translate(dir, 2);

      stair.setUpsideDown(false).setFacing(orthogonals.reverse());
      cursor.translate(orthogonals, 1);
      stair.stroke(editor, cursor);

      stair.setUpsideDown(true).setFacing(orthogonals.reverse());
      cursor.up(1);
      stair.stroke(editor, cursor);

      cursor.up(1);
      wall.stroke(editor, cursor);
      cursor.translate(orthogonals.reverse(), 1);
      wall.stroke(editor, cursor);
    }

  }

}
