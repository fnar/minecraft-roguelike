package greymerk.roguelike.dungeon.base;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.rooms.RoomSettingParser;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import static com.google.common.collect.Lists.newLinkedList;
import static greymerk.roguelike.dungeon.base.DungeonRoom.CORNER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.getInstance;
import static greymerk.roguelike.dungeon.base.DungeonRoom.getRandomRoom;
import static java.util.stream.IntStream.range;

public class DungeonFactory implements IDungeonFactory {

  private Map<DungeonRoom, Integer> randomRooms = new HashMap<>();
  private DungeonRoom base = CORNER;

  private Iterator<IDungeonRoom> singleRoomsIterator;
  private List<RoomSetting> singleRoomSettings = new LinkedList<>();

  public DungeonFactory() {
  }

  public DungeonFactory(DungeonRoom base) {
    this.base = base;
  }

  public DungeonFactory(JsonArray json) throws Exception {
    for (JsonElement roomSettingJson : json) {
      RoomSetting roomSetting = RoomSettingParser.parse(roomSettingJson.getAsJsonObject());
      add(roomSetting);
    }
  }

  public DungeonFactory(DungeonFactory toCopy) {
    singleRoomSettings = newLinkedList(toCopy.singleRoomSettings);
    for (DungeonRoom room : toCopy.randomRooms.keySet()) {
      randomRooms.put(room, toCopy.randomRooms.get(room));
    }
    base = toCopy.base;
  }

  public DungeonFactory(DungeonFactory parent, DungeonFactory child) {
    base = child.base;
    DungeonFactory dungeonFactory = child.randomRooms.keySet().isEmpty() ? parent : child;
    singleRoomSettings = newLinkedList(dungeonFactory.singleRoomSettings);
    dungeonFactory.randomRooms.keySet()
        .forEach(room -> randomRooms.put(room, dungeonFactory.randomRooms.get(room)));
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

    if (randomRooms.isEmpty()) {
      return getInstance(base);
    }

    WeightedRandomizer<DungeonRoom> randomizer = new WeightedRandomizer<>();
    for (DungeonRoom room : randomRooms.keySet()) {
      randomizer.add(new WeightedChoice<>(room, randomRooms.get(room)));
    }

    DungeonRoom choice = randomizer.get(rand);
    return getInstance(choice);
  }

  public void addSingle(DungeonRoom type) {
    addSingle(type, 1);
  }

  public void addSingle(DungeonRoom type, int num) {
    RoomSetting roomSetting = new RoomSetting(type, null, "single", 0);
    addSingleRoom(roomSetting, num);
  }

  public void addSingleRoom(RoomSetting roomSetting, int num) {
    range(0, num)
        .mapToObj(operand -> roomSetting)
        .forEach(singleRoomSettings::add);
  }

  public void addRandom(DungeonRoom type, int weight) {
    randomRooms.put(type, weight);
  }

  public void addRandomRoom(RoomSetting roomSetting, int weight) {

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
    return randomRooms.equals(other.randomRooms);
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
}
