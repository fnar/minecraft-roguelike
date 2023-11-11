package com.github.fnar.roguelike.loot.special;

import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldBaseItem;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.loot.special.armour.SpecialBoots;
import com.github.fnar.roguelike.loot.special.armour.SpecialChestplate;
import com.github.fnar.roguelike.loot.special.armour.SpecialHelmet;
import com.github.fnar.roguelike.loot.special.armour.SpecialLeggings;
import com.github.fnar.roguelike.loot.special.tools.SpecialAxe;
import com.github.fnar.roguelike.loot.special.tools.SpecialHoe;
import com.github.fnar.roguelike.loot.special.tools.SpecialPickaxe;
import com.github.fnar.roguelike.loot.special.tools.SpecialShovel;
import com.github.fnar.roguelike.loot.special.weapons.SpecialBow;
import com.github.fnar.roguelike.loot.special.weapons.SpecialSword;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class SpecialEquipment {

  private final StringBuilder prefix = new StringBuilder();
  private final StringBuilder canonicalName = new StringBuilder();
  private final StringBuilder suffix = new StringBuilder();
  private Quality quality;
  protected RldItem rldItem;
  private String lore, loclore;

  public static RldItemStack newRandomSpecialEquipment(Random random, Equipment type, Quality quality) {
    switch (type) {
      case SWORD:
        return SpecialSword.newSpecialSword(random, quality).asStack();
      case BOW:
        return SpecialBow.newSpecialBow(random, quality).asStack();
      case HELMET:
        return new SpecialHelmet(random, quality).asStack();
      case CHEST:
        return new SpecialChestplate(random, quality).asStack();
      case LEGS:
        return new SpecialLeggings(random, quality).asStack();
      case FEET:
        return new SpecialBoots(random, quality).asStack();
      case PICK:
        return new SpecialPickaxe(random, quality).asStack();
      case AXE:
        return new SpecialAxe(random, quality).asStack();
      case SHOVEL:
        return new SpecialShovel(random, quality).asStack();
      case HOE:
        return new SpecialHoe(random, quality).asStack();
      default:
        return null;
    }
  }

  protected SpecialEquipment withRldItem(RldItem rldItem) {
    this.rldItem = rldItem;
    return this;
  }

  protected SpecialEquipment withQuality(Quality quality) {
    this.setQuality(quality);
    return this;
  }

  public SpecialEquipment withName(String name) {
    canonicalName.append(name);
    return this;
  }

  public SpecialEquipment withLore(String s) {
    lore = s;
    return this;
  }

  public SpecialEquipment withLore(String s, TextFormat textFormatColor) {
    lore = textFormatColor.apply(s);
    return this;
  }

  public SpecialEquipment withLocLore(String langkey) {
    loclore = langkey;
    return this;
  }

  protected SpecialEquipment withEnchantment(Enchantment.Effect effect) {
    return this.withEnchantment(effect.asEnchantment());
  }

  protected SpecialEquipment withEnchantment(Enchantment enchantment) {
    ((RldBaseItem) rldItem).withEnchantment(enchantment);
    return this;
  }

  public SpecialEquipment withCommonEnchantments(Random random) {
    return this
        .withMending(random)
        .withUnbreaking(random);
  }

  protected SpecialEquipment withMending(Random random) {
    if (random.nextDouble() >= .03) {
      return this;
    }

    withEnchantment(Enchantment.Effect.MENDING);

    String[] descriptors = new String[]{
        "mend_0", // "Alchemical",
        "mend_1", // "Prideful",
        "mend_2", // "Forbidden",
        "mend_3", // "Fnar's Lucky",
        "mend_4", // "Greymerk's Lost",
        "mend_5", // "Cheater's",
        "mend_6", // "Quicksilver",
        "mend_7", // "Living",
        "mend_8", // "Autonomous"
    };

    withPrefix(descriptors[random.nextInt(descriptors.length)]);
    return this;
  }

  public SpecialEquipment withUnbreaking(Random random) {
    int enchantmentLevel = random.nextInt(5) - 1;
    if (enchantmentLevel > 0) {
      withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(enchantmentLevel));

      if (enchantmentLevel >= 3) {
        //withPrefix("Masterwork");
        withPrefix("unbr3");
      }
      if (enchantmentLevel == 2) {
        //withPrefix("Tempered");
        withPrefix("unbr2");
        withLore("Highly Durable", TextFormat.DARKGREEN);
        withLocLore("lore.roguelike.unbreaking2");
      }
      if (enchantmentLevel == 1) {
        //withPrefix("Reinforced");
        withPrefix("unbr1");
      }
    }
    return this;
  }

  protected SpecialEquipment withPrefix(String prefix) {
    this.prefix.append(prefix).append(".");
    return this;
  }

  protected SpecialEquipment withSuffix(String suffix) {
    this.suffix.append(".").append(suffix);
    return this;
  }

  public RldItemStack asStack() {
    String name = "item.roguelike." + String.valueOf(prefix) + canonicalName + suffix;

    RldItemStack rldItemStack = rldItem.asStack().withDisplayName(name);
    if (lore != null) {
      rldItemStack.withDisplayLore(lore);
    }
    if (loclore != null){
      rldItemStack.withDisplayLocLore(loclore);
    }
    return rldItemStack;
  }

  protected Quality getQuality() {
    return quality;
  }

  protected void setQuality(Quality quality) {
    this.quality = quality;
  }
}
