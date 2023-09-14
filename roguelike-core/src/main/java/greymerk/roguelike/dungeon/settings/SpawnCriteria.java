package greymerk.roguelike.dungeon.settings;

import com.github.fnar.minecraft.world.BiomeTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class SpawnCriteria {

  private int weight;
  private final List<Integer> validDimensions = new ArrayList<>();
  private final Set<String> biomeStrings = new HashSet<>();
  private final Set<BiomeTag> biomeTags = new HashSet<>();

  public SpawnCriteria() {
  }

  public SpawnCriteria(SpawnCriteria spawnCriteria) {
    this.setWeight(spawnCriteria.weight);
    this.addDimensions(spawnCriteria.validDimensions);
    this.addBiomeStrings(spawnCriteria.biomeStrings);
    this.addBiomeTags(spawnCriteria.biomeTags);
  }

  public SpawnCriteria inherit(SpawnCriteria toInherit) {
    SpawnCriteria result = new SpawnCriteria(this);
    result.setWeight(toInherit.getWeight());
    result.addBiomeStrings(toInherit.biomeStrings);
    result.addBiomeTags(toInherit.biomeTags);
    result.addDimensions(toInherit.getValidDimensions());
    return result;
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

  public void addBiomeStrings(Collection<String> biomes) {
    this.biomeStrings.addAll(biomes);
  }

  public void addBiomeTags(BiomeTag... biomeTags) {
    Collections.addAll(this.biomeTags, biomeTags);
  }

  public void addBiomeTags(Collection<BiomeTag> biomeTags) {
    this.biomeTags.addAll(biomeTags);
  }

  public void addDimensions(Collection<Integer> dimensions) {
    this.validDimensions.addAll(dimensions);
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
    return biomeTags.isEmpty() || includesBiomeType(worldEditor, coord);
  }

  private boolean includesBiomeType(WorldEditor worldEditor, Coord coord) {
    return biomeTags.stream().anyMatch(biomeType -> worldEditor.isBiomeTypeAt(biomeType, coord));
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