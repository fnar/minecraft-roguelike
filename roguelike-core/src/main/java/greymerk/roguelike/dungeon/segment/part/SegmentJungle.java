package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.normal.Wood;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentJungle extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction wallDirection, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();

    BlockBrush leaves = Wood.JUNGLE.getLeaves();

    Coord cursor;
    Coord start;
    Coord end;

    Direction[] orthogonals = wallDirection.orthogonals();
    start = origin.copy();
    start.translate(wallDirection, 2);
    end = start.copy();
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.up(1);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.down();
    end.down(2);

    if (rand.nextInt(5) == 0) {
      RectSolid.newRect(start, end).fill(editor, BlockType.WATER_FLOWING.getBrush());
    } else {
      RectSolid.newRect(start, end).fill(editor, BlockType.GRASS.getBrush());
      start.up(1);
      end.up(1);
      if (rand.nextBoolean()) {
        RectSolid.newRect(start, end).fill(editor, leaves);
      }
    }

    for (Direction d : orthogonals) {
      cursor = origin.copy();
      cursor.translate(wallDirection, 2);
      cursor.translate(d, 1);
      cursor.up(1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }

  }
}
