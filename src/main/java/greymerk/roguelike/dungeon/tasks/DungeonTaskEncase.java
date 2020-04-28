package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;

public class DungeonTaskEncase implements IDungeonTask {

  @Override
  public void execute(IWorldEditor editor, Random rand, Dungeon dungeon, DungeonSettings settings) {

    List<IDungeonLevel> levels = dungeon.getLevels();

    // encase
    if (RogueConfig.getBoolean(RogueConfig.ENCASE)) {
      for (IDungeonLevel level : levels) {
        level.filter(editor, rand, Filter.get(Filter.ENCASE));
      }
    }
  }
}
