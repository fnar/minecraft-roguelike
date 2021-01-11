package greymerk.roguelike.worldgen.filter;

import com.github.srwaggon.minecraft.block.BlockType;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.Shape;

public class CobwebFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Random rand, ThemeBase theme, IBounded box) {
    for (Coord pos : box.getShape(Shape.RECTSOLID)) {
      if (rand.nextInt(60) != 0) {
        continue;
      }
      if (!editor.isAirBlock(pos)) {
        continue;
      }
      if (!validLocation(editor, pos)) {
        continue;
      }

      generate(editor, rand, pos, rand.nextInt(2) + 2);
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

  private void generate(WorldEditor editor, Random rand, Coord pos, int count) {
    if (!editor.isAirBlock(pos)) {
      return;
    }
    if (count <= 0) {
      return;
    }

    BlockType.WEB.getBrush().stroke(editor, pos);

    for (int i = 0; i < 2; ++i) {
      Direction dir = Direction.values()[rand.nextInt(Direction.values().length)];
      Coord cursor = pos.copy();
      cursor.translate(dir);
      generate(editor, rand, cursor, count - 1);
    }
  }
}
