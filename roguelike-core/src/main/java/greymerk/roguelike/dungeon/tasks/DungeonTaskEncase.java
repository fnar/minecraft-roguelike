package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;

public class DungeonTaskEncase implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings) {

    List<DungeonLevel> levels = dungeon.getLevels();

    // encase
    if (RogueConfig.getBoolean(RogueConfig.ENCASE)) {
      for (DungeonLevel level : levels) {
        level.filter(editor, random, Filter.get(Filter.ENCASE));
      }
    }
  }
}
