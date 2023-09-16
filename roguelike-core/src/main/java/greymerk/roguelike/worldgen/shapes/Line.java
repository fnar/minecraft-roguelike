package greymerk.roguelike.worldgen.shapes;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import greymerk.roguelike.worldgen.Coord;

public class Line implements IShape {

  Coord start;
  Coord end;

  public Line(Coord start, Coord end) {
    this.start = start.copy();
    this.end = end.copy();
  }

  @Override
  public List<Coord> get() {
    List<Coord> coords = new ArrayList<>();
    for (Coord c : this) {
      coords.add(c);
    }
    return coords;
  }

  @Override
  public Set<Coord> getAnchors() {
    return Sets.newHashSet(start, end);
  }

  @Override
  public Iterator<Coord> iterator() {
    return new LineIterator();
  }

  private class LineIterator implements Iterator<Coord> {

    int x;
    int y;
    int z;
    int dx;
    int dy;
    int dz;
    int absDx;
    int absDy;
    int absDz;
    int x_inc;
    int y_inc;
    int z_inc;
    int err_1;
    int err_2;
    int dx2;
    int dy2;
    int dz2;
    int i;

    public LineIterator() {

      Coord point = start.copy();
      x = point.getX();
      y = point.getY();
      z = point.getZ();
      dx = end.getX() - start.getX();
      dy = end.getY() - start.getY();
      dz = end.getZ() - start.getZ();

      x_inc = dx < 0 ? -1 : 1;
      absDx = Math.abs(dx);

      y_inc = dy < 0 ? -1 : 1;
      absDy = Math.abs(dy);

      z_inc = dz < 0 ? -1 : 1;
      absDz = Math.abs(dz);

      dx2 = absDx << 1;
      dy2 = absDy << 1;
      dz2 = absDz << 1;
      i = 0;

      if (absDx >= absDy && absDx >= absDz) {
        err_1 = dy2 - absDx;
        err_2 = dz2 - absDx;
      } else if (absDy >= absDx && absDy >= absDz) {
        err_1 = dx2 - absDy;
        err_2 = dz2 - absDy;
      } else {
        err_1 = dy2 - absDz;
        err_2 = dz2 - absDz;
      }

    }

    @Override
    public boolean hasNext() {
      if (absDx >= absDy && absDx >= absDz) {
        return i < absDx;
      } else if (absDy >= absDx && absDy >= absDz) {
        return i < absDy;
      } else {
        return i < absDz;
      }
    }

    @Override
    public Coord next() {
      if (absDx >= absDy && absDx >= absDz) {
        if (err_1 > 0) {
          y += y_inc;
          err_1 -= dx2;
        }
        if (err_2 > 0) {
          z += z_inc;
          err_2 -= dx2;
        }
        err_1 += dy2;
        err_2 += dz2;
        x += x_inc;
      } else if (absDy >= absDx && absDy >= absDz) {
        if (err_1 > 0) {
          x += x_inc;
          err_1 -= dy2;
        }
        if (err_2 > 0) {
          z += z_inc;
          err_2 -= dy2;
        }
        err_1 += dx2;
        err_2 += dz2;
        y += y_inc;
      } else {
        if (err_1 > 0) {
          y += y_inc;
          err_1 -= dz2;
        }
        if (err_2 > 0) {
          x += x_inc;
          err_2 -= dz2;
        }
        err_1 += dy2;
        err_2 += dx2;
        z += z_inc;
      }

      ++i;

      return new Coord(x, y, z);
    }
  }

}
