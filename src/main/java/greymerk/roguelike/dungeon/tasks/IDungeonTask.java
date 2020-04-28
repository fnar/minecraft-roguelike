package greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface IDungeonTask {

  void execute(IWorldEditor editor, Random rand, Dungeon dungeon, ISettings settings);

}
