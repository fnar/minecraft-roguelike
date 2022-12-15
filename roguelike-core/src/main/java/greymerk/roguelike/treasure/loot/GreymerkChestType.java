package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;

import greymerk.roguelike.treasure.loot.provider.ArmourLootItem;
import greymerk.roguelike.treasure.loot.provider.BlockLootItem;
import greymerk.roguelike.treasure.loot.provider.EnchantedBookLootItem;
import greymerk.roguelike.treasure.loot.provider.GardenLootItem;
import greymerk.roguelike.treasure.loot.provider.ItemBrewing;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.treasure.loot.provider.ItemJunk;
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

  ARMOUR,
  BLOCK,
  BREWING,
  ENCHANTBONUS,
  ENCHANTBOOK,
  FOOD,
  GARDEN,
  JUNK,
  MUSIC,
  ORE,
  POTION,
  REWARD,
  SMITHY,
  SPECIAL,
  SUPPLY,
  TOOL,
  WEAPON,
  ;

  public static IWeighted<RldItemStack> getLootItem(GreymerkChestType type, int level) {
    switch (type) {

      case ARMOUR:
        return new ArmourLootItem(1, level);
      case BLOCK:
        return new BlockLootItem(1, level);
      case BREWING:
        return new ItemBrewing(1, level);
      case FOOD:
        return new ItemFood(1, level);
      case ENCHANTBOOK:
        return new EnchantedBookLootItem(1, level);
      case ENCHANTBONUS:
        return new ItemEnchBonus(1, level);
      case GARDEN:
        return new GardenLootItem(1, level);
      case JUNK:
        return new ItemJunk(1, level);
      case MUSIC:
        return new RecordLootItem(1, level);
      case ORE:
        return new ItemOre(1, level);
      case POTION:
        return new PotionLootItem(1, level);
      case REWARD:
        break;
      case TOOL:
        return new ToolLootItem(1, level);
      case SMITHY:
        return new ItemSmithy(1, level);
      case SPECIAL:
        return new SpecialtyLootItem(1, level);
      case SUPPLY:
        return new SupplyLootItem(1, level);
      case WEAPON:
        return new WeaponLootItem(1, level);
    }

    return new WeightedChoice<>(Material.Type.STICK.asItem().asStack(), 1);
  }

}
