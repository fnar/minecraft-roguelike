package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.filter.IFilter;

public class DungeonLevel {

  private LevelSettings settings;
  private ILevelGenerator generator;

  public DungeonLevel(LevelSettings settings) {
    this.settings = settings;
  }

  public void generate(ILevelGenerator generator, Coord start) {
    this.generator = generator;
    generator.generate(start);
  }

  public int nodeCount() {
    return generator.getLayout().getNodes().size();
  }

  public LevelSettings getSettings() {
    return settings;
  }

  public boolean hasNearbyNode(Coord pos) {
    return generator
        .getLayout()
        .getNodes().stream()
        .anyMatch(node -> isNearby(pos, node));
  }

  private boolean isNearby(Coord pos, DungeonNode node) {
    return (int) node.getPosition().distance(pos) < node.getSize();
  }

  public LevelLayout getLayout() {
    return generator.getLayout();
  }

  public void encase(IWorldEditor editor, Random rand) {
    encaseNodes(editor, rand);
    encaseTunnels(editor, rand);
  }

  private void encaseNodes(IWorldEditor editor, Random rand) {
    DungeonNode start = generator.getLayout().getStart();
    DungeonNode end = generator.getLayout().getEnd();

    generator.getLayout().getNodes().stream()
        .filter(node -> node != start && node != end)
        .forEach(node -> node.encase(editor, rand, settings.getTheme()));
  }

  private void encaseTunnels(IWorldEditor editor, Random rand) {
    generator.getLayout()
        .getTunnels()
        .forEach(t -> t.encase(editor, rand, settings.getTheme()));
  }

  public void applyFilters(IWorldEditor editor, Random rand) {
    settings.getFilters().stream()
        .map(Filter::get)
        .forEach(filter -> filter(editor, rand, filter));
  }

  public void filter(IWorldEditor editor, Random rand, IFilter filter) {
    generator.getLayout()
        .getBoundingBoxes()
        .forEach(box -> filter.apply(editor, rand, settings.getTheme(), box));
  }
}
