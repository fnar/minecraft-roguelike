package greymerk.roguelike.dungeon.base;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.collect.Lists.newLinkedList;
import static greymerk.roguelike.dungeon.base.DungeonRoom.getRandomRoom;
import static java.util.stream.IntStream.range;

@ToString
@EqualsAndHashCode
@Getter
public class DungeonFactory {

  private RoomIterator singleRoomsIterator;
  private List<RoomSetting> singleRoomSettings = new LinkedList<>();
  private WeightedRandomizer<RoomSetting> randomRooms = new WeightedRandomizer<>();

  public DungeonFactory() {
  }

  public DungeonFactory(DungeonFactory toCopy) {
    singleRoomSettings = newLinkedList(toCopy.singleRoomSettings);
    randomRooms = new WeightedRandomizer<>(toCopy.randomRooms);
  }

  public DungeonFactory(DungeonFactory parent, DungeonFactory child) {
    singleRoomSettings = newLinkedList((child.singleRoomSettings.isEmpty() ? parent : child).singleRoomSettings);
    randomRooms = new WeightedRandomizer<>((child.randomRooms.isEmpty() ? parent : child).randomRooms);
  }

  public static DungeonFactory getRandom(Random rand, int numRooms) {
    DungeonFactory rooms = new DungeonFactory();
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
    randomRooms.add(new WeightedChoice<>(roomSetting, roomSetting.getWeight()));
  }

  public static IDungeonRoom get(DungeonFactory dungeonFactory, Random random) {
    if (dungeonFactory.singleRoomsIterator == null) {
      dungeonFactory.singleRoomsIterator = new RoomIterator(dungeonFactory, random);
    }

    return dungeonFactory.singleRoomsIterator.getDungeonRoom();
  }

}
