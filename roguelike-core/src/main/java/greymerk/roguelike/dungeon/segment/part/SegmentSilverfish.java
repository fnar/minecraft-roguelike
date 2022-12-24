package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.segment.alcove.SilverfishNest;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSilverfish extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {
    StairsBlock stair = getSecondaryStairs(theme);

    Direction[] orthogonals = dir.orthogonals();

    Coord cursor = origin.copy();
    cursor.translate(dir, 2);
    Coord start = cursor.copy();
    start.translate(orthogonals[0], 1);
    Coord end = cursor.copy();
    end.translate(orthogonals[1], 1);
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    // front wall
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.newRect(start, end).fill(editor, getPrimaryWalls(theme), false, true);

    // stairs
    cursor.up(2);
    for (Direction d : orthogonals) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }

    stair = getPrimaryStairs(theme);

    cursor = origin.copy();
    cursor.translate(dir, 3);
    stair.setUpsideDown(false).setFacing(dir.reverse());
    stair.stroke(editor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up();
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);

    SilverfishNest.generate(new SilverfishNest(), editor, editor.getRandom(), level, dir, origin);
  }

}
