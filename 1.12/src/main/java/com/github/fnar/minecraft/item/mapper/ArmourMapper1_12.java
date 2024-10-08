package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Armour;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.util.Color;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import greymerk.roguelike.treasure.loot.Quality;

public class ArmourMapper1_12 extends BaseItemMapper1_12<Armour> {

  private static void applyColourTags(Armour rldItem, ItemStack itemStack) {
    Color color = rldItem.getColor();
    if (color == null) {
      return;
    }
    if (!Quality.WOOD.equals(rldItem.getQuality())) {
      return;
    }
    if (ArmourType.HORSE.equals(rldItem.getArmourType())) {
      return;
    }
    NBTTagCompound tags = itemStack.getTagCompound();

    if (tags == null) {
      tags = new NBTTagCompound();
      itemStack.setTagCompound(tags);
    }

    NBTTagCompound displayTag = tags.getCompoundTag("display");

    if (!tags.hasKey("display")) {
      tags.setTag("display", displayTag);
    }

    displayTag.setInteger("color", color.asInt());
  }

  @Override
  public Class<Armour> getClazz() {
    return Armour.class;
  }

  @Override
  public ItemStack map(Armour rldItem) throws CouldNotMapItemException {
    Item item = map(rldItem, rldItem.getArmourType(), rldItem.getQuality());

    ItemStack itemStack = addEnchantmentNbtTags(rldItem, item);

    applyColourTags(rldItem, itemStack);

    return itemStack;
  }

  public Item map(Armour item, ArmourType armourType, Quality quality) throws CouldNotMapItemException {
    switch (armourType) {
      case HELMET:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.DIAMOND_HELMET);
      case CHESTPLATE:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE);
      case HORSE:
        return EquipmentMapper1_12.map(quality, Items.SADDLE, Items.SADDLE, Items.IRON_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR, Items.DIAMOND_HORSE_ARMOR);
      case LEGGINGS:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.DIAMOND_LEGGINGS);
      case BOOTS:
        return EquipmentMapper1_12.map(quality, Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS);
    }
    throw new CouldNotMapItemException(item);
  }

}
