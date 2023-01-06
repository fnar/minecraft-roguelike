package greymerk.roguelike.worldgen.filter;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IFilter {

  void apply(WorldEditor editor, Theme theme, Bounded box);

}
