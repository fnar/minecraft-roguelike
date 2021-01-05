package greymerk.roguelike.worldgen.filter;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectWireframe;

public class WireframeFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Random rand, ThemeBase theme, IBounded box) {
    Coord start = box.getStart();
    Coord end = box.getEnd();

    start.translate(Cardinal.UP, 100);
    end.translate(Cardinal.UP, 100);

    IShape shape = new RectWireframe(start, end);
    BlockBrush block = BlockType.SEA_LANTERN.getBrush();

    shape.fill(editor, block);
  }
}
