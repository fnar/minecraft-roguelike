package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.TorchBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.LeverBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class SegmentLamp extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    StairsBlock stair = getSecondaryStairs(theme);
    BlockBrush wall = getSecondaryWall(theme);

    Direction[] orthogonal = dir.orthogonals();

    Coord start = origin.copy();
    start.translate(dir, 2);
    Coord end = start.copy();
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end.up(2);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.up(3);
    end = start.copy();
    start.translate(dir);
    start.translate(orthogonal[0]);
    end.translate(dir.reverse());
    end.translate(orthogonal[1]);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(dir, 3);
    end = start.copy();
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end.translate(dir, 2);
    end.up(6);
    RectSolid.newRect(start, end).fill(editor, wall);
    start = end.copy();
    start.down(2);
    start.translate(dir.reverse(), 6);
    start.translate(orthogonal[0], 2);
    RectSolid.newRect(start, end).fill(editor, wall);

    Coord cursor;
    for (Direction side : orthogonal) {

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.up(2);
      stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.up(4);
    overheadLight(editor, theme, cursor);

    cursor = origin.copy();
    cursor.up();
    cursor.translate(dir, 2);

    Coord lever = cursor.copy();
    cursor.translate(dir);
    stainedHardenedClay().setColor(DyeColor.ORANGE).stroke(editor, cursor);
    LeverBlock.lever().setActive(false).setFacing(dir.reverse()).stroke(editor, lever);

    cursor.translate(dir);
    TorchBlock.redstone().setFacing(dir).stroke(editor, cursor);
    cursor.up(2);
    TorchBlock.redstone().stroke(editor, cursor);
    cursor.up(2);
    start = cursor.copy();
    end = start.copy();
    end.translate(dir.reverse(), 3);
    BlockBrush wire = BlockType.REDSTONE_WIRE.getBrush();
    RectSolid.newRect(start, end).fill(editor, wire);
  }

  private void overheadLight(WorldEditor editor, Theme theme, Coord origin) {

    StairsBlock stair = getPrimaryStairs(theme);

    Coord cursor;

    SingleBlockBrush.AIR.stroke(editor, origin);

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(dir.orthogonals()[0]);
      stair.stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.up();
    BlockType.REDSTONE_LAMP.getBrush().stroke(editor, cursor);
  }


}
