package greymerk.roguelike.treasure.loot;

import java.util.Arrays;

public enum Quality {

  WOOD("Hand-carved", "leather", "wooden"),
  STONE("Stonecut", "chainmail", "stone"),
  IRON("Iron", "iron", "iron"),
  GOLD("Gilded", "golden", "golden"),
  DIAMOND("Crystal", "diamond", "diamond");

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

  public static Quality get(int level) {
    return Arrays.stream(Quality.values())
        .filter(quality -> quality.ordinal() == level)
        .findFirst()
        .orElse(WOOD);
  }
}
