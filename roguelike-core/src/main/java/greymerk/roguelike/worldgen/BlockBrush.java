package greymerk.roguelike.worldgen;

import greymerk.roguelike.worldgen.shapes.IShape;

public interface BlockBrush {

  boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid);

  default boolean stroke(WorldEditor editor, Coord pos) {
    return stroke(editor, pos, true, true);
  }

  default void fill(WorldEditor editor, IShape shape) {
    fill(editor, shape, true, true);
  }

  default void fill(WorldEditor editor, IShape shape, boolean fillAir, boolean replaceSolid) {
    shape.fill(editor, this, fillAir, replaceSolid);
  }

  default BlockBrush setFacing(Direction direction) { return this; }

  BlockBrush copy();

}
