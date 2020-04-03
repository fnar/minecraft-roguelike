package greymerk.roguelike.dungeon.base;

import com.google.common.collect.Lists;

import java.util.Collections;
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
    idkCopy(toCopy);
  }

  public SecretFactory(SecretFactory base, SecretFactory other) {
    if (base != null) {
      idkCopy(base);
    }

    if (other != null) {
      idkCopy(other);
    }
  }

  private void idkCopy(SecretFactory toCopy) {
    toCopy.secretRooms.forEach(this::addRoom);
  }

  public static SecretFactory getRandom(Random rand, int count) {
    SecretFactory secrets = new SecretFactory();
    IntStream.range(0, count).mapToObj(i -> DungeonRoom.getRandomSecret(rand)).forEach(secrets::addRoom);
    return secrets;
  }

  public void add(RoomSetting roomSetting) {
    addRoom(roomSetting.getDungeonRoom(), roomSetting.getCount());
  }

  public void addRoom(DungeonRoom type) {
    addRoom(type, 1);
  }

  private void addRoom(SecretRoom secretRoom) {
    secretRooms.add(secretRoom);
  }

  public void addRoom(DungeonRoom type, int count) {
    RoomSetting roomSetting = newRoomSetting(type, count);
    IntStream.range(0, count)
        .forEach(value -> secretRooms.add(new SecretRoom(roomSetting)));
  }

  private RoomSetting newRoomSetting(DungeonRoom type, int count) {
    return new RoomSetting(type, null, "builtin:spawner", "single", 0, count, Collections.emptyList());
  }

  // todo: return optional
  public IDungeonRoom generateRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos) {
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
        .findFirst()
        .orElse(null);
  }

}