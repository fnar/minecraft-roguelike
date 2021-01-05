package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSquareArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    Coord start;
    Coord end;

    BlockBrush pillar = level.getSettings().getTheme().getPrimary().getPillar();

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(dir, 3);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, start, end, pillar);

    for (Cardinal orthogonals : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(orthogonals, 1);
      start.translate(dir, 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, start, end, pillar);
    }
  }
}
