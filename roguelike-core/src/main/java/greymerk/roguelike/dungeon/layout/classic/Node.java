package greymerk.roguelike.dungeon.layout.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.layout.DungeonNode;
import greymerk.roguelike.dungeon.layout.DungeonTunnel;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public class Node {

  private final LayoutGeneratorClassic layoutGeneratorClassic;
  private List<Tunneler> tunnelers;
  private final Direction direction;
  private final Coord pos;

  public Node(LayoutGeneratorClassic layoutGeneratorClassic, Direction direction, Coord pos) {
    this.layoutGeneratorClassic = layoutGeneratorClassic;
    tunnelers = new ArrayList<>();
    this.direction = direction;
    this.pos = pos;

    spawnTunnelers();
  }

  private void spawnTunnelers() {

    if (layoutGeneratorClassic.getStart().distance(pos) > layoutGeneratorClassic.getRange()) {
      return;
    }

    for (Direction dir : Direction.CARDINAL) {

      if (dir.equals(direction.reverse())) {
        continue;
      }

      tunnelers.add(new Tunneler(layoutGeneratorClassic, dir, pos.copy(), layoutGeneratorClassic.getScatter()));
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
    tunnelers.stream().map(tunneler -> tunneler.getDir()).forEach(entrances::add);
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
      if (t.isDone()) {
        toKeep.add(t);
      }
    }
    tunnelers = toKeep;
  }
}
