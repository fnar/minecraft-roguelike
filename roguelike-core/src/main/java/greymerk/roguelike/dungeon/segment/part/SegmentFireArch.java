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
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFireArch extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush walls = theme.getPrimary().getWall();

    Coord start;
    Coord end;
    Coord cursor;

    Cardinal[] orthogonals = dir.orthogonals();

    start = origin.copy();
    start.translate(dir, 3);
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[0]);
    end.translate(Cardinal.UP, 2);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, walls);
    cursor = origin.copy();
    cursor.translate(dir, 2);
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 2);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Cardinal.DOWN, 2);
    cursor.translate(dir);
    BlockType.NETHERRACK.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.FIRE.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse());
    BlockType.IRON_BAR.getBrush().stroke(editor, cursor);

    for (Cardinal orthogonal : orthogonals) {

      cursor = origin.copy();
      cursor.translate(dir);
      cursor.translate(orthogonal);
      cursor.translate(Cardinal.UP, 2);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    }
  }
}
