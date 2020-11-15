package greymerk.roguelike.worldgen.shapes;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IShape extends Iterable<Coord> {

  void fill(WorldEditor editor, Random rand, IBlockFactory block);

  void fill(WorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid);

  List<Coord> get();

}
