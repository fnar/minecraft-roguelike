package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Armour;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.CouldNotMapItemException;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.loot.Quality;

public class ArmourMapper1_12 extends RldBaseItemMapper1_12<Armour> {

  @Override
  public Class<Armour> getClazz() {
    return Armour.class;
  }

  @Override
  public ItemStack map(Armour rldItem) {
    Item item = map(rldItem, rldItem.getArmourType(), rldItem.getQuality());
    return map(rldItem, item);
  }

  public Item map(Armour item, ArmourType armourType, Quality quality) {
    switch(armourType) {
      case HELMET:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.GOLDEN_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET);
      case CHESTPLATE:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE);
      case HORSE:
        return EquipmentMapper1_12.map(quality, Items.SADDLE, Items.SADDLE, Items.IRON_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR, Items.DIAMOND_HORSE_ARMOR);
      case LEGGINGS:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS);
      case BOOTS:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.GOLDEN_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS);
    }
    throw new CouldNotMapItemException(item);
  }
}
