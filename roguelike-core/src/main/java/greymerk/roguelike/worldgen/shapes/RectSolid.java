package greymerk.roguelike.worldgen.shapes;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.Coord;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class RectSolid implements IShape, Bounded {

  private final Coord start;
  private final Coord end;

  private RectSolid(Coord start, Coord end) {
    this.start = start.copy();
    this.end = end.copy();
  }

  public static RectSolid newRect(Coord start, Coord end) {
    return new RectSolid(start, end);
  }

  public RectSolid withHeight(int height) {
    Coord lower = start.getY() < end.getY() ? start : end;
    start.setY(lower.getY());
    end.setY(lower.getY() + Math.max(0, height - 1));
    return this;
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
    return new RectSolidIterator(this.start, this.end);
  }

  @Override
  public IShape getShape(Shape type) {
    return this;
  }

  @Override
  public Coord getStart() {
    return start;
  }

  @Override
  public Coord getEnd() {
    return end;
  }

  private static class RectSolidIterator implements Iterator<Coord> {

    Coord cursor;
    Coord start;
    Coord end;

    public RectSolidIterator(Coord start, Coord end) {
      this.start = start.copy();
      this.end = end.copy();

      Coord.correct(this.start, this.end);
      cursor = this.start.copy();
    }

    @Override
    public boolean hasNext() {
      return this.cursor.getY() <= this.end.getY();
    }

    @Override
    public Coord next() {

      Coord toReturn = cursor.copy();

      if (cursor.getZ() == end.getZ() && cursor.getX() == end.getX()) {
        cursor = new Coord(start.getX(), cursor.getY(), start.getZ());
        cursor.up();
        return toReturn;
      }

      if (cursor.getX() == end.getX()) {
        cursor = new Coord(start.getX(), cursor.getY(), cursor.getZ());
        cursor.south();
        return toReturn;
      }

      cursor.east();
      return toReturn;

    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
