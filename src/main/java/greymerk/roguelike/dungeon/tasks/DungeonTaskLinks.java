package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.DungeonNode;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskLinks implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings) {

    List<DungeonLevel> levels = dungeon.getLevels();

    // generate level links
    DungeonLevel previous = null;
    for (DungeonLevel level : levels) {
      DungeonNode upper = previous == null ? null : previous.getLayout().getEnd();
      DungeonNode lower = level.getLayout().getStart();
      LevelGenerator.generateLevelLink(editor, random, level.getSettings(), lower, upper);
      previous = level;
    }

  }

}
