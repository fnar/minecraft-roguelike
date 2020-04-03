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
import static greymerk.roguelike.dungeon.base.RoomType.getRandomIntersection;
import static java.util.stream.IntStream.range;

@ToString
@EqualsAndHashCode
@Getter
public class RoomsSetting {

  private List<RoomSetting> singleRoomSettings = new LinkedList<>();
  private WeightedRandomizer<RoomSetting> randomRooms = new WeightedRandomizer<>();

  public RoomsSetting() {
  }

  public RoomsSetting(RoomsSetting toCopy) {
    singleRoomSettings = newLinkedList(toCopy.singleRoomSettings);
    randomRooms = new WeightedRandomizer<>(toCopy.randomRooms);
  }

  public RoomsSetting(RoomsSetting parent, RoomsSetting child) {
    singleRoomSettings = newLinkedList((child.singleRoomSettings.isEmpty() ? parent : child).singleRoomSettings);
    randomRooms = new WeightedRandomizer<>((child.randomRooms.isEmpty() ? parent : child).randomRooms);
  }

  public static RoomsSetting getRandom(Random random, int numRooms) {
    RoomsSetting rooms = new RoomsSetting();
    range(0, numRooms).forEach(i -> rooms.add(getRandomIntersection(random).newRandomRoomSetting(1)));
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
}
