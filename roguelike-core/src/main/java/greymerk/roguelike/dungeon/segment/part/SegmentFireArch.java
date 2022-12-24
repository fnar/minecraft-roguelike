package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFireArch extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    StairsBlock stair = getPrimaryStairs(theme);
    BlockBrush walls = getPrimaryWalls(theme);

    Direction[] orthogonals = dir.orthogonals();

    Coord start = origin.copy();
    start.translate(dir, 3);
    Coord end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[0]);
    end.up(2);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, walls);
    Coord cursor = origin.copy();
    cursor.translate(dir, 2);
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.up(2);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.down(2);
    cursor.translate(dir);
    BlockType.NETHERRACK.getBrush().stroke(editor, cursor);
    cursor.up();
    BlockType.FIRE.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse());
    BlockType.IRON_BAR.getBrush().stroke(editor, cursor);

    for (Direction orthogonal : orthogonals) {

      cursor = origin.copy();
      cursor.translate(dir);
      cursor.translate(orthogonal);
      cursor.up(2);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    }
  }
}
