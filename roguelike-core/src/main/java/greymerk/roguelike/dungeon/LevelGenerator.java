package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.worldgen.WorldEditor;

public enum LevelGenerator {

  CLASSIC,
  MST;

  public static ILevelGenerator getGenerator(WorldEditor editor, Random rand, LevelGenerator type, DungeonLevel level) {
    switch (type) {
      case CLASSIC:
      default:
        return new LevelGeneratorClassic(rand, level.getSettings());
      case MST:
        return new LevelGeneratorMST(editor, rand, level.getSettings());
    }
  }

}
