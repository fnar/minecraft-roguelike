package com.github.fnar.roguelike.loot.special.armour;

import com.github.fnar.minecraft.item.Armour;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

public class SpecialArmour extends SpecialEquipment {

  public static SpecialArmour createArmour(Random random, ArmourType armourType, Quality quality) {
    switch (armourType) {
      case HELMET:
        return new SpecialHelmet(random, quality);
      case CHESTPLATE:
        return new SpecialChestplate(random, quality);
      case LEGGINGS:
        return new SpecialLeggings(random, quality);
      case BOOTS:
      default:
        return new SpecialBoots(random, quality);
    }
  }

  protected SpecialArmour withArmourPrefix() {
    withPrefix(getArmourPrefix());
    return this;
  }

  private String getArmourPrefix() {
    switch (quality) {
      case WOOD:
        return "Surplus";
      case STONE:
        return "Riveted";
      case IRON:
        return "Gothic";
      case GOLD:
        return "Jewelled";
      case DIAMOND:
        return "Crystal";
      default:
        return "Strange";
    }
  }

  protected void withRandomColour(Random random) {
    if (quality == Quality.WOOD) {
      ((Armour) rldItem).withColor(Color.random(random));
    }
  }

  protected void withProtection(Random random) {
    withEnchantment(Enchantment.Effect.PROTECTION, SpecialEquipment.getProtectionLevel(quality, random));
    withSuffix("of Defense");
  }

  protected void withProjectileProtection(Random random) {
    withEnchantment(Enchantment.Effect.PROJECTILE_PROTECTION, SpecialEquipment.getProtectionLevel(quality, random));
    withSuffix("of Deflection");
  }

  protected void withBlastProtection(Random random) {
    withEnchantment(Enchantment.Effect.BLAST_PROTECTION, SpecialEquipment.getProtectionLevel(quality, random));
    withSuffix("of Integrity");
  }

  protected void withFlameProtection(Random random) {
    withEnchantment(Enchantment.Effect.FIRE_PROTECTION, SpecialEquipment.getProtectionLevel(quality, random));
    withSuffix("of Flamewarding");
  }

  protected void withArmorEnchantments(Random random) {
    if (random.nextInt(10) == 0) {
      withFlameProtection(random);
    } else if (random.nextInt(10) == 0) {
      withBlastProtection(random);
    } else if (random.nextInt(3) == 0) {
      withProjectileProtection(random);
    } else {
      withProtection(random);
    }
  }
}
