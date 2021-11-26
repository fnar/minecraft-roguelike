package greymerk.roguelike.dungeon.base;

import java.util.Iterator;
import java.util.LinkedList;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.WorldEditor;

import static greymerk.roguelike.dungeon.base.RoomType.CORNER;
import static java.util.stream.Collectors.toCollection;

public class RoomIterator implements Iterator<BaseRoom> {

  private final LinkedList<BaseRoom> singleRooms;
  private final WeightedRandomizer<RoomSetting> randomRooms;
  private final LevelSettings levelSettings;
  private WorldEditor worldEditor;

  public RoomIterator(LevelSettings levelSettings, WorldEditor worldEditor) {
    singleRooms = levelSettings.getRooms().getSingleRoomSettings().stream()
        .map(roomSetting -> roomSetting.instantiate(levelSettings, worldEditor))
        .collect(toCollection(LinkedList::new));
    randomRooms = levelSettings.getRooms().getRandomRooms();
    this.levelSettings = levelSettings;
    this.worldEditor = worldEditor;
  }

  public BaseRoom getDungeonRoom() {
    if (hasNext()) {
      return next();
    } else if (randomRooms.isEmpty()) {
      return CORNER.newSingleRoomSetting().instantiate(levelSettings, worldEditor);
    } else {
      return randomRooms.get(worldEditor.getRandom()).instantiate(levelSettings, worldEditor);
    }
  }

  @Override
  public boolean hasNext() {
    return !singleRooms.isEmpty();
  }

  @Override
  public BaseRoom next() {
    return singleRooms.poll();
  }
}
