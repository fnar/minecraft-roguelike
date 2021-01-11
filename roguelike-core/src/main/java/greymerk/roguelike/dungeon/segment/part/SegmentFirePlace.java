package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFirePlace extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor = origin.copy();
    Coord start;
    Coord end;

    Direction[] orthogonals = dir.orthogonals();

    cursor.translate(dir, 2);
    start = cursor.copy();
    start.translate(orthogonals[0], 1);
    end = cursor.copy();
    end.translate(orthogonals[1], 1);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    // front wall
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall(), false, true);

    // stairs
    cursor.translate(Direction.UP, 2);
    for (Direction d : orthogonals) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }

    stair = theme.getPrimary().getStair();

    cursor = origin.copy();
    cursor.translate(dir, 3);
    stair.setUpsideDown(false).setFacing(dir.reverse());
    stair.stroke(editor, cursor);
    cursor.translate(Direction.UP);
    BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
    cursor.translate(Direction.UP);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);

    start = origin.copy();
    start.translate(dir, 4);
    end = start.copy();
    start.translate(Direction.DOWN);
    start.translate(orthogonals[0]);
    end.translate(Direction.UP, 3);
    end.translate(orthogonals[1]);
    end.translate(dir, 2);
    for (Coord c : new RectHollow(start, end)) {
      if (!editor.isSolidBlock(c)) {
        return;
      }
    }

    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall());

    cursor = origin.copy();
    cursor.translate(dir, 4);
    BlockType.NETHERRACK.getBrush().stroke(editor, cursor);
    cursor.translate(Direction.UP);
    BlockType.FIRE.getBrush().stroke(editor, cursor);
  }
}
