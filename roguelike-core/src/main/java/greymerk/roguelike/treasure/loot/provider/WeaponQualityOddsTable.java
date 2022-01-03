package greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class WeaponQualityOddsTable {

  private static final Map<Integer, IWeighted<Quality>> weaponQuality = new HashMap<>();

  static {
    WeaponQualityOddsTable.loadWeaponQualityOddsTable();
  }

  public static Quality rollWeaponQuality(Random rand, int level) {
    return weaponQuality.get(level).get(rand);
  }

  public static void loadWeaponQualityOddsTable() {
    for (int i = 0; i < 5; ++i) {
      WeightedRandomizer<Quality> weapon = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          weapon.add(new WeightedChoice<>(Quality.WOOD, 200));
          weapon.add(new WeightedChoice<>(Quality.STONE, 50));
          weapon.add(new WeightedChoice<>(Quality.IRON, 10));
          weapon.add(new WeightedChoice<>(Quality.GOLD, 3));
          weapon.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 1:
          weapon.add(new WeightedChoice<>(Quality.WOOD, 100));
          weapon.add(new WeightedChoice<>(Quality.STONE, 30));
          weapon.add(new WeightedChoice<>(Quality.IRON, 10));
          weapon.add(new WeightedChoice<>(Quality.GOLD, 3));
          weapon.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 2:
          weapon.add(new WeightedChoice<>(Quality.WOOD, 50));
          weapon.add(new WeightedChoice<>(Quality.STONE, 20));
          weapon.add(new WeightedChoice<>(Quality.IRON, 10));
          weapon.add(new WeightedChoice<>(Quality.GOLD, 3));
          weapon.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 3:
          weapon.add(new WeightedChoice<>(Quality.WOOD, 1));
          weapon.add(new WeightedChoice<>(Quality.STONE, 3));
          weapon.add(new WeightedChoice<>(Quality.IRON, 5));
          weapon.add(new WeightedChoice<>(Quality.GOLD, 3));
          weapon.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 4:
          weapon.add(new WeightedChoice<>(Quality.WOOD, 1));
          weapon.add(new WeightedChoice<>(Quality.STONE, 2));
          weapon.add(new WeightedChoice<>(Quality.IRON, 15));
          weapon.add(new WeightedChoice<>(Quality.GOLD, 5));
          weapon.add(new WeightedChoice<>(Quality.DIAMOND, 3));
          break;
      }
      weaponQuality.put(i, weapon);
    }
  }
}
