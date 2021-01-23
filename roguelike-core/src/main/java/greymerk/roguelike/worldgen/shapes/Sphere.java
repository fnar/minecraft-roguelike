package greymerk.roguelike.worldgen.shapes;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;

import static java.lang.Math.max;

public class Sphere implements IShape {

  private final Coord start;
  private final Coord end;

  public Sphere(Coord start, Coord end) {
    this.start = start.copy();
    this.end = end.copy();
  }

  @Nonnull
  @Override
  public Iterator<Coord> iterator() {
    return new SphereIterator(start, end);
  }

  @Override
  public List<Coord> get() {
    List<Coord> copy = new ArrayList<>();
    for (Coord pos : this) {
      copy.add(pos);
    }
    return copy;
  }

  @Override
  public Set<Coord> getAnchors() {
    return Sets.newHashSet(start, end);
  }

  private static class SphereIterator implements Iterator<Coord> {

    private final Coord centre;
    private final int radius;

    private int layer;
    private int row;
    private int col;

    private Direction dir;
    private boolean top;

    public SphereIterator(Coord centre, Coord end) {
      this.centre = centre.copy();
      Coord s = centre.copy();
      Coord e = end.copy();

      Coord.correct(s, e);
      Coord diff = e.translate(-s.getX(), -s.getY(), -s.getZ());

      int radius = diff.getX();
      radius = max(radius, diff.getY());
      radius = max(radius, diff.getZ());
      this.radius = radius;

      layer = 0;
      row = 0;
      col = 0;
      top = true;
      dir = Direction.NORTH;
    }

    @Override
    public boolean hasNext() {
      return layer < radius;
    }

    @Override
    public Coord next() {
      Coord toReturn = centre.copy();
      toReturn.translate(top ? Direction.UP : Direction.DOWN, layer);
      toReturn.translate(dir, row);
      toReturn.translate(dir.antiClockwise(), col);
      if (dir != Direction.NORTH || top) {
        if (dir == Direction.NORTH) {
          top = false;
        }
        dir = dir.antiClockwise();
        return toReturn;
      }

      col += 1;

      if (inRange(col, layer, row)) {
        dir = dir.antiClockwise();
        top = true;
        return toReturn;
      } else {
        col = 0;
      }

      row += 1;

      if (inRange(col, layer, row)) {
        dir = dir.antiClockwise();
        top = true;
        return toReturn;
      } else {
        row = 0;
      }

      layer += 1;
      dir = dir.antiClockwise();
      top = true;
      return toReturn;
    }

    private boolean inRange(int x, int y, int z) {
      return x * x + y * y + z * z < radius * radius;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
