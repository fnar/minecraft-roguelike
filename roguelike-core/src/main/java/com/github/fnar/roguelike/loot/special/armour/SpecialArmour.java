package com.github.fnar.roguelike.loot.special.armour;

import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;

public class SpecialArmour extends SpecialEquipment {

  public static ItemStack createArmour(Random random, int level) {
    Quality quality = ItemArmour.rollArmourQuality(random, level);
    return createArmour(random, quality);
  }

  public static ItemStack createArmour(Random random, Quality quality) {
    switch (random.nextInt(4)) {
      case 0:
        return new SpecialHelmet(random, quality).complete();
      case 1:
        return new SpecialChestplate(random, quality).complete();
      case 2:
        return new SpecialLeggings(random, quality).complete();
      case 3:
        return new SpecialBoots(random, quality).complete();
      default:
        return null;
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

  protected void withRandomColourIfWood() {
    if (quality == Quality.WOOD) {
      withRandomColor();
    }
  }

  protected void withProtection(Random random) {
    withEnchantment(Enchant.getEnchant(Enchant.PROTECTION), Enchant.getProtectionLevel(quality, random));
    withSuffix("of Defense");
  }

  protected void withProjectileProtection(Random random) {
    withEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), Enchant.getProtectionLevel(quality, random));
    withSuffix("of Deflection");
  }

  protected void withBlastProtection(Random random) {
    withEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), Enchant.getProtectionLevel(quality, random));
    withSuffix("of Integrity");
  }

  protected void withFlameProtection(Random random) {
    withEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), Enchant.getProtectionLevel(quality, random));
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
