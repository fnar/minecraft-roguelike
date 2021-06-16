package greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.DungeonNode;
import greymerk.roguelike.dungeon.LevelLayout;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskRooms implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings) {
    dungeon.getLevels()
        .forEach(this::generateLevel);
  }

  private void generateLevel(DungeonLevel level) {
    LevelLayout layout = level.getLayout();
    layout.getNodes().stream()
        .filter(node -> !layout.isStartOrEnd(node))
        .forEach(DungeonNode::generate);
  }

}
