package greymerk.roguelike.dungeon.layout.classic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.layout.DungeonNode;
import greymerk.roguelike.dungeon.layout.DungeonTunnel;
import greymerk.roguelike.dungeon.layout.LayoutGenerator;
import greymerk.roguelike.dungeon.layout.LevelLayout;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public class LayoutGeneratorClassic implements LayoutGenerator {

  private static final int MIN_ROOMS = 6;

  private final LevelLayout layout;
  private Coord start;
  private final int numRooms;
  private final int scatter;
  private final int range;
  private final Map<Node, Set<Edge>> graph = new HashMap<>();

  public LayoutGeneratorClassic(int numRooms, int scatter, int range) {
    layout = new LevelLayout();
    this.numRooms = numRooms;
    this.scatter = scatter;
    this.range = range;
  }

  public LevelLayout generate(Coord start, Random random) {
    this.start = start;
    Node node = createNode(start, Direction.randomCardinal(random));

    while (!isDone()) {
      update(random);
    }

    getNodes().forEach(this::cull);

    return generateLayoutFromGraph(node, random);
  }

  private boolean isDone(Node node) {
    return getEdges(node).stream().allMatch(Edge::isDone);
  }

  private List<DungeonTunnel> createTunnels(Node node) {
    return getEdges(node).stream().map(Edge::asDungeonTunnel).collect(Collectors.toList());
  }

  private Set<Node> getNodes() {
    return graph.keySet();
  }

  private Set<Edge> getEdges(Node node) {
    return graph.get(node);
  }

  private Node createNode(Coord coord, Direction direction) {
    Node node = new Node(coord, direction);
    graph.put(node, new HashSet<>());

    if (start.distance(coord) <= range) {
      Direction.cardinals().stream()
          .filter(dir -> !dir.equals(direction.reverse()))
          .map(dir -> new Edge(coord.copy(), dir))
          .forEach(edge -> graph.get(node).add(edge));
    }

    return node;
  }

  private LevelLayout generateLayoutFromGraph(Node startNode, Random random) {
    DungeonNode startDungeonNode = null;

    for (Node node : getNodes()) {
      DungeonNode dungeonNode = asDungeonNode(node);
      if (node == startNode) {
        startDungeonNode = dungeonNode;
      }
      layout.addNode(dungeonNode);
      layout.addTunnels(createTunnels(node));
    }

    layout.setStartEnd(random, startDungeonNode);

    return layout;
  }

  private DungeonNode asDungeonNode(Node node) {
    return new DungeonNode(getEntrances(node), node.getCoord());
  }

  private List<Direction> getEntrances(Node node) {
    List<Direction> entrances = new ArrayList<>();
    entrances.add(node.getDirection().reverse());
    getEdges(node).stream().map(Edge::getDir).forEach(entrances::add);
    return entrances;
  }

  public void update(Random random) {
    if (isFull()) {
      return;
    }
    defeatConcurrentModification().forEach(node -> updateNode(random, node));
  }

  private HashSet<Node> defeatConcurrentModification() {
    return new HashSet<>(getNodes());
  }

  private boolean isDone() {
    return getNodes().stream().allMatch(this::isDone) || isFull();
  }

  private boolean isFull() {
    return getNodes().size() >= Math.max(numRooms, MIN_ROOMS);
  }

  public void updateNode(Random random, Node node) {
    getEdges(node).forEach(edge -> updateEdge(random, edge));
  }

  public void updateEdge(Random random, Edge edge) {
    if (edge.isDone()) {
      return;
    }

    if (hasNodeNearby(edge.getEnd())) {
      edge.getEnd().translate(edge.getDir(), scatter);
    } else if (random.nextInt(1 + edge.getExtensionsRemaining()) > 0) {
      edge.getEnd().translate(edge.getDir(), scatter);
      edge.setExtensionsRemaining(edge.getExtensionsRemaining() - 1);
    } else {
      createNode(edge.getEnd().copy(), edge.getDir());
      edge.setDone(true);
    }
  }

  private boolean hasNodeNearby(Coord coord) {
    return getNodes().stream()
        .map(Node::getCoord)
        .mapToInt(coord::distanceAsInt)
        .anyMatch(distance -> distance < scatter);
  }

  private void cull(Node node) {
    graph.put(node, graph.get(node).stream().filter(Edge::isDone).collect(Collectors.toSet()));
  }

  @Override
  public LevelLayout getLayout() {
    return layout;
  }

}
