package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentShelf extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor = origin.copy();
    Coord start;
    Coord end;

    Direction[] orthogonals = dir.orthogonals();

    cursor.translate(dir, 2);
    start = cursor.copy();
    start.translate(orthogonals[0], 1);
    end = cursor.copy();
    end.translate(orthogonals[1], 1);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall(), false, true);
    start.translate(dir, 1);
    end.translate(dir, 1);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall(), false, true);
    start.translate(dir.reverse(), 1);
    start.translate(Direction.UP, 1);
    end.translate(dir.reverse(), 1);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR, false, true);
    cursor.translate(Direction.UP, 2);
    for (Direction d : orthogonals) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }
  }
}
