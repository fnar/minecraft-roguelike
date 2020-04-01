package greymerk.roguelike.dungeon.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.rooms.RoomSetting;

class RoomIterator implements Iterator<IDungeonRoom> {

  private LinkedList<IDungeonRoom> rooms;

  public RoomIterator(List<RoomSetting> singleRoomSettings) {
    rooms = singleRoomSettings.stream()
        .map(roomSetting -> roomSetting.getDungeonRoom().instantiate(roomSetting))
        .collect(Collectors.toCollection(LinkedList::new));
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
