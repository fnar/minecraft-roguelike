package greymerk.roguelike.treasure;


import net.minecraft.init.Blocks;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

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

  public static void createChests(WorldEditor editor, Random random, int level, List<Coord> chestLocations, boolean isTrapped, Treasure... types) {
    chestLocations.forEach(chestLocation ->
        createChest(editor, random, level, chestLocation, isTrapped, types));
  }

  public static void createChest(WorldEditor editor, Random random, int level, Coord chestLocation, boolean isTrapped, Treasure... treasures) {
    if (isValidChestSpace(editor, chestLocation)) {
      Treasure type = chooseRandomType(random, treasures);
      safeGenerate(editor, random, level, chestLocation, isTrapped, type);
    }
  }

  private static Treasure chooseRandomType(Random random, Treasure... treasures) {
    return treasures[random.nextInt(treasures.length)];
  }

  private static void safeGenerate(WorldEditor editor, Random random, int level, Coord chestLocation, boolean isTrapped, Treasure type) {
    try {
      editor.generateTreasureChest(random, chestLocation, isTrapped, type, level);
    } catch (ChestPlacementException ignored) {
    }
  }

  private static boolean isValidChestSpace(WorldEditor editor, Coord coord) {
    return editor.isAirBlock(coord)
        && !isNonSolidBlock(editor, coord)
        && isNotNextToChest(editor, coord);
  }

  private static boolean isNotNextToChest(WorldEditor editor, Coord coord) {
    return Arrays.stream(Cardinal.directions).noneMatch(dir -> isBesideChest(editor, coord, dir));
  }

  private static boolean isBesideChest(WorldEditor editor, Coord coord, Cardinal dir) {
    Coord otherCursor = coord.add(dir);
    return editor.getBlock(otherCursor).getBlock() == Blocks.CHEST;
  }

  private static boolean isNonSolidBlock(WorldEditor editor, Coord coord) {
    Coord cursor = coord.add(Cardinal.DOWN);
    return !editor.getBlock(cursor).getMaterial().isSolid();
  }
}
