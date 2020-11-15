package greymerk.roguelike.worldgen;

import java.util.Random;

public interface IStair extends IBlockFactory {

  MetaStair setOrientation(Cardinal dir, Boolean upsideDown);

  boolean set(WorldEditor editor, Coord pos);

  boolean set(WorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);

}
