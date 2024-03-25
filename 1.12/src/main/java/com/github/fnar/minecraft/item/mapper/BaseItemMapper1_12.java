package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.ItemMapper;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.tag.TagMapper1_12;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Optional;

public abstract class BaseItemMapper1_12<ItemClass> implements ItemMapper {

  private static final TagMapper1_12 tagMapper = new TagMapper1_12();

  public abstract Class<ItemClass> getClazz();

  public ItemStack map(RldItemStack rldItemStack) throws CouldNotMapItemException {
    Class<ItemClass> clazz = getClazz();
    if (!(clazz.isAssignableFrom(rldItemStack.getItem().getClass()))) {
      throw new CouldNotMapItemException(rldItemStack);
    }

    ItemStack itemStack = map((getClazz().cast(rldItemStack.getItem())));
    itemStack.setItemDamage(rldItemStack.getDamage());
    itemStack.setCount(rldItemStack.getCount());
    mapPlzNbt(rldItemStack, itemStack);
    mergeTags(rldItemStack, itemStack);
    return itemStack;
  }

  public abstract ItemStack map(ItemClass item) throws CouldNotMapItemException;

  private static void mapPlzNbt(RldItemStack rldItemStack, ItemStack itemStack) throws CouldNotMapItemException {
    if (!rldItemStack.isPlzNbt()) {
      return;
    }
    try {
      itemStack.setTagCompound(JsonToNBT.getTagFromJson(rldItemStack.getPlzNbt()));
    } catch (NBTException e) {
      throw new CouldNotMapItemException(rldItemStack);
    }
  }

  private static void mergeTags(RldItemStack rldItemStack, ItemStack itemStack) {
    Optional.ofNullable(rldItemStack.getTags())
        .ifPresent(compoundTag ->
            Optional.ofNullable(itemStack.getTagCompound()).orElseGet(() -> ensureNbtTags(itemStack))
                .merge(tagMapper.map(compoundTag)));
  }

  private static NBTTagCompound ensureNbtTags(ItemStack itemStack) {
    itemStack.setTagCompound(new NBTTagCompound());
    return itemStack.getTagCompound();
  }

}
