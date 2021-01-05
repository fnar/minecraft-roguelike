package greymerk.roguelike.worldgen.shapes;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import greymerk.roguelike.worldgen.Coord;

import static java.util.stream.Collectors.toList;

public class MultiShape implements IShape {

  private Set<Coord> shape;

  public MultiShape() {
    shape = new HashSet<>();
  }

  public void addShape(IShape toAdd) {
    for (Coord pos : toAdd) {
      shape.add(pos);
    }
  }

  @Nonnull
  @Override
  public Iterator<Coord> iterator() {
    return shape.iterator();
  }

  @Override
  public List<Coord> get() {
    return shape.stream()
        .map(Coord::new)
        .collect(toList());
  }
}
