package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.DungeonNode;
import greymerk.roguelike.dungeon.ILevelGenerator;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.LevelLayout;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.RoomIterator;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskLayout implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings) {
    List<DungeonLevel> levels = dungeon.getLevels();
    Coord start = dungeon.getPosition();


    // generate level layouts
    for (DungeonLevel level : levels) {
      ILevelGenerator generator = LevelGenerator.getGenerator(editor, random, level.getSettings().getGenerator(), level);

      try {
        level.generate(generator, start);
      } catch (Exception e) {
        e.printStackTrace();
      }

      LevelLayout layout = generator.getLayout();
      random = editor.getRandom(start);
      start = new Coord(layout.getEnd().getPosition());
      start.translate(Cardinal.DOWN, Dungeon.VERTICAL_SPACING);
    }


    // assign dungeon rooms
    for (DungeonLevel level : levels) {
      LevelLayout layout = level.getLayout();
      RoomIterator roomIterator = new RoomIterator(level.getSettings().getRooms(), random);

      int count = 0;
      while (layout.hasEmptyRooms()) {
        DungeonBase toGenerate = count < level.getSettings().getNumRooms()
            ? roomIterator.getDungeonRoom()
            : RoomType.CORNER.newSingleRoomSetting().instantiate();
        DungeonNode node = layout.getBestFit(toGenerate);
        node.setDungeon(toGenerate);
        ++count;
      }
    }
  }

}
