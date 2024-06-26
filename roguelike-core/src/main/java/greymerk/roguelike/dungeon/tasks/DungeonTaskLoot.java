package greymerk.roguelike.dungeon.tasks;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.books.BookStatistics;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.WorldEditor;

import static greymerk.roguelike.treasure.TreasureManager.ofType;

public class DungeonTaskLoot implements IDungeonTask {

  @Override
  public void execute(WorldEditor editor, Dungeon dungeon, DungeonSettings settings) {
    TreasureManager treasureManager = editor.getTreasureManager();
    settings.processLoot(treasureManager);
    treasureManager.addItem(ofType(ChestType.STARTER), new WeightedChoice<>(new BookStatistics(editor).asStack(), 0), 1);
  }
}
