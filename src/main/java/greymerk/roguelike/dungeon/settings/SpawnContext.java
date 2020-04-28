package greymerk.roguelike.dungeon.settings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.List;

import greymerk.roguelike.worldgen.IPositionInfo;

public class SpawnContext {

  private IPositionInfo info;

  public SpawnContext(IPositionInfo info) {
    this.info = info;
  }

  public boolean biomeHasType(Type type) {
    return BiomeDictionary.hasType(info.getBiome(), type);
  }

  public boolean includesBiome(List<ResourceLocation> biomeNames) {
    return biomeNames.contains(info.getBiome().getRegistryName());
  }

  public boolean includesBiomeType(List<Type> biomeTypes) {
    return biomeTypes.stream().anyMatch(this::biomeHasType);
  }

  public int getDimension() {
    return this.info.getDimension();
  }

  // todo: includesDimension

}
