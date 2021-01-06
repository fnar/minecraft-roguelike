package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentInset extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orthogonals = dir.orthogonals();

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall());

    for (Cardinal d : orthogonals) {
      cursor = origin.copy();
      cursor.translate(Cardinal.UP, 2);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setUpsideDown(true).setFacing(dir.reverse());
      stair.stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setUpsideDown(false).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.translate(Cardinal.UP, 1);
    cursor.translate(dir, 3);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);
  }
}
