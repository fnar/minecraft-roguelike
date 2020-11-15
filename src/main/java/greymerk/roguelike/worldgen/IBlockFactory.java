package greymerk.roguelike.worldgen;

import java.util.Random;

import greymerk.roguelike.worldgen.shapes.IShape;

public interface IBlockFactory {

  boolean set(WorldEditor editor, Random rand, Coord pos);

  boolean set(WorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);

  void fill(WorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid);

  void fill(WorldEditor editor, Random rand, IShape shape);

}
