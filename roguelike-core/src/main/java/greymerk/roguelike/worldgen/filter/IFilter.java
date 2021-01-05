package greymerk.roguelike.worldgen.filter;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IFilter {

  void apply(WorldEditor editor, Random rand, ThemeBase theme, IBounded box);

}
