package com.github.fnar.roguelike.loot.special.weapons;

import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.Weapon;
import com.github.fnar.minecraft.item.WeaponType;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class SpecialSword extends SpecialEquipment {

  private static Weapon getItem() {
    return WeaponType.SWORD.asItem();
  }

  @Override
  protected SpecialSword withQuality(Quality quality) {
    super.withQuality(quality);
    return this;
  }

  @Override
  protected SpecialSword withRldItem(RldItem rldItem) {
    super.withRldItem(rldItem);
    return this;
  }

  public static SpecialEquipment newSpecialSword(Random random, Quality quality) {
    return new SpecialSword()
        .withQuality(quality)
        .withRldItem(getItem().withQuality(quality))
        .withSwordEnchantments(random)
        .withCommonEnchantments(random)
        .withName(quality.getDescriptor() + " Blade");
  }

  public SpecialSword withSwordEnchantments(Random random) {
    return this.withSharpness(random)
        .withLooting(random)
        .withFiery(random);
  }

  public SpecialSword withSharpness(Random random) {
    if (random.nextBoolean()) {
      return this;
    }
    int enchantmentLevel = random.nextInt(6);
    if (enchantmentLevel <= 0) {
      return this;
    }
    withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(enchantmentLevel));
    return this;
  }

  public SpecialSword withLooting(Random random) {
    if (random.nextBoolean()) {
      return this;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return this;
    }
    withEnchantment(Enchantment.Effect.LOOTING.atLevel(enchantmentLevel));
    if (enchantmentLevel < 3) {
      withPrefix("Burglar's");
    } else {
      //withLore("Once belonged to a king of hidden desert thieves.", TextFormat.DARKGREEN);
      withLore("lore.roguelike.looting3");
      withPrefix("Bandit King's");
    }
    return this;
  }

  public SpecialSword withFiery(Random random) {
    if (random.nextBoolean()) {
      return this;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return this;
    }
    withEnchantment(Enchantment.Effect.FIRE_ASPECT.atLevel(enchantmentLevel));
    if (enchantmentLevel == 1) {
      //withLore("Warm to the touch", TextFormat.YELLOW);
      withLore("lore.roguelike.fire_aspect1");
      withPrefix("Ember");
      return this;
    }

    if (enchantmentLevel == 2) {
      //withLore("Almost too hot to hold", TextFormat.RED);
      withLore("lore.roguelike.fire_aspect2");
      withPrefix("Fiery");
      return this;
    }
    //withLore("From the fiery depths", TextFormat.DARKRED);
    withLore("lore.roguelike.fire_aspect3");
    withSuffix("of the Inferno");
    return this;
  }

}
