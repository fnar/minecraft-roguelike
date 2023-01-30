package greymerk.roguelike.dungeon;

import com.github.fnar.roguelike.worldgen.generatables.BaseGeneratable;
import com.github.fnar.roguelike.worldgen.generatables.LadderPillar;
import com.github.fnar.roguelike.worldgen.generatables.SpiralStaircase;

import java.util.Optional;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.base.RoomIterator;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.layout.DungeonNode;
import greymerk.roguelike.dungeon.layout.LevelLayout;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
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
    layout = settings.getLayoutGenerator().generate(start, editor.getRandom(start));
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

  public DungeonLevel generateLinkers(WorldEditor editor, DungeonLevel previous) {
    DungeonNode lower = getLayout().getStart();

    lower.generate();

    if (previous != null) {
      DungeonNode upper = previous.getLayout().getEnd();
      upper.generate();

      generateStairsOrLadder(editor, lower, upper);
    }

    return this;
  }

  private void generateStairsOrLadder(WorldEditor editor, DungeonNode lower, DungeonNode upper) {
    Theme theme = getSettings().getTheme();

    int height = upper.getPosition().getY() - lower.getPosition().getY();

    BaseGeneratable linker = (editor.getRandom().nextDouble() < 0.75)
        ? SpiralStaircase.newStaircase(editor).withHeight(height)
        : LadderPillar.newLadderPillar(editor).withHeight(height);

    linker.withTheme(theme).generate(lower.getPosition());
  }

  public void generateRooms() {
    LevelLayout layout = getLayout();
    layout.getNodes().stream()
        .filter(node -> !layout.isStartOrEnd(node))
        .forEach(DungeonNode::generate);
  }

  public void tunnel(WorldEditor editor) {
    getLayout().getTunnels()
        .forEach(tunnel -> tunnel.construct(editor, getSettings()));
  }

  public void generateSegments(WorldEditor editor) {
    getLayout().getTunnels().forEach(tunnel -> tunnel.genSegments(editor, this));
  }
}
