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

public class SegmentSewerDrain extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    BlockBrush water = BlockType.WATER_FLOWING.getBrush();
    StairsBlock stair = theme.getSecondary().getStair();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orthogonals = dir.orthogonals();

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.fill(editor, start, end, water, false, true);

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);
    start.translate(dir);
    end.translate(dir);
    RectSolid.fill(editor, start, end, theme.getPrimary().getWall());

    for (Cardinal o : orthogonals) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      bars.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    end.translate(dir, 5);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);
    water.stroke(editor, end);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir);
    SingleBlockBrush.AIR.stroke(editor, cursor);

  }
}
