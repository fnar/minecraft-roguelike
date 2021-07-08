package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.worldgen.IBounded;

public class LevelLayout {

  private List<DungeonNode> nodes = new ArrayList<>();
  private List<DungeonTunnel> tunnels = new ArrayList<>();
  private DungeonNode start;
  private DungeonNode end;

  public LevelLayout() {
  }

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

    int attempts = 0;
    do {
      end = nodes.get(rand.nextInt(nodes.size()));
      attempts++;
    } while (end == this.start || end.getPosition().distance(start.getPosition()) > (16 + attempts * 2));
  }

  private boolean anyTunnelsOverlap(DungeonNode node, int size) {
    return getTunnels().stream()
        .anyMatch(tunnel -> node.overlaps(size, tunnel));
  }

  public DungeonNode getBestFit(DungeonBase room) {
    return findFirstNonOverlappingNode(room)
        .orElseGet(this::findFirstConnectingNode);
  }

  private Optional<DungeonNode> findFirstNonOverlappingNode(DungeonBase room) {
    return getNodes().stream()
        .filter(this::isNotEdgeNode)
        .filter(DungeonNode::isNotYetGenerated)
        .filter(node -> !node.hasOverlappingNode(room.getSize(), getNodes()))
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

  public List<IBounded> getBoundingBoxes() {
    List<IBounded> boxes = new ArrayList<>();
    boxes.addAll(nodes);
    boxes.addAll(tunnels);
    return boxes;
  }

  public boolean isStartOrEnd(DungeonNode node) {
    return getStart() == node || node == getEnd();
  }
}
