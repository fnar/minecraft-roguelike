package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewerDrain extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {

    BlockBrush water = BlockType.WATER_FLOWING.getBrush();
    StairsBlock stair = theme.getSecondary().getStair();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord cursor;
    Coord start;
    Coord end;

    Direction[] orthogonals = dir.orthogonals();

    start = origin.copy();
    start.translate(Direction.DOWN);
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(Direction.DOWN);
    end.translate(Direction.DOWN);
    RectSolid.newRect(start, end).fill(editor, water, false, true);

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(dir);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall());

    for (Direction o : orthogonals) {
      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(Direction.UP);
      bars.stroke(editor, cursor);
      cursor.translate(Direction.UP);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }

    start = origin.copy();
    start.translate(Direction.UP);
    end = start.copy();
    end.translate(dir, 5);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    water.stroke(editor, end);

    cursor = origin.copy();
    cursor.translate(Direction.DOWN);
    cursor.translate(dir);
    SingleBlockBrush.AIR.stroke(editor, cursor);

  }
}
