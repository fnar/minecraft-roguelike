package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonAshlea extends DungeonBase {

  public DungeonAshlea(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {
    return this;
  }

  public int getSize() {
    return 10;
  }
}
