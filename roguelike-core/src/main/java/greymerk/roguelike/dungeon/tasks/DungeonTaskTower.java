package greymerk.roguelike.dungeon.tasks;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskTower implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Dungeon dungeon, DungeonSettings settings) {
    Coord pos = dungeon.getPosition();

    TowerType tower = settings.getTower().getType();
    Theme theme = settings.getTower().getTheme();
    TowerType.instantiate(tower, editor, theme).generate(pos);
  }

}
