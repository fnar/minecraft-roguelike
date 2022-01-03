package com.github.fnar.minecraft.item;

public class StringlyNamedItem extends RldBaseItem {

  private String itemName;

  public StringlyNamedItem(String itemName) {
    this.itemName = itemName;
  }

  public String getItemName() {
    return itemName;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.STRINGLY_NAMED;
  }

}
