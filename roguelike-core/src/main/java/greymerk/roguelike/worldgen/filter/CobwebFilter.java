package greymerk.roguelike.worldgen.filter;

import com.github.fnar.minecraft.block.BlockType;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.Shape;

public class CobwebFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Theme theme, Bounded box) {
    for (Coord pos : box.getShape(Shape.RECTSOLID)) {
      if (editor.getRandom().nextInt(60) != 0) {
        continue;
      }
      if (!editor.isAirBlock(pos)) {
        continue;
      }
      if (!validLocation(editor, pos)) {
        continue;
      }

      generate(editor, pos, editor.getRandom().nextInt(2) + 2);
    }
  }

  private boolean validLocation(WorldEditor editor, Coord pos) {
    for (Direction dir : Direction.values()) {
      Coord cursor = pos.copy();
      cursor.translate(dir);
      if (!editor.isAirBlock(cursor)) {
        return true;
      }
    }
    return false;
  }

  private void generate(WorldEditor editor, Coord pos, int count) {
    if (!editor.isAirBlock(pos)) {
      return;
    }
    if (count <= 0) {
      return;
    }

    BlockType.COBWEB.getBrush().stroke(editor, pos);

    for (int i = 0; i < 2; ++i) {
      Direction dir = Direction.values()[editor.getRandom().nextInt(Direction.values().length)];
      Coord cursor = pos.copy();
      cursor.translate(dir);
      generate(editor, cursor, count - 1);
    }
  }
}
