package com.github.fnar.forge;

import net.minecraftforge.fml.ModList;

public class ModLoader1_14 implements ModLoader {

  @Override
  public boolean isModLoaded(String requiredModName) {
    return ModList.get().isLoaded(requiredModName);
  }

}
