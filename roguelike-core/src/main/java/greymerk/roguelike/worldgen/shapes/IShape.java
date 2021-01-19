package greymerk.roguelike.worldgen.shapes;

import java.util.List;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IShape extends Iterable<Coord> {

  default IShape fill(WorldEditor editor, BlockBrush block) {
    return fill(editor, block, true, true);
  }

  default IShape fill(WorldEditor editor, BlockBrush block, boolean fillAir, boolean replaceSolid) {
    for (Coord coord : this) {
      block.stroke(editor, coord, fillAir, replaceSolid);
    }
    return this;
  }

  List<Coord> get();

}
