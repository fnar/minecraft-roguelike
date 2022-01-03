package greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class ToolQualityOddsTable {

  private static final Map<Integer, IWeighted<Quality>> toolQuality = new HashMap<>();

  static {
    ToolQualityOddsTable.loadToolQualityOddsTable();
  }

  public static Quality rollToolQuality(Random rand, int level) {
    return toolQuality.get(level).get(rand);
  }

  public static void loadToolQualityOddsTable() {
    for (int i = 0; i < 5; ++i) {
      WeightedRandomizer<Quality> tool = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          tool.add(new WeightedChoice<>(Quality.WOOD, 10));
          tool.add(new WeightedChoice<>(Quality.STONE, 20));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 3));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 1:
          tool.add(new WeightedChoice<>(Quality.WOOD, 2));
          tool.add(new WeightedChoice<>(Quality.STONE, 10));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 3));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 2:
          tool.add(new WeightedChoice<>(Quality.WOOD, 1));
          tool.add(new WeightedChoice<>(Quality.STONE, 5));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 5));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 3));
          break;
        case 3:
          tool.add(new WeightedChoice<>(Quality.WOOD, 1));
          tool.add(new WeightedChoice<>(Quality.STONE, 3));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 5));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 5));
          break;
        case 4:
          tool.add(new WeightedChoice<>(Quality.WOOD, 1));
          tool.add(new WeightedChoice<>(Quality.STONE, 2));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 3));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 5));
          break;
      }
      toolQuality.put(i, tool);
    }
  }
}
