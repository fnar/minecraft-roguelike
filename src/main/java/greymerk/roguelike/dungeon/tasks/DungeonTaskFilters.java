package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskFilters implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings) {

    List<DungeonLevel> levels = dungeon.getLevels();

    for (DungeonLevel level : levels) {
      level.applyFilters(editor, random);
    }
  }
}
