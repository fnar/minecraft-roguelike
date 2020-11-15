package greymerk.roguelike.dungeon.settings;

import org.junit.Test;

import java.util.Collections;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.SecretRoom;
import greymerk.roguelike.dungeon.rooms.Frequency;
import greymerk.roguelike.dungeon.rooms.RoomSetting;

import static org.assertj.core.api.Assertions.assertThat;

public class SecretRoomTest {

  @Test
  public void testEquals() {
    SecretRoom test = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 1));
    SecretRoom other = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 1));
    assertThat(test).isEqualTo(other);

    SecretRoom third = new SecretRoom(newRoomSetting(RoomType.CAKE, 1));
    assertThat(test).isNotEqualTo(third);

    SecretRoom twoBeds = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 2));
    assertThat(test).isNotEqualTo(twoBeds);

    SecretRoom twoCakes = new SecretRoom(newRoomSetting(RoomType.CAKE, 2));
    assertThat(twoBeds).isNotEqualTo(twoCakes);

  }

  private RoomSetting newRoomSetting(RoomType type, int count) {
    return new RoomSetting(
        type,
        null,
        Frequency.SINGLE,
        0,
        count,
        Collections.emptyList(),
        null
    );
  }
}
