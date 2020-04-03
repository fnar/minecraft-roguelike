package greymerk.roguelike.dungeon.base;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import lombok.ToString;

import static com.google.common.collect.Lists.newLinkedList;
import static greymerk.roguelike.dungeon.base.DungeonRoom.CORNER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.getRandomRoom;
import static java.util.stream.IntStream.range;

@ToString
public class DungeonFactory implements IDungeonFactory {

  private DungeonRoom base = CORNER;
  private Iterator<IDungeonRoom> singleRoomsIterator;
  private List<RoomSetting> singleRoomSettings = new LinkedList<>();
  private WeightedRandomizer<RoomSetting> roomRandomizer = new WeightedRandomizer<>();

  public DungeonFactory() {
  }

  public DungeonFactory(DungeonRoom base) {
    this.base = base;
  }

  public DungeonFactory(DungeonFactory toCopy) {
    singleRoomSettings = newLinkedList(toCopy.singleRoomSettings);
    roomRandomizer = new WeightedRandomizer<>(toCopy.roomRandomizer);
    base = toCopy.base;
  }

  public DungeonFactory(DungeonFactory parent, DungeonFactory child) {
    base = child.base;
    singleRoomSettings = newLinkedList((child.singleRoomSettings.isEmpty() ? parent : child).singleRoomSettings);
    roomRandomizer = new WeightedRandomizer<>((child.roomRandomizer.isEmpty() ? parent : child).roomRandomizer);
  }

  public static DungeonFactory getRandom(Random rand, int numRooms) {
    DungeonFactory rooms = new DungeonFactory();
    rooms.base = CORNER;
    range(0, numRooms).forEach(i -> {
      if (rand.nextBoolean()) {
        rooms.add(getRandomRoom(rand).newRandomRoomSetting(1));
      } else {
        rooms.add(getRandomRoom(rand).newSingleRoomSetting());
      }
    });
    return rooms;
  }

  public void add(RoomSetting roomSetting) {
    if (roomSetting.getFrequency().equals("single")) {
      addSingleRoom(roomSetting);
    }
    if (roomSetting.getFrequency().equals("random")) {
      addRandomRoom(roomSetting);
    }
  }

  private void addSingleRoom(RoomSetting roomSetting) {
    range(0, roomSetting.getCount())
        .mapToObj(operand -> roomSetting)
        .forEach(singleRoomSettings::add);
  }

  private void addRandomRoom(RoomSetting roomSetting) {
    roomRandomizer.add(new WeightedChoice<>(roomSetting, roomSetting.getWeight()));
  }

  public IDungeonRoom get(Random random) {
    if (singleRoomsIterator == null) {
      singleRoomsIterator = new RoomIterator(singleRoomSettings);
    }

    if (singleRoomsIterator.hasNext()) {
      return singleRoomsIterator.next();
    }

    if (roomRandomizer.isEmpty()) {
      return base.newSingleRoomSetting().instantiate();
    }

    return roomRandomizer.get(random).instantiate();
  }

  @Override
  public boolean equals(Object o) {
    // I think this is only for tests, which means the behaviour isn't being tested, just the state
    DungeonFactory other = (DungeonFactory) o;
    if (!base.equals(other.base)) {
      return false;
    }
    if (!singleRoomSettings.equals(other.singleRoomSettings)) {
      return false;
    }
    return roomRandomizer.equals(other.roomRandomizer);
  }
}
