package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.util.graph.Edge;
import greymerk.roguelike.util.graph.Graph;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class LevelGeneratorMST implements ILevelGenerator {

  WorldEditor editor;
  Random rand;
  LevelSettings settings;
  private LevelLayout layout;
  private int length;
  private int scatter;


  public LevelGeneratorMST(WorldEditor editor, Random rand, LevelSettings settings) {
    this.editor = editor;
    this.rand = rand;
    this.settings = settings;
    this.length = (int) Math.ceil(Math.sqrt(settings.getNumRooms()));
    this.scatter = settings.getScatter() % 2 == 0 ? settings.getScatter() + 1 : settings.getScatter();
    this.layout = new LevelLayout();
  }

  @Override
  public void generate(Coord start) {
    MinimumSpanningTree mst = new MinimumSpanningTree(rand, length, scatter, start.copy());
    Graph<Coord> layout = mst.getGraph();
    List<Edge<Coord>> edges = layout.getEdges();
    List<Coord> vertices = layout.getPoints();

    addTunnels(edges, vertices);

    DungeonNode startDungeonNode = null;

    for (Coord vertex : vertices) {
      DungeonNode toAdd = new DungeonNode(findEntrances(vertex), vertex);
      this.layout.addNode(toAdd);

      if (vertex.equals(start)) {
        startDungeonNode = toAdd;
      }
    }

    this.layout.setStartEnd(rand, startDungeonNode);

  }

  private List<Direction> findEntrances(Coord vertex) {
    return this.layout.getTunnels().stream()
        .map(tunnel -> tunnel.getEntrance(vertex))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

  private void addTunnels(List<Edge<Coord>> edges, List<Coord> vertices) {
    List<Edge<Coord>> used = new ArrayList<>();
    for (Coord vertex : vertices) {
      for (Edge<Coord> edge : edges) {
        if (used.contains(edge)) {
          continue;
        }
        if (vertex.equals(edge.getStart()) || vertex.equals(edge.getEnd())) {
          this.layout.addTunnel(new DungeonTunnel(edge.getStart(), edge.getEnd()));
          used.add(edge);
        }
      }
    }
  }

  @Override
  public LevelLayout getLayout() {
    return this.layout;
  }

}
