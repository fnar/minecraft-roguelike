package greymerk.roguelike.dungeon.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.Coord;

public class LevelLayout {

  private final List<DungeonNode> nodes = new ArrayList<>();
  private final List<DungeonTunnel> tunnels = new ArrayList<>();
  private DungeonNode start;
  private DungeonNode end;

  public DungeonNode getStart() {
    return start;
  }

  public void setStart(DungeonNode start) {
    this.start = start;
    addNode(start);
  }

  public DungeonNode getEnd() {
    return end;
  }

  public void setEnd(DungeonNode end) {
    this.end = end;
    addNode(end);
  }

  public void addNode(DungeonNode node) {
    nodes.add(node);
  }

  public void addTunnel(DungeonTunnel tunnel) {
    tunnels.add(tunnel);
  }

  public void addTunnels(List<DungeonTunnel> tunnels) {
    this.tunnels.addAll(tunnels);
  }

  public List<DungeonNode> getNodes() {
    return nodes;
  }

  public List<DungeonTunnel> getTunnels() {
    return tunnels;
  }

  public void setStartEnd(Random rand, DungeonNode start) {
    this.start = start;
    this.end = start;

    int attempts = 0;
    do {
      end = nodes.get(rand.nextInt(nodes.size()));
      attempts++;
    } while (end == this.start || start.getDistance(end) > (16 + attempts * 2));


    // todo: experiment with alternative layout start selection
//    end = nodes.stream().filter(node -> node != start).max(Comparator.comparingDouble(node -> node.getDistance(start))).orElse(start);
//    end = nodes.stream().filter(node -> node != start)
//        .max(Comparator.comparingDouble(node -> largestXZDistance(start, node))).orElse(start);

//    int attemptsRemaining = 3;
//    while (end == start && attemptsRemaining > 0) {
//      attemptsRemaining--;
//      end = nodes.get(nodes.size() - 1);
//    }
  }

  private int largestXZDistance(DungeonNode start, DungeonNode node) {
    return Math.abs(start.getPosition().getX() - node.getPosition().getX()) + Math.abs(start.getPosition().getZ() - node.getPosition().getZ());
  }

  private boolean anyTunnelsOverlap(DungeonNode node, int size) {
    return getTunnels().stream()
        .anyMatch(tunnel -> node.overlaps(size, tunnel));
  }

  public DungeonNode getBestFit(BaseRoom room) {
    return findFirstNonOverlappingNode(room)
        .orElseGet(this::findFirstConnectingNode);
  }

  private Optional<DungeonNode> findFirstNonOverlappingNode(BaseRoom room) {
    return getNodes().stream()
        .filter(this::isNotEdgeNode)
        .filter(DungeonNode::isNotYetGenerated)
        .filter(node -> !node.hasOverlappingNode(room.getSize() + DungeonNode.ENCASING_SIZE, getNodes()))
        .findFirst();
  }

  private DungeonNode findFirstConnectingNode() {
    return getNodes().stream()
        .filter(this::isNotEdgeNode)
        .filter(DungeonNode::isNotYetGenerated)
        .findFirst()
        .orElse(null);
  }

  private boolean overlaps(DungeonNode node, int size) {
    return anyTunnelsOverlap(node, size) || node.hasOverlappingNode(size, this.getNodes());
  }

  private boolean isNotEdgeNode(DungeonNode node) {
    return !isEdgeNode(node);
  }

  private boolean isEdgeNode(DungeonNode node) {
    return node == start || node == end;
  }

  public boolean hasEmptyRooms() {
    return nodes.stream()
        .filter(this::isNotEdgeNode)
        .anyMatch(DungeonNode::isNotYetGenerated);
  }

  public List<Bounded> getBoundingBoxes() {
    List<Bounded> boxes = new ArrayList<>();
    boxes.addAll(nodes);
    boxes.addAll(tunnels);
    return boxes;
  }

  public boolean isStartOrEnd(DungeonNode node) {
    return getStart() == node || node == getEnd();
  }

  public boolean containsRoomAt(Coord coord) {
    return getNodes().stream().anyMatch(node -> node.contains(coord));
  }

}
