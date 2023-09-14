package greymerk.roguelike.dungeon.settings;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import com.github.fnar.minecraft.world.BiomeTag;

import net.minecraft.init.Biomes;
import net.minecraft.init.Bootstrap;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.parsing.spawncriteria.SpawnCriteriaParser;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpawnCriteriaTest {

  @Mock
  private WorldEditor worldEditor;

  @Mock
  private Coord coord;

  @BeforeClass
  public static void beforeClass() {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void overworld() {
    boolean valid;
    int dim = 0;
    List<Integer> wl = new ArrayList<>();
    wl.add(0);
    List<Integer> bl = new ArrayList<>();

    valid = SpawnCriteria.isValidDimension(dim, wl, bl);
    assertTrue(valid);

  }

  @Test
  public void notInNether() {

    int dim = -1;
    List<Integer> wl = new ArrayList<>();
    wl.add(0);
    List<Integer> bl = new ArrayList<>();

    assertFalse(SpawnCriteria.isValidDimension(dim, wl, bl));
  }

  @Test
  public void whiteListSeveral() {
    List<Integer> wl = new ArrayList<>();
    wl.add(5);
    wl.add(8);
    wl.add(12);
    List<Integer> bl = new ArrayList<>();

    assertFalse(SpawnCriteria.isValidDimension(0, wl, bl)); // not overworld
    assertFalse(SpawnCriteria.isValidDimension(-1, wl, bl)); // not nether
    assertFalse(SpawnCriteria.isValidDimension(1, wl, bl)); // not end
    assertFalse(SpawnCriteria.isValidDimension(15, wl, bl));
    assertTrue(SpawnCriteria.isValidDimension(5, wl, bl));
    assertTrue(SpawnCriteria.isValidDimension(8, wl, bl));
    assertTrue(SpawnCriteria.isValidDimension(12, wl, bl));
  }

  @Test
  public void isValid_ReturnsTrue_WhenBothBiomeCriteriaAndBiomeTypeCriteriaArePresentAndSatisfied() {
    when(worldEditor.getBiomeAt(any(Coord.class))).thenReturn(Biomes.ICE_MOUNTAINS);
    when(worldEditor.getBiomeName(any(Coord.class))).thenReturn(Biomes.ICE_MOUNTAINS.getRegistryName().toString());
    when(worldEditor.isBiomeTypeAt(eq(BiomeTag.SNOWY), any(Coord.class))).thenReturn(true);

    assertThat(newSpawnCriteria(Biomes.ICE_MOUNTAINS, BiomeDictionary.Type.SNOWY).isValid(worldEditor, coord)).isTrue();
  }

  @Test
  public void isValid_ReturnsTrue_WhenOnlyBiomeCriteriaIsPresentAndSatisfied() {
    when(worldEditor.getBiomeAt(any(Coord.class))).thenReturn(Biomes.ICE_MOUNTAINS);
    when(worldEditor.getBiomeName(any(Coord.class))).thenReturn(Biomes.ICE_MOUNTAINS.getRegistryName().toString());

    assertThat(newSpawnCriteria(Biomes.ICE_MOUNTAINS).isValid(worldEditor, coord)).isTrue();
  }

  @Test
  public void isValid_ReturnsFalse_WhenOnlyBiomeCriteriaIsPresentAndIsNotSatisfied() {
    when(worldEditor.getBiomeAt(any(Coord.class))).thenReturn(Biomes.BEACH);

    assertThat(newSpawnCriteria(Biomes.ICE_MOUNTAINS).isValid(worldEditor, coord)).isFalse();
  }

  @Test
  public void isValid_ReturnsTrue_WhenOnlyBiomeTypeCriteriaIsProvidedAndSatisfied() {
    when(worldEditor.getBiomeAt(any(Coord.class))).thenReturn(Biomes.ICE_MOUNTAINS);
    when(worldEditor.isBiomeTypeAt(eq(BiomeTag.SNOWY), any(Coord.class))).thenReturn(true);

    assertThat(newSpawnCriteria(BiomeDictionary.Type.SNOWY).isValid(worldEditor, coord)).isTrue();
  }

  @Test
  public void isValid_ReturnsFalse_WhenOnlyBiomeTypeCriteriaIsProvidedAndIsNotSatisfied() {
    when(worldEditor.getBiomeAt(any(Coord.class))).thenReturn(Biomes.BEACH);

    assertThat(newSpawnCriteria(BiomeDictionary.Type.SNOWY).isValid(worldEditor, coord)).isFalse();
  }

  private JsonArray newBiomeTypeCriteriaJson(BiomeDictionary.Type biomeType) {
    JsonArray criteriaBiomeTypes = new JsonArray();
    criteriaBiomeTypes.add(new JsonPrimitive(biomeType.toString()));
    return criteriaBiomeTypes;
  }

  private JsonArray newBiomeCriteriaJson(Biome biome) {
    JsonArray criteriaBiomes = new JsonArray();
    criteriaBiomes.add(new JsonPrimitive(biome.getRegistryName().toString()));
    return criteriaBiomes;
  }

  private JsonObject newSpawnCriteriaJson(Biome biome, BiomeDictionary.Type biomeType) {
    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomes", newBiomeCriteriaJson(biome));
    spawnCriteriaJson.add("biomeTypes", newBiomeTypeCriteriaJson(biomeType));
    return spawnCriteriaJson;
  }

  private JsonObject newSpawnCriteriaJson(JsonArray criteriaBiomeTypes) {
    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomeTypes", criteriaBiomeTypes);
    return spawnCriteriaJson;
  }

  private SpawnCriteria newSpawnCriteria(Biome biome) {
    return SpawnCriteriaParser.parse(newSpawnCriteriaJson(biome));
  }

  private JsonObject newSpawnCriteriaJson(Biome biome) {
    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomes", newBiomeCriteriaJson(biome));
    return spawnCriteriaJson;
  }

  private SpawnCriteria newSpawnCriteria(Biome biome, BiomeDictionary.Type biomeType) {
    return SpawnCriteriaParser.parse(newSpawnCriteriaJson(biome, biomeType));
  }

  private SpawnCriteria newSpawnCriteria(BiomeDictionary.Type biomeType) {
    return SpawnCriteriaParser.parse(newSpawnCriteriaJson(newBiomeTypeCriteriaJson(biomeType)));
  }
}