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
  public void execute(WorldEditor editor, Random rand, Dungeon dungeon, DungeonSettings settings) {
    dungeon.getLevels()
        .forEach(level -> generateLevel(editor, rand, level));
  }

  private void generateLevel(WorldEditor editor, Random rand, DungeonLevel level) {
    LevelLayout layout = level.getLayout();
    DungeonNode startRoom = layout.getStart();
    DungeonNode endRoom = layout.getEnd();
    layout.getNodes().stream()
        .filter(node -> startRoom != node && node != endRoom)
        .forEach(node -> node.getRoom().generate(editor, rand, level.getSettings(), node.getPosition(), node.getEntrances()));
  }
}
