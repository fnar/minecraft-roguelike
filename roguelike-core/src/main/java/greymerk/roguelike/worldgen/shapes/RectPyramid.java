package greymerk.roguelike.worldgen.shapes;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;

public class RectPyramid implements IShape {

  private Coord start;
  private Coord end;

  public RectPyramid(Coord start, Coord end) {
    this.start = start.copy();
    this.end = end.copy();
  }

  @Override
  public Iterator<Coord> iterator() {
    return new SquarePyramidIterator(start, end);
  }

  @Override
  public List<Coord> get() {
    List<Coord> shape = new ArrayList<>();
    for (Coord pos : this) {
      shape.add(pos);
    }
    return shape;
  }

  @Override
  public Set<Coord> getAnchors() {
    return Sets.newHashSet(start, end);
  }

  private class SquarePyramidIterator implements Iterator<Coord> {

    Coord start;
    Coord diff;
    Coord cursor;
    Direction dir;
    double thetaX;
    double thetaZ;

    public SquarePyramidIterator(Coord start, Coord end) {
      this.start = start.copy();
      Coord s = start.copy();
      Coord e = end.copy();
      Coord.correct(s, e);

      cursor = new Coord(0, 0, 0);
      dir = Direction.NORTH;

      diff = e.copy();
      diff.translate(-s.getX(), -s.getY(), -s.getZ());

      double hx = Math.sqrt(Math.pow(diff.getX(), 2) + Math.pow(diff.getY(), 2));
      thetaX = Math.acos((double) diff.getY() / hx);

      double hz = Math.sqrt(Math.pow(diff.getZ(), 2) + Math.pow(diff.getY(), 2));
      thetaZ = Math.acos((double) diff.getY() / hz);
    }

    @Override
    public boolean hasNext() {
      return cursor.getY() < diff.getY();
    }

    @Override
    public Coord next() {

      Coord toReturn = start.copy();
      toReturn.up(cursor.getY());
      if (dir == Direction.NORTH || dir == Direction.SOUTH) {
        toReturn.translate(dir.antiClockwise(), cursor.getX());
        toReturn.translate(dir, cursor.getZ());
      } else {
        toReturn.translate(dir, cursor.getX());
        toReturn.translate(dir.antiClockwise(), cursor.getZ());
      }

      if (dir != Direction.NORTH) {
        dir = dir.antiClockwise();
        return toReturn;
      }

      cursor.south();

      if (inRange(cursor)) {
        dir = dir.antiClockwise();
        return toReturn;
      }

      cursor = new Coord(cursor.getX(), cursor.getY(), 0);


      cursor.east();

      if (inRange(cursor)) {
        dir = dir.antiClockwise();
        return toReturn;
      }

      cursor = new Coord(0, cursor.getY(), cursor.getZ());
      cursor.up();
      dir = dir.antiClockwise();
      return toReturn;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    private boolean inRange(Coord pos) {
      int y = diff.getY() - cursor.getY();

      if (!(cursor.getX() < Math.tan(thetaX) * y)) {
        return false;
      }

      return cursor.getZ() < Math.tan(thetaZ) * y;

    }

  }

}
