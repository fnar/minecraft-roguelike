package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.ItemMapper;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.tag.TagMapper1_14;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;

import java.util.Optional;

public abstract class BaseItemMapper1_14<ItemClass> implements ItemMapper {

  private static final TagMapper1_14 tagMapper = new TagMapper1_14();

  public abstract Class<ItemClass> getClazz();

  public ItemStack map(RldItemStack rldItemStack) {
    Class<ItemClass> clazz = getClazz();
    if (!(clazz.isAssignableFrom(rldItemStack.getItem().getClass()))) {
      throw new CouldNotMapItemException(rldItemStack);
    }

    ItemStack itemStack = map((getClazz().cast(rldItemStack.getItem())));
    itemStack.setDamage(rldItemStack.getDamage());
    itemStack.setCount(rldItemStack.getCount());
    mapPlzNbt(rldItemStack, itemStack);
    mergeTags(rldItemStack, itemStack);
    return itemStack;
  }

  public abstract ItemStack map(ItemClass item);

  private static void mapPlzNbt(RldItemStack rldItemStack, ItemStack itemStack) {
    if (!rldItemStack.isPlzNbt()) {
      return;
    }
    try {
      itemStack.setTag(JsonToNBT.getTagFromJson(rldItemStack.getPlzNbt()));
    } catch (CommandSyntaxException exception) {
      throw new CouldNotMapItemException(rldItemStack, exception);
    }
  }

  private static void mergeTags(RldItemStack rldItemStack, ItemStack itemStack) {
    Optional.ofNullable(rldItemStack.getTags())
        .ifPresent(compoundTag ->
            Optional.ofNullable(itemStack.getTag()).orElseGet(() -> ensureNbtTags(itemStack))
                .merge(tagMapper.map(compoundTag)));
  }

  private static CompoundNBT ensureNbtTags(ItemStack itemStack) {
    itemStack.setTag(new CompoundNBT());
    return itemStack.getTag();
  }

}
