package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.CouldNotMapException;

public class CouldNotMapItemException extends CouldNotMapException {

  public CouldNotMapItemException(RldItem rldItem) {
    super("Could not map item: " + rldItem.toString());
  }

  public CouldNotMapItemException(RldItemStack rldItemStack) {
    super("Could not map item: " + rldItemStack.toString());
  }


  public CouldNotMapItemException(RldItem rldItem, Exception e) {
    super("Could not map item: " + rldItem.toString(), e);
  }

  public CouldNotMapItemException(RldItemStack rldItemStack, Exception e) {
    super("Could not map item: " + rldItemStack.toString(), e);
  }

}
