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

  public void applyFilters(WorldEditor editor) {
    settings.getFilters().stream()
        .map(Filter::get)
        .forEach(filter -> filter(editor, filter));
  }

  public void filter(WorldEditor editor, IFilter filter) {
    layout.getBoundingBoxes()
        .forEach(box -> filter.apply(editor, settings.getTheme(), box));
  }

  public Coord generateLayout(WorldEditor editor, Coord start) {
    layout = settings.getLayoutGenerator().generate(start, editor.getRandom());
    Coord end = layout.getEnd().getPosition().copy();
    assignRooms(editor);
    return end;
  }

  private void assignRooms(WorldEditor editor) {
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
    layout.getStart().setDungeon(RoomType.LINKER.newSingleRoomSetting().instantiate(settings, editor));
    layout.getEnd().setDungeon(RoomType.LINKERTOP.newSingleRoomSetting().instantiate(settings, editor));
  }

}
