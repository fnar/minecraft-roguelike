package greymerk.roguelike.dungeon.settings;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.SecretRoom;
import greymerk.roguelike.dungeon.rooms.Frequency;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.worldgen.WorldEditor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecretRoomTest {

  private WorldEditor mockWorldEditor;
  private LevelSettings mockLevelSettings;

  @Before
  public void setUp() {
    mockLevelSettings = mock(LevelSettings.class);
    mockWorldEditor = mock(WorldEditor.class);
    when(mockWorldEditor.getRandom()).thenReturn(new Random());
  }

  @Test
  public void testEquals() {
    SecretRoom test = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 1), mockLevelSettings, mockWorldEditor);
    SecretRoom other = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 1), mockLevelSettings, mockWorldEditor);
    assertThat(test).isEqualTo(other);

    SecretRoom third = new SecretRoom(newRoomSetting(RoomType.CAKE, 1), mockLevelSettings, mockWorldEditor);
    assertThat(test).isNotEqualTo(third);

    SecretRoom twoBeds = new SecretRoom(newRoomSetting(RoomType.BEDROOM, 2), mockLevelSettings, mockWorldEditor);
    assertThat(test).isNotEqualTo(twoBeds);

    SecretRoom twoCakes = new SecretRoom(newRoomSetting(RoomType.CAKE, 2), mockLevelSettings, mockWorldEditor);
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
