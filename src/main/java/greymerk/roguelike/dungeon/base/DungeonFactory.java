package greymerk.roguelike.dungeon.base;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import static com.google.common.collect.Lists.newLinkedList;
import static greymerk.roguelike.dungeon.base.DungeonRoom.CORNER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.getRandomRoom;
import static java.util.stream.IntStream.range;

public class DungeonFactory implements IDungeonFactory {

  private DungeonRoom base = CORNER;
  private Iterator<IDungeonRoom> singleRoomsIterator;
  private List<RoomSetting> singleRoomSettings = new LinkedList<>();
  private WeightedRandomizer<DungeonRoom> dungeonRoomWeightedRandomizer = new WeightedRandomizer<>();

  public DungeonFactory() {
  }

  public DungeonFactory(DungeonRoom base) {
    this.base = base;
  }

  public DungeonFactory(DungeonFactory toCopy) {
    singleRoomSettings = newLinkedList(toCopy.singleRoomSettings);
    dungeonRoomWeightedRandomizer = new WeightedRandomizer<>(toCopy.dungeonRoomWeightedRandomizer);
    base = toCopy.base;
  }

  public DungeonFactory(DungeonFactory parent, DungeonFactory child) {
    base = child.base;
    singleRoomSettings = newLinkedList((child.singleRoomSettings.isEmpty() ? parent : child).singleRoomSettings);
    dungeonRoomWeightedRandomizer = new WeightedRandomizer<>((child.dungeonRoomWeightedRandomizer.isEmpty() ? parent : child).dungeonRoomWeightedRandomizer);
  }

  public static DungeonFactory getRandom(Random rand, int numRooms) {
    DungeonFactory rooms = new DungeonFactory();
    rooms.base = CORNER;
    range(0, numRooms).forEach(i -> {
      if (rand.nextBoolean()) {
        rooms.addRandom(getRandomRoom(rand), 1);
      } else {
        rooms.addSingle(getRandomRoom(rand), 1);
      }
    });
    return rooms;
  }

  public void add(RoomSetting roomSetting) {
    if (roomSetting.getFrequency().equals("single")) {
      addSingleRoom(roomSetting, roomSetting.getCount());
    }
    if (roomSetting.getFrequency().equals("random")) {
      addRandom(roomSetting.getDungeonRoom(), roomSetting.getWeight());
    }
  }

  public IDungeonRoom get(Random random) {
    if (singleRoomsIterator == null) {
      singleRoomsIterator = new RoomIterator(singleRoomSettings);
    }

    if (singleRoomsIterator.hasNext()) {
      return singleRoomsIterator.next();
    }

    if (dungeonRoomWeightedRandomizer.isEmpty()) {
      return instantiate(base);
    }

    DungeonRoom dungeonRoom = dungeonRoomWeightedRandomizer.get(random);
    return instantiate(dungeonRoom);
  }

  private IDungeonRoom instantiate(DungeonRoom dungeonRoom) {
    return dungeonRoom.instantiate(newRoomSetting(dungeonRoom, 1));
  }

  public void addSingle(DungeonRoom type) {
    addSingle(type, 1);
  }

  public void addSingle(DungeonRoom type, int count) {
    // TODO: the fact that I have to set an empty list of levels here indicates that levels is at the wrong code level. It should be higher up.
    RoomSetting roomSetting = newRoomSetting(type, count);
    addSingleRoom(roomSetting, count);
  }

  private RoomSetting newRoomSetting(DungeonRoom type, int count) {
    return new RoomSetting(type, null, "builtin:spawner", "single", 0, count, Collections.emptyList());
  }

  public void addSingleRoom(RoomSetting roomSetting, int count) {
    range(0, count)
        .mapToObj(operand -> roomSetting)
        .forEach(singleRoomSettings::add);
  }

  public void addRandom(DungeonRoom type, int weight) {
    dungeonRoomWeightedRandomizer.add(new WeightedChoice<>(type, weight));
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
    return dungeonRoomWeightedRandomizer.equals(other.dungeonRoomWeightedRandomizer);
  }

  @Override
  public String toString() {
    return "DungeonFactory{" +
        "base=" + base +
        ", singleRoomsIterator=" + singleRoomsIterator +
        ", singleRoomSettings=" + singleRoomSettings +
        ", dungeonRoomWeightedRandomizer=" + dungeonRoomWeightedRandomizer +
        '}';
  }
}
