package greymerk.roguelike.dungeon.layout.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.layout.DungeonNode;
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
  private List<Node> graphNodes;

  public LayoutGeneratorClassic(int numRooms, int scatter, int range) {
    layout = new LevelLayout();
    this.numRooms = numRooms;
    this.scatter = scatter;
    this.range = range;
  }

  public LevelLayout generate(Coord start, Random random) {
    this.setStart(start);
    Node startNode = new Node(this, start, Direction.randomCardinal(random));
    List<Node> gNodes = generateGraph(startNode, random);

    return generateLayoutFromGraph(gNodes, startNode, random);
  }

  private List<Node> generateGraph(Node startNode, Random random) {
    graphNodes = new ArrayList<>();
    graphNodes.add(startNode);

    while (!isDone(graphNodes)) {
      update(graphNodes, random);
    }

    graphNodes.forEach(Node::cull);

    return graphNodes;
  }

  private LevelLayout generateLayoutFromGraph(List<Node> graphNodes, Node startNode, Random random) {
    DungeonNode startDungeonNode = null;

    for (Node node : graphNodes) {
      DungeonNode dungeonNode = node.asDungeonNode();
      if (node == startNode) {
        startDungeonNode = dungeonNode;
      }
      layout.addNode(dungeonNode);
      layout.addTunnels(node.createTunnels());
    }

    layout.setStartEnd(random, startDungeonNode);

    return layout;
  }

  public void update(List<Node> nodes, Random random) {
    if (!isFull(nodes)) {
      for (int i = 0; i < nodes.size(); i++) {
        nodes.get(i).update(random);
      }
    }
  }

  private boolean isDone(List<Node> nodes) {
    return nodes.stream().allMatch(Node::isDone) || isFull(nodes);
  }

  private boolean isFull(List<Node> nodes) {
    return nodes.size() >= Math.max(numRooms, MIN_ROOMS);
  }

  void createNode(Edge edge) {
    graphNodes.add(new Node(this, edge.getEnd(), edge.getDirection()));
  }

  boolean hasNodeNearby(Coord coord) {
    return graphNodes.stream()
        .map(Node::getPos)
        .mapToInt(coord::distanceAsInt)
        .anyMatch(distance -> distance < scatter);
  }

  @Override
  public LevelLayout getLayout() {
    return layout;
  }

  public int getScatter() {
    return scatter;
  }

  public Coord getStart() {
    return start;
  }

  public void setStart(Coord start) {
    this.start = start;
  }

  public int getRange() {
    return range;
  }

}
