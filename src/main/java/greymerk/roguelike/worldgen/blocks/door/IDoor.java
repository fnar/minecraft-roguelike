package greymerk.roguelike.worldgen.blocks.door;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IDoor {

  void generate(WorldEditor editor, Coord pos, Cardinal dir);

  void generate(WorldEditor editor, Coord pos, Cardinal dir, boolean open);
}
