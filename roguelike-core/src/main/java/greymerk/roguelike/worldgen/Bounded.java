package greymerk.roguelike.worldgen;

import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.Shape;

public interface Bounded {

  default boolean collide(Bounded other) {
    if (getEnd().getX() < other.getStart().getX()
        || other.getEnd().getX() < getStart().getX()) {
      return false;
    }

    if (getEnd().getY() < other.getStart().getY()
        || other.getEnd().getY() < getStart().getY()) {
      return false;
    }

    return getEnd().getZ() >= other.getStart().getZ()
        && other.getEnd().getZ() >= getStart().getZ();
  }

  IShape getShape(Shape type);

  Coord getStart();

  Coord getEnd();

}
