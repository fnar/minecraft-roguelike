package greymerk.roguelike.worldgen;

import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.Shape;

public interface Bounded {

  BoundingBox getBoundingBox();

  boolean collide(Bounded other);

  IShape getShape(Shape type);

  Coord getStart();

  Coord getEnd();

}
