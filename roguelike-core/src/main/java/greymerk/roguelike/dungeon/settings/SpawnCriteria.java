package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.PositionInfo;

import static net.minecraftforge.common.BiomeDictionary.Type;
import static net.minecraftforge.common.BiomeDictionary.hasType;

public class SpawnCriteria {

  private int weight;
  private final List<ResourceLocation> biomes = new ArrayList<>();
  private final List<Type> biomeTypes = new ArrayList<>();
  private final List<Integer> validDimensions = new ArrayList<>();

  public SpawnCriteria() {
    this(new JsonObject());
  }

  public SpawnCriteria (SpawnCriteria spawnCriteria) {
    this.weight = spawnCriteria.weight;
    this.biomes.addAll(spawnCriteria.biomes);
    this.biomeTypes.addAll(spawnCriteria.biomeTypes);
    this.validDimensions.addAll(spawnCriteria.validDimensions);
  }

  public SpawnCriteria(JsonObject data) {
    weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
    addBiomeCriteria(data);
    addBiomeTypeCriteria(data);
    addDimensionCriteria(data);
  }

  public SpawnCriteria inherit(SpawnCriteria toInherit) {
    SpawnCriteria result = new SpawnCriteria(this);
    result.biomes.addAll(toInherit.biomes);
    result.biomeTypes.addAll(toInherit.biomeTypes);
    result.validDimensions.addAll(toInherit.validDimensions);
    return result;
  }

  private void addBiomeCriteria(JsonObject data) {
    if (data.has("biomes")) {
      for (JsonElement biome : data.get("biomes").getAsJsonArray()) {
        if (biome.isJsonNull()) {
          continue;
        }
        biomes.add(new ResourceLocation(biome.getAsString()));
      }
    }
  }

  private void addBiomeTypeCriteria(JsonObject data) {
    if (data.has("biomeTypes")) {
      for (JsonElement biomeType : data.get("biomeTypes").getAsJsonArray()) {
        if (biomeType.isJsonNull()) {
          continue;
        }
        Type type = Type.getType(biomeType.getAsString().toUpperCase());
        if (BiomeDictionary.getBiomes(type).size() > 0) {
          biomeTypes.add(type);
        }
      }
    }
  }

  private void addDimensionCriteria(JsonObject data) {
    if (data.has("dimensions")) {
      for (JsonElement dimension : data.get("dimensions").getAsJsonArray()) {
        if (dimension.isJsonNull()) {
          continue;
        }
        validDimensions.add(dimension.getAsInt());
      }
    }
  }

  public static boolean isValidDimension(int dimension) {
    return isValidDimension(dimension, RogueConfig.getIntList(RogueConfig.DIMENSIONWL), RogueConfig.getIntList(RogueConfig.DIMENSIONBL));
  }

  public static boolean isValidDimension(int dimension, List<Integer> whiteList, List<Integer> blackList) {
    return !blackList.contains(dimension) && (whiteList.isEmpty() || whiteList.contains(dimension));
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setBiomeTypes(List<Type> biomeTypes) {
    this.biomeTypes.addAll(biomeTypes);
  }

  public boolean isValid(PositionInfo positionInfo) {
    return isBiomeValid(positionInfo)
        && isBiomeTypeValid(positionInfo)
        && isDimensionValid(positionInfo);
  }

  private boolean isBiomeValid(PositionInfo positionInfo) {
    return biomes.isEmpty() || includesBiome(positionInfo);
  }

  private boolean includesBiome(PositionInfo positionInfo) {
    return biomes.contains(positionInfo.getBiome().getRegistryName());
  }

  private boolean isBiomeTypeValid(PositionInfo positionInfo) {
    return biomeTypes.isEmpty() || includesBiomeType(positionInfo);
  }

  private boolean includesBiomeType(PositionInfo positionInfo) {
    Biome biomeHere = positionInfo.getBiome();
    return biomeTypes.stream().anyMatch(biomeType -> hasType(biomeHere, biomeType));
  }

  private boolean isDimensionValid(PositionInfo positionInfo) {
    return isValidDimension(positionInfo.getDimension()) && validDimensions.isEmpty() || includesDimension(positionInfo);
  }

  private boolean includesDimension(PositionInfo positionInfo) {
    return validDimensions.contains(positionInfo.getDimension());
  }

  public List<ResourceLocation> getBiomes() {
    return biomes;
  }

  public List<Type> getBiomeTypes() {
    return biomeTypes;
  }

  public List<Integer> getValidDimensions() {
    return validDimensions;
  }
}