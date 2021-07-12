package com.github.fnar.minecraft.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import greymerk.roguelike.treasure.loot.Quality;

public class WeaponMapper1_12 {

  public Item map(Quality quality, WeaponType weaponType) {
    switch(weaponType) {
      case BOW:
        return EquipmentMapper1_12.map(quality, Items.BOW, Items.BOW, Items.BOW, Items.BOW, Items.BOW);
      case SWORD:
        return EquipmentMapper1_12.map(quality, Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD);
    }
    return null;
  }
}
