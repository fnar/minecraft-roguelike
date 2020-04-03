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
public class SecretFactory {

  private List<SecretRoom> secretRooms = Lists.newArrayList();

  public SecretFactory() {
  }

  public SecretFactory(SecretFactory toCopy) {
    getSecretRooms().addAll(toCopy.getSecretRooms());
  }

  public SecretFactory(SecretFactory base, SecretFactory other) {
    if (base != null) {
      getSecretRooms().addAll(base.getSecretRooms());
    }

    if (other != null) {
      getSecretRooms().addAll(other.getSecretRooms());
    }
  }

  public static SecretFactory getRandom(Random rand, int count) {
    SecretFactory secrets = new SecretFactory();
    IntStream.range(0, count).mapToObj(i -> RoomType.getRandomSecret(rand)).forEach(type -> secrets.add(type.newSingleRoomSetting()));
    return secrets;
  }

  public void add(RoomSetting roomSetting) {
    IntStream.range(0, roomSetting.getCount())
        .forEach(value -> getSecretRooms().add(new SecretRoom(roomSetting)));
  }

}