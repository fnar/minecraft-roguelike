package greymerk.roguelike.dungeon.settings;

import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ILoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;

import static greymerk.roguelike.theme.Theme.randomTheme;

public class SettingsRandom extends DungeonSettings {

  public SettingsRandom(Random rand) {

    setTowerSettings(new TowerSettings(Tower.randomTower(rand), randomTheme()));

    IntStream.range(0, 5)
        .forEach(i -> getLevelSettings().put(i, createRandomLevel(rand, i)));

    ILoot loot = Loot.getLoot();
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(Loot.WEAPON, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(Loot.FOOD, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(Loot.TOOL, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(Loot.SUPPLY, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new ItemSpecialty(0, 0, Equipment.LEGS, Quality.WOOD), 0, 2));
    for (int i = 0; i < 5; ++i) {
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(Loot.POTION, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(Loot.ARMOUR, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(Loot.FOOD, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(Loot.POTION, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(Loot.WEAPON, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(Loot.FOOD, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.BLOCKS, loot.get(Loot.BLOCK, i), i, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(Loot.FOOD, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ENCHANTING, loot.get(Loot.ENCHANTBONUS, i), i, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.ENCHANTING, loot.get(Loot.ENCHANTBOOK, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.FOOD, loot.get(Loot.FOOD, i), i, 8));
      getLootRules().add(new TypedForEachLootRule(ChestType.ORE, loot.get(Loot.ORE, i), i, 5));
      getLootRules().add(new TypedForEachLootRule(ChestType.POTIONS, loot.get(Loot.POTION, i), i, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(Loot.ORE, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(Loot.TOOL, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(Loot.BLOCK, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.SUPPLIES, loot.get(Loot.SUPPLY, i), i, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.SMITH, loot.get(Loot.ORE, i), i, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.SMITH, loot.get(Loot.SMITHY, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.MUSIC, loot.get(Loot.MUSIC, i), i, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.REWARD, loot.get(Loot.REWARD, i), i, 1));
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
