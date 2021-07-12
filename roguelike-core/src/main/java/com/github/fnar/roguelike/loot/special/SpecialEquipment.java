package com.github.fnar.roguelike.loot.special;

import com.github.fnar.roguelike.loot.special.armour.SpecialBoots;
import com.github.fnar.roguelike.loot.special.armour.SpecialChestplate;
import com.github.fnar.roguelike.loot.special.armour.SpecialHelmet;
import com.github.fnar.roguelike.loot.special.armour.SpecialLeggings;
import com.github.fnar.roguelike.loot.special.tools.SpecialAxe;
import com.github.fnar.roguelike.loot.special.tools.SpecialPickaxe;
import com.github.fnar.roguelike.loot.special.tools.SpecialShovel;
import com.github.fnar.roguelike.loot.special.weapons.SpecialBow;
import com.github.fnar.roguelike.loot.special.weapons.SpecialSword;
import com.github.fnar.util.Color;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.util.TextFormat;

public class SpecialEquipment {
  private final StringBuilder prefix = new StringBuilder();
  private final StringBuilder canonicalName = new StringBuilder();
  private final StringBuilder suffix = new StringBuilder();
  protected Quality quality;
  private ItemStack itemStack;

  public static ItemStack getRandomEquipment(Random random, int level) {
    Equipment equipment = Equipment.random(random);
    return getRandomEquipment(random, equipment, level);
  }

  public static ItemStack getRandomEquipment(Random random, Equipment type, int level) {
    Quality quality = Quality.rollRandomQualityByLevel(random, level, type);
    return getRandomEquipment(random, type, quality);
  }

  public static ItemStack getRandomEquipment(Random random, Equipment type, Quality quality) {
    switch (type) {
      case SWORD:
        return new SpecialSword(random, quality).complete();
      case BOW:
        return new SpecialBow(random, quality).complete();
      case HELMET:
        return new SpecialHelmet(random, quality).complete();
      case CHEST:
        return new SpecialChestplate(random, quality).complete();
      case LEGS:
        return new SpecialLeggings(random, quality).complete();
      case FEET:
        return new SpecialBoots(random, quality).complete();
      case PICK:
        return new SpecialPickaxe(random, quality).complete();
      case AXE:
        return new SpecialAxe(random, quality).complete();
      case SHOVEL:
        return new SpecialShovel(random, quality).complete();
      default:
        return null;
    }
  }

  public SpecialEquipment withItem(Item item) {
    this.itemStack = new ItemStack(item);
    return this;
  }

  public SpecialEquipment withQuality(Quality quality) {
    this.quality = quality;
    return this;
  }

  public SpecialEquipment withName(String name) {
    canonicalName.append(name);
    return this;
  }

  protected SpecialEquipment withLore(String s, TextFormat textFormatColor) {
    Loot.setItemLore(itemStack, s, textFormatColor);
    return this;
  }

  public SpecialEquipment withRandomColor() {
    withColor(Color.random());
    return this;
  }

  public SpecialEquipment withColor(Color color) {
    ItemArmour.dyeArmor(itemStack, color);
    return this;
  }

  public SpecialEquipment withEnchantment(Enchantment enchantment, int level) {
    itemStack.addEnchantment(enchantment, level);
    return this;
  }

  public SpecialEquipment withCommonEnchantments(Random random) {
    withMending(random);
    withUnbreaking(random);
    return this;
  }

  public void withMending(Random random) {
    if (random.nextInt(20) == 0) {
      withEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
      withPrefix("Prideful");
    }
  }

  public void withUnbreaking(Random random) {
    int enchantmentLevel = random.nextInt(5) - 1;
    if (enchantmentLevel > 0) {
      withEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), enchantmentLevel);

      if (enchantmentLevel == 1) {
        withPrefix("Reinforced");
      } else if (enchantmentLevel == 2) {
        withPrefix("Tempered");
      } else if (enchantmentLevel >= 3) {
        withPrefix("Masterwork");
      }
    }
  }

  public SpecialEquipment withPrefix(String prefix) {
    this.prefix.append(prefix).append(" ");
    return this;
  }

  public SpecialEquipment withSuffix(String suffix) {
    this.suffix.append(" ").append(suffix);
    return this;
  }

  public ItemStack complete() {
    String name = new StringBuilder()
        .append(prefix)
        .append(canonicalName)
        .append(suffix)
        .toString();
    itemStack.setStackDisplayName(name);
    return itemStack;
  }
}
