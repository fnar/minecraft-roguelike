package greymerk.roguelike.dungeon.settings;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import com.github.fnar.minecraft.world.BiomeTag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
    when(worldEditor.getBiomeName(any(Coord.class))).thenReturn("minecraft:ice_mountains");
    when(worldEditor.isBiomeTypeAt(eq(BiomeTag.SNOWY), any(Coord.class))).thenReturn(true);

    assertThat(newSpawnCriteria("minecraft:ice_mountains", "snowy").isValid(worldEditor, coord)).isTrue();
  }

  @Test
  public void isValid_ReturnsTrue_WhenOnlyBiomeCriteriaIsPresentAndSatisfied() {
    when(worldEditor.getBiomeName(any(Coord.class))).thenReturn("minecraft:ice_mountains");

    assertThat(newSpawnCriteriaWithBiome("minecraft:ice_mountains").isValid(worldEditor, coord)).isTrue();
  }

  @Test
  public void isValid_ReturnsFalse_WhenOnlyBiomeCriteriaIsPresentAndIsNotSatisfied() {
    when(worldEditor.getBiomeName(any(Coord.class))).thenReturn("minecraft:beach");

    assertThat(newSpawnCriteriaWithBiome("minecraft:ice_mountains").isValid(worldEditor, coord)).isFalse();
  }

  @Test
  public void isValid_ReturnsTrue_WhenOnlyBiomeTypeCriteriaIsProvidedAndSatisfied() {
    when(worldEditor.getBiomeName(any(Coord.class))).thenReturn("minecraft:ice_mountains");
    when(worldEditor.isBiomeTypeAt(eq(BiomeTag.SNOWY), any(Coord.class))).thenReturn(true);

    assertThat(newSpawnCriteriaWithBiomeTag("snowy").isValid(worldEditor, coord)).isTrue();
  }

  @Test
  public void isValid_ReturnsFalse_WhenOnlyBiomeTypeCriteriaIsProvidedAndIsNotSatisfied() {
    when(worldEditor.getBiomeName(any(Coord.class))).thenReturn("minecraft:beach");

    assertThat(newSpawnCriteriaWithBiomeTag("snowy").isValid(worldEditor, coord)).isFalse();
  }

  private SpawnCriteria newSpawnCriteria(String biomeName, String biomeTag) {
    return SpawnCriteriaParser.parse(newSpawnCriteriaJson(biomeName, biomeTag));
  }

  private JsonObject newSpawnCriteriaJson(String biomeName, String biomeTag) {
    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomes", newJsonArrayContaining(biomeName));
    spawnCriteriaJson.add("biomeTypes", newJsonArrayContaining(biomeTag));
    return spawnCriteriaJson;
  }

  private SpawnCriteria newSpawnCriteriaWithBiome(String biome) {
    return SpawnCriteriaParser.parse(newSpawnCriteriaJson(biome));
  }

  private JsonObject newSpawnCriteriaJson(String biome) {
    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomes", newJsonArrayContaining(biome));
    return spawnCriteriaJson;
  }

  private SpawnCriteria newSpawnCriteriaWithBiomeTag(String biomeTag) {
    return SpawnCriteriaParser.parse(newSpawnCriteriaJson(newJsonArrayContaining(biomeTag)));
  }

  private JsonObject newSpawnCriteriaJson(JsonArray criteriaBiomeTypes) {
    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomeTypes", criteriaBiomeTypes);
    return spawnCriteriaJson;
  }

  private JsonArray newJsonArrayContaining(String element) {
    JsonArray criteriaBiomes = new JsonArray();
    criteriaBiomes.add(new JsonPrimitive(element));
    return criteriaBiomes;
  }
}