package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentCave extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    BlockBrush wall = theme.getPrimary().getWall();
    BlockJumble fill = new BlockJumble();
    fill.addBlock(SingleBlockBrush.AIR);
    fill.addBlock(wall);

    Cardinal[] orthogonals = dir.orthogonals();

    Coord cursor = origin.copy();
    Coord start;
    Coord end;

    start = cursor.copy();
    start.translate(Cardinal.UP, 2);
    start.translate(dir);
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    RectSolid.newRect(start, end).fill(editor, fill);
    start.translate(dir);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, fill);
    start.translate(Cardinal.DOWN);
    RectSolid.newRect(start, end).fill(editor, fill);
    start.translate(Cardinal.DOWN);
    RectSolid.newRect(start, end).fill(editor, fill);

  }
}
