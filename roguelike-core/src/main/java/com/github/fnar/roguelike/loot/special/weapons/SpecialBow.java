package com.github.fnar.roguelike.loot.special.weapons;

import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.Weapon;
import com.github.fnar.minecraft.item.WeaponType;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class SpecialBow extends SpecialEquipment {

  private static Weapon getItem() {
    return WeaponType.BOW.asItem();
  }

  @Override
  protected SpecialBow withQuality(Quality quality) {
    super.withQuality(quality);
    return this;
  }

  @Override
  protected SpecialBow withRldItem(RldItem rldItem) {
    super.withRldItem(rldItem);
    return this;
  }

  private SpecialBow withPower(Random random) {
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return this;
    }
    withEnchantment(Enchantment.Effect.POWER.atLevel(enchantmentLevel));
    return this;
  }

  public static SpecialEquipment newSpecialBow(Random random, Quality quality) {
    switch (quality) {
      case WOOD:
      case STONE:
        return yewLongbow(random);
      default:
      case IRON:
        return laminatedBow(random);
      case GOLD:
        return random.nextBoolean() ? elvenBow(random)
            : random.nextBoolean() ? faerieBow(random)
                : recurveBow(random);
      case DIAMOND:
        return eldritchBow(random);
    }
  }

  public static SpecialEquipment yewLongbow(Random random) {
    return new SpecialBow()
        .withQuality(Quality.STONE)
        .withRldItem(getItem())
        .withCommonEnchantments(random)
        //.withName("Yew Longbow")
        .withName("stone.bow")
        .withLore("Superior craftsmanship", TextFormat.DARKGREEN)
        .withLocLore("lore.roguelike.yew_bow");
  }

  private static SpecialEquipment laminatedBow(Random random) {
    return new SpecialBow()
        .withQuality(Quality.IRON)
        .withRldItem(getItem())
        .withPower(random)
        .withCommonEnchantments(random)
        //.withName("Laminated Bow")
        .withName("iron.bow")
        .withLore("Highly polished", TextFormat.DARKGREEN)
        .withLocLore("lore.roguelike.laminated_bow");
  }

  private static SpecialEquipment elvenBow(Random random) {
    return new SpecialBow()
        .withQuality(Quality.GOLD)
        .withRldItem(getItem())
        .withPower(random)
        .withEnchantment(Enchantment.Effect.INFINITY)
        .withCommonEnchantments(random)
        //.withName("Elven Bow")
        .withName("gold_0.bow")
        .withLore("Beautifully crafted", TextFormat.DARKGREEN)
        .withLocLore("lore.roguelike.elven_bow");
  }

  private static SpecialEquipment faerieBow(Random random) {
    return new SpecialBow()
        .withQuality(Quality.GOLD)
        .withRldItem(getItem())
        .withPower(random)
        .withEnchantment(Enchantment.Effect.MENDING)
        .withCommonEnchantments(random)
        //.withName("Faerie Bow")
        .withName("gold_1.bow")
        .withLore("Blessed by the Dreaming", TextFormat.DARKGREEN)
        .withLocLore("lore.roguelike.faerie_bow");
  }

  private static SpecialEquipment recurveBow(Random random) {
    return new SpecialBow()
        .withQuality(Quality.GOLD)
        .withRldItem(getItem())
        .withPower(random)
        .withCommonEnchantments(random)
        //.withName("Recurve Bow")
        .withName("gold_2.bow")
        .withLore("Curves outward toward the target", TextFormat.DARKGREEN)
        .withLocLore("lore.roguelike.recurve_bow");
  }

  private static SpecialEquipment eldritchBow(Random random) {
    return new SpecialBow()
        .withQuality(Quality.DIAMOND)
        .withRldItem(getItem())
        .withPower(random)
        .withEnchantment(Enchantment.Effect.FLAME)
        .withCommonEnchantments(random)
        //.withName("Eldritch Bow")
        .withName("diamond.bow")
        .withLore("Warm to the touch", TextFormat.DARKGREEN)
        .withLocLore("lore.roguelike.eldritch_bow");
  }

}
