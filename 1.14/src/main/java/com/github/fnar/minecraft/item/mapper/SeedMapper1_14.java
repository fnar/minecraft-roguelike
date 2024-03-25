package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.block.BlockMapper1_14;
import com.github.fnar.minecraft.block.CouldNotMapBlockException;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Seed;
import com.github.fnar.minecraft.material.Crop;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SeedMapper1_14 extends BaseItemMapper1_14<Seed> {
  @Override
  public Class<Seed> getClazz() {
    return Seed.class;
  }

  @Override
  public ItemStack map(Seed item) throws CouldNotMapItemException {
    return new ItemStack(mapToItem(item));
  }

  private Item mapToItem(Seed item) throws CouldNotMapItemException {
    switch (item.getCrop()) {
      case BEETROOTS:
        return Items.BEETROOT;
      case CARROTS:
        return Items.CARROT;
      case COCOA:
        return asItem(Crop.COCOA.getBrush());
      case MELON:
        return Items.MELON;
      case NETHER_WART:
        return Items.NETHER_WART;
      case POTATOES:
        return Items.POTATO;
      case PUMPKIN:
        return Items.PUMPKIN_SEEDS;
      case WHEAT:
        return Items.WHEAT;
    }
    throw new CouldNotMapItemException(item);
  }

  private Item asItem(SingleBlockBrush brush) throws CouldNotMapItemException {
    try {
      return Item.getItemFromBlock(BlockMapper1_14.map(brush).getBlock());
    } catch (CouldNotMapBlockException e) {
      throw new CouldNotMapItemException(brush.getBlockType().asItem(), e);
    }
  }

}
