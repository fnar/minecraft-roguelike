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

  default IShape translate(Direction direction) {
    translate(direction, 1);
    return this;
  }

  default IShape up(int amount) {
    return translate(Direction.UP, amount);
  }

  default IShape up() {
    return up(1);
  }

  default IShape down(int amount) {
    return translate(Direction.DOWN, amount);
  }

  default IShape down() {
    return down(1);
  }

  default IShape north(int amount) {
    return translate(Direction.NORTH, amount);
  }

  default IShape north() {
    return north(1);
  }

  default IShape east(int amount) {
    return translate(Direction.EAST, amount);
  }

  default IShape east() {
    return east(1);
  }

  default IShape south(int amount) {
    return translate(Direction.SOUTH, amount);
  }

  default IShape south() {
    return south(1);
  }

  default IShape west(int amount) {
    return translate(Direction.WEST, amount);
  }

  default IShape west() {
    return west(1);
  }

  default List<Coord> asList() {
    List<Coord> coords = Lists.newArrayList();
    for(Coord c : this) {
      coords.add(c);
    }
    return coords;
  }

}
