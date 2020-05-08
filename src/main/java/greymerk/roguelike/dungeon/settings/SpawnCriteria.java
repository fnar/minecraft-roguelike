package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.config.RogueConfig;

public class SpawnCriteria {

  private int weight;
  private final List<ResourceLocation> biomes = new ArrayList<>();
  private List<BiomeDictionary.Type> biomeTypes = new ArrayList<>();
  private final List<Integer> validDimensions = new ArrayList<>();

  public SpawnCriteria() {
    this(new JsonObject());
  }

  public SpawnCriteria(JsonObject data) {
    weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
    addBiomeCriteria(data);
    addBiomeTypeCriteria(data);
    addDimensionCriteria(data);
  }

  private void addBiomeCriteria(JsonObject data) {
    if (data.has("biomes")) {
      for (JsonElement biome : data.get("biomes").getAsJsonArray()) {
        biomes.add(new ResourceLocation(biome.getAsString()));
      }
    }
  }

  private void addBiomeTypeCriteria(JsonObject data) {
    if (data.has("biomeTypes")) {
      for (JsonElement biomeType : data.get("biomeTypes").getAsJsonArray()) {
        BiomeDictionary.Type t = BiomeDictionary.Type.getType(biomeType.getAsString().toUpperCase());
        if (BiomeDictionary.getBiomes(t).size() > 0) {
          biomeTypes.add(t);
        }
      }
    }
  }

  private void addDimensionCriteria(JsonObject data) {
    if (data.has("dimensions")) {
      for (JsonElement dimension : data.get("dimensions").getAsJsonArray()) {
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

  public void setBiomeTypes(List<BiomeDictionary.Type> biomeTypes) {
    this.biomeTypes = biomeTypes;
  }

  public boolean isValid(SpawnContext spawnContext) {
    return isBiomeValid(spawnContext)
        && isBiomeTypeValid(spawnContext)
        && isDimensionValid(spawnContext);
  }

  private boolean isBiomeValid(SpawnContext spawnContext) {
    return biomes.isEmpty() || spawnContext.includesBiome(biomes);
  }

  private boolean isBiomeTypeValid(SpawnContext spawnContext) {
    return biomeTypes.isEmpty() || spawnContext.includesBiomeType(biomeTypes);
  }

  private boolean isDimensionValid(SpawnContext spawnContext) {
    return isValidDimension(spawnContext.getDimension()) && validDimensions.isEmpty() || spawnContext.includesDimension(validDimensions);
  }

}