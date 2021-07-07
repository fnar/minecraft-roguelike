package com.github.fnar.minecraft.worldgen.generatables;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class Doorways {

  public static void generateDoorway(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing) {
    // new WoolDoorway() // for debugging
    // feature doesn't seem ready yet -- disabled for now
    // getDoorwayType(worldEditor, origin, facing)
    //     .generate(worldEditor, levelSettings, origin, facing);
  }

  private static Generatable getDoorwayType(WorldEditor worldEditor, Coord origin, Direction facing) {
    if (worldEditor.getRandom().nextBoolean()) {
      return new Entryway();
    }

    if (isNextToAir(worldEditor, origin, facing)) {
      if (worldEditor.getRandom().nextBoolean()) {
        new Doorway();
      }
      if (worldEditor.getRandom().nextBoolean()) {
        return new IronBarredEntryway();
      }
    }

    return new WalledDoorway();
  }

  private static boolean isNextToAir(WorldEditor worldEditor, Coord origin, Direction facing) {
    return worldEditor.isAirBlock(origin.copy().translate(facing))
        && worldEditor.isAirBlock(origin.copy().translate(facing.reverse()));
  }
}
