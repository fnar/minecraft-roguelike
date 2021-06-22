package com.github.fnar.minecraft.block.decorative;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

public class PlantBlock extends SingleBlockBrush {

  private Plant plant;

  public PlantBlock() {
    super(BlockType.PLANT);
  }

  public PlantBlock setPlant(Plant plant) {
    this.plant = plant;
    return this;
  }

  public Plant getPlant() {
    return plant;
  }

  public static PlantBlock plant() {
    return new PlantBlock();
  }

}
