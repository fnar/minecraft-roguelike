package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.block.BlockType;

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
