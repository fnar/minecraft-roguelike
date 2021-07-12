package com.github.fnar.minecraft.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import greymerk.roguelike.treasure.loot.Quality;

public class ArmourMapper1_12 {
  public Item map(Quality quality, ArmourType armourType) {
    switch(armourType) {
      case HELMET:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.GOLDEN_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET);
      case CHESTPLATE:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE);
      case LEGGINGS:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS);
      case BOOTS:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.GOLDEN_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS);
    }
    return null;
  }

}
