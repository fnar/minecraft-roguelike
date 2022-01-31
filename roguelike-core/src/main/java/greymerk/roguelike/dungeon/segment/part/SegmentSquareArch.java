package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSquareArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {
    BlockBrush pillar = level.getSettings().getTheme().getPrimary().getPillar();

    Coord start = origin.copy();
    start.translate(dir, 2);
    Coord end = start.copy();
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = origin.copy();
    start.translate(dir, 3);
    end = start.copy();
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, pillar);

    for (Direction orthogonals : dir.orthogonals()) {
      start = origin.copy();
      start.translate(orthogonals, 1);
      start.translate(dir, 2);
      end = start.copy();
      end.up(2);
      RectSolid.newRect(start, end).fill(editor, pillar);
    }
  }
}
