package greymerk.roguelike.dungeon.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import greymerk.roguelike.dungeon.rooms.RoomSetting;

import static java.util.stream.Collectors.toCollection;

class RoomIterator implements Iterator<IDungeonRoom> {

  private LinkedList<IDungeonRoom> rooms;

  public RoomIterator(List<RoomSetting> singleRoomSettings) {
    rooms = singleRoomSettings.stream()
        .map(RoomSetting::instantiate)
        .collect(toCollection(LinkedList::new));
  }

  @Override
  public boolean hasNext() {
    return !rooms.isEmpty();
  }

  @Override
  public IDungeonRoom next() {
    return rooms.poll();
  }
}
