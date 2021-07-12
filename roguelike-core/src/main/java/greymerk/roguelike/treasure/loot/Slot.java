package greymerk.roguelike.treasure.loot;

import net.minecraft.inventory.EntityEquipmentSlot;

public enum Slot {

  WEAPON,
  HEAD,
  CHEST,
  LEGS,
  FEET;

  public static EntityEquipmentSlot getSlot(Slot slot) {
    switch (slot) {
      case HEAD:
        return EntityEquipmentSlot.HEAD;
      case CHEST:
        return EntityEquipmentSlot.CHEST;
      case LEGS:
        return EntityEquipmentSlot.LEGS;
      case FEET:
        return EntityEquipmentSlot.FEET;
      case WEAPON:
        return EntityEquipmentSlot.MAINHAND;
      default:
        return null;
    }
  }

}
