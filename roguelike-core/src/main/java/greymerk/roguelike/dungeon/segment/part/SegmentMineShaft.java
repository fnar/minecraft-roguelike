package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMineShaft extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    BlockBrush wall = getSecondaryWall(theme);

    Direction[] orthogonal = dir.orthogonals();

    Coord cursor = origin.copy();
    Coord start = cursor.copy();
    start.translate(dir, 2);
    Coord end = start.copy();
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end.up(3);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    cursor.up(3);
    cursor.translate(orthogonal[0]);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    end.up(3);
    RectSolid.newRect(start, end).fill(editor, wall);
    start = end.copy();
    cursor = end.copy();
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = cursor.copy();
    end = cursor.copy();
    end.translate(dir.reverse(), 2);
    RectSolid.newRect(start, end).fill(editor, wall);
  }
}
