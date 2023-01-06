package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;

public interface ILevelGenerator {

  LevelLayout generate(Coord start, Random random);

  LevelLayout getLayout();

}
