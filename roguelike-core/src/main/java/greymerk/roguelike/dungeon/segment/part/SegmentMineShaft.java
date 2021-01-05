package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMineShaft extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    BlockBrush wall = theme.getSecondary().getWall();

    Cardinal[] orthogonal = dir.orthogonals();

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    start = new Coord(cursor);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

    cursor.translate(Cardinal.UP, 3);
    cursor.translate(orthogonal[0]);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, start, end, wall);
    start = new Coord(end);
    cursor = new Coord(end);
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    RectSolid.fill(editor, start, end, wall);

    start = new Coord(cursor);
    end = new Coord(cursor);
    end.translate(dir.reverse(), 2);
    RectSolid.fill(editor, start, end, wall);
  }
}
