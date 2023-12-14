package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Dye;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DyeMapper1_14 extends BaseItemMapper1_14<Dye> {
  @Override
  public Class<Dye> getClazz() {
    return Dye.class;
  }

  @Override
  public ItemStack map(Dye item) throws CouldNotMapItemException {
    return new ItemStack(getItemForDyeColour(item));
  }

  public Item getItemForDyeColour(Dye item) throws CouldNotMapItemException {
    switch (item.getDyeColor()) {
      case BLACK:
        return Items.BLACK_DYE;
      case BLUE:
        return Items.BLUE_DYE;
      case BROWN:
        return Items.BROWN_DYE;
      case CYAN:
        return Items.CYAN_DYE;
      case GRAY:
        return Items.GRAY_DYE;
      case GREEN:
        return Items.GREEN_DYE;
      case LIGHT_BLUE:
        return Items.LIGHT_BLUE_DYE;
      case LIGHT_GRAY:
        return Items.LIGHT_GRAY_DYE;
      case LIME:
        return Items.LIME_DYE;
      case MAGENTA:
        return Items.MAGENTA_DYE;
      case ORANGE:
        return Items.ORANGE_DYE;
      case PINK:
        return Items.PINK_DYE;
      case PURPLE:
        return Items.PURPLE_DYE;
      case RED:
        return Items.RED_DYE;
      case WHITE:
        return Items.WHITE_DYE;
      case YELLOW:
        return Items.YELLOW_DYE;
    }
    throw new CouldNotMapItemException(item);
  }
}
