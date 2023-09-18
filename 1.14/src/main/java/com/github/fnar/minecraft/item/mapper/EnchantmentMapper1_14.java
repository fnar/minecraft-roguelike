package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.CouldNotMapException;
import com.github.fnar.minecraft.item.Enchantment;

import net.minecraft.enchantment.Enchantments;

public class EnchantmentMapper1_14 {

  public net.minecraft.enchantment.Enchantment map(Enchantment enchantment) {
    Enchantment.Effect effect = enchantment.getEffect();
    switch (effect) {
      case AQUA_AFFINITY: return Enchantments.AQUA_AFFINITY;
      case BANE_OF_ARTHROPODS: return Enchantments.BANE_OF_ARTHROPODS;
      case BINDING_CURSE: return Enchantments.BINDING_CURSE;
      case BLAST_PROTECTION: return Enchantments.BLAST_PROTECTION;
      case CHANNELING: return Enchantments.CHANNELING;
      case DEPTH_STRIDER: return Enchantments.DEPTH_STRIDER;
      case EFFICIENCY: return Enchantments.EFFICIENCY;
      case FEATHER_FALLING: return Enchantments.FEATHER_FALLING;
      case FIRE_ASPECT: return Enchantments.FIRE_ASPECT;
      case FIRE_PROTECTION: return Enchantments.FIRE_PROTECTION;
      case FLAME: return Enchantments.FLAME;
      case FORTUNE: return Enchantments.FORTUNE;
      case FROST_WALKER: return Enchantments.FROST_WALKER;
      case IMPALING: return Enchantments.IMPALING;
      case INFINITY: return Enchantments.INFINITY;
      case KNOCKBACK: return Enchantments.KNOCKBACK;
      case LOOTING: return Enchantments.LOOTING;
      case LOYALTY: return Enchantments.LOYALTY;
      case LUCK_OF_THE_SEA: return Enchantments.LUCK_OF_THE_SEA;
      case LURE: return Enchantments.LURE;
      case MENDING: return Enchantments.MENDING;
      case MULTISHOT: return Enchantments.MULTISHOT;
      case PIERCING: return Enchantments.PIERCING;
      case POWER: return Enchantments.POWER;
      case PROJECTILE_PROTECTION: return Enchantments.PROJECTILE_PROTECTION;
      case PROTECTION: return Enchantments.PROTECTION;
      case PUNCH: return Enchantments.PUNCH;
      case QUICK_CHARGE: return Enchantments.QUICK_CHARGE;
      case RESPIRATION: return Enchantments.RESPIRATION;
      case RIPTIDE: return Enchantments.RIPTIDE;
      case SHARPNESS: return Enchantments.SHARPNESS;
      case SILK_TOUCH: return Enchantments.SILK_TOUCH;
      case SMITE: return Enchantments.SMITE;
      case SWEEPING: return Enchantments.SWEEPING;
      case THORNS: return Enchantments.THORNS;
      case UNBREAKING: return Enchantments.UNBREAKING;
      case VANISHING_CURSE: return Enchantments.VANISHING_CURSE;
    }
    throw new CouldNotMapException(effect.toString());
  }

}
