package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.CouldNotMapException;
import com.github.fnar.minecraft.item.Enchantment;

public class EnchantmentMapper1_12 {

  public net.minecraft.enchantment.Enchantment map(Enchantment enchantment) {
    String name = getName(enchantment.getEffect());
    return net.minecraft.enchantment.Enchantment.getEnchantmentByLocation(name);
  }

  private static String getName(Enchantment.Effect effect) {
    switch (effect) {
      case AQUA_AFFINITY: return "aqua_affinity";
      case BANE_OF_ARTHROPODS: return "bane_of_arthropods";
      case BINDING_CURSE: break;
      case BLAST_PROTECTION: return "blast_protection";
      case CHANNELING: break;
      case DEPTH_STRIDER: return "depth_strider";
      case EFFICIENCY: return "efficiency";
      case FEATHER_FALLING: return "feather_falling";
      case FIRE_ASPECT: return "fire_aspect";
      case FIRE_PROTECTION: return "fire_protection";
      case FLAME: return "flame";
      case FORTUNE: return "fortune";
      case FROST_WALKER: break;
      case IMPALING: break;
      case INFINITY: return "infinity";
      case KNOCKBACK: return "knockback";
      case LOOTING: return "looting";
      case LOYALTY: break;
      case LUCK_OF_THE_SEA: return "luck_of_the_sea";
      case LURE: return "lure";
      case MENDING: return "mending";
      case MULTISHOT: break;
      case PIERCING: break;
      case POWER: return "power";
      case PROJECTILE_PROTECTION: return "projectile_protection";
      case PROTECTION: return "protection";
      case PUNCH: return "punch";
      case QUICK_CHARGE: break;
      case RESPIRATION: return "respiration";
      case RIPTIDE: break;
      case SHARPNESS: return "sharpness";
      case SILK_TOUCH: return "silk_touch";
      case SMITE: return "smite";
      case SWEEPING: break;
      case THORNS: return "thorns";
      case UNBREAKING: return "unbreaking";
      case VANISHING_CURSE: break;
    }
    throw new CouldNotMapException(effect.toString());
  }
}
