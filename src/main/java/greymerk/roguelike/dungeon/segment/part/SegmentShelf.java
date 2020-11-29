package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentShelf extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    cursor.translate(dir, 2);
    start = new Coord(cursor);
    start.translate(orth[0], 1);
    end = new Coord(cursor);
    end.translate(orth[1], 1);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);
    start.translate(dir, 1);
    end.translate(dir, 1);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);
    start.translate(dir.reverse(), 1);
    start.translate(Cardinal.UP, 1);
    end.translate(dir.reverse(), 1);
    RectSolid.fill(editor, rand, start, end, air, false, true);
    cursor.translate(Cardinal.UP, 2);
    for (Cardinal d : orth) {
      Coord c = new Coord(cursor);
      c.translate(d, 1);
      stair.setOrientation(d.reverse(), true);
      stair.set(editor, c);
    }
  }
}
