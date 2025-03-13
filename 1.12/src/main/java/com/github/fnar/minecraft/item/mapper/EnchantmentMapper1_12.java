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

  public static int mapNameToId(String enchantmentId) {
    try {
      return Integer.parseInt(enchantmentId);
    } catch (NumberFormatException nfe) {
      String minecraftNamespace = "minecraft";
      String enchantmentNameWithoutNamespace = enchantmentId.replace(minecraftNamespace + ":", "");
      switch (enchantmentNameWithoutNamespace) {
        case "aqua_affinity":
          return 6;
        case "bane_of_arthropods":
          return 18;
        case "blast_protection":
          return 3;
        case "breach":
          return 0;
        case "channeling":
          return 68;
        case "binding_curse":
          return 10;
        case "vanishing_curse":
          return 71;
        case "density":
          return 0;
        case "depth_strider":
          return 8;
        case "efficiency":
          return 32;
        case "feather_falling":
          return 2;
        case "fire_aspect":
          return 20;
        case "fire_protection":
          return 1;
        case "flame":
          return 50;
        case "fortune":
          return 35;
        case "frost_walker":
          return 9;
        case "impaling":
          return 66;
        case "infinity":
          return 51;
        case "knockback":
          return 19;
        case "looting":
          return 21;
        case "loyalty":
          return 65;
        case "luck_of_the_sea":
          return 61;
        case "lure":
          return 62;
        case "mending":
          return 70;
        case "multishot":
          return 0;
        case "piercing":
          return 0;
        case "power":
          return 48;
        case "projectile_protection":
          return 4;
        case "protection":
          return 0;
        case "punch":
          return 49;
        case "quick_charge":
          return 0;
        case "respiration":
          return 5;
        case "riptide":
          return 67;
        case "sharpness":
          return 16;
        case "silk_touch":
          return 33;
        case "smite":
          return 17;
        case "soul_speed":
          return 0;
        case "sweeping_edge":
          return 0;
        case "swift_sneak":
          return 0;
        case "thorns":
          return 7;
        case "unbreaking":
          return 34;
        case "wind_burst":
          return 0;
        default:
          return 0;
      }
    }
  }
}
