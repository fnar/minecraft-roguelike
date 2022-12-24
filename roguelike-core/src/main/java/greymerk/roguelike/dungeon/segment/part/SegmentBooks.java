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

public class SegmentBooks extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction outward, Theme theme, Coord origin) {
    StairsBlock stair = getSecondaryStairs(theme);

    Direction[] orthogonals = outward.orthogonals();

    Coord cursor = origin.copy();
    cursor.translate(outward, 2);
    Coord start = cursor.copy();
    start.translate(orthogonals[0], 1);
    Coord end = cursor.copy();
    end.translate(orthogonals[1], 1);
    end.up(2);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    generateSecret(level.getSettings().getSecrets(), editor, level.getSettings(), outward, origin.copy());

    start.translate(outward, 1);
    end.translate(outward, 1);
    RectSolid.newRect(start, end).fill(editor, getSecondaryWall(theme), false, true);

    cursor.up(2);
    for (Direction d : orthogonals) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }

    cursor = origin.copy();
    cursor.translate(outward, 3);
    BlockType.BOOKSHELF.getBrush().stroke(editor, cursor);
    cursor.up();
    BlockType.BOOKSHELF.getBrush().stroke(editor, cursor);
  }
}
