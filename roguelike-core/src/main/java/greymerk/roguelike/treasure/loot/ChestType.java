package greymerk.roguelike.treasure.loot;

import java.util.Objects;
import java.util.Random;

public class ChestType {

  public static ChestType EMPTY = new ChestType("EMPTY");
  public static ChestType ARMOUR = new ChestType("ARMOUR");
  public static ChestType WEAPONS = new ChestType("WEAPONS");
  public static ChestType BLOCKS = new ChestType("BLOCKS");
  public static ChestType ENCHANTING = new ChestType("ENCHANTING");
  public static ChestType FOOD = new ChestType("FOOD");
  public static ChestType ORE = new ChestType("ORE");
  public static ChestType POTIONS = new ChestType("POTIONS");
  public static ChestType STARTER = new ChestType("STARTER");
  public static ChestType TOOLS = new ChestType("TOOLS");
  public static ChestType SUPPLIES = new ChestType("SUPPLIES");
  public static ChestType SMITH = new ChestType("SMITH");
  public static ChestType MUSIC = new ChestType("MUSIC");
  public static ChestType REWARD = new ChestType("REWARD");
  public static ChestType BREWING = new ChestType("BREWING");
  public static ChestType VANILLA = new ChestType("VANILLA");

  public static final ChestType[] COMMON_TREASURES = {ARMOUR, BLOCKS, FOOD, SUPPLIES, TOOLS, VANILLA, WEAPONS};
  public static final ChestType[] RARE_TREASURES = {ARMOUR, ENCHANTING, POTIONS, ORE, REWARD, TOOLS, VANILLA, WEAPONS};
  public static final ChestType[] SUPPLIES_TREASURES = {BLOCKS, SUPPLIES};

  private final String identity;

  public ChestType(String identity) {
    this.identity = identity.toUpperCase();
  }

  public static ChestType chooseRandomType(Random random, ChestType[] chestTypes) {
    return chestTypes[random.nextInt(chestTypes.length)];
  }

  @Override
  public boolean equals(Object other) {
    return other != null
        && Objects.equals(other.getClass(), this.getClass())
        && equals((ChestType) other);
  }

  public boolean equals(ChestType other) {
    return Objects.equals(this.identity, other.identity);
  }
}
