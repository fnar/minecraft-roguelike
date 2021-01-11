package com.github.srwaggon.minecraft.item;


import com.github.srwaggon.minecraft.block.BlockType;

public class RldItemStack {

  private RldItem item;
  private int count;

  public RldItemStack(RldItem item, int count) {
    this.item = item;
    this.count = count;
  }

  public RldItemStack(RldItem item) {
    this(item, 1);
  }

  public RldItem getItem() {
    return item;
  }

  public void setItem(RldItem item) {
    this.item = item;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public static RldItemStack forBlockType(BlockType blockType) {
    return new RldItemStack(new BlockItem(blockType));
  }
}
