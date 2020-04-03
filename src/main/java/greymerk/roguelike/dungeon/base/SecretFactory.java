package greymerk.roguelike.dungeon.base;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static java.util.Optional.ofNullable;

@EqualsAndHashCode
@ToString
public class SecretFactory {

  private List<SecretRoom> secretRooms = Lists.newArrayList();

  public SecretFactory() {
  }

  public SecretFactory(SecretFactory toCopy) {
    secretRooms.addAll(toCopy.secretRooms);
  }

  public SecretFactory(SecretFactory base, SecretFactory other) {
    if (base != null) {
      secretRooms.addAll(base.secretRooms);
    }

    if (other != null) {
      secretRooms.addAll(other.secretRooms);
    }
  }

  public static SecretFactory getRandom(Random rand, int count) {
    SecretFactory secrets = new SecretFactory();
    IntStream.range(0, count).mapToObj(i -> RoomType.getRandomSecret(rand)).forEach(type -> secrets.add(type.newSingleRoomSetting()));
    return secrets;
  }

  public void add(RoomSetting roomSetting) {
    IntStream.range(0, roomSetting.getCount())
        .forEach(value -> secretRooms.add(new SecretRoom(roomSetting)));
  }

  public Optional<IDungeonRoom> generateSecretMaybe(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos) {
    return secretRooms.stream()
        .map(secretRoom -> {
          Optional<IDungeonRoom> dungeonRoomMaybe = ofNullable(secretRoom.generate(editor, rand, settings, dir, pos));
          if (!dungeonRoomMaybe.isPresent()) {
            return dungeonRoomMaybe.orElse(null);
          }
          secretRooms.remove(secretRoom);
          return dungeonRoomMaybe.orElse(null);
        })
        .filter(Objects::nonNull)
        .findFirst();
  }

}