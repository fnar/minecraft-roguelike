package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewerArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    BlockBrush stair = theme.getSecondary().getStair().setUpsideDown(true).setFacing(dir.reverse());
    BlockBrush water = BlockType.WATER_FLOWING.getBrush();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();
    BlockBrush mossy = BlockType.COBBLESTONE_MOSSY.getBrush();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orthogonals = dir.orthogonals();

    cursor = origin.copy();
    cursor.translate(Cardinal.UP, 3);
    mossy.stroke(editor, cursor, false, true);
    cursor.translate(Cardinal.UP);
    water.stroke(editor, cursor, false, true);

    cursor = origin.copy();
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    stair.stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(dir, 2);
    bars.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    bars.stroke(editor, cursor);

    start = origin.copy();
    start.translate(Cardinal.DOWN);
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.newRect(start, end).fill(editor, water);

    for (Cardinal o : orthogonals) {
      cursor = origin.copy();
      cursor.translate(o, 1);
      cursor.translate(dir, 2);
      theme.getSecondary().getPillar().stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 1);
      theme.getSecondary().getPillar().stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 1);
      theme.getPrimary().getWall().stroke(editor, cursor);
      cursor.translate(dir.reverse(), 1);
      stair.stroke(editor, cursor);
    }
  }
}
