package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.TallPlant;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentPlant extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    Coord cursor = origin.copy().translate(dir, 2);
    Coord start = cursor.copy().translate(dir.left());
    Coord end = cursor.copy().translate(dir.right()).up(2);
    RectSolid wall = RectSolid.newRect(start, end);
    SingleBlockBrush.AIR.fill(editor, wall);

    wall.translate(dir, 1);
    getSecondaryWall(theme).fill(editor, wall, false, true);

    cursor.up(2);
    for (Direction d : dir.orthogonals()) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      getSecondaryStairs(theme).setUpsideDown(true).setFacing(d.reverse()).stroke(editor, c);
    }

    cursor = origin.copy();
    cursor.translate(dir, 2);
    TallPlant.placePlant(editor, cursor, dir.reverse());
  }

}
