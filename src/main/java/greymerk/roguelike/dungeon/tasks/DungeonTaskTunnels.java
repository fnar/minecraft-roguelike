package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.DungeonTunnel;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskTunnels implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random rand, Dungeon dungeon, DungeonSettings settings) {
    List<DungeonLevel> levels = dungeon.getLevels();

    // generate tunnels
    for (DungeonLevel level : levels) {
      for (DungeonTunnel t : level.getLayout().getTunnels()) {
        t.construct(editor, rand, level.getSettings());
      }
    }
  }
}
