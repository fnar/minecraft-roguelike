package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.settings.loot.ArmourLootItemParser;
import com.github.fnar.roguelike.settings.loot.MinecraftItemLootItemParser;
import com.github.fnar.roguelike.settings.loot.NoveltyLootItemParser;
import com.github.fnar.roguelike.settings.loot.PotionLootItemParser;
import com.github.fnar.roguelike.settings.loot.SpecialtyLootItemParser;
import com.github.fnar.roguelike.settings.loot.ToolLootItemParser;
import com.github.fnar.roguelike.settings.loot.WeaponLootItemParser;

import greymerk.roguelike.treasure.loot.provider.ArmourLootItem;
import greymerk.roguelike.treasure.loot.provider.BlockLootItem;
import greymerk.roguelike.treasure.loot.provider.EnchantedBookLootItem;
import greymerk.roguelike.treasure.loot.provider.ItemBrewing;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.treasure.loot.provider.ItemJunk;
import greymerk.roguelike.treasure.loot.provider.ItemMixture;
import greymerk.roguelike.treasure.loot.provider.ItemOre;
import greymerk.roguelike.treasure.loot.provider.ItemSmithy;
import greymerk.roguelike.treasure.loot.provider.PotionLootItem;
import greymerk.roguelike.treasure.loot.provider.RecordLootItem;
import greymerk.roguelike.treasure.loot.provider.SpecialtyLootItem;
import greymerk.roguelike.treasure.loot.provider.SupplyLootItem;
import greymerk.roguelike.treasure.loot.provider.ToolLootItem;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;

public enum GreymerkChestType {

  WEAPON,
  ARMOUR,
  BLOCK,
  JUNK,
  ORE,
  TOOL,
  POTION,
  FOOD,
  ENCHANTBOOK,
  ENCHANTBONUS,
  SUPPLY,
  MUSIC,
  SMITHY,
  SPECIAL,
  REWARD,
  BREWING;

  enum LootType {
    POTION,
    MIXTURE,
    WEAPON,
    SPECIALTY,
    NOVELTY,
    TOOL,
    ARMOUR,
    ENCHANTED_BOOK,
  }

  public static IWeighted<RldItemStack> parseLootItem(JsonObject data, int weight) throws Exception {

    if (!data.has("type")) {
      return MinecraftItemLootItemParser.parse(data, weight);
    }

    String type = data.get("type").getAsString().toUpperCase();

    switch (LootType.valueOf(type)) {
      case POTION:
        return new WeightedChoice<>(new PotionLootItemParser().parsePotion(data).asStack(), weight);
      case MIXTURE:
        return new ItemMixture(data, weight);
      case WEAPON:
        return WeaponLootItemParser.parse(data, weight);
      case SPECIALTY:
        return SpecialtyLootItemParser.parse(data, weight);
      case NOVELTY:
        return NoveltyLootItemParser.parse(data, weight);
      case TOOL:
        return ToolLootItemParser.parse(data, weight);
      case ARMOUR:
        return ArmourLootItemParser.parse(data, weight);
      case ENCHANTED_BOOK:
        return parseEnchantedBookProvider(data, weight);
      default:
        throw new Exception("No such loot type as: " + type);
    }
  }

  private static EnchantedBookLootItem parseEnchantedBookProvider(JsonObject data, int weight) {
    int level = data.has("level") ? data.get("level").getAsInt() : 0;
    int enchantmentLevel = data.has("ench") ? data.get("ench").getAsInt() : 0;
    return new EnchantedBookLootItem(weight, level, enchantmentLevel);
  }

  public static IWeighted<RldItemStack> getLootItem(GreymerkChestType type, int level) {
    switch (type) {
      case WEAPON:
        return new WeaponLootItem(1, level);
      case ARMOUR:
        return new ArmourLootItem(1, level);
      case BLOCK:
        return new BlockLootItem(1, level);
      case JUNK:
        return new ItemJunk(1, level);
      case ORE:
        return new ItemOre(1, level);
      case TOOL:
        return new ToolLootItem(1, level);
      case POTION:
        return new PotionLootItem(1, level);
      case BREWING:
        return new ItemBrewing(1, level);
      case FOOD:
        return new ItemFood(1, level);
      case ENCHANTBOOK:
        return new EnchantedBookLootItem(1, level);
      case ENCHANTBONUS:
        return new ItemEnchBonus(1, level);
      case SUPPLY:
        return new SupplyLootItem(1, level);
      case MUSIC:
        return new RecordLootItem(1, level);
      case SMITHY:
        return new ItemSmithy(1, level);
      case SPECIAL:
        return new SpecialtyLootItem(1, level);
      case REWARD:
    }

    return new WeightedChoice<>(Material.Type.STICK.asItem().asStack(), 1);
  }

}
