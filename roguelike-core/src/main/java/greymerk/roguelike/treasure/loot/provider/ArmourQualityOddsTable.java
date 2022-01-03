package greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class ArmourQualityOddsTable {

  private static final Map<Integer, IWeighted<Quality>> armourQuality = new HashMap<>();

  static {
    ArmourQualityOddsTable.loadArmourQualityOddsTable();
  }

  public static Quality rollArmourQuality(Random rand, int level) {
    return armourQuality.get(level).get(rand);
  }

  public static void loadArmourQualityOddsTable() {
    for (int i = 0; i < 5; ++i) {
      WeightedRandomizer<Quality> armour = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          armour.add(new WeightedChoice<>(Quality.WOOD, 250));
          armour.add(new WeightedChoice<>(Quality.STONE, 50));
          armour.add(new WeightedChoice<>(Quality.IRON, 20));
          armour.add(new WeightedChoice<>(Quality.GOLD, 3));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 1:
          armour.add(new WeightedChoice<>(Quality.WOOD, 150));
          armour.add(new WeightedChoice<>(Quality.STONE, 30));
          armour.add(new WeightedChoice<>(Quality.IRON, 10));
          armour.add(new WeightedChoice<>(Quality.GOLD, 3));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 2:
          armour.add(new WeightedChoice<>(Quality.WOOD, 50));
          armour.add(new WeightedChoice<>(Quality.STONE, 30));
          armour.add(new WeightedChoice<>(Quality.IRON, 20));
          armour.add(new WeightedChoice<>(Quality.GOLD, 3));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 3:
          armour.add(new WeightedChoice<>(Quality.WOOD, 20));
          armour.add(new WeightedChoice<>(Quality.STONE, 10));
          armour.add(new WeightedChoice<>(Quality.IRON, 10));
          armour.add(new WeightedChoice<>(Quality.GOLD, 5));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 3));
          break;
        case 4:
          armour.add(new WeightedChoice<>(Quality.WOOD, 2));
          armour.add(new WeightedChoice<>(Quality.STONE, 3));
          armour.add(new WeightedChoice<>(Quality.IRON, 10));
          armour.add(new WeightedChoice<>(Quality.GOLD, 3));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 3));
          break;
      }
      armourQuality.put(i, armour);
    }
  }
}
