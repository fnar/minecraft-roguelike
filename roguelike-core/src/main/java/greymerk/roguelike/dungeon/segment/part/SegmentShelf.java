package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentShelf extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    StairsBlock stair = getSecondaryStairs(theme);

    Coord cursor = origin.copy();

    Direction[] orthogonals = dir.orthogonals();

    cursor.translate(dir, 2);
    Coord start = cursor.copy();
    start.translate(orthogonals[0], 1);
    Coord end = cursor.copy();
    end.translate(orthogonals[1], 1);
    RectSolid.newRect(start, end).fill(editor, getSecondaryWall(theme), false, true);
    start.translate(dir, 1);
    end.translate(dir, 1);
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, getSecondaryWall(theme), false, true);
    start.translate(dir.reverse(), 1);
    start.up(1);
    end.translate(dir.reverse(), 1);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR, false, true);
    cursor.up(2);
    for (Direction d : orthogonals) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }
  }
}
