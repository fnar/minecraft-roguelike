package greymerk.roguelike.worldgen.shapes;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

  @Override
  public Iterator<Coord> iterator() {
    return shape.iterator();
  }

  @Override
  public List<Coord> get() {
    return shape.stream()
        .map(Coord::copy)
        .collect(toList());
  }

  @Override
  public Set<Coord> getAnchors() {
    return Sets.newHashSet(shape);
  }
}
