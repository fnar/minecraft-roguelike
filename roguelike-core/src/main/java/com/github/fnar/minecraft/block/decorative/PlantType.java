package com.github.fnar.minecraft.block.decorative;

import java.util.Random;

import greymerk.roguelike.worldgen.BlockBrush;

public enum PlantType {

  // TODO: Consider deconstructing (flower, sapling, tall grass)
  DANDELION,
  POPPY,
  BLUE_ORCHID,
  ALLIUM,
  AZURE_BLUET,
  RED_TULIP,
  ORANGE_TULIP,
  WHITE_TULIP,
  PINK_TULIP,
  OXEYE_DAISY,
  RED_MUSHROOM,
  BROWN_MUSHROOM,
  CACTUS,
  OAK_SAPLING,
  BIRCH_SAPLING,
  SPRUCE_SAPLING,
  JUNGLE_SAPLING,
  ACACIA_SAPLING,
  DARK_OAK_SAPLING,
  DEAD_BUSH,
  FERN,
  GRASS,
  ;

  public BlockBrush getBrush() {
    return PlantBlock.plant().setPlant(this);
  }

  public static PlantType chooseRandom(Random random) {
    return PlantType.values()[random.nextInt(PlantType.values().length)];
  }

}
