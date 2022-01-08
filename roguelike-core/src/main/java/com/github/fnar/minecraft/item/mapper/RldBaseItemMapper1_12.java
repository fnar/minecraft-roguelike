package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.RldBaseItem;
import com.github.fnar.util.Color;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.Random;

public abstract class RldBaseItemMapper1_12<T extends RldBaseItem> extends BaseItemMapper1_12<T> {

  // TODO: use seed for randomness, or use other random.
  private static final Random RAND = new Random(0);

  private final EnchantmentMapper1_12 enchantmentMapper = new EnchantmentMapper1_12();

  protected ItemStack map(T rldItem, Item item) {
    ItemStack itemStack = new ItemStack(item);
    if (rldItem.isPlzEnchant()) {
      itemStack = enchantItem(RAND, itemStack, rldItem.getPlzEnchantLevel());
    }

    ItemStack finalItemStack = itemStack;
    rldItem.getEnchantments()
        .forEach(enchantment ->
            finalItemStack.addEnchantment(enchantmentMapper.map(enchantment), enchantment.getLevel()));


    Color color = rldItem.getColor();
    if (color != null) {
      dyeArmor(finalItemStack, color);
    }

    return finalItemStack;
  }

  public static ItemStack enchantItem(Random rand, ItemStack item, int enchantLevel) {

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

  private static void dyeArmor(ItemStack armor, Color color) {
    NBTTagCompound tags = armor.getTagCompound();

    if (tags == null) {
      tags = new NBTTagCompound();
      armor.setTagCompound(tags);
    }

    NBTTagCompound displayTag = tags.getCompoundTag("display");

    if (!tags.hasKey("display")) {
      tags.setTag("display", displayTag);
    }

    displayTag.setInteger("color", color.asInt());

  }
}
