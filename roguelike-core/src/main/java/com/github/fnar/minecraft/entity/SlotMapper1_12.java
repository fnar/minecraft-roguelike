package com.github.fnar.minecraft.entity;

import com.github.fnar.minecraft.CouldNotMapException;

import net.minecraft.inventory.EntityEquipmentSlot;

public class SlotMapper1_12 {
  public EntityEquipmentSlot map(Slot slot) {
    switch (slot) {
      case CHEST:
        return EntityEquipmentSlot.CHEST;
      case FEET:
        return EntityEquipmentSlot.FEET;
      case HEAD:
        return EntityEquipmentSlot.HEAD;
      case LEGS:
        return EntityEquipmentSlot.LEGS;
      case MAINHAND:
        return EntityEquipmentSlot.MAINHAND;
      case OFFHAND:
        return EntityEquipmentSlot.OFFHAND;
    }
    throw new CouldNotMapException(slot.toString());
  }
}
