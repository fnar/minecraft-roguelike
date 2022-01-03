package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.block.BlockType;

import net.minecraft.item.ItemStack;

public class SaplingMapper1_12 extends BaseItemMapper1_12<Sapling> {
  @Override
  public Class<Sapling> getClazz() {
    return Sapling.class;
  }

  @Override
  public ItemStack map(Sapling item) {
    return new BlockMapper1_12().map(mapToBlock(item));
  }

  private BlockItem mapToBlock(Sapling item) {
    switch(item.getWood()) {
      case OAK:
        return BlockType.OAK_SAPLING.asItem();
      case SPRUCE:
        return BlockType.SPRUCE_SAPLING.asItem();
      case BIRCH:
        return BlockType.BIRCH_SAPLING.asItem();
      case JUNGLE:
        return BlockType.JUNGLE_SAPLING.asItem();
      case ACACIA:
        return BlockType.ACACIA_SAPLING.asItem();
      case DARK_OAK:
        return BlockType.DARK_OAK_SAPLING.asItem();
    }
    throw new CouldNotMapItemException(item);
  }

}
