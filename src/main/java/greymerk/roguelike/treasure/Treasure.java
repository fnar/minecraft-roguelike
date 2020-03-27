package greymerk.roguelike.treasure;


import net.minecraft.init.Blocks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

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
  BREWING;

  public static final Treasure[] COMMON_TREASURES = {ARMOUR, TOOLS, WEAPONS};
  public static final Treasure[] RARE_TREASURES = {ARMOUR, ENCHANTING, POTIONS, ORE, TOOLS, WEAPONS};

  public static final Treasure[] SUPPLIES_TREASURES = {BLOCKS, SUPPLIES};

  public static void createChests(IWorldEditor editor, Random random, int level, List<Coord> chestLocations, boolean isTrapped, Treasure... types) {
    chestLocations.forEach(chestLocation ->
        createChest(editor, random, level, chestLocation, isTrapped, types));
  }

  public static void createChest(IWorldEditor editor, Random random, int level, Coord chestLocation, boolean isTrapped, Treasure... treasures) {
    if (isValidChestSpace(editor, chestLocation)) {
      Treasure type = chooseRandomType(random, treasures);
      safeGenerate(editor, random, level, chestLocation, isTrapped, type);
    }
  }

  private static Treasure chooseRandomType(Random random, Treasure... treasures) {
    return treasures[random.nextInt(treasures.length)];
  }

  private static void safeGenerate(IWorldEditor editor, Random random, int level, Coord chestLocation, boolean isTrapped, Treasure type) {
    try {
      ITreasureChest chest = new TreasureChest(type);
      chest.generate(editor, random, chestLocation, level, isTrapped);
    } catch (ChestPlacementException ignored) {
    }
  }

  private static boolean isValidChestSpace(IWorldEditor editor, Coord pos) {

    if (!editor.isAirBlock(pos)) {
      return false;
    }

    Coord cursor;
    cursor = new Coord(pos);
    cursor.add(Cardinal.DOWN);

    if (!editor.getBlock(cursor).getMaterial().isSolid()) {
      return false;
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(pos);
      cursor.add(dir);
      if (editor.getBlock(cursor).getBlock() == Blocks.CHEST) {
        return false;
      }
    }

    return true;
  }
}
