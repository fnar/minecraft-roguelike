package greymerk.roguelike.worldgen.shapes;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
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

  Set<Coord> getAnchors();

  default IShape translate(Direction direction, int amount) {
    getAnchors().forEach(coord -> coord.translate(direction, amount));
    return this;
  }

  default List<Coord> asList() {
    List<Coord> coords = Lists.newArrayList();
    for(Coord c : this) {
      coords.add(c);
    }
    return coords;
  }

}
