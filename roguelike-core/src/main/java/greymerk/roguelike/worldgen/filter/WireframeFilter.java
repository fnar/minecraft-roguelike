package greymerk.roguelike.worldgen.filter;

import com.github.fnar.minecraft.block.normal.ColoredBlock;

import java.util.Random;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectWireframe;

public class WireframeFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Random rand, Theme theme, IBounded box) {
    Coord start = box.getStart();
    Coord end = box.getEnd();

    start.up(100);
    end.up(100);

    IShape shape = new RectWireframe(start, end);
    BlockBrush block = ColoredBlock.stainedHardenedClay().setColor(DyeColor.RED);

    shape.fill(editor, block);
  }
}
