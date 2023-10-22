package greymerk.roguelike.citadel;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class Citadel {

  public static final int EDGE_LENGTH = 17;

  public static void generate(WorldEditor editor, int x, int z) {
    MinimumSpanningTree mst = new MinimumSpanningTree(editor.getRandom(), 7, EDGE_LENGTH);
    new CityGrounds(editor, mst, Theme.OAK).generate(new Coord(x, 50, z));
  }

}
