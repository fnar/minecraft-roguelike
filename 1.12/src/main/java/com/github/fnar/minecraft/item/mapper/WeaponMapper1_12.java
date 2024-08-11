package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Weapon;
import com.github.fnar.minecraft.item.WeaponType;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.loot.Quality;

public class WeaponMapper1_12 extends BaseItemMapper1_12<Weapon> {

  @Override
  public Class<Weapon> getClazz() {
    return Weapon.class;
  }

  @Override
  public ItemStack map(Weapon rldItem) throws CouldNotMapItemException {
    Item item = map(rldItem, rldItem.getWeaponType(), rldItem.getQuality());
    return addEnchantmentNbtTags(rldItem, item);
  }

  private Item map(Weapon item, WeaponType weaponType, Quality quality) throws CouldNotMapItemException {
    switch (weaponType) {
      case BOW:
        return EquipmentMapper1_12.map(quality, Items.BOW, Items.BOW, Items.BOW, Items.BOW, Items.BOW);
      case SWORD:
        return EquipmentMapper1_12.map(quality, Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD);
    }
    throw new CouldNotMapItemException(item);
  }
}
