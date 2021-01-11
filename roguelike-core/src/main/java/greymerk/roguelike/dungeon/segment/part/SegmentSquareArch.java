package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSquareArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {

    Coord start;
    Coord end;

    BlockBrush pillar = level.getSettings().getTheme().getPrimary().getPillar();

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = origin.copy();
    start.translate(dir, 3);
    end = start.copy();
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, pillar);

    for (Direction orthogonals : dir.orthogonals()) {
      start = origin.copy();
      start.translate(orthogonals, 1);
      start.translate(dir, 2);
      end = start.copy();
      end.translate(Direction.UP, 2);
      RectSolid.newRect(start, end).fill(editor, pillar);
    }
  }
}
