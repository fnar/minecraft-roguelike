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

public class SegmentSewer extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
    IStair stair = theme.getSecondary().getStair();

    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(origin);
    start.translate(Cardinal.UP, 2);
    start.translate(dir);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    stair.setOrientation(dir.reverse(), true);
    RectSolid.fill(editor, rand, start, end, stair);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    RectSolid.fill(editor, rand, start, end, air);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, water);
  }
}
