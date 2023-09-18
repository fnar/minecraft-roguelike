package com.github.fnar.minecraft.entity;

import com.github.fnar.minecraft.CouldNotMapException;

import net.minecraft.inventory.EquipmentSlotType;

public class SlotMapper1_14 {
  public EquipmentSlotType map(Slot slot) {
    switch (slot) {
      case CHEST:
        return EquipmentSlotType.CHEST;
      case FEET:
        return EquipmentSlotType.FEET;
      case HEAD:
        return EquipmentSlotType.HEAD;
      case LEGS:
        return EquipmentSlotType.LEGS;
      case MAINHAND:
        return EquipmentSlotType.MAINHAND;
      case OFFHAND:
        return EquipmentSlotType.OFFHAND;
    }
    throw new CouldNotMapException(slot.toString());
  }
}
