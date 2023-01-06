package greymerk.roguelike.worldgen.filter;

import com.github.fnar.minecraft.block.decorative.VineBlock;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.Shape;

public class VineFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Theme theme, Bounded box) {
    for (Coord pos : box.getShape(Shape.RECTSOLID)) {
      if (editor.getRandom().nextInt(10) == 0) {
        VineBlock.vine().stroke(editor, pos);
      }
    }
  }
}
