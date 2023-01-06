package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.RoomIterator;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.rooms.prototype.CornerRoom;
import greymerk.roguelike.dungeon.rooms.prototype.PitRoom;
import greymerk.roguelike.dungeon.rooms.prototype.CakeRoom;
import greymerk.roguelike.worldgen.WorldEditor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomsSettingTest {

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void testEquals() {
    RoomsSetting base = new RoomsSetting();
    RoomsSetting other = new RoomsSetting();
    assertThat(base).isEqualTo(other);

    base.add(RoomType.BRICK.newSingleRoomSetting());
    assertThat(base).isNotEqualTo(other);

    other.add(RoomType.BRICK.newSingleRoomSetting());
    assertThat(base).isEqualTo(other);

    base.add(RoomType.CRYPT.newRandomRoomSetting(2));
    assertThat(base).isNotEqualTo(other);

    other.add(RoomType.CRYPT.newRandomRoomSetting(2));
    assertThat(base).isEqualTo(other);

    base.add(RoomType.DARKHALL.newRandomRoomSetting(3));
    assertThat(base).isNotEqualTo(other);

    other.add(RoomType.DARKHALL.newRandomRoomSetting(1));
    assertThat(base).isNotEqualTo(other);

    other.add(RoomType.DARKHALL.newRandomRoomSetting(3));
    assertThat(base).isNotEqualTo(other);

    base.add(RoomType.DARKHALL.newRandomRoomSetting(1));
    assertThat(base).isNotEqualTo(other);
  }

  @Test
  public void testMerge() {
    RoomsSetting expected = new RoomsSetting();
    expected.add(RoomType.BLAZE.newRandomRoomSetting(5));
    expected.add(RoomType.CAKE.newRandomRoomSetting(1));
    expected.add(RoomType.SLIME.newRandomRoomSetting(2));
    expected.add(RoomType.BRICK.newRandomRoomSetting(5));
    expected.add(RoomType.TREETHO.newRandomRoomSetting(1));
    expected.add(RoomType.AVIDYA.newRandomRoomSetting(2));

    RoomsSetting parent0 = new RoomsSetting();
    parent0.add(RoomType.BLAZE.newRandomRoomSetting(5));
    parent0.add(RoomType.CAKE.newRandomRoomSetting(1));
    parent0.add(RoomType.SLIME.newRandomRoomSetting(2));

    RoomsSetting parent1 = new RoomsSetting();
    parent1.add(RoomType.BRICK.newRandomRoomSetting(5));
    parent1.add(RoomType.TREETHO.newRandomRoomSetting(1));
    parent1.add(RoomType.AVIDYA.newRandomRoomSetting(2));

    RoomsSetting child = new RoomsSetting()
        .inherit(parent0)
        .inherit(parent1);

    assertThat(child).isEqualTo(expected);
  }

  @Test
  public void testMerge1() {
    RoomsSetting base = new RoomsSetting();
    base.add(RoomType.BLAZE.newRandomRoomSetting(5));
    base.add(RoomType.CAKE.newRandomRoomSetting(1));
    base.add(RoomType.SLIME.newRandomRoomSetting(2));
    RoomsSetting other = new RoomsSetting(base);

    base.add(RoomType.CREEPER.newSingleRoomSetting());
    base.add(RoomType.CREEPER.newSingleRoomSetting());
    base.add(RoomType.CREEPER.newSingleRoomSetting());

    RoomsSetting merge = new RoomsSetting().inherit(base);
    assertThat(other).isNotEqualTo(merge);

    other.add(RoomType.CREEPER.newSingleRoomSetting());
    assertThat(other).isNotEqualTo(merge);

    other.add(RoomType.CREEPER.newSingleRoomSetting());
    other.add(RoomType.CREEPER.newSingleRoomSetting());
    assertThat(other).isEqualTo(merge);
  }

  @Test
  public void testGetSingle() {
    Dungeon.settingsResolver = new SettingsResolver(new SettingsContainer());
    WorldEditor editor = mock(WorldEditor.class);
    when(editor.getRandom()).thenReturn(new Random());

    RoomsSetting roomSettings = new RoomsSetting();
    LevelSettings levelSettings = new LevelSettings(0);
    levelSettings.setRooms(roomSettings);
    assertThat(new RoomIterator(levelSettings, editor).getDungeonRoom()).isInstanceOf(CornerRoom.class);

    roomSettings = new RoomsSetting();
    roomSettings.add(RoomType.CAKE.newSingleRoomSetting());
    roomSettings.add(RoomType.CAKE.newSingleRoomSetting());
    levelSettings = new LevelSettings(0);
    levelSettings.setRooms(roomSettings);
    RoomIterator roomIterator = new RoomIterator(levelSettings, editor);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(CakeRoom.class);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(CakeRoom.class);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(CornerRoom.class);

    roomSettings = new RoomsSetting();
    roomSettings.add(RoomType.PIT.newSingleRoomSetting());
    levelSettings = new LevelSettings(0);
    levelSettings.setRooms(roomSettings);
    assertThat(new RoomIterator(levelSettings, editor).getDungeonRoom()).isInstanceOf(PitRoom.class);
  }
}
