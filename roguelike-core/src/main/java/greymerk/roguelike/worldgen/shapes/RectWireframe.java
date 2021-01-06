package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

public class RectWireframe implements IShape {

  private final Coord start;
  private final Coord end;

  public RectWireframe(Coord start, Coord end) {
    this.start = start;
    this.end = end;
  }

  public static RectWireframe newRect(Coord start, Coord end) {
    return new RectWireframe(start, end);
  }

  @Override
  public List<Coord> get() {
    List<Coord> coords = new ArrayList<Coord>();

    for (Coord c : this) {
      coords.add(c);
    }

    return coords;
  }

  @Override
  public Iterator<Coord> iterator() {
    return new RectWireframeIterator(start, end);
  }

  private class RectWireframeIterator implements Iterator<Coord> {

    Coord cursor;
    Coord c1;
    Coord c2;

    public RectWireframeIterator(Coord c1, Coord c2) {
      this.c1 = c1.copy();
      this.c2 = c2.copy();

      Coord.correct(this.c1, this.c2);
      cursor = c1.copy();
    }

    @Override
    public boolean hasNext() {
      return this.cursor.getY() <= this.c2.getY();
    }

    @Override
    public Coord next() {

      Coord toReturn = cursor.copy();

      if (cursor.getZ() == c2.getZ() && cursor.getX() == c2.getX()) {
        cursor = new Coord(c1.getX(), cursor.getY(), c1.getZ());
        cursor.translate(Cardinal.UP);
        return toReturn;
      }

      if (cursor.getY() == c1.getY() || cursor.getY() == c2.getY()) {

        if (cursor.getX() == c2.getX()) {
          cursor = new Coord(c1.getX(), cursor.getY(), cursor.getZ());
          cursor.translate(Cardinal.SOUTH);
          return toReturn;
        }

        if (cursor.getZ() == c1.getZ() || cursor.getZ() == c2.getZ()) {
          cursor.translate(Cardinal.EAST);
          return toReturn;
        }

        if (cursor.getX() == c1.getX()) {
          cursor = new Coord(c2.getX(), cursor.getY(), cursor.getZ());
          return toReturn;
        }


      }

      if (cursor.getX() == c1.getX()) {
        cursor = new Coord(c2.getX(), cursor.getY(), cursor.getZ());
        return toReturn;
      }

      if (cursor.getX() == c2.getX()) {
        cursor = new Coord(c1.getX(), cursor.getY(), c2.getZ());
        return toReturn;
      }

      cursor = new Coord(c2.getX(), cursor.getY(), cursor.getZ());
      return toReturn;

    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
