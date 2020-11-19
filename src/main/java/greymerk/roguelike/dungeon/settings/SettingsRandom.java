package greymerk.roguelike.dungeon.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ILoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;

import static greymerk.roguelike.theme.Theme.randomTheme;

public class SettingsRandom extends DungeonSettings {

  public SettingsRandom(Random rand) {

    setTowerSettings(new TowerSettings(Tower.randomTower(rand), randomTheme()));

    Map<Integer, LevelSettings> levels = new HashMap<>();

    IntStream.range(0, 5)
        .forEach(i -> levels.put(i, createRandomLevel(rand, i)));

    setLevels(levels);

    setLootRules(new LootRuleManager());
    ILoot loot = Loot.getLoot();
    getLootRules().add(new TypedForEachLootRule(Treasure.STARTER, loot.get(Loot.WEAPON, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(Treasure.STARTER, loot.get(Loot.FOOD, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(Treasure.STARTER, loot.get(Loot.TOOL, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(Treasure.STARTER, loot.get(Loot.SUPPLY, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(Treasure.STARTER, new ItemSpecialty(0, 0, Equipment.LEGS, Quality.WOOD), 0, 2));
    for (int i = 0; i < 5; ++i) {
      getLootRules().add(new TypedForEachLootRule(Treasure.ARMOUR, loot.get(Loot.POTION, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.ARMOUR, loot.get(Loot.ARMOUR, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.ARMOUR, loot.get(Loot.FOOD, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.WEAPONS, loot.get(Loot.POTION, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.WEAPONS, loot.get(Loot.WEAPON, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.WEAPONS, loot.get(Loot.FOOD, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.BLOCKS, loot.get(Loot.BLOCK, i), i, 6));
      getLootRules().add(new TypedForEachLootRule(Treasure.WEAPONS, loot.get(Loot.FOOD, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBONUS, i), i, 2));
      getLootRules().add(new TypedForEachLootRule(Treasure.ENCHANTING, loot.get(Loot.ENCHANTBOOK, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.FOOD, loot.get(Loot.FOOD, i), i, 8));
      getLootRules().add(new TypedForEachLootRule(Treasure.ORE, loot.get(Loot.ORE, i), i, 5));
      getLootRules().add(new TypedForEachLootRule(Treasure.POTIONS, loot.get(Loot.POTION, i), i, 6));
      getLootRules().add(new TypedForEachLootRule(Treasure.TOOLS, loot.get(Loot.ORE, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.TOOLS, loot.get(Loot.TOOL, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.TOOLS, loot.get(Loot.BLOCK, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.SUPPLIES, loot.get(Loot.SUPPLY, i), i, 6));
      getLootRules().add(new TypedForEachLootRule(Treasure.SMITH, loot.get(Loot.ORE, i), i, 6));
      getLootRules().add(new TypedForEachLootRule(Treasure.SMITH, loot.get(Loot.SMITHY, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.MUSIC, loot.get(Loot.MUSIC, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(Treasure.REWARD, loot.get(Loot.REWARD, i), i, 1));
      getLootRules().add(new ForEachLootRule(loot.get(Loot.JUNK, i), i, 6));
      getLootRules().add(new SingleUseLootRule(new ItemSpecialty(0, i, Quality.get(i)), i, 3));
      getLootRules().add(new SingleUseLootRule(new ItemEnchBook(0, i), i, i * 2 + 5));
    }
  }

  private LevelSettings createRandomLevel(Random rand, int i) {
    LevelSettings level = new LevelSettings();
    level.setDifficulty(i);
    level.setGenerator(LevelGenerator.CLASSIC);
    level.setNumRooms(15);
    level.setRange(60);
    level.setRooms(RoomsSetting.getRandom(rand, 8));
    level.setScatter(15);
    level.setSecrets(SecretsSetting.getRandom(rand, 2));
    level.setSegments(SegmentGenerator.getRandom(rand, 12));
    level.setTheme(randomTheme());
    return level;
  }
}
