package com.github.fnar.forge.minecraft.entity;

import net.minecraft.world.World;

public class EntitySlime extends net.minecraft.entity.monster.EntitySlime {
  public EntitySlime(World worldIn) {
    super(worldIn);
  }

  public void setSlimeSize(int size, boolean resetHealth) {
    super.setSlimeSize(size, resetHealth);
  }
}
