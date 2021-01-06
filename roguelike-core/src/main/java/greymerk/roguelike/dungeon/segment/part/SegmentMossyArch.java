package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.VineBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMossyArch extends SegmentBase {

  private boolean spawnHoleSet = false;

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal wallDirection, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(wallDirection.reverse());

    generateSecret(level.getSettings().getSecrets(), editor, level.getSettings(), wallDirection, origin.copy());

    Coord cursor = origin.copy();
    cursor.translate(wallDirection, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    stair.stroke(editor, cursor);

    for (Cardinal orthogonals : wallDirection.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(orthogonals, 1);
      cursor.translate(wallDirection, 2);
      theme.getSecondary().getPillar().stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 1);
      theme.getSecondary().getPillar().stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 1);
      theme.getSecondary().getWall().stroke(editor, cursor);
      cursor.translate(wallDirection.reverse(), 1);
      stair.stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.translate(wallDirection, 2);
    cursor.translate(Cardinal.DOWN, 1);
    BlockType.WATER_FLOWING.getBrush().stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(Cardinal.UP, 3);
    cursor.translate(wallDirection, 1);
    VineBlock.vine().stroke(editor, cursor);

    if (!spawnHoleSet) {
      RectSolid.newRect(new Coord(0, 2, 0).translate(origin), new Coord(0, 5, 0).translate(origin)).fill(editor, SingleBlockBrush.AIR);
      final Coord translate = new Coord(0, 3, 0).translate(origin);
      final Coord translate1 = new Coord(0, 5, 0).translate(origin);
      VineBlock.vine().fill(editor, new RectSolid(translate, translate1));

      if (!editor.isAirBlock(new Coord(0, 6, 0).translate(origin))) {
        BlockType.WATER_FLOWING.getBrush().stroke(editor, new Coord(0, 7, 0).translate(origin));
      }
      spawnHoleSet = true;
    }
  }

}
