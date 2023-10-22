package greymerk.roguelike.dungeon.settings;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.layout.LayoutGenerator;
import greymerk.roguelike.dungeon.segment.Segment;
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

import static greymerk.roguelike.theme.Theme.Type.random;

public class SettingsRandom extends DungeonSettings {

  public SettingsRandom(Random random) {

    setTowerSettings(new TowerSettings(TowerType.random(random), random(random)));

    IntStream.range(0, 5)
        .forEach(i -> getLevelSettings().put(i, createRandomLevel(random, i)));

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

  private LevelSettings createRandomLevel(Random random, int i) {
    LevelSettings level = new LevelSettings(i);
    level.setGenerator(i % 2 == 0 ? LayoutGenerator.Type.CLASSIC : LayoutGenerator.Type.MST);
    level.setNumRooms(15);
    level.setRange(60);
    level.setScatter(15);

    level.setTheme(random(random));
    RoomType.getIntersections().stream().map(roomType -> roomType.newRandomRoomSetting(1)).forEach(level.getRooms()::add);
    RoomType.getSecrets().stream().map(roomType -> roomType.newRandomRoomSetting(1)).forEach(level.getSecrets()::add);
    Arrays.stream(Segment.values()).map(segment -> new SegmentGenerator().with(segment, 1)).forEach(level.getSegments()::add);

    return level;
  }

}
