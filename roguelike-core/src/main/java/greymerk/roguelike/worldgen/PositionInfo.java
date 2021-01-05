package greymerk.roguelike.worldgen;

import net.minecraft.world.biome.Biome;

public interface PositionInfo {
  int getDimension();

  Biome getBiome();
}
