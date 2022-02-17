package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public interface ITower {

  void generate(WorldEditor editor, Random rand, Theme theme, Coord origin);

  default void chest(WorldEditor worldEditor, Direction dir, Coord coord) {
    new TreasureChest(coord, worldEditor)
        .withChestType(ChestType.STARTER)
        .withTrap(false)
        .withFacing(dir)
        .stroke(worldEditor, coord);
  }
}
