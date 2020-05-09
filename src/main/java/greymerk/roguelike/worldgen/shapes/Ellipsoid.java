package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

public class Ellipsoid implements IShape {

  private Coord start;
  private Coord end;

  public Ellipsoid(Coord start, Coord end) {
    this.start = new Coord(start);
    this.end = new Coord(end);
  }

  @Override
  public Iterator<Coord> iterator() {
    return new EllipsoidIterator(start, end);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
    fill(editor, rand, block, true, true);

  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
    for (Coord pos : this) {
      block.set(editor, rand, pos, fillAir, replaceSolid);
    }
  }

  @Override
  public List<Coord> get() {
    List<Coord> copy = new ArrayList<>();
    for (Coord pos : this) {
      copy.add(pos);
    }
    return copy;
  }

  private class EllipsoidIterator implements Iterator<Coord> {

    private Coord centre;
    private Coord diff;
    private Coord cursor;

    private Cardinal dir;
    private boolean top;


    public EllipsoidIterator(Coord centre, Coord end) {
      this.centre = new Coord(centre);
      Coord s = new Coord(centre);
      Coord e = new Coord(end);

      diff = e.translate(-s.getX(), -s.getY(), -s.getZ());
      diff = new Coord(Math.abs(diff.getX()), Math.abs(diff.getY()), Math.abs(diff.getZ()));

      cursor = new Coord(0, 0, 0);
      top = true;
      dir = Cardinal.NORTH;
    }

    @Override
    public boolean hasNext() {
      return cursor.getY() < diff.getY();
    }

    @Override
    public Coord next() {
      Coord toReturn = new Coord(centre);
      toReturn.translate(top ? Cardinal.UP : Cardinal.DOWN, cursor.getY());
      if (dir == Cardinal.NORTH || dir == Cardinal.SOUTH) {
        toReturn.translate(dir.antiClockwise(), cursor.getX());
        toReturn.translate(dir, cursor.getZ());
      } else {
        toReturn.translate(dir, cursor.getX());
        toReturn.translate(dir.antiClockwise(), cursor.getZ());
      }

      if (dir != Cardinal.NORTH || top) {
        if (dir == Cardinal.NORTH) {
          top = false;
        }
        dir = dir.antiClockwise();
        return toReturn;
      }

      cursor.translate(Cardinal.SOUTH);

      if (inRange(cursor)) {
        dir = dir.antiClockwise();
        top = true;
        return toReturn;
      } else {
        cursor = new Coord(cursor.getX(), cursor.getY(), 0);
      }

      cursor.translate(Cardinal.EAST);

      if (inRange(cursor)) {
        dir = dir.antiClockwise();
        top = true;
        return toReturn;
      } else {
        cursor = new Coord(0, cursor.getY(), cursor.getZ());
      }

      cursor.translate(Cardinal.UP);
      dir = dir.antiClockwise();
      top = true;
      return toReturn;
    }

    private boolean inRange(Coord pos) {
      double x = pos.getX();
      double y = pos.getY();
      double z = pos.getZ();

      double rx = diff.getX() == 0 ? 1 : diff.getX();
      double ry = diff.getY() == 0 ? 1 : diff.getY();
      double rz = diff.getZ() == 0 ? 1 : diff.getZ();

      return ((x / rx) * (x / rx)) +
          ((y / ry) * (y / ry)) +
          ((z / rz) * (z / rz))
          <= 1;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
