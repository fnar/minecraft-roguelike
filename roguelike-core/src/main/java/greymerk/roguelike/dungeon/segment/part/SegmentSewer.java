package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewer extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    BlockBrush water = BlockType.WATER_FLOWING.getBrush();
    StairsBlock stair = theme.getSecondary().getStair();

    Coord start;
    Coord end;

    Cardinal[] orthogonals = dir.orthogonals();

    start = new Coord(origin);
    start.translate(Cardinal.UP, 2);
    start.translate(dir);
    end = new Coord(start);
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    RectSolid.newRect(start, end).fill(editor, stair);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.newRect(start, end).fill(editor, water);
  }
}
