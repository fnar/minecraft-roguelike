package greymerk.roguelike.dungeon.base;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.rooms.RoomSettingParser;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import static greymerk.roguelike.dungeon.base.DungeonRoom.CORNER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.getInstance;
import static greymerk.roguelike.dungeon.base.DungeonRoom.getRandomRoom;
import static java.util.stream.IntStream.range;

public class DungeonFactory implements IDungeonFactory {

  private Map<DungeonRoom, Integer> singles;
  private Map<DungeonRoom, Integer> multiple;
  private DungeonRoom base;

  private Iterator<IDungeonRoom> singleRooms;


  public DungeonFactory() {
    this(CORNER);
  }

  public DungeonFactory(DungeonRoom base) {
    singles = new HashMap<>();
    multiple = new HashMap<>();
    this.base = base;
  }

  public DungeonFactory(JsonArray json) throws Exception {
    this();

    for (JsonElement e : json) {
      RoomSetting roomSetting = RoomSettingParser.parse(e.getAsJsonObject());
      add(roomSetting);
    }
  }

  public DungeonFactory(DungeonFactory toCopy) {
    this();
    for (DungeonRoom room : toCopy.singles.keySet()) {
      singles.put(room, toCopy.singles.get(room));
    }

    for (DungeonRoom room : toCopy.multiple.keySet()) {
      multiple.put(room, toCopy.multiple.get(room));
    }

    base = toCopy.base;
  }

  public DungeonFactory(DungeonFactory base, DungeonFactory other) {
    this();
    this.base = other.base;
    DungeonFactory dungeonFactory = other.multiple.keySet().isEmpty() ? base : other;
    dungeonFactory.singles.keySet()
        .forEach(room -> singles.put(room, dungeonFactory.singles.get(room)));
    dungeonFactory.multiple.keySet()
        .forEach(room -> multiple.put(room, dungeonFactory.multiple.get(room)));
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
      addSingle(roomSetting.getDungeonRoom());
    }
    if (roomSetting.getFrequency().equals("random")) {
      addRandom(roomSetting.getDungeonRoom(), roomSetting.getWeight());
    }
  }

  public IDungeonRoom get(Random rand) {

    if (singleRooms == null) {
      singleRooms = new RoomIterator();
    }

    if (singleRooms.hasNext()) {
      return singleRooms.next();
    }

    Set<DungeonRoom> keyset = multiple.keySet();
    if (keyset.isEmpty()) {
      return getInstance(base);
    }

    WeightedRandomizer<DungeonRoom> randomizer = new WeightedRandomizer<>();
    for (DungeonRoom room : keyset) {
      randomizer.add(new WeightedChoice<>(room, multiple.get(room)));
    }

    DungeonRoom choice = randomizer.get(rand);
    return getInstance(choice);
  }

  public void addSingle(DungeonRoom type) {
    addSingle(type, 1);
  }

  public void addSingle(DungeonRoom type, int num) {
    if (!singles.containsKey(type)) {
      singles.put(type, num);
      return;
    }

    int count = singles.get(type);
    count += num;
    singles.put(type, count);
  }

  public void addRandom(DungeonRoom type, int weight) {
    multiple.put(type, weight);
  }

  @Override
  public boolean equals(Object o) {
    DungeonFactory other = (DungeonFactory) o;

    if (!base.equals(other.base)) {
      return false;
    }

    if (!singles.equals(other.singles)) {
      return false;
    }

    return multiple.equals(other.multiple);
  }

  private class RoomIterator implements Iterator<IDungeonRoom> {
    private PriorityQueue<IDungeonRoom> rooms;

    public RoomIterator() {
      rooms = new PriorityQueue<>();

      singles.keySet()
          .forEach(dungeonRoom ->
              range(0, singles.get(dungeonRoom))
                  .forEach(i -> rooms.add(getInstance(dungeonRoom))));
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
