package com.github.fnar.minecraft.worldgen.generatables.thresholds;

import com.github.fnar.minecraft.worldgen.generatables.BaseGeneratable;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public abstract class Threshold {

  enum Type {
    ENTRYWAY,
    DOORWAY,
    IRON_BARRED,
    WALLED,
    WOOL,
    ;

    public static Type random(Random random) {
      Type[] values = values();
      return values[random.nextInt(values.length)];
    }

    public BaseGeneratable toThreshold(WorldEditor worldEditor) {
      switch(this) {
        case ENTRYWAY:
        default:
          return new Entryway(worldEditor);
        case DOORWAY:
          return new Doorway(worldEditor);
        case IRON_BARRED:
          return new IronBarredEntryway(worldEditor);
        case WALLED:
          return new WalledDoorway(worldEditor);
      }
    }
  }

  public static void generateDoorway(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing) {
    // new WoolDoorway() // for debugging
    getDoorwayType(worldEditor, origin, facing)
        .withLevelSettings(levelSettings)
        .withFacing(facing)
        .generate(origin);
  }

  private static BaseGeneratable getDoorwayType(WorldEditor worldEditor, Coord origin, Direction facing) {
    if (!worldEditor.getRandom().nextBoolean() || !isNextToAir(worldEditor, origin, facing)) {
      return new Entryway(worldEditor);
    }
    return Type.random(worldEditor.getRandom()).toThreshold(worldEditor);
  }

  private static boolean isNextToAir(WorldEditor worldEditor, Coord origin, Direction facing) {
    return worldEditor.isAirBlock(origin.copy().translate(facing))
        && worldEditor.isAirBlock(origin.copy().translate(facing.reverse()));
  }
}
