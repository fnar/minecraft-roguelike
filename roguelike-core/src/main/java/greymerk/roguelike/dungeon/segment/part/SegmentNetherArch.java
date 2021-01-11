package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class SegmentNetherArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(dir.reverse());
    BlockBrush pillar = theme.getSecondary().getPillar();


    Coord cursor;

    boolean hasLava = rand.nextInt(5) == 0;

    for (Direction orthogonals : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 1);
      cursor.translate(orthogonals, 1);
      cursor.translate(Direction.UP, 2);
      stair.stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(orthogonals, 1);
      pillar.stroke(editor, cursor);
      cursor.translate(Direction.UP, 1);
      pillar.stroke(editor, cursor);
    }

    BlockBrush fence = BlockType.FENCE_NETHER_BRICK.getBrush();
    BlockBrush lava = BlockType.LAVA_FLOWING.getBrush();

    cursor = origin.copy();
    cursor.translate(dir, 2);
    fence.stroke(editor, cursor);
    cursor.translate(Direction.UP, 1);
    fence.stroke(editor, cursor);

    if (hasLava) {
      cursor.translate(dir, 1);
      lava.stroke(editor, cursor);
      cursor.translate(Direction.DOWN, 1);
      lava.stroke(editor, cursor);
    }
  }
}
