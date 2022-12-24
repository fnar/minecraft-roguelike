package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewerArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {
    BlockBrush stair = getSecondaryStairs(theme).setUpsideDown(true).setFacing(dir.reverse());

    Direction[] orthogonals = dir.orthogonals();

    Coord cursor = origin.copy();
    cursor.up(3);
    BlockType.COBBLESTONE_MOSSY.getBrush().stroke(editor, cursor, false, true);
    cursor.up();
    BlockType.WATER_FLOWING.getBrush().stroke(editor, cursor, false, true);

    cursor = origin.copy();
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up(1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up(1);
    stair.stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(dir, 2);
    BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
    cursor.up();
    BlockType.IRON_BAR.getBrush().stroke(editor, cursor);

    Coord start = origin.copy();
    start.down();
    Coord end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));
    start.down();
    end.down();
    BlockType.WATER_FLOWING.getBrush().fill(editor, RectSolid.newRect(start, end));

    for (Direction o : orthogonals) {
      cursor = origin.copy();
      cursor.translate(o, 1);
      cursor.translate(dir, 2);
      getSecondaryPillar(theme).stroke(editor, cursor);
      cursor.up(1);
      getSecondaryPillar(theme).stroke(editor, cursor);
      cursor.up(1);
      getPrimaryWalls(theme).stroke(editor, cursor);
      cursor.translate(dir.reverse(), 1);
      stair.stroke(editor, cursor);
    }
  }
}
