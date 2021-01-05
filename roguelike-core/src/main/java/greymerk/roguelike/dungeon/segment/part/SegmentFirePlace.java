package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFirePlace extends SegmentBase {

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
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

    // front wall
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.fill(editor, start, end, theme.getPrimary().getWall(), false, true);

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
    BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);

    start = new Coord(origin);
    start.translate(dir, 4);
    end = new Coord(start);
    start.translate(Cardinal.DOWN);
    start.translate(orthogonals[0]);
    end.translate(Cardinal.UP, 3);
    end.translate(orthogonals[1]);
    end.translate(dir, 2);
    for (Coord c : new RectHollow(start, end)) {
      if (!editor.isSolidBlock(c)) {
        return;
      }
    }

    RectSolid.fill(editor, start, end, theme.getPrimary().getWall());

    cursor = new Coord(origin);
    cursor.translate(dir, 4);
    BlockType.NETHERRACK.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.FIRE.getBrush().stroke(editor, cursor);
  }
}
