package com.github.srwaggon.roguelike.minecraft.item;


public class ItemStack {

  private Item item;
  private int count;

  public ItemStack(Item item, int count) {
    this.item = item;
    this.count = count;
  }

  public ItemStack(Item item) {
    this(item, 1);
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
