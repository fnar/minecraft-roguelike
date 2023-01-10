package com.github.fnar.roguelike.worldgen.generatables.thresholds;

import com.github.fnar.roguelike.worldgen.generatables.BaseGeneratable;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
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

    private static Type randomBlocking(Random random) {
      Type[] values = new Type[]{DOORWAY, IRON_BARRED, WALLED};
      return values[random.nextInt(values.length)];
    }

    public BaseGeneratable toThreshold(WorldEditor worldEditor) {
      switch (this) {
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
    int level = levelSettings.getLevel();
    getDoorwayType(worldEditor, level, origin, facing)
        .withLevelSettings(levelSettings)
        .withFacing(facing)
        .generate(origin);
  }

  private static BaseGeneratable getDoorwayType(WorldEditor worldEditor, int level, Coord origin, Direction facing) {
    return rollForDoor(worldEditor.getRandom(), level) && isNextToAir(worldEditor, origin, facing)
        ? Type.randomBlocking(worldEditor.getRandom()).toThreshold(worldEditor)
        : new Entryway(worldEditor);
  }

  private static Boolean rollForDoor(Random random, int level) {
    return random.nextDouble() < getDoorChance(level);
  }

  private static Double getDoorChance(int level) {
    return RogueConfig.DUNGEONS_GENERATION_THRESHOLD_CHANCE.getDoubleAtIndexIfNonNegative(level).orElse(0.0);
  }

  private static boolean isNextToAir(WorldEditor worldEditor, Coord origin, Direction facing) {
    return worldEditor.isAirBlock(origin.copy().translate(facing))
        && worldEditor.isAirBlock(origin.copy().translate(facing.reverse()));
  }
}
