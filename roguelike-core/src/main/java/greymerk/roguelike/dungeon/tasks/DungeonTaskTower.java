package greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonTaskTower implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings) {
    Coord pos = dungeon.getPosition();

    TowerType tower = settings.getTower().getType();
    Random r = editor.getRandom();
    TowerType.get(tower).generate(editor, r, settings.getTower().getTheme(), pos);


  }

}
