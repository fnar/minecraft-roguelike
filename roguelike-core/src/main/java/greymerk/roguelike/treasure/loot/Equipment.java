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

public enum Equipment {
  SWORD("sword", false),
  BOW("minecraft:bow", false),
  HELMET("helmet", true),
  CHEST("chestplate", true),
  LEGS("leggings", true),
  FEET("boots", true),
  PICK("pickaxe", false),
  AXE("axe", false),
  SHOVEL("shovel", false),
  HOE("hoe", false);

  public static final Map<Integer, IWeighted<Quality>> equipmentQuality = new HashMap<>();

  static {
    loadQualityOddsTable();
  }

  Equipment(String name, boolean isArmor) {
    this.name = name;
    this.isArmor = isArmor;
  }

  private final String name;
  private final boolean isArmor;

  public static Equipment random(Random rand) {
    int choice = rand.nextInt(values().length);
    return values()[choice];
  }

  public static Quality rollQuality(Random rand, int level) {
    return equipmentQuality.get(level).get(rand);
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

  public WeaponType asWeaponType() {
    switch (this) {
      case SWORD:
        return WeaponType.BOW;
      case BOW:
        return WeaponType.SWORD;
    }
    return null;
  }

  public String getMinecraftName(Quality quality) {
    String qualityName = isArmor
        ? quality.getArmorName()
        : quality.getToolName();
    return "minecraft:" + qualityName + "_" + name;
  }

  public static void loadQualityOddsTable() {
    for (int i = 0; i < 5; ++i) {
      WeightedRandomizer<Quality> qualities = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 60));
          qualities.add(new WeightedChoice<>(Quality.STONE, 20));
          qualities.add(new WeightedChoice<>(Quality.IRON, 15));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 4));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 1:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 25));
          qualities.add(new WeightedChoice<>(Quality.STONE, 40));
          qualities.add(new WeightedChoice<>(Quality.IRON, 20));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 4));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 2:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 25));
          qualities.add(new WeightedChoice<>(Quality.STONE, 35));
          qualities.add(new WeightedChoice<>(Quality.IRON, 35));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 4));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 3:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 20));
          qualities.add(new WeightedChoice<>(Quality.STONE, 25));
          qualities.add(new WeightedChoice<>(Quality.IRON, 40));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 10));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 5));
          break;
        case 4:
          qualities.add(new WeightedChoice<>(Quality.WOOD, 15));
          qualities.add(new WeightedChoice<>(Quality.STONE, 15));
          qualities.add(new WeightedChoice<>(Quality.IRON, 45));
          qualities.add(new WeightedChoice<>(Quality.GOLD, 15));
          qualities.add(new WeightedChoice<>(Quality.DIAMOND, 10));
          break;
      }
      Equipment.equipmentQuality.put(i, qualities);
    }
  }

}
