package com.github.fnar.minecraft.worldgen.generatables.thresholds;

import com.github.fnar.minecraft.worldgen.generatables.Generatable;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class Doorways {

  public static final Generatable[] DOORWAYS = {
      new Entryway(),
      new Doorway(),
      new IronBarredEntryway(),
      new WalledDoorway()
  };

  public static void generateDoorway(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing) {
    // new WoolDoorway() // for debugging
    getDoorwayType(worldEditor, origin, facing)
        .generate(worldEditor, levelSettings, origin, facing);
  }

  private static Generatable getDoorwayType(WorldEditor worldEditor, Coord origin, Direction facing) {
    if (!worldEditor.getRandom().nextBoolean() || !isNextToAir(worldEditor, origin, facing)) {
      return new Entryway();
    }
    int choice = worldEditor.getRandom().nextInt(DOORWAYS.length);
    return DOORWAYS[choice];
  }

  private static boolean isNextToAir(WorldEditor worldEditor, Coord origin, Direction facing) {
    return worldEditor.isAirBlock(origin.copy().translate(facing))
        && worldEditor.isAirBlock(origin.copy().translate(facing.reverse()));
  }
}
