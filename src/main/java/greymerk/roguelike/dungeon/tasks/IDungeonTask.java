package greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IDungeonTask {

  void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings);

}
