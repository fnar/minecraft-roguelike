package com.github.srwaggon.roguelike.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.PositionInfo;

public class PositionInfo1_14 implements PositionInfo {

  private World world;
  private Coord pos;

  public PositionInfo1_14(World world, Coord pos) {
    this.world = world;
    this.pos = pos;
  }

  @Override
  public int getDimension() {
    return 0; // TODO: Fix? What are the implications? Only dungeons in dimension 0?
  }

  @Override
  public Biome getBiome() {
    return world.getBiome(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
  }
}
