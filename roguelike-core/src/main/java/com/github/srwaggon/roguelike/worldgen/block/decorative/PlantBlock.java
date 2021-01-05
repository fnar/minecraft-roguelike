package com.github.srwaggon.roguelike.worldgen.block.decorative;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

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
