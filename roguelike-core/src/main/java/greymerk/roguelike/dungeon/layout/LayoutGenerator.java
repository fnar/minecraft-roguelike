package greymerk.roguelike.dungeon.layout;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;

public interface LayoutGenerator {

  LevelLayout generate(Coord start, Random random);

  LevelLayout getLayout();

  enum Type {

    CLASSIC,
    MST;

    public LayoutGenerator instantiate(LevelSettings levelSettings) {
      int numRooms = levelSettings.getNumRooms();
      int scatter = levelSettings.getScatter();
      int range = levelSettings.getRange();
      switch (this) {
        case CLASSIC:
        default:
          return new LayoutGeneratorClassic(numRooms, scatter, range);
        case MST:
          return new LayoutGeneratorMST(numRooms, scatter);
      }
    }
  }

}
