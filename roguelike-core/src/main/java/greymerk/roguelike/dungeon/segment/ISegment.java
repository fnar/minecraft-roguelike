package greymerk.roguelike.dungeon.segment;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface ISegment {

  void generate(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Theme theme, Coord pos);

}
