package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.EnchantmentMapper;

public class EnchantmentMapper1_12 implements EnchantmentMapper {

  @Override
  public net.minecraft.enchantment.Enchantment map(Enchantment enchantment) {
    String name = getName(enchantment.getEnchant());
    return net.minecraft.enchantment.Enchantment.getEnchantmentByLocation(name);
  }

  private static String getName(Enchantment.Effect effect) {
    switch (effect) {
      case SHARPNESS:
        return "sharpness";
      case SMITE:
        return "smite";
      case BANE_OF_ARTHROPODS:
        return "bane_of_arthropods";
      case LOOTING:
        return "looting";
      case KNOCKBACK:
        return "knockback";
      case FIRE_ASPECT:
        return "fire_aspect";
      case AQUA_AFFINITY:
        return "aqua_affinity";
      case RESPIRATION:
        return "respiration";
      case FEATHER_FALLING:
        return "feather_falling";
      case DEPTH_STRIDER:
        return "depth_strider";
      case PROTECTION:
        return "protection";
      case BLAST_PROTECTION:
        return "blast_protection";
      case FIRE_PROTECTION:
        return "fire_protection";
      case PROJECTILE_PROTECTION:
        return "projectile_protection";
      case THORNS:
        return "thorns";
      case UNBREAKING:
        return "unbreaking";
      case EFFICIENCY:
        return "efficiency";
      case SILK_TOUCH:
        return "silk_touch";
      case FORTUNE:
        return "fortune";
      case POWER:
        return "power";
      case PUNCH:
        return "punch";
      case FLAME:
        return "flame";
      case INFINITY:
        return "infinity";
      case LURE:
        return "lure";
      case LUCK_OF_THE_SEA:
        return "luck_of_the_sea";
      case MENDING:
        return "mending";
      default:
        return "efficiency";
    }
  }
}
