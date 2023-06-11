package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class SpawnCriteria {

  private int weight;
  private final List<Integer> validDimensions = new ArrayList<>();
  private Set<String> biomeStrings = new HashSet<>();
  private final Set<String> biomeTypes = new HashSet<>();

  public SpawnCriteria() {
    this(new JsonObject());
  }

  public SpawnCriteria(SpawnCriteria spawnCriteria) {
    this.weight = spawnCriteria.weight;
    this.validDimensions.addAll(spawnCriteria.validDimensions);
    this.biomeStrings.addAll(spawnCriteria.biomeStrings);
    this.biomeTypes.addAll(spawnCriteria.biomeTypes);
  }

  public SpawnCriteria(JsonObject data) {
    weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
    biomeStrings = parseBiomes(data);
    addBiomeTypeCriteria(data);
    addDimensionCriteria(data);
  }

  public SpawnCriteria inherit(SpawnCriteria toInherit) {
    SpawnCriteria result = new SpawnCriteria(this);
    result.biomeStrings.addAll(toInherit.biomeStrings);
    result.validDimensions.addAll(toInherit.validDimensions);
    return result;
  }

  private static Set<String> parseBiomes(JsonObject data) {
    if (!data.has("biomes")) {
      return new HashSet<>();
    }

    Set<String> biomes = new HashSet<>();
    for (JsonElement biome : data.get("biomes").getAsJsonArray()) {
      if (!biome.isJsonNull()) {
        biomes.add(biome.getAsString());
      }
    }
    return biomes;
  }

  private void addBiomeTypeCriteria(JsonObject data) {
    if (data.has("biomeTypes")) {
      for (JsonElement biomeType : data.get("biomeTypes").getAsJsonArray()) {
        if (biomeType.isJsonNull()) {
          continue;
        }
        biomeTypes.add(biomeType.getAsString().toUpperCase());
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
    return isValidDimension(dimension, RogueConfig.DIMENSIONWL.getIntList(), RogueConfig.DIMENSIONBL.getIntList());
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

  public void setBiomeTypes(List<String> types) {
    this.biomeTypes.addAll(types);
  }

  public boolean isValid(WorldEditor worldEditor, Coord coord) {
    return isBiomeValid(worldEditor, coord) && isBiomeTypeValid(worldEditor, coord) && isDimensionValid(worldEditor);
  }

  private boolean isBiomeValid(WorldEditor worldEditor, Coord coord) {
    return biomeStrings.isEmpty() || includesBiome(worldEditor, coord);
  }

  private boolean includesBiome(WorldEditor worldEditor, Coord coord) {
    return biomeStrings.contains(worldEditor.getBiomeName(coord));
  }

  private boolean isBiomeTypeValid(WorldEditor worldEditor, Coord coord) {
    return biomeTypes.isEmpty() || includesBiomeType(worldEditor, coord);
  }

  private boolean includesBiomeType(WorldEditor worldEditor, Coord coord) {
    return biomeTypes.stream().anyMatch(biomeType -> worldEditor.isBiomeTypeAt(biomeType, coord));
  }

  private boolean isDimensionValid(WorldEditor worldEditor) {
    return isValidDimension(worldEditor.getDimension()) && validDimensions.isEmpty() || includesDimension(worldEditor);
  }

  private boolean includesDimension(WorldEditor worldEditor) {
    return validDimensions.contains(worldEditor.getDimension());
  }

  public List<Integer> getValidDimensions() {
    return validDimensions;
  }
}