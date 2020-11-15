package greymerk.roguelike.dungeon.segment;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IAlcove {

  void generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, Cardinal dir);

  boolean isValidLocation(WorldEditor editor, Coord pos, Cardinal dir);

}
