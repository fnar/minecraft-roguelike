package greymerk.roguelike.dungeon.tasks;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.books.BookStatistics;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.WorldEditor;

import static greymerk.roguelike.treasure.TreasureManager.ofType;

public class DungeonTaskLoot implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Random random, Dungeon dungeon, DungeonSettings settings) {
    TreasureManager treasureManager = editor.treasureChestEditor.getTreasureManager();
    settings.processLoot(treasureManager);
    treasureManager.addItem(ofType(Treasure.STARTER), new WeightedChoice<>(new BookStatistics(editor).get(), 0), 1);
  }
}
