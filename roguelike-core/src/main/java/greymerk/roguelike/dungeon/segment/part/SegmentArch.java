package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    StairsBlock stair = getSecondaryStairs(theme);
    stair.setUpsideDown(true).setFacing(dir.reverse());

    Coord cursor = origin.copy()
        .translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up(1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up(1);
    stair.stroke(editor, cursor);

    for (Direction orthogonals : dir.orthogonals()) {
      Coord pillarBase = origin.copy().translate(dir, 2).translate(orthogonals, 1);
      Coord pillarTop = pillarBase.copy().up(2);
      RectSolid.newRect(pillarBase, pillarTop).fill(editor, getSecondaryPillar(theme));
      pillarTop.translate(dir.reverse(), 1);
      stair.stroke(editor, pillarTop);
    }
  }
}
