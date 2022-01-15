package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Miscellaneous;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MiscellaneousMapper1_12 extends RldBaseItemMapper1_12<Miscellaneous> {
  @Override
  public Class<Miscellaneous> getClazz() {
    return Miscellaneous.class;
  }

  @Override
  public ItemStack map(Miscellaneous item) {
    return map(item, mapItem(item));
  }

  private Item mapItem(Miscellaneous item) {
    switch(item.getType()) {
      case BOOK:
        return Items.BOOK;
      case ENCHANTED_BOOK:
        return Items.ENCHANTED_BOOK;
      case ENDER_PEARL:
        return Items.ENDER_PEARL;
      case ENDER_EYE:
        return Items.ENDER_EYE;
      case EXPERIENCE_BOTTLE:
        return Items.EXPERIENCE_BOTTLE;
      case GLASS_BOTTLE:
        return Items.GLASS_BOTTLE;
      case LEAD:
        return Items.LEAD;
      case SADDLE:
        return Items.SADDLE;
    }
    throw new CouldNotMapItemException(item);
  }
}
