package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.DungeonTunnel;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskSegments implements IDungeonTask {

  @Override
  public void execute(IWorldEditor editor, Random rand, Dungeon dungeon, DungeonSettings settings) {

    List<DungeonLevel> levels = dungeon.getLevels();

    // generate segments
    for (DungeonLevel level : levels) {
      for (DungeonTunnel tunnel : level.getLayout().getTunnels()) {
        tunnel.genSegments(editor, rand, level);
      }
    }
  }
}
