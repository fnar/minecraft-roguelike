package com.github.fnar.minecraft.item;

import net.minecraft.item.Item;

import greymerk.roguelike.treasure.loot.Quality;

public class EquipmentMapper1_12 {
  public static Item map(Quality quality, Item wooden, Item stone, Item iron, Item golden, Item diamond) {
    switch (quality) {
      case WOOD:
        return wooden;
      case STONE:
        return stone;
      case IRON:
        return iron;
      case GOLD:
        return golden;
      case DIAMOND:
        return diamond;
    }
    return null;
  }
}
