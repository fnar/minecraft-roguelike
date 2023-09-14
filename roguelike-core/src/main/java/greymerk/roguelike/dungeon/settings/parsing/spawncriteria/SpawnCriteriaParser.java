package greymerk.roguelike.dungeon.settings.parsing.spawncriteria;

import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.settings.parsing.WeightParser;

public class SpawnCriteriaParser {

  public static SpawnCriteria parse(JsonObject data) {
    SpawnCriteria spawnCriteria = new SpawnCriteria();
    spawnCriteria.setWeight(WeightParser.parse(data));
    spawnCriteria.addBiomeStrings(BiomeParser.parse(data));
    spawnCriteria.addBiomeTags(BiomeTagParser.parse(data));
    spawnCriteria.addDimensions(DimensionParser.parse(data));
    return spawnCriteria;
  }
}
