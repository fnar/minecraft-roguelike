package greymerk.roguelike.dungeon.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.util.WeightedRandomizer;

import static greymerk.roguelike.dungeon.base.RoomType.CORNER;
import static java.util.stream.Collectors.toCollection;

public class RoomIterator implements Iterator<DungeonBase> {

  private final LinkedList<DungeonBase> singleRooms;
  private final WeightedRandomizer<RoomSetting> randomRooms;
  private Random random;

  public RoomIterator(RoomsSetting roomsSetting, Random random) {
    singleRooms = roomsSetting.getSingleRoomSettings().stream()
        .map(RoomSetting::instantiate)
        .collect(toCollection(LinkedList::new));
    randomRooms = roomsSetting.getRandomRooms();
    this.random = random;
  }

  public DungeonBase getDungeonRoom() {
    if (hasNext()) {
      return next();
    } else if (randomRooms.isEmpty()) {
      return CORNER.newSingleRoomSetting().instantiate();
    } else {
      return randomRooms.get(random).instantiate();
    }
  }

  @Override
  public boolean hasNext() {
    return !singleRooms.isEmpty();
  }

  @Override
  public DungeonBase next() {
    return singleRooms.poll();
  }
}
