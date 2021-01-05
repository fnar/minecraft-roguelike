package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.redstone.LeverBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.TorchBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedHardenedClay;

public class SegmentLamp extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush wall = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orthogonal = dir.orthogonals();

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 3);
    end = new Coord(start);
    start.translate(dir);
    start.translate(orthogonal[0]);
    end.translate(dir.reverse());
    end.translate(orthogonal[1]);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(dir, 3);
    end = new Coord(start);
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end.translate(dir, 2);
    end.translate(Cardinal.UP, 6);
    RectSolid.fill(editor, start, end, wall);
    start = new Coord(end);
    start.translate(Cardinal.DOWN, 2);
    start.translate(dir.reverse(), 6);
    start.translate(orthogonal[0], 2);
    RectSolid.fill(editor, start, end, wall);

    for (Cardinal side : orthogonal) {

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 2);
      stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    overheadLight(editor, theme, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir, 2);

    Coord lever = new Coord(cursor);
    cursor.translate(dir);
    stainedHardenedClay().setColor(DyeColor.ORANGE).stroke(editor, cursor);
    LeverBlock.lever().setActive(false).setFacing(dir.reverse()).stroke(editor, lever);

    cursor.translate(dir);
    TorchBlock.redstone().setFacing(dir).stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 2);
    TorchBlock.redstone().setFacing(Cardinal.UP).stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 2);
    start = new Coord(cursor);
    end = new Coord(start);
    end.translate(dir.reverse(), 3);
    BlockBrush wire = BlockType.REDSTONE_WIRE.getBrush();
    RectSolid.fill(editor, start, end, wire);
  }

  private void overheadLight(WorldEditor editor, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getPrimary().getStair();

    Coord cursor;

    SingleBlockBrush.AIR.stroke(editor, origin);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(dir.orthogonals()[0]);
      stair.stroke(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    BlockType.REDSTONE_LAMP.getBrush().stroke(editor, cursor);
  }


}
