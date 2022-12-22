package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.ToolType;
import com.github.fnar.minecraft.item.WeaponType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

// todo: eliminate?
public enum Equipment {

  SWORD,
  BOW,
  HELMET,
  CHEST,
  LEGS,
  FEET,
  PICK,
  AXE,
  SHOVEL,
  HOE;

  public static final Map<Integer, IWeighted<Quality>> equipmentQuality = new HashMap<>();

  static {
    loadQualityOddsTable();
  }

  public static Equipment random(Random rand) {
    int choice = rand.nextInt(values().length);
    return values()[choice];
  }

  public static Quality rollQuality(Random rand, int level) {
    return equipmentQuality.get(level).get(rand);
  }

  public static WeaponType asWeaponType(Equipment type) {
    switch (type) {
      case BOW:
        return WeaponType.BOW;
      case SWORD:
        return WeaponType.SWORD;
      default:
        throw new IllegalArgumentException("Could not parse WeaponType from EquipmentType: " + type);
    }
  }

  public ArmourType asArmourType() {
    switch (this) {
      case HELMET:
        return ArmourType.HELMET;
      case CHEST:
        return ArmourType.CHESTPLATE;
      case LEGS:
        return ArmourType.LEGGINGS;
      case FEET:
        return ArmourType.BOOTS;
    }
    return null;
  }

  public ToolType asToolType() {
    switch (this) {
      case PICK:
        return ToolType.PICKAXE;
      case AXE:
        return ToolType.AXE;
      case SHOVEL:
        return ToolType.SHOVEL;
    }
    return null;
  }

  public static void loadQualityOddsTable() {
    for (int i = 0; i < 5; ++i) {
      WeightedRandomizer<Quality> qualities = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 70));
          qualities.add(new WeightedChoice<>(Quality.STONE, 20));
          qualities.add(new WeightedChoice<>(Quality.IRON, 5));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 4));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 1:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 50));
          qualities.add(new WeightedChoice<>(Quality.STONE, 30));
          qualities.add(new WeightedChoice<>(Quality.IRON, 15));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 4));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 2:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 30));
          qualities.add(new WeightedChoice<>(Quality.STONE, 50));
          qualities.add(new WeightedChoice<>(Quality.IRON, 25));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 4));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 3:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 20));
          qualities.add(new WeightedChoice<>(Quality.STONE, 30));
          qualities.add(new WeightedChoice<>(Quality.IRON, 35));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 10));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 5));
          break;
        case 4:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 5));
          qualities.add(new WeightedChoice<>(Quality.STONE, 20));
          qualities.add(new WeightedChoice<>(Quality.IRON, 50));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 10));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 15));
          break;
      }
      Equipment.equipmentQuality.put(i, qualities);
    }
  }

}
