package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewerDrain extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {
    StairsBlock stair = theme.getSecondary().getStair();

    Direction[] orthogonals = dir.orthogonals();

    Coord start = origin.copy();
    start.down();
    Coord end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.down();
    end.down();
    RectSolid.newRect(start, end).fill(editor, BlockType.WATER_FLOWING.getBrush(), false, true);

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(dir);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall());

    Coord cursor;
    for (Direction o : orthogonals) {
      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.up();
      BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }

    start = origin.copy();
    start.up();
    end = start.copy();
    end.translate(dir, 5);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    BlockType.WATER_FLOWING.getBrush().stroke(editor, end);

    cursor = origin.copy();
    cursor.down();
    cursor.translate(dir);
    SingleBlockBrush.AIR.stroke(editor, cursor);

  }
}
