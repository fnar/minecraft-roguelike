package greymerk.roguelike.dungeon.layout.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.layout.DungeonNode;
import greymerk.roguelike.dungeon.layout.DungeonTunnel;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public class Node {

  private final Coord pos;
  private final Direction direction;
  private List<Edge> edges;

  public Node(LayoutGeneratorClassic layoutGeneratorClassic, Coord pos, Direction direction) {
//    System.out.printf("Generating node at %s%n", pos);
    edges = new ArrayList<>();
    this.pos = pos;
    this.direction = direction;

    if (layoutGeneratorClassic.getStart().distance(this.pos) <= layoutGeneratorClassic.getRange()) {
      Direction.CARDINAL.stream()
          .filter(dir -> !dir.equals(this.direction.reverse()))
          .map(dir -> new Edge(layoutGeneratorClassic, this.pos.copy(), dir))
          .forEach(edge -> edges.add(edge));
    }
  }

  public void update(Random random) {
    edges.forEach(edge -> edge.update(random));
  }

  public boolean isDone() {
    return edges.stream().allMatch(Edge::isDone);
  }

  public Coord getPos() {
    return pos.copy();
  }

  public List<Direction> getEntrances() {
    List<Direction> entrances = new ArrayList<>();
    entrances.add(direction.reverse());
    edges.stream().map(Edge::getDir).forEach(entrances::add);
    return entrances;
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public List<DungeonTunnel> createTunnels() {
    return getEdges().stream().map(Edge::asDungeonTunnel).collect(Collectors.toList());
  }

  public DungeonNode asDungeonNode() {
    return new DungeonNode(getEntrances(), pos);
  }

  public void cull() {
    edges = edges.stream().filter(Edge::isDone).collect(Collectors.toList());
  }
}
