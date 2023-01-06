package greymerk.roguelike.dungeon;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.base.RoomIterator;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.filter.IFilter;

public class DungeonLevel {

  private final LevelSettings settings;
  private LevelLayout layout;

  public DungeonLevel(LevelSettings settings) {
    this.settings = settings;
  }

  public LevelSettings getSettings() {
    return settings;
  }

  public boolean hasNodeContaining(Coord pos) {
    return findNodeContaining(pos).isPresent();
  }

  public Optional<DungeonNode> findNodeContaining(Coord pos) {
    return layout
        .getNodes().stream()
        .filter(node -> node.contains(pos))
        .findFirst();
  }

  public LevelLayout getLayout() {
    return layout;
  }

  public void encase(WorldEditor editor) {
    encaseNodes(editor);
    encaseTunnels(editor);
  }

  private void encaseNodes(WorldEditor editor) {
    DungeonNode start = layout.getStart();
    DungeonNode end = layout.getEnd();

    layout.getNodes().stream()
        .filter(node -> node != start && node != end)
        .forEach(node -> node.encase(editor, settings.getTheme()));
  }

  private void encaseTunnels(WorldEditor editor) {
    layout.getTunnels()
        .forEach(t -> t.encase(editor, settings.getTheme()));
  }

  public void applyFilters(WorldEditor editor, Random rand) {
    settings.getFilters().stream()
        .map(Filter::get)
        .forEach(filter -> filter(editor, rand, filter));
  }

  public void filter(WorldEditor editor, Random rand, IFilter filter) {
    layout.getBoundingBoxes()
        .forEach(box -> filter.apply(editor, rand, settings.getTheme(), box));
  }

  public Coord generateLayout(WorldEditor editor, Random random, Coord start) {
    layout = settings.getLayoutGenerator().generate(start, random);
    Coord end = layout.getEnd().getPosition().copy();

    RoomIterator roomIterator = new RoomIterator(settings, editor);
    int count = 0;
    while (layout.hasEmptyRooms()) {
      BaseRoom toGenerate = count < settings.getNumRooms()
          ? roomIterator.getDungeonRoom()
          : RoomType.CORNER.newSingleRoomSetting().instantiate(settings, editor);
      DungeonNode node = layout.getBestFit(toGenerate);
      node.setDungeon(toGenerate);
      ++count;
    }
    return end;
  }

}
