package greymerk.roguelike.worldgen.shapes;

import java.util.List;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IShape extends Iterable<Coord> {

  default void fill(WorldEditor editor, BlockBrush block) {
    fill(editor, block, true, true);
  }

  default void fill(WorldEditor editor, BlockBrush block, boolean fillAir, boolean replaceSolid) {
    for (Coord coord : this) {
      block.stroke(editor, coord, fillAir, replaceSolid);
    }
  }

  List<Coord> get();

}
