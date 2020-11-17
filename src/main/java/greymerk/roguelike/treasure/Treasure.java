package greymerk.roguelike.treasure;


import java.util.Random;

public enum Treasure {

  ARMOUR,
  WEAPONS,
  BLOCKS,
  ENCHANTING,
  FOOD,
  ORE,
  POTIONS,
  STARTER,
  TOOLS,
  SUPPLIES,
  SMITH,
  MUSIC,
  REWARD,
  EMPTY,
  BREWING,
  VANILLA
  ;

  public static final Treasure[] COMMON_TREASURES = {ARMOUR, BLOCKS, EMPTY, FOOD, SUPPLIES, TOOLS, VANILLA, WEAPONS};
  public static final Treasure[] RARE_TREASURES = {ARMOUR, ENCHANTING, POTIONS, ORE, REWARD, TOOLS, VANILLA, WEAPONS};

  public static final Treasure[] SUPPLIES_TREASURES = {BLOCKS, SUPPLIES};

  public static Treasure chooseRandomType(Random random, Treasure... treasures) {
    return treasures[random.nextInt(treasures.length)];
  }

}
