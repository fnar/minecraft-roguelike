package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.segment.alcove.SilverfishNest;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSilverfish extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    Cardinal[] orthogonals = dir.orthogonals();

    cursor.translate(dir, 2);
    start = new Coord(cursor);
    start.translate(orthogonals[0], 1);
    end = new Coord(cursor);
    end.translate(orthogonals[1], 1);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    // front wall
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall(), false, true);

    // stairs
    cursor.translate(Cardinal.UP, 2);
    for (Cardinal d : orthogonals) {
      Coord c = new Coord(cursor);
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }

    stair = theme.getPrimary().getStair();

    cursor = new Coord(origin);
    cursor.translate(dir, 3);
    stair.setUpsideDown(false).setFacing(dir.reverse());
    stair.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);

    IAlcove nest = new SilverfishNest();
    if (nest.isValidLocation(editor, new Coord(origin), dir)) {
      nest.generate(editor, rand, level.getSettings(), new Coord(origin), dir);
    }
  }
}
