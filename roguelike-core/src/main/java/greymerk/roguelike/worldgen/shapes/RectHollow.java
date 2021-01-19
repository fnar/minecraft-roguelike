package greymerk.roguelike.worldgen.shapes;

import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class RectHollow implements IShape {

  private Coord start;
  private Coord end;

  public RectHollow(Coord start, Coord end) {
    this.start = start;
    this.end = end;
  }

  public static RectHollow newRect(Coord start, Coord end) {
    return new RectHollow(start, end);
  }

  @Override
  public IShape fill(WorldEditor editor, BlockBrush block, boolean fillAir, boolean replaceSolid) {
    IShape.super.fill(editor, block, fillAir, replaceSolid);

    Coord innerStart = start.copy();
    Coord innerEnd = end.copy();
    Coord.correct(innerStart, innerEnd);
    innerStart.translate(new Coord(1, 1, 1));
    innerEnd.translate(new Coord(-1, -1, -1));
    RectSolid.newRect(innerStart, innerEnd).fill(editor, SingleBlockBrush.AIR);
    return this;
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
    return new RectHollowIterator(start, end);
  }

  private class RectHollowIterator implements Iterator<Coord> {

    Coord cursor;
    Coord c1;
    Coord c2;

    public RectHollowIterator(Coord c1, Coord c2) {
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
        cursor.translate(Direction.UP);
        return toReturn;
      }

      if (cursor.getX() == c2.getX()) {
        cursor = new Coord(c1.getX(), cursor.getY(), cursor.getZ());
        cursor.translate(Direction.SOUTH);
        return toReturn;
      }

      if (
          cursor.getY() != c1.getY()
              && cursor.getY() != c2.getY()
              && cursor.getZ() != c1.getZ()
              && cursor.getZ() != c2.getZ()
              && cursor.getX() == c1.getX()
      ) {
        cursor.translate(Direction.EAST, c2.getX() - c1.getX());
      } else {
        cursor.translate(Direction.EAST);
      }

      return toReturn;

    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
