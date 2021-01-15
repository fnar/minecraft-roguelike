package com.github.srwaggon.minecraft.item;

import com.github.srwaggon.minecraft.tag.TagMapper;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Optional;

public class ItemMapper1_12 {

  public static ItemStack map(RldItemStack rldItemStack) {
    ItemStack item = mapItemByType(rldItemStack);
    mergeTags(rldItemStack, item);
    return item;
  }

  public static ItemStack mapItemByType(RldItemStack rldItemStack) {
    RldItem item = rldItemStack.getItem();

    switch (item.getItemType()) {

      case BLOCK:
        return BlockItemMapper1_12.map(rldItemStack);
      case POTION:
        return PotionMapper1_12.map(rldItemStack);
      case RECORD:
        return RecordMapper1_12.map(rldItemStack);
      default:
        return new ItemStack(Items.AIR);
    }
  }

  public static void mergeTags(RldItemStack rldItemStack, ItemStack itemStack) {
    Optional.ofNullable(rldItemStack.getTags())
        .ifPresent(compoundTag ->
            Optional.ofNullable(itemStack.getTagCompound()).orElseGet(() -> ensureNbtTags(itemStack))
                .merge(TagMapper.map(compoundTag)));
  }

  public static NBTTagCompound ensureNbtTags(ItemStack itemStack) {
    itemStack.setTagCompound(new NBTTagCompound());
    return itemStack.getTagCompound();
  }
}

