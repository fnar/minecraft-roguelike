package greymerk.roguelike.worldgen;

import java.util.Collection;

import greymerk.roguelike.worldgen.shapes.IShape;

public interface BlockBrush {

  boolean FILL_AIR_DEFAULT = true;
  boolean REPLACE_SOLID_DEFAULT = true;

  boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid);

  default boolean stroke(WorldEditor editor, Coord pos) {
    return stroke(editor, pos, FILL_AIR_DEFAULT, REPLACE_SOLID_DEFAULT);
  }

  default IShape fill(WorldEditor editor, IShape shape) {
    return fill(editor, shape, FILL_AIR_DEFAULT, REPLACE_SOLID_DEFAULT);
  }

  default IShape fill(WorldEditor editor, IShape shape, boolean fillAir, boolean replaceSolid) {
    return shape.fill(editor, this, fillAir, replaceSolid);
  }

  default void fill(WorldEditor editor, Collection<Coord> coords, boolean fillAir, boolean replaceSolid) {
    coords.forEach(coord -> stroke(editor, coord, fillAir, replaceSolid));
  }

  default void fill(WorldEditor editor, Collection<Coord> coords) {
    fill(editor, coords, FILL_AIR_DEFAULT, REPLACE_SOLID_DEFAULT);
  }

  default BlockBrush setFacing(Direction direction) {
    return this;
  }

  BlockBrush copy();

}
