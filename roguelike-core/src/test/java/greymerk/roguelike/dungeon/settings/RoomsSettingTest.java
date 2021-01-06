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
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsPit;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsWood;
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

    RoomsSetting base = new RoomsSetting();
    RoomsSetting other = new RoomsSetting();

    RoomsSetting third = new RoomsSetting();
    base.add(RoomType.BLAZE.newRandomRoomSetting(5));
    base.add(RoomType.CAKE.newRandomRoomSetting(1));
    base.add(RoomType.SLIME.newRandomRoomSetting(2));

    RoomsSetting merge = new RoomsSetting(base, other);
    assertThat(third).isNotEqualTo(merge);

    third.add(RoomType.CAKE.newRandomRoomSetting(1));
    assertThat(third).isNotEqualTo(merge);

    third.add(RoomType.SLIME.newRandomRoomSetting(2));
    assertThat(third).isNotEqualTo(merge);

    third.add(RoomType.BLAZE.newRandomRoomSetting(1));
    assertThat(third).isNotEqualTo(merge);

    third.add(RoomType.BLAZE.newRandomRoomSetting(5));
    assertThat(third).isNotEqualTo(merge);
  }

  @Test
  public void testMerge1() {
    RoomsSetting base = new RoomsSetting();
    base.add(RoomType.BLAZE.newRandomRoomSetting(5));
    base.add(RoomType.CAKE.newRandomRoomSetting(1));
    base.add(RoomType.SLIME.newRandomRoomSetting(2));

    RoomsSetting third = new RoomsSetting(base);
    base.add(RoomType.CREEPER.newSingleRoomSetting());
    base.add(RoomType.CREEPER.newSingleRoomSetting());
    base.add(RoomType.CREEPER.newSingleRoomSetting());
    RoomsSetting merge = new RoomsSetting(base, new RoomsSetting());
    assertThat(third).isNotEqualTo(merge);

    third.add(RoomType.CREEPER.newSingleRoomSetting());
    assertThat(third).isNotEqualTo(merge);

    third.add(RoomType.CREEPER.newSingleRoomSetting());
    third.add(RoomType.CREEPER.newSingleRoomSetting());
    assertThat(third).isEqualTo(merge);
  }

  @Test
  public void testGetSingle() {
    Dungeon.settingsResolver = new SettingsResolver(new SettingsContainer());
    WorldEditor editor = mock(WorldEditor.class);
    when(editor.getRandom()).thenReturn(new Random());

    RoomsSetting roomSettings = new RoomsSetting();
    LevelSettings levelSettings = new LevelSettings();
    levelSettings.setRooms(roomSettings);
    assertThat(new RoomIterator(levelSettings, editor).getDungeonRoom()).isInstanceOf(CornerRoom.class);

    roomSettings = new RoomsSetting();
    roomSettings.add(RoomType.CAKE.newSingleRoomSetting());
    roomSettings.add(RoomType.CAKE.newSingleRoomSetting());
    levelSettings = new LevelSettings();
    levelSettings.setRooms(roomSettings);
    RoomIterator roomIterator = new RoomIterator(levelSettings, editor);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(DungeonsWood.class);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(DungeonsWood.class);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(CornerRoom.class);

    roomSettings = new RoomsSetting();
    roomSettings.add(RoomType.PIT.newSingleRoomSetting());
    levelSettings = new LevelSettings();
    levelSettings.setRooms(roomSettings);
    assertThat(new RoomIterator(levelSettings, editor).getDungeonRoom()).isInstanceOf(DungeonsPit.class);
  }
}
