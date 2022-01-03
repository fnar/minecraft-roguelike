package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.block.BlockType;

public class BlockItem extends RldBaseItem {

  private BlockType blockType;

  public BlockItem(BlockType blockType) {
    this.blockType = blockType;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.BLOCK;
  }

  public BlockType getBlockType() {
    return blockType;
  }

  public void setBlockType(BlockType blockType) {
    this.blockType = blockType;
  }

}
