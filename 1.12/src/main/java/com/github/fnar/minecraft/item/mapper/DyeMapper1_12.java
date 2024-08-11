package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Dye;
import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DyeMapper1_12 extends BaseItemMapper1_12<Dye> {

  @Override
  public Class<Dye> getClazz() {
    return Dye.class;
  }

  @Override
  public ItemStack map(RldItemStack rldItemStack) throws CouldNotMapItemException {
    ItemStack itemStack = super.map(rldItemStack);
    itemStack.setItemDamage(getDamageForDyeColour(rldItemStack));
    return itemStack;
  }

  private int getDamageForDyeColour(RldItemStack rldItemStack) throws CouldNotMapItemException {
    Dye dye = getClazz().cast(rldItemStack.getItem());
    return getMetaForDyeColour(dye);
  }

  @Override
  public ItemStack map(Dye dye) throws CouldNotMapItemException {
    return addEnchantmentNbtTags(dye, new ItemStack(Items.DYE, 1, getMetaForDyeColour(dye)));
  }

  public int getMetaForDyeColour(Dye item) throws CouldNotMapItemException {
    switch (item.getDyeColor()) {
      case BLACK:
        return 0;
      case BLUE:
        return 4;
      case BROWN:
        return 3;
      case CYAN:
        return 6;
      case GRAY:
        return 8;
      case GREEN:
        return 2;
      case LIGHT_BLUE:
        return 12;
      case LIGHT_GRAY:
        return 7;
      case LIME:
        return 10;
      case MAGENTA:
        return 13;
      case ORANGE:
        return 14;
      case PINK:
        return 9;
      case PURPLE:
        return 5;
      case RED:
        return 1;
      case WHITE:
        return 15;
      case YELLOW:
        return 11;
    }
    throw new CouldNotMapItemException(item);
  }
}
