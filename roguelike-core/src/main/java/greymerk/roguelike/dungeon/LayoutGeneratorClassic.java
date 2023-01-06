package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    this.start = start;
    List<Node> gNodes = new ArrayList<>();
    Node startNode = new Node(Direction.randomCardinal(random), start);
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
    Node toAdd = new Node(tunneler.getDirection(), tunneler.getPosition());
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

  private class Tunneler {

    private boolean done;
    private final Direction dir;
    private final Coord start;
    private final Coord end;
    private int extend;

    public Tunneler(Direction dir, Coord start) {
      done = false;
      this.dir = dir;
      this.start = start.copy();
      end = start.copy();
      extend = scatter * 2;
    }

    public void update(List<Node> nodes, Random random) {
      if (done) {
        return;
      }

      if (hasNearbyNode(nodes, end, scatter)) {
        end.translate(dir);
      } else {
        if (random.nextInt(extend) == 0) {
          spawnNode(nodes, this);
          done = true;
        } else {
          end.translate(dir);
          extend--;
        }
      }
    }

    public boolean isDone() {
      return done;
    }

    public Direction getDirection() {
      return dir;
    }

    public Coord getPosition() {
      return end.copy();
    }

    public DungeonTunnel createTunnel() {
      return new DungeonTunnel(start.copy(), end.copy());
    }
  }

  private class Node {

    private List<Tunneler> tunnelers;
    private final Direction direction;
    private final Coord pos;

    public Node(Direction direction, Coord pos) {
      tunnelers = new ArrayList<>();
      this.direction = direction;
      this.pos = pos;

      spawnTunnelers();
    }

    private void spawnTunnelers() {

      if (start.distance(pos) > range) {
        return;
      }

      for (Direction dir : Direction.CARDINAL) {

        if (dir.equals(direction.reverse())) {
          continue;
        }

        tunnelers.add(new Tunneler(dir, pos.copy()));
      }
    }

    public void update(List<Node> nodes, Random random) {
      for (Tunneler tunneler : tunnelers) {
        tunneler.update(nodes, random);
      }
    }

    public boolean isDone() {
      for (Tunneler tunneler : tunnelers) {
        if (!tunneler.isDone()) {
          return false;
        }
      }
      return true;
    }

    public Coord getPos() {
      return pos.copy();
    }

    public List<Direction> getEntrances() {
      List<Direction> entrances = new ArrayList<>();
      entrances.add(direction.reverse());
      tunnelers.stream().map(tunneler -> tunneler.dir).forEach(entrances::add);
      return entrances;
    }

    public List<DungeonTunnel> createTunnels() {
      List<DungeonTunnel> tunnels = new ArrayList<>();
      for (Tunneler t : tunnelers) {
        tunnels.add(t.createTunnel());
      }
      return tunnels;
    }

    public DungeonNode createNode() {
      return new DungeonNode(getEntrances(), pos);
    }

    public void cull() {
      List<Tunneler> toKeep = new ArrayList<>();
      for (Tunneler t : tunnelers) {
        if (t.done) {
          toKeep.add(t);
        }
      }
      tunnelers = toKeep;
    }
  }
}
