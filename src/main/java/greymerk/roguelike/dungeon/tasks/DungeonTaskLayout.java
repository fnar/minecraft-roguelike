package greymerk.roguelike.dungeon.tasks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonNode;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.ILevelGenerator;
import greymerk.roguelike.dungeon.ILevelLayout;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.base.RoomIterator;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskLayout implements IDungeonTask {

  @Override
  public void execute(IWorldEditor editor, Random random, IDungeon dungeon, ISettings settings) {
    List<IDungeonLevel> levels = dungeon.getLevels();
    Coord start = dungeon.getPosition();


    // generate level layouts
    for (IDungeonLevel level : levels) {
      ILevelGenerator generator = LevelGenerator.getGenerator(editor, random, level.getSettings().getGenerator(), level);

      try {
        level.generate(generator, start);
      } catch (Exception e) {
        e.printStackTrace();
      }

      ILevelLayout layout = generator.getLayout();
      random = Dungeon.getRandom(editor, start);
      start = new Coord(layout.getEnd().getPosition());
      start.add(Cardinal.DOWN, Dungeon.VERTICAL_SPACING);
    }


    // assign dungeon rooms
    for (IDungeonLevel level : levels) {
      ILevelLayout layout = level.getLayout();
      RoomIterator roomIterator = new RoomIterator(level.getSettings().getRooms(), random);

      int count = 0;
      while (layout.hasEmptyRooms()) {
        IDungeonRoom toGenerate = count < level.getSettings().getNumRooms()
            ? roomIterator.getDungeonRoom()
            : cornerRoom().instantiate();
        DungeonNode node = layout.getBestFit(toGenerate);
        node.setDungeon(toGenerate);
        ++count;
      }
    }
  }

  private RoomSetting cornerRoom() {
    // todo: There's some smell here
    return new RoomSetting(DungeonRoom.CORNER, null, "builtin:spawners", "single", 1, 1, Collections.emptyList());
  }
}
