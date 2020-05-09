package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

import static java.lang.Math.max;

public class Sphere implements IShape {

  private Coord start;
  private Coord end;

  public Sphere(Coord start, Coord end) {
    this.start = new Coord(start);
    this.end = new Coord(end);
  }

  @Override
  public Iterator<Coord> iterator() {
    return new SphereIterator(start, end);
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


  private class SphereIterator implements Iterator<Coord> {

    private Coord centre;
    private int radius;

    private int layer;
    private int row;
    private int col;

    private Cardinal dir;
    private boolean top;

    public SphereIterator(Coord centre, Coord end) {
      this.centre = new Coord(centre);
      Coord s = new Coord(centre);
      Coord e = new Coord(end);

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
      dir = Cardinal.NORTH;
    }

    @Override
    public boolean hasNext() {
      return layer < radius;
    }

    @Override
    public Coord next() {
      Coord toReturn = new Coord(centre);
      toReturn.translate(top ? Cardinal.UP : Cardinal.DOWN, layer);
      toReturn.translate(dir, row);
      toReturn.translate(dir.antiClockwise(), col);
      if (dir != Cardinal.NORTH || top) {
        if (dir == Cardinal.NORTH) {
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
