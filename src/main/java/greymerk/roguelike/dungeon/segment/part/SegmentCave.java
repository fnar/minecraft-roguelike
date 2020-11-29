package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentCave extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);

    IBlockFactory wall = theme.getPrimary().getWall();
    BlockJumble fill = new BlockJumble();
    fill.addBlock(air);
    fill.addBlock(wall);


    Cardinal[] orth = dir.orthogonal();

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    start = new Coord(cursor);
    start.translate(Cardinal.UP, 2);
    start.translate(dir);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    RectSolid.fill(editor, rand, start, end, fill);
    start.translate(dir);
    end.translate(dir);
    RectSolid.fill(editor, rand, start, end, fill);
    start.translate(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, fill);
    start.translate(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, fill);

  }
}
