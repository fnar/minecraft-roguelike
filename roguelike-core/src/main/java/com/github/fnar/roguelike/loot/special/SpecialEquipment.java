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
  protected Quality quality;
  protected RldItem rldItem;
  private String lore;

  public static RldItemStack newRandomSpecialEquipment(Random random, Equipment type, Quality quality) {
    switch (type) {
      case SWORD:
        return SpecialSword.newSpecialSword(random, quality);
      case BOW:
        return SpecialBow.newSpecialBow(random, quality);
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
      case HOE:
        return new SpecialHoe(random, quality).complete();
      default:
        return null;
    }
  }

  public static int getProtectionLevel(Quality quality, Random rand) {
    switch (quality) {
      case WOOD:
        return 1 + (rand.nextInt(3) == 0 ? 1 : 0);
      case STONE:
        return 1 + (rand.nextBoolean() ? 1 : 0);
      case IRON:
      case GOLD:
        return 1 + rand.nextInt(3);
      case DIAMOND:
        return 1 + 2 + rand.nextInt(2);
      default:
        return 1;
    }
  }

  public SpecialEquipment withRldItem(RldItem rldItem) {
    this.rldItem = rldItem;
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
    lore = textFormatColor.apply(s);
    return this;
  }

  public SpecialEquipment withEnchantment(Enchantment.Effect enchantment, int level) {
    ((RldBaseItem) rldItem).withEnchantment(enchantment.asEnchantment().withLevel(level));
    return this;
  }

  public SpecialEquipment withCommonEnchantments(Random random) {
    withMending(random);
    withUnbreaking(random);
    return this;
  }

  public void withMending(Random random) {
    if (random.nextDouble() >= .03) {
      return;
    }

    withEnchantment(Enchantment.Effect.MENDING, 1);

    String[] descriptors = new String[]{
        "Prideful",
        "Forbidden",
        "Fnar's Lucky",
        "Greymerk's Lost",
        "Cheater's",
        "Quicksilver",
        "Living",
        "Autonomous"
    };

    withPrefix(descriptors[random.nextInt(descriptors.length)]);
  }

  public void withUnbreaking(Random random) {
    int enchantmentLevel = random.nextInt(5) - 1;
    if (enchantmentLevel > 0) {
      withEnchantment(Enchantment.Effect.UNBREAKING, enchantmentLevel);

      if (enchantmentLevel >= 3) {
        withPrefix("Masterwork");
      }
      if (enchantmentLevel == 2) {
        withPrefix("Tempered");
      }
      if (enchantmentLevel == 1) {
        withPrefix("Reinforced");
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

  public RldItemStack complete() {
    String name = String.valueOf(prefix) + canonicalName + suffix;

    RldItemStack rldItemStack = rldItem.asStack().withDisplayName(name);
    if (lore != null) {
      return rldItemStack.withDisplayLore(lore);
    }
    return rldItemStack;
  }
}
