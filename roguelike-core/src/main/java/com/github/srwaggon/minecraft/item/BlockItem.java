package com.github.srwaggon.minecraft.item;

import com.github.srwaggon.minecraft.block.BlockType;

public class BlockItem implements RldItem {

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
