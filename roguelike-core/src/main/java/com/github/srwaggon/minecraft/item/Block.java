package com.github.srwaggon.minecraft.item;

import com.github.srwaggon.minecraft.block.BlockType;

public class Block implements RldItem {

  private BlockType blockType;


  public Block(BlockType blockType) {
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
