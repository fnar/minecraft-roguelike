package com.github.fnar.forge;

import net.minecraftforge.fml.common.Loader;

public class ModLoader1_12 implements ModLoader {

  public boolean isModLoaded(String requiredModName) {
    return Loader.isModLoaded(requiredModName);
  }

}
