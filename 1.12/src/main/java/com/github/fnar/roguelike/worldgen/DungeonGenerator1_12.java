package com.github.fnar.roguelike.worldgen;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.forge.ModLoader1_12;
import com.github.fnar.minecraft.WorldEditor1_12;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonGenerator1_12 implements IWorldGenerator {

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    WorldEditor editor = new WorldEditor1_12(world);
    ModLoader modLoader = new ModLoader1_12();
    Dungeon dungeon = new Dungeon(editor, modLoader);
    dungeon.spawnInChunk(random, chunkX, chunkZ);
  }
}
