package greymerk.roguelike.dungeon.settings.base;

import com.github.fnar.minecraft.item.ItemMapper1_12;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.treasure.loot.Book;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ILoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.util.WeightedChoice;

import static com.google.common.collect.Lists.newArrayList;

public class SettingsLootRules extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "loot");

  public SettingsLootRules() {
    super(ID);

    ILoot loot = Loot.getLoot();

    addStarterLoot(loot);

    addRewardLoot();

    for (int level = 0; level < 5; level++) {
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(Loot.POTION, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(Loot.ARMOUR, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.ARMOUR, loot.get(Loot.FOOD, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(Loot.POTION, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(Loot.WEAPON, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(Loot.FOOD, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.WEAPONS, loot.get(Loot.FOOD, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.BLOCKS, loot.get(Loot.BLOCK, level), level, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.ENCHANTING, loot.get(Loot.ENCHANTBONUS, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.ENCHANTING, loot.get(Loot.ENCHANTBOOK, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.FOOD, loot.get(Loot.FOOD, level), level, 8));
      getLootRules().add(new TypedForEachLootRule(ChestType.ORE, loot.get(Loot.ORE, level), level, 5));
      getLootRules().add(new TypedForEachLootRule(ChestType.POTIONS, loot.get(Loot.POTION, level), level, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.BREWING, loot.get(Loot.BREWING, level), level, 8));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(Loot.ORE, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(Loot.TOOL, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.TOOLS, loot.get(Loot.BLOCK, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.SUPPLIES, loot.get(Loot.SUPPLY, level), level, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.SMITH, loot.get(Loot.ORE, level), level, 6));
      getLootRules().add(new TypedForEachLootRule(ChestType.SMITH, loot.get(Loot.SMITHY, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.MUSIC, loot.get(Loot.MUSIC, level), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.REWARD, loot.get(Loot.REWARD, level), level, 1));
      getLootRules().add(new ForEachLootRule(loot.get(Loot.JUNK, level), level, 6));
      getLootRules().add(new SingleUseLootRule(new ItemSpecialty(1, level, Quality.get(level)), level, 3));
      getLootRules().add(new SingleUseLootRule(new ItemEnchBook(1, level), level, level * 2 + 5));
    }
  }

  private void addRewardLoot() {
    useLootTableForLevel(LootTableList.CHESTS_SIMPLE_DUNGEON, 0);
    useLootTableForLevel(LootTableList.CHESTS_DESERT_PYRAMID, 1);
    useLootTableForLevel(LootTableList.CHESTS_JUNGLE_TEMPLE, 2);
    useLootTableForLevel(LootTableList.CHESTS_NETHER_BRIDGE, 3);
    useLootTableForLevel(LootTableList.CHESTS_END_CITY_TREASURE, 4);
  }

  private void useLootTableForLevel(ResourceLocation chestsSimpleDungeon, int level) {
    getLootTables().add(new LootTableRule(newArrayList(level), chestsSimpleDungeon.getResourcePath(), newArrayList(ChestType.REWARD)));
  }

  private void addStarterLoot(ILoot loot) {
    for (int level = 0; level < 5; level++) {
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(Book.get(Book.CREDITS), 1), level, 1));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(Loot.WEAPON, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(Loot.FOOD, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(Loot.TOOL, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, loot.get(Loot.SUPPLY, level), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new ItemSpecialty(1, level, Equipment.LEGS, Quality.WOOD), level, 2));
      getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(ItemMapper1_12.map(PotionMixture.getCoffee()), 1), level, 2));
    }
  }
}
