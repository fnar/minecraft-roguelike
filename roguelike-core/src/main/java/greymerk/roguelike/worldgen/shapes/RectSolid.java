package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class RectSolid implements IShape {

  private final Coord start;
  private final Coord end;

  public RectSolid(Coord start, Coord end) {
    this.start = start;
    this.end = end;
  }

  // todo: inline?
  public static void fill(WorldEditor editor, Coord start, Coord end, BlockBrush block) {
    fill(editor, start, end, block, true, true);
  }

  // todo: inline?
  public static void fill(WorldEditor editor, Coord start, Coord end, BlockBrush block, boolean fillAir, boolean replaceSolid) {
    block.fill(editor, new RectSolid(start, end), fillAir, replaceSolid);
  }

  @Override
  public List<Coord> get() {
    List<Coord> coords = new ArrayList<>();

    for (Coord c : this) {
      coords.add(c);
    }

    return coords;
  }

  @Nonnull
  @Override
  public Iterator<Coord> iterator() {
    return new RectSolidIterator(this.start, this.end);
  }

  private class RectSolidIterator implements Iterator<Coord> {

    Coord cursor;
    Coord c1;
    Coord c2;

    public RectSolidIterator(Coord c1, Coord c2) {
      this.c1 = new Coord(c1);
      this.c2 = new Coord(c2);

      Coord.correct(this.c1, this.c2);
      cursor = new Coord(this.c1);
    }

    @Override
    public boolean hasNext() {
      return this.cursor.getY() <= this.c2.getY();
    }

    @Override
    public Coord next() {

      Coord toReturn = new Coord(cursor);

      if (cursor.getZ() == c2.getZ() && cursor.getX() == c2.getX()) {
        cursor = new Coord(c1.getX(), cursor.getY(), c1.getZ());
        cursor.translate(Cardinal.UP);
        return toReturn;
      }

      if (cursor.getX() == c2.getX()) {
        cursor = new Coord(c1.getX(), cursor.getY(), cursor.getZ());
        cursor.translate(Cardinal.SOUTH);
        return toReturn;
      }

      cursor.translate(Cardinal.EAST);
      return toReturn;

    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
