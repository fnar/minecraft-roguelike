package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.RldItemStack;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;

public class LootItemParser {

  private enum LootType {
    POTION,
    MIXTURE,
    WEAPON,
    SPECIALTY,
    NOVELTY,
    TOOL,
    ARMOUR,
    ENCHANTED_BOOK,
  }

  public static IWeighted<RldItemStack> parseLootItem(JsonObject data, int weight) {
    return data.has("type")
        ? parseGreymerkLootItemThing(data, weight)
        : MinecraftItemLootItemParser.parse(data, weight);
  }

  private static IWeighted<RldItemStack> parseGreymerkLootItemThing(JsonObject data, int weight) {
    String type = data.get("type").getAsString().toUpperCase();

    switch (LootType.valueOf(type)) {
      case POTION:
        return new WeightedChoice<>(new PotionLootItemParser().parsePotion(data).asStack(), weight);
      case MIXTURE:
        return MixtureParser.parse(data, weight);
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
        return EnchantedBookLootItemParser.parse(data, weight);
      default:
        throw new DungeonSettingParseException("No such loot type as: " + type);
    }
  }

}
