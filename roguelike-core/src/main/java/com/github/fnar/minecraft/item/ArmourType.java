package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.entity.Slot;

import java.util.Random;

public enum ArmourType {

  BOOTS,
  CHESTPLATE,
  HELMET,
  HORSE,
  LEGGINGS,
  ;

  public static ArmourType random(Random random) {
    int choice = random.nextInt(values().length);
    return values()[choice];
  }

  public Armour asItem() {
    return new Armour(this);
  }

  public Slot asSlot() {
    switch (this) {
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
