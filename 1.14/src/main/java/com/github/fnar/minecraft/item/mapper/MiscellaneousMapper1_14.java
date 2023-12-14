package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Miscellaneous;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MiscellaneousMapper1_14 extends RldBaseItemMapper1_14<Miscellaneous> {
  @Override
  public Class<Miscellaneous> getClazz() {
    return Miscellaneous.class;
  }

  @Override
  public ItemStack map(Miscellaneous item) throws CouldNotMapItemException {
    return map(item, mapItem(item));
  }

  private Item mapItem(Miscellaneous item) throws CouldNotMapItemException {
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
      case SIGN:
        return Items.OAK_SIGN;
    }
    throw new CouldNotMapItemException(item);
  }
}
