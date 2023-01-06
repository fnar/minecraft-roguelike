package greymerk.roguelike.dungeon;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.filter.IFilter;

public class DungeonLevel {

  private final LevelSettings settings;
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

  public boolean hasNodeContaining(Coord pos) {
    return findNodeContaining(pos).isPresent();
  }

  public Optional<DungeonNode> findNodeContaining(Coord pos) {
    return generator
        .getLayout()
        .getNodes().stream()
        .filter(node -> node.contains(pos))
        .findFirst();
  }

  public LevelLayout getLayout() {
    return generator.getLayout();
  }

  public void encase(WorldEditor editor) {
    encaseNodes(editor);
    encaseTunnels(editor);
  }

  private void encaseNodes(WorldEditor editor) {
    DungeonNode start = generator.getLayout().getStart();
    DungeonNode end = generator.getLayout().getEnd();

    generator.getLayout().getNodes().stream()
        .filter(node -> node != start && node != end)
        .forEach(node -> node.encase(editor, settings.getTheme()));
  }

  private void encaseTunnels(WorldEditor editor) {
    generator.getLayout()
        .getTunnels()
        .forEach(t -> t.encase(editor, settings.getTheme()));
  }

  public void applyFilters(WorldEditor editor, Random rand) {
    settings.getFilters().stream()
        .map(Filter::get)
        .forEach(filter -> filter(editor, rand, filter));
  }

  public void filter(WorldEditor editor, Random rand, IFilter filter) {
    generator.getLayout()
        .getBoundingBoxes()
        .forEach(box -> filter.apply(editor, rand, settings.getTheme(), box));
  }
}
