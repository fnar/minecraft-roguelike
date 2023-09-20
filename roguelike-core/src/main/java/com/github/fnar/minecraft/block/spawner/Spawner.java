package com.github.fnar.minecraft.block.spawner;

import com.google.common.collect.Lists;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

public class Spawner extends SingleBlockBrush {

  private final List<SpawnPotential> potentials = Lists.newArrayList();

  public Spawner(List<SpawnPotential> spawnPotentials) {
    super(BlockType.SPAWNER);
    potentials.addAll(spawnPotentials);
  }

  public List<SpawnPotential> getPotentials() {
    return potentials;
  }

}
