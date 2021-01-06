package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class SegmentArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(dir.reverse());

    Coord cursor = origin.copy();
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Direction.UP, 1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Direction.UP, 1);
    stair.stroke(editor, cursor);

    for (Direction orthogonals : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(orthogonals, 1);
      cursor.translate(dir, 2);
      theme.getSecondary().getPillar().stroke(editor, cursor);
      cursor.translate(Direction.UP, 1);
      theme.getSecondary().getPillar().stroke(editor, cursor);
      cursor.translate(Direction.UP, 1);
      theme.getPrimary().getWall().stroke(editor, cursor);
      cursor.translate(dir.reverse(), 1);
      stair.stroke(editor, cursor);
    }
  }
}
