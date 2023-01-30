package greymerk.roguelike.dungeon.layout.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.layout.DungeonNode;
import greymerk.roguelike.dungeon.layout.LayoutGenerator;
import greymerk.roguelike.dungeon.layout.LevelLayout;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;

public class LayoutGeneratorClassic implements LayoutGenerator {

  private static final int MIN_ROOMS = 6;

  private final LevelLayout layout;
  private Coord start;
  private final int numRooms;
  private final int scatter;
  private final int range;

  public LayoutGeneratorClassic(int numRooms, int scatter, int range) {
    layout = new LevelLayout();
    this.numRooms = numRooms;
    this.scatter = scatter;
    this.range = range;
  }

  public LevelLayout generate(Coord start, Random random) {
    this.setStart(start);
    List<Node> gNodes = new ArrayList<>();
    Node startNode = new Node(this, Direction.randomCardinal(random), start);
    gNodes.add(startNode);

    while (!isDone(gNodes)) {
      update(gNodes, random);
    }

    for (Node n : gNodes) {
      n.cull();
    }

    DungeonNode startDungeonNode = null;

    for (Node n : gNodes) {
      DungeonNode nToAdd = n.createNode();
      if (n == startNode) {
        startDungeonNode = nToAdd;
      }
      layout.addNode(nToAdd);
      layout.addTunnels(n.createTunnels());
    }

    layout.setStartEnd(random, startDungeonNode);

    return layout;
  }

  public void update(List<Node> nodes, Random random) {
    if (!full(nodes)) {
      for (int i = 0; i < nodes.size(); i++) {
        nodes.get(i).update(nodes, random);
      }
    }
  }

  private boolean isDone(List<Node> nodes) {
    boolean allDone = true;

    for (Node node : nodes) {
      if (!node.isDone()) {
        allDone = false;
      }
    }

    return allDone || full(nodes);
  }

  private boolean full(List<Node> nodes) {
    return nodes.size() >= Math.max(numRooms, MIN_ROOMS);
  }

  public void spawnNode(List<Node> nodes, Tunneler tunneler) {
    Node toAdd = new Node(this, tunneler.getDirection(), tunneler.getPosition());
    nodes.add(toAdd);
  }

  public boolean hasNearbyNode(List<Node> nodes, Coord pos, int min) {
    for (Node node : nodes) {
      int dist = (int) node.getPos().distance(pos);
      if (dist < min) {
        return true;
      }
    }
    return false;
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
