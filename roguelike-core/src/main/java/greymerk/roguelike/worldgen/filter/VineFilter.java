package greymerk.roguelike.worldgen.filter;

import com.github.srwaggon.roguelike.worldgen.block.decorative.VineBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.Shape;

public class VineFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Random rand, ThemeBase theme, IBounded box) {
    for (Coord pos : box.getShape(Shape.RECTSOLID)) {
      if (rand.nextInt(10) == 0) {
        VineBlock.vine().stroke(editor, pos);
      }
    }
  }
}
