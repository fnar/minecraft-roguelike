package com.github.fnar.minecraft.block.decorative;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

public class PlantBlock extends SingleBlockBrush {

  private PlantType plantType;

  public PlantBlock() {
    super(BlockType.PLANT);
  }

  public PlantBlock setPlant(PlantType plantType) {
    this.plantType = plantType;
    return this;
  }

  public PlantType getPlant() {
    return plantType;
  }

  public static PlantBlock plant() {
    return new PlantBlock();
  }

  @Override
  public PlantBlock copy() {
    PlantBlock copy = new PlantBlock();
    copy.setFacing(getFacing());
    copy.setPlant(plantType);
    return copy;
  }
}
