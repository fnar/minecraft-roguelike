package greymerk.roguelike.dungeon.base;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class SecretsSetting {

  private List<SecretRoom> secretRooms = Lists.newArrayList();

  public SecretsSetting() {
  }

  public SecretsSetting(SecretsSetting toCopy) {
    getSecretRooms().addAll(toCopy.getSecretRooms());
  }

  public SecretsSetting(SecretsSetting base, SecretsSetting other) {
    if (base != null) {
      getSecretRooms().addAll(base.getSecretRooms());
    }

    if (other != null) {
      getSecretRooms().addAll(other.getSecretRooms());
    }
  }

  public static SecretsSetting getRandom(Random rand, int count) {
    SecretsSetting secrets = new SecretsSetting();
    IntStream.range(0, count).mapToObj(i -> RoomType.getRandomSecret(rand)).forEach(type -> secrets.add(type.newSingleRoomSetting()));
    return secrets;
  }

  public void add(RoomSetting roomSetting) {
    IntStream.range(0, roomSetting.getCount())
        .forEach(value -> getSecretRooms().add(new SecretRoom(roomSetting)));
  }

}