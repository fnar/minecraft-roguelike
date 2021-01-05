package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

public class SegmentNetherArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(dir.reverse());
    BlockBrush pillar = theme.getSecondary().getPillar();


    Coord cursor;

    boolean hasLava = rand.nextInt(5) == 0;

    for (Cardinal orthogonals : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 1);
      cursor.translate(orthogonals, 1);
      cursor.translate(Cardinal.UP, 2);
      stair.stroke(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(orthogonals, 1);
      pillar.stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 1);
      pillar.stroke(editor, cursor);
    }

    BlockBrush fence = BlockType.FENCE_NETHER_BRICK.getBrush();
    BlockBrush lava = BlockType.LAVA_FLOWING.getBrush();

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    fence.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    fence.stroke(editor, cursor);

    if (hasLava) {
      cursor.translate(dir, 1);
      lava.stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN, 1);
      lava.stroke(editor, cursor);
    }
  }
}
