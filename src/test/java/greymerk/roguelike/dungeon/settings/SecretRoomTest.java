package greymerk.roguelike.dungeon.settings;

import org.junit.Test;

import java.util.Collections;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.SecretRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;

public class SecretRoomTest {

  @Test
  public void testEquals() {

    SecretRoom test = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 1));
    SecretRoom other = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 1));

    assert (test.equals(other));

    SecretRoom third = new SecretRoom(newRoomSetting(RoomType.CAKE, 1));

    assert (!test.equals(third));

    SecretRoom twoBeds = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 2));

    assert (!test.equals(twoBeds));

    SecretRoom twoCakes = new SecretRoom(newRoomSetting(RoomType.CAKE, 2));

    assert (!twoBeds.equals(twoCakes));

  }

  private RoomSetting newRoomSetting(RoomType type, int count) {
    return new RoomSetting(type, null, "builtin:spawner", "single", 0, count, Collections.emptyList());
  }
}
