package com.github.fnar.minecraft.world;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeTagMapper1_12 {

  public static BiomeDictionary.Type toBiomeDictionaryType(BiomeTag biomeTag) {
    return BiomeDictionary.Type.getType(biomeTag.toString());
  }

}
