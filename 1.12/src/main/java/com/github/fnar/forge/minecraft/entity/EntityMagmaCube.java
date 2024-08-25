package com.github.fnar.forge.minecraft.entity;

import net.minecraft.world.World;

public class EntityMagmaCube extends net.minecraft.entity.monster.EntityMagmaCube {
  public EntityMagmaCube(World worldIn) {
    super(worldIn);
  }

  public void setSlimeSize(int size, boolean resetHealth) {
    super.setSlimeSize(size, resetHealth);
  }
}
