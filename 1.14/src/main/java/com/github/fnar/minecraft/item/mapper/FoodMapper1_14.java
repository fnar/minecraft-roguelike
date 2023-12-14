package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Food;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class FoodMapper1_14 extends BaseItemMapper1_14<Food> {
  @Override
  public Class<Food> getClazz() {
    return Food.class;
  }

  @Override
  public ItemStack map(Food item) throws CouldNotMapItemException {
    return new ItemStack(getItemForFood(item));
  }

  private Item getItemForFood(Food item) throws CouldNotMapItemException {
    switch (item.getFoodType()) {
      case APPLE:
        return Items.APPLE;
      case BAKED_POTATO:
        return Items.BAKED_POTATO;
      case BREAD:
        return Items.BREAD;
      case CARROT:
        return Items.CARROT;
      case COD:
        return Items.COD;
      case COOKED_BEEF:
        return Items.COOKED_BEEF;
      case COOKED_CHICKEN:
        return Items.COOKED_CHICKEN;
      case COOKED_COD:
        return Items.COOKED_COD;
      case COOKED_MUTTON:
        return Items.COOKED_MUTTON;
      case COOKED_PORKCHOP:
        return Items.COOKED_PORKCHOP;
      case COOKED_SALMON:
        return Items.COOKED_SALMON;
      case COOKIE:
        return Items.COOKIE;
      case GOLDEN_APPLE:
        return Items.GOLDEN_APPLE;
      case GOLDEN_CARROT:
        return Items.GOLDEN_CARROT;
      case MILK_BUCKET:
        return Items.MILK_BUCKET;
      case MUSHROOM_STEW:
        return Items.MUSHROOM_STEW;
      case POTATO:
        return Items.POTATO;
      case ROTTEN_FLESH:
        return Items.ROTTEN_FLESH;
      case SALMON:
        return Items.SALMON;
    }
    throw new CouldNotMapItemException(item);
  }
}
