package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.GreymerkChestType;
import greymerk.roguelike.treasure.loot.GreymerkLootProvider;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.books.BookStarter;
import greymerk.roguelike.treasure.loot.provider.EnchantedBookLootItem;
import greymerk.roguelike.treasure.loot.provider.SpecialtyLootItem;
import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedSingleUseLootRule;
import greymerk.roguelike.util.WeightedChoice;

import static com.google.common.collect.Lists.newArrayList;

public class BuiltinLootSettings extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "loot");

  public BuiltinLootSettings() {
    super(ID);

    GreymerkLootProvider loot = new GreymerkLootProvider();

    addStarterLoot(loot);

    addRewardLoot();

    for (int level = 0; level < 5; level++) {
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(GreymerkChestType.POTION, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(GreymerkChestType.ARMOUR, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(GreymerkChestType.FOOD, level), level, 1));

      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(GreymerkChestType.POTION, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(GreymerkChestType.WEAPON, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(GreymerkChestType.FOOD, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(GreymerkChestType.FOOD, level), level, 1));

      getLootRules().add(new TypedForEachLootRule(ChestType.BLOCKS, loot.get(GreymerkChestType.BLOCK, level), level, 6));

      getLootRules().add(new TypedForEachLootRule(ChestType.ENCHANTING, loot.get(GreymerkChestType.ENCHANTBONUS, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.ENCHANTING, loot.get(GreymerkChestType.ENCHANTBOOK, level), level, 1));

      getLootRules().add(new TypedForEachLootRule(ChestType.FOOD, loot.get(GreymerkChestType.FOOD, level), level, 8));

      getLootRules().add(new TypedForEachLootRule(ChestType.JUNK, loot.get(GreymerkChestType.JUNK, level), level, 4));

      getLootRules().add(new TypedForEachLootRule(ChestType.MAGIC, loot.get(GreymerkChestType.SPECIAL, level), level, 4));
      getLootRules().add(new TypedSingleUseLootRule(ChestType.MAGIC, loot.get(GreymerkChestType.ENCHANTBOOK, level), level, 1));
      getLootRules().add(new TypedSingleUseLootRule(ChestType.MAGIC, loot.get(GreymerkChestType.ENCHANTBONUS, level), level, 1));

      getLootRules().add(new TypedForEachLootRule(ChestType.ORE, loot.get(GreymerkChestType.ORE, level), level, 5));

      getLootRules().add(new TypedForEachLootRule(ChestType.POTIONS, loot.get(GreymerkChestType.POTION, level), level, 6));

      getLootRules().add(new TypedForEachLootRule(ChestType.BREWING, loot.get(GreymerkChestType.BREWING, level), level, 8));

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

    addGardenLoot(loot);
  }

  private void addGardenLoot(GreymerkLootProvider loot) {
    for (int level = 0; level < 5; level++) {
      for (int i = 0; i < 5; i++) {
        getLootRules().add(new TypedForEachLootRule(ChestType.GARDEN, loot.get(GreymerkChestType.GARDEN, 1), level, 2));
        getLootRules().add(new TypedForEachLootRule(ChestType.GARDEN, loot.get(GreymerkChestType.GARDEN, 1), level, 2));
      }
    }
  }

  private void addRewardLoot() {
    useLootTableForLevel(0, "chests/simple_dungeon");
    useLootTableForLevel(1, "chests/desert_pyramid");
    useLootTableForLevel(2, "chests/jungle_temple");
    useLootTableForLevel(3, "chests/nether_bridge");
    useLootTableForLevel(4, "chests/end_city_treasure");
  }

  private void useLootTableForLevel(int level, String resourcePath) {
    getLootTables().add(new LootTableRule(newArrayList(level), resourcePath, newArrayList(ChestType.REWARD)));
  }

  private void addStarterLoot(GreymerkLootProvider loot) {
    for (int level = 0; level < 5; level++) {
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new BookStarter().asStack(), 1), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(GreymerkChestType.WEAPON, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(GreymerkChestType.FOOD, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(GreymerkChestType.TOOL, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(GreymerkChestType.SUPPLY, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new SpecialtyLootItem(1, level, Equipment.LEGS, Quality.WOOD), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(PotionMixture.getCoffee(), 1), level, 2));
    }
  }
}
