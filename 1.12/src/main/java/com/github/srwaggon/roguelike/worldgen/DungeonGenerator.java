package com.github.srwaggon.roguelike.worldgen;

import com.github.srwaggon.minecraft.WorldEditor1_12;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonGenerator implements IWorldGenerator {

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    WorldEditor editor = new WorldEditor1_12(world);
    Dungeon dungeon = new Dungeon(editor);
    dungeon.spawnInChunk(random, chunkX, chunkZ);
  }
}