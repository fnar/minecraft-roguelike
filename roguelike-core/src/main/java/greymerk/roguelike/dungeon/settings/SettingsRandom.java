package greymerk.roguelike.dungeon.settings;

import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.GreymerkChestType;
import greymerk.roguelike.treasure.loot.GreymerkLootProvider;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.EnchantedBookLootItem;
import greymerk.roguelike.treasure.loot.provider.SpecialtyLootItem;
import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;

import static greymerk.roguelike.theme.Themes.randomTheme;

public class SettingsRandom extends DungeonSettings {

  public SettingsRandom(Random rand) {

    setTowerSettings(new TowerSettings(TowerType.randomTower(rand), randomTheme()));

    IntStream.range(0, 5)
        .forEach(i -> getLevelSettings().put(i, createRandomLevel(rand, i)));

    GreymerkLootProvider loot = new GreymerkLootProvider();
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(GreymerkChestType.WEAPON, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(GreymerkChestType.FOOD, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(GreymerkChestType.TOOL, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(GreymerkChestType.SUPPLY, 0), 0, 2));
    getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new SpecialtyLootItem(0, 0, Equipment.LEGS, Quality.WOOD), 0, 2));
    for (int level = 0; level < 5; ++level) {
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(GreymerkChestType.POTION, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(GreymerkChestType.ARMOUR, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(GreymerkChestType.FOOD, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(GreymerkChestType.POTION, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(GreymerkChestType.WEAPON, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(GreymerkChestType.FOOD, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.BLOCKS, loot.get(GreymerkChestType.BLOCK, level), level, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(GreymerkChestType.FOOD, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ENCHANTING, loot.get(GreymerkChestType.ENCHANTBONUS, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.ENCHANTING, loot.get(GreymerkChestType.ENCHANTBOOK, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.FOOD, loot.get(GreymerkChestType.FOOD, level), level, 8));
      getLootRules().add(new TypedForEachLootRule(ChestType.ORE, loot.get(GreymerkChestType.ORE, level), level, 5));
      getLootRules().add(new TypedForEachLootRule(ChestType.POTIONS, loot.get(GreymerkChestType.POTION, level), level, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(GreymerkChestType.ORE, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(GreymerkChestType.TOOL, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(GreymerkChestType.BLOCK, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.SUPPLIES, loot.get(GreymerkChestType.SUPPLY, level), level, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.SMITH, loot.get(GreymerkChestType.ORE, level), level, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.SMITH, loot.get(GreymerkChestType.SMITHY, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.MUSIC, loot.get(GreymerkChestType.MUSIC, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.REWARD, loot.get(GreymerkChestType.REWARD, level), level, 1));
      getLootRules().add(new ForEachLootRule(loot.get(GreymerkChestType.JUNK, level), level, 6));
      getLootRules().add(new SingleUseLootRule(new SpecialtyLootItem(1, level, Quality.get(level)), level, 3));
      getLootRules().add(new SingleUseLootRule(new EnchantedBookLootItem(1, level), level, level * 2 + 5));
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
