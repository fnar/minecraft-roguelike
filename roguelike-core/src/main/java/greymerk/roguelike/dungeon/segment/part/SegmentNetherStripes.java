package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.SlabBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentNetherStripes extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    boolean isAir = editor.isAirBlock(cursor);
    boolean isLava = rand.nextInt(5) == 0;


    BlockBrush slab = SlabBlock.netherBrick();
    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    slab.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    slab.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    slab.stroke(editor, cursor);

    for (Cardinal orthogonal : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(orthogonal, 1);
      start.translate(Cardinal.UP, 3);
      end.translate(Cardinal.DOWN, 2);
      if (isLava && !isAir) {
        RectSolid.fill(editor, start, end, BlockType.LAVA_FLOWING.getBrush(), false, true);
      }

      stair.setUpsideDown(true).setFacing(orthogonal.reverse());
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(orthogonal, 1);
      stair.stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 1);
      stair.stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 1);
      stair.stroke(editor, cursor);
      cursor.translate(dir.reverse(), 1);
      stair.stroke(editor, cursor);
    }
  }

}