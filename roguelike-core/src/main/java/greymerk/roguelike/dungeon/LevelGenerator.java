package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.settings.LevelSettings;

public enum LevelGenerator {

  CLASSIC,
  MST;

  public ILevelGenerator instantiate(LevelSettings levelSettings) {
    int numRooms = levelSettings.getNumRooms();
    int scatter = levelSettings.getScatter();
    int range = levelSettings.getRange();
    switch (this) {
      case CLASSIC:
      default:
        return new LevelGeneratorClassic(numRooms, scatter, range);
      case MST:
        return new LevelGeneratorMST(numRooms, scatter);
    }
  }
}
