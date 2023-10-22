package greymerk.roguelike.citadel;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.graph.Edge;
import greymerk.roguelike.util.graph.Graph;
import greymerk.roguelike.util.mst.MSTPoint;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.dungeon.Dungeon.TOPLEVEL;

public class CityGrounds {

  private final WorldEditor editor;
  private final MinimumSpanningTree mst;
  private final Theme theme;

  public CityGrounds(WorldEditor editor, MinimumSpanningTree mst, Theme theme) {
    this.editor = editor;
    this.mst = mst;
    this.theme = theme;
  }

  public void generate(Coord coord) {
    clearArea(coord);
    wall1(coord);
    wall2(coord);
    wall3(coord);

    for (Edge<MSTPoint> edge : mst.getEdges()) {
      Coord start = edge.getStart().getPosition().up(20);
      Coord end = edge.getEnd().getPosition();
      RectSolid.newRect(start, end).fill(editor, getWalls());
    }

    generateTowers(coord);
  }

  private BlockBrush getWalls() {
    return theme.getPrimary().getWall();
  }

  private void clearArea(Coord coord) {
    Coord start = coord.copy().translate(new Coord(Citadel.EDGE_LENGTH * -3, 10, Citadel.EDGE_LENGTH * -3));
    Coord end = coord.copy().translate(new Coord(Citadel.EDGE_LENGTH * 3, 40, Citadel.EDGE_LENGTH * 3));
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
  }

  private void wall1(Coord coord) {
    Coord start = coord.copy().translate(new Coord(Citadel.EDGE_LENGTH * -3, 10, Citadel.EDGE_LENGTH * -3));
    Coord end = coord.copy().translate(new Coord(Citadel.EDGE_LENGTH * 3, 20, Citadel.EDGE_LENGTH * 3));
    RectSolid.newRect(start, end).fill(editor, getWalls());
  }

  private void wall2(Coord coord) {
    Coord start = coord.copy().translate(new Coord(Citadel.EDGE_LENGTH * -2, 20, Citadel.EDGE_LENGTH * -2));
    Coord end = coord.copy().translate(new Coord(Citadel.EDGE_LENGTH * 2, 30, Citadel.EDGE_LENGTH * 2));
    RectSolid.newRect(start, end).fill(editor, getWalls());
  }

  private void wall3(Coord coord) {
    Coord start = coord.copy().translate(new Coord(Citadel.EDGE_LENGTH * -1, 30, Citadel.EDGE_LENGTH * -1));
    Coord end = coord.copy().translate(new Coord(Citadel.EDGE_LENGTH, 40, Citadel.EDGE_LENGTH));
    RectSolid.newRect(start, end).fill(editor, getWalls());
  }

  private void generateTowers(Coord coord) {
    Graph<Coord> layout = mst.getGraph();
    List<Coord> towers = layout.getPoints();
    for (Coord towerCoord : towers) {
      towerCoord.translate(coord);
      TowerType towerType = TowerType.random(editor.getRandom());
      Theme themeBase = Theme.Type.random(editor.getRandom()).asTheme();
      Tower tower = towerType.instantiate(editor, themeBase);
      tower.generate(towerCoord.copy().setY(TOPLEVEL));
    }
  }
}
