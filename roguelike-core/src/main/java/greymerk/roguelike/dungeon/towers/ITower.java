package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface ITower {

  void generate(WorldEditor editor, Random rand, Theme theme, Coord origin);

}
