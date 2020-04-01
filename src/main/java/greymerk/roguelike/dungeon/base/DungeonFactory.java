package greymerk.roguelike.dungeon.base;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import static com.google.common.collect.Lists.newLinkedList;
import static greymerk.roguelike.dungeon.base.DungeonRoom.CORNER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.getInstance;
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
      addSingleRoom(roomSetting, 1);
    }
    if (roomSetting.getFrequency().equals("random")) {
      addRandom(roomSetting.getDungeonRoom(), roomSetting.getWeight());
    }
  }

  public IDungeonRoom get(Random rand) {
    if (singleRoomsIterator == null) {
      singleRoomsIterator = new RoomIterator();
    }

    if (singleRoomsIterator.hasNext()) {
      return singleRoomsIterator.next();
    }

    if (dungeonRoomWeightedRandomizer.isEmpty()) {
      return getInstance(base);
    }

    return getInstance(dungeonRoomWeightedRandomizer.get(rand));
  }

  public void addSingle(DungeonRoom type) {
    addSingle(type, 1);
  }

  public void addSingle(DungeonRoom type, int num) {
    // TODO: the fact that I have to set an empty list of levels here indicates that levels is at the wrong code level. It should be higher up.
    RoomSetting roomSetting = new RoomSetting(type, null, "single", 0, Collections.emptyList());
    addSingleRoom(roomSetting, num);
  }

  public void addSingleRoom(RoomSetting roomSetting, int num) {
    range(0, num)
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

  private class RoomIterator implements Iterator<IDungeonRoom> {
    private LinkedList<IDungeonRoom> rooms;

    public RoomIterator() {
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
