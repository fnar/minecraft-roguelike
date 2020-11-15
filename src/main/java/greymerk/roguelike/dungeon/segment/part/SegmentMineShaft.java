package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMineShaft extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);

    IBlockFactory wall = theme.getSecondary().getWall();

    Cardinal[] orth = dir.orthogonal();

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    start = new Coord(cursor);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, air);

    cursor.translate(Cardinal.UP, 3);
    cursor.translate(orth[0]);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, wall);
    start = new Coord(end);
    cursor = new Coord(end);
    start.translate(orth[0]);
    end.translate(orth[1]);
    RectSolid.fill(editor, rand, start, end, wall);

    start = new Coord(cursor);
    end = new Coord(cursor);
    end.translate(dir.reverse(), 2);
    RectSolid.fill(editor, rand, start, end, wall);
  }
}
