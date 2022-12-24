package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewer extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    StairsBlock stair = getSecondaryStairs(theme);

    Direction[] orthogonals = dir.orthogonals();

    Coord start = origin.copy();
    start.up(2);
    start.translate(dir);
    Coord end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.down();
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));
    start.down();
    end.down();
    BlockType.WATER_FLOWING.getBrush().fill(editor, RectSolid.newRect(start, end));
  }
}
