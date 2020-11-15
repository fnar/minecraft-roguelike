package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface ITower {

  void generate(WorldEditor editor, Random rand, ITheme theme, Coord origin);

}
