package greymerk.roguelike.dungeon.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.rooms.RoomSetting;

import static greymerk.roguelike.dungeon.base.DungeonRoom.getInstance;

class RoomIterator implements Iterator<IDungeonRoom> {

  private LinkedList<IDungeonRoom> rooms;

  public RoomIterator(List<RoomSetting> singleRoomSettings) {
    rooms = singleRoomSettings.stream()
        .map(roomSetting -> getInstance(roomSetting.getDungeonRoom()))
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
