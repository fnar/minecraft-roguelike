package greymerk.roguelike.dungeon.tasks;

import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.DungeonTunnel;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskSegments implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Dungeon dungeon, DungeonSettings settings) {

    List<DungeonLevel> levels = dungeon.getLevels();

    for (DungeonLevel level : levels) {
      for (DungeonTunnel tunnel : level.getLayout().getTunnels()) {
        tunnel.genSegments(editor, level);
      }
    }
  }
}
