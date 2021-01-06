package greymerk.roguelike.dungeon.settings;

import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.SecretRoom;
import greymerk.roguelike.dungeon.rooms.Frequency;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.worldgen.WorldEditor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SecretRoomTest {

  @Test
  public void testEquals() {
    LevelSettings levelSettings = mock(LevelSettings.class);
    WorldEditor worldEditor = mock(WorldEditor.class);
    SecretRoom test = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 1), levelSettings, worldEditor);
    SecretRoom other = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 1), levelSettings, worldEditor);
    assertThat(test).isEqualTo(other);

    SecretRoom third = new SecretRoom(newRoomSetting(RoomType.CAKE, 1), levelSettings, worldEditor);
    assertThat(test).isNotEqualTo(third);

    SecretRoom twoBeds = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 2), levelSettings, worldEditor);
    assertThat(test).isNotEqualTo(twoBeds);

    SecretRoom twoCakes = new SecretRoom(newRoomSetting(RoomType.CAKE, 2), levelSettings, worldEditor);
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
        Optional.empty()
    );
  }
}
