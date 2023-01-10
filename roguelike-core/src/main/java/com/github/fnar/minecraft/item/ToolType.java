package com.github.fnar.minecraft.item;

import java.util.List;
import java.util.Random;

public enum ToolType {
  AXE,
  FISHING_ROD,
  FLINT_AND_STEEL,
  HOE,
  PICKAXE,
  SHEARS,
  SHOVEL,
  ;

  public static ToolType randomAmong(Random random, List<ToolType> values) {
    return values.get(random.nextInt(values.size()));
  }

  public static ToolType random(Random random) {
    int choice = random.nextInt(values().length);
    return values()[choice];
  }

  public Tool asItem() {
    return new Tool(this);
  }

  public RldItemStack asItemStack() {
    return asItem().asStack();
  }

}
