package greymerk.roguelike.treasure.loot;

import java.util.Arrays;
import java.util.Random;

public enum Quality {

  WOOD("wood", "leather", "wooden"), // WOOD("Hand-carved", "leather", "wooden"),
  STONE("stone", "chainmail", "stone"), // STONE("Stonecut", "chainmail", "stone"),
  IRON("iron", "iron", "iron"), // IRON("Iron", "iron", "iron"),
  GOLD("gold", "golden", "golden"), // GOLD("Gilded", "golden", "golden"),
  DIAMOND("diamond", "diamond", "diamond"); // DIAMOND("Crystal", "diamond", "diamond");

  Quality(String descriptor, String armorName, String toolName) {
    this.descriptor = descriptor;
    this.armorName = armorName;
    this.toolName = toolName;
  }

  private final String descriptor;
  private final String armorName;
  private final String toolName;

  public String getDescriptor() {
    return descriptor;
  }

  public String getArmorName() {
    return armorName;
  }

  public String getToolName() {
    return toolName;
  }

  public static Quality random(Random random) {
    Quality[] values = values();
    return values[random.nextInt(values.length)];
  }

  public static Quality get(int level) {
    return Arrays.stream(Quality.values())
        .filter(quality -> quality.ordinal() == level)
        .findFirst()
        .orElse(WOOD);
  }
}
