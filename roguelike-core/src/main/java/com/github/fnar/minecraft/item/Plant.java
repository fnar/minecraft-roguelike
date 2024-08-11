package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.block.decorative.PlantType;

public class Plant extends RldBaseItem {

  private PlantType type;

  public Plant(PlantType type) {
    this.type = type;
  }

  public PlantType getPlantType() {
    return type;
  }

  public void setType(PlantType type) {
    this.type = type;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.PLANT;
  }
}
