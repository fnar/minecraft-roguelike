package greymerk.roguelike.worldgen;

import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.Shape;

public class BoundingBox implements IBounded {

  private Coord start;
  private Coord end;

  public BoundingBox(Coord start, Coord end) {
    this.start = start.copy();
    this.end = end.copy();

    Coord.correct(this.start, this.end);
  }

  public BoundingBox getBoundingBox() {
    return this;
  }

  public boolean collide(IBounded other) {

    BoundingBox otherBox = other.getBoundingBox();

    if (end.getX() < otherBox.start.getX()
        || otherBox.end.getX() < start.getX()) {
      return false;
    }

    if (end.getY() < otherBox.start.getY()
        || otherBox.end.getY() < start.getY()) {
      return false;
    }

    return end.getZ() >= otherBox.start.getZ()
        && otherBox.end.getZ() >= start.getZ();
  }

  @Override
  public IShape getShape(Shape type) {
    return Shape.get(type, start, end);
  }

  @Override
  public Coord getStart() {
    return start.copy();
  }

  @Override
  public Coord getEnd() {
    return end.copy();
  }
}
