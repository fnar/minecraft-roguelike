package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.ItemMapper;
import com.github.fnar.minecraft.item.RldBaseItem;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.tag.TagMapper1_12;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class BaseItemMapper1_12<RldBaseItemClass extends RldBaseItem> implements ItemMapper {

  // TODO: use seed for randomness, or use other random.
  private static final Random RAND = new Random(0);
  private static final TagMapper1_12 tagMapper = new TagMapper1_12();
  private final EnchantmentMapper1_12 enchantmentMapper = new EnchantmentMapper1_12();

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

  private static ItemStack enchantItem(Random rand, ItemStack item, int enchantLevel) {

    if (item == null) {
      return null;
    }

    List<EnchantmentData> enchants = EnchantmentHelper.buildEnchantmentList(rand, item, enchantLevel, false);

    boolean isBook = item.getItem() == Items.BOOK;

    if (isBook) {
      item = new ItemStack(Items.ENCHANTED_BOOK);
      if (enchants.size() > 1) {
        enchants.remove(rand.nextInt(enchants.size()));
      }
    }

    for (EnchantmentData toAdd : enchants) {
      if (isBook) {
        ItemEnchantedBook.addEnchantment(item, toAdd);
      } else {
        item.addEnchantment(toAdd.enchantment, toAdd.enchantmentLevel);
      }
    }

    return item;
  }

  public abstract Class<RldBaseItemClass> getClazz();

  public ItemStack map(RldItemStack rldItemStack) throws CouldNotMapItemException {
    Class<RldBaseItemClass> clazz = getClazz();
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

  public abstract ItemStack map(RldBaseItemClass item) throws CouldNotMapItemException;

  protected ItemStack addEnchantmentNbtTags(RldBaseItemClass rldItem, Item item) {
    return addEnchantmentNbtTags(rldItem, new ItemStack(item));
  }

  protected ItemStack addEnchantmentNbtTags(RldBaseItemClass rldItem, ItemStack itemStack) {
    if (rldItem.isPlzEnchant()) {
      itemStack = enchantItem(RAND, itemStack, rldItem.getPlzEnchantLevel());
    }

    ItemStack finalItemStack = itemStack;
    rldItem.getEnchantments()
        .forEach(enchantment ->
            finalItemStack.addEnchantment(enchantmentMapper.map(enchantment), enchantment.getLevel()));

    return finalItemStack;
  }

}
