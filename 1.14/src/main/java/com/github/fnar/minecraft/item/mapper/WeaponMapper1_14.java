package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Weapon;
import com.github.fnar.minecraft.item.WeaponType;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import greymerk.roguelike.treasure.loot.Quality;

public class WeaponMapper1_14 extends RldBaseItemMapper1_14<Weapon> {

  @Override
  public Class<Weapon> getClazz() {
    return Weapon.class;
  }

  @Override
  public ItemStack map(Weapon rldItem) {
    Item item = map(rldItem, rldItem.getWeaponType(), rldItem.getQuality());
    return map(rldItem, item);
  }

  private Item map(Weapon item, WeaponType weaponType, Quality quality) {
    switch(weaponType) {
      case BOW:
        return EquipmentMapper1_14.map(quality, Items.BOW, Items.BOW, Items.BOW, Items.BOW, Items.BOW);
      case SWORD:
        return EquipmentMapper1_14.map(quality, Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD);
    }
    throw new CouldNotMapItemException(item);
  }
}
