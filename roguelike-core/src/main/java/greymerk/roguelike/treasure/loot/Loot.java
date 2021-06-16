package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonObject;

import com.github.srwaggon.minecraft.item.PotionMapper1_12;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.Random;

import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemBlock;
import greymerk.roguelike.treasure.loot.provider.ItemBrewing;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.treasure.loot.provider.ItemJunk;
import greymerk.roguelike.treasure.loot.provider.ItemMixture;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.treasure.loot.provider.ItemOre;
import greymerk.roguelike.treasure.loot.provider.ItemPotion;
import greymerk.roguelike.treasure.loot.provider.ItemRecord;
import greymerk.roguelike.treasure.loot.provider.ItemSmithy;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.provider.ItemSupply;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.util.WeightedChoice;

public enum Loot {

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

  public static ILoot getLoot() {

    LootProvider loot = new LootProvider();
    for (int i = 0; i < 5; ++i) {
      loot.put(i, new LootSettings(i));
    }

    return loot;
  }

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

  public static IWeighted<ItemStack> get(JsonObject data, int weight) throws Exception {

    if (!data.has("type")) {
      return new WeightedRandomLoot(data, weight);
    }

    String type = data.get("type").getAsString().toUpperCase();

    switch (LootType.valueOf(type)) {
      case POTION:
        return new WeightedChoice<>(PotionMapper1_12.parsePotion(data), weight);
      case MIXTURE:
        return new ItemMixture(data, weight);
      case WEAPON:
        return new ItemWeapon(data, weight);
      case SPECIALTY:
        return new ItemSpecialty(data, weight);
      case NOVELTY:
        return ItemNovelty.get(data, weight);
      case TOOL:
        return new ItemTool(data, weight);
      case ARMOUR:
        return new ItemArmour(data, weight);
      case ENCHANTED_BOOK:
        return new ItemEnchBook(data, weight);
      default:
        throw new Exception("No such loot type as: " + type);
    }
  }

  public static IWeighted<ItemStack> getProvider(Loot type, int level) {
    switch (type) {
      case WEAPON:
        return new ItemWeapon(1, level);
      case ARMOUR:
        return new ItemArmour(1, level);
      case BLOCK:
        return new ItemBlock(1, level);
      case JUNK:
        return new ItemJunk(1, level);
      case ORE:
        return new ItemOre(1, level);
      case TOOL:
        return new ItemTool(1, level);
      case POTION:
        return new ItemPotion(1, level);
      case BREWING:
        return new ItemBrewing(1, level);
      case FOOD:
        return new ItemFood(1, level);
      case ENCHANTBOOK:
        return new ItemEnchBook(1, level);
      case ENCHANTBONUS:
        return new ItemEnchBonus(1, level);
      case SUPPLY:
        return new ItemSupply(1, level);
      case MUSIC:
        return new ItemRecord(1, level);
      case SMITHY:
        return new ItemSmithy(1, level);
      case SPECIAL:
        return new ItemSpecialty(1, level);
      case REWARD:
    }

    return new WeightedRandomLoot(Items.STICK, 0, 1);
  }

  public static ItemStack getEquipmentBySlot(Random rand, Slot slot, int level, boolean enchant) {

    if (slot == Slot.WEAPON) {
      return ItemWeapon.getRandom(rand, level, enchant);
    }

    return ItemArmour.getRandom(rand, level, slot, enchant);
  }

  public static void setItemLore(ItemStack item, String loreText) {

    NBTTagCompound tag = item.getTagCompound();

    if (tag == null) {
      tag = new NBTTagCompound();
      item.setTagCompound(tag);
    }

    if (!tag.hasKey("display")) {
      tag.setTag("display", new NBTTagCompound());
    }

    NBTTagCompound display = tag.getCompoundTag("display");

    if (!(display.hasKey("Lore"))) {
      display.setTag("Lore", new NBTTagList());
    }

    NBTTagList lore = display.getTagList("Lore", 0);

    NBTTagString toAdd = new NBTTagString(loreText);

    lore.appendTag(toAdd);

    display.setTag("Lore", lore);
  }

  public static void setItemLore(ItemStack item, String loreText, TextFormat option) {
    setItemLore(item, option.apply(loreText));
  }

  public static void setItemName(ItemStack item, String name, TextFormat option) {
    item.setStackDisplayName(option.apply(name));
  }

}
