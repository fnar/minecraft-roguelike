package com.github.fnar.minecraft.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;

public enum ArmourType {
  HELMET,
  CHESTPLATE,
  LEGGINGS,
  BOOTS,
  ;

  public static ArmourType random(Random random) {
    int choice = random.nextInt(values().length);
    return values()[choice];
  }

  public Item asItem(Quality quality) {
    switch (this) {
      case HELMET:
        return EquipmentType.asItem(quality, Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.GOLDEN_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET);
      case CHESTPLATE:
        return EquipmentType.asItem(quality, Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE);
      case LEGGINGS:
        return EquipmentType.asItem(quality, Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS);
      case BOOTS:
        return EquipmentType.asItem(quality, Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.GOLDEN_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS);
    }
    throw new IllegalArgumentException("Unexpected ArmourType: " + this);
  }

  public Equipment asEquipment() {
    switch(this) {
      case HELMET:
        return Equipment.HELMET;
      case CHESTPLATE:
        return Equipment.CHEST;
      case LEGGINGS:
        return Equipment.LEGS;
      case BOOTS:
        return Equipment.FEET;
    }
    throw new IllegalArgumentException("Unexpected ArmourType: " + this);
  }

  public Slot asSlot() {
    switch(this) {
      case HELMET:
        return Slot.HEAD;
      case CHESTPLATE:
        return Slot.CHEST;
      case LEGGINGS:
        return Slot.LEGS;
      case BOOTS:
        return Slot.FEET;
    }
    throw new IllegalArgumentException("Unexpected ArmourType: " + this);
  }
}
