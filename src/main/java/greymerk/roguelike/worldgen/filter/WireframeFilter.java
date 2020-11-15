package greymerk.roguelike.worldgen.filter;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectWireframe;

public class WireframeFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Random rand, ITheme theme, IBounded box) {
    Coord start = box.getStart();
    Coord end = box.getEnd();

    start.translate(Cardinal.UP, 100);
    end.translate(Cardinal.UP, 100);

    IShape shape = new RectWireframe(start, end);
    IBlockFactory block = BlockType.get(BlockType.SEA_LANTERN);

    shape.fill(editor, rand, block);
  }
}
