package greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.books.BookStatistics;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskLoot implements IDungeonTask {

  @Override
  public void execute(IWorldEditor editor, Random rand, Dungeon dungeon, DungeonSettings settings) {
    TreasureManager treasure = editor.getTreasure();
    settings.processLoot(rand, treasure);
    treasure.addItem(rand, Treasure.STARTER, new WeightedChoice<>(new BookStatistics(editor).get(), 0), 1);
  }
}
