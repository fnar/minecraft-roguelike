package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.RoomIterator;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.rooms.DungeonCorner;
import greymerk.roguelike.dungeon.rooms.DungeonsPit;
import greymerk.roguelike.dungeon.rooms.DungeonsWood;

import static org.assertj.core.api.Assertions.assertThat;

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

    base.add(DungeonRoom.BRICK.newSingleRoomSetting());
    assertThat(base).isNotEqualTo(other);

    other.add(DungeonRoom.BRICK.newSingleRoomSetting());
    assertThat(base).isEqualTo(other);

    base.add(DungeonRoom.CRYPT.newRandomRoomSetting(2));
    assertThat(base).isNotEqualTo(other);

    other.add(DungeonRoom.CRYPT.newRandomRoomSetting(2));
    assertThat(base).isEqualTo(other);

    base.add(DungeonRoom.DARKHALL.newRandomRoomSetting(3));
    assertThat(base).isNotEqualTo(other);

    other.add(DungeonRoom.DARKHALL.newRandomRoomSetting(1));
    assertThat(base).isNotEqualTo(other);

    other.add(DungeonRoom.DARKHALL.newRandomRoomSetting(3));
    assertThat(base).isNotEqualTo(other);

    base.add(DungeonRoom.DARKHALL.newRandomRoomSetting(1));
    assertThat(base).isNotEqualTo(other);
  }

  @Test
  public void testMerge() {

    RoomsSetting base = new RoomsSetting();
    RoomsSetting other = new RoomsSetting();

    RoomsSetting third = new RoomsSetting();
    base.add(DungeonRoom.BLAZE.newRandomRoomSetting(5));
    base.add(DungeonRoom.CAKE.newRandomRoomSetting(1));
    base.add(DungeonRoom.SLIME.newRandomRoomSetting(2));

    RoomsSetting merge = new RoomsSetting(base, other);
    assertThat(third).isNotEqualTo(merge);

    third.add(DungeonRoom.CAKE.newRandomRoomSetting(1));
    assertThat(third).isNotEqualTo(merge);

    third.add(DungeonRoom.SLIME.newRandomRoomSetting(2));
    assertThat(third).isNotEqualTo(merge);

    third.add(DungeonRoom.BLAZE.newRandomRoomSetting(1));
    assertThat(third).isNotEqualTo(merge);

    third.add(DungeonRoom.BLAZE.newRandomRoomSetting(5));
    assertThat(third).isNotEqualTo(merge);
  }

  @Test
  public void testMerge1() {
    RoomsSetting base = new RoomsSetting();
    base.add(DungeonRoom.BLAZE.newRandomRoomSetting(5));
    base.add(DungeonRoom.CAKE.newRandomRoomSetting(1));
    base.add(DungeonRoom.SLIME.newRandomRoomSetting(2));

    RoomsSetting third = new RoomsSetting(base);
    base.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    base.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    base.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    RoomsSetting merge = new RoomsSetting(base, new RoomsSetting());
    assertThat(third).isNotEqualTo(merge);

    third.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    assertThat(third).isNotEqualTo(merge);

    third.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    third.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    assertThat(third).isEqualTo(merge);
  }

  @Test
  public void testGetSingle() {
    Dungeon.settingsResolver = new SettingsResolver(new SettingsContainer());
    Random random = new Random();
    RoomsSetting rooms = new RoomsSetting();
    assertThat(new RoomIterator(rooms, random).getDungeonRoom()).isInstanceOf(DungeonCorner.class);

    rooms = new RoomsSetting();
    rooms.add(DungeonRoom.CAKE.newSingleRoomSetting());
    rooms.add(DungeonRoom.CAKE.newSingleRoomSetting());
    RoomIterator roomIterator = new RoomIterator(rooms, random);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(DungeonsWood.class);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(DungeonsWood.class);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(DungeonCorner.class);

    rooms = new RoomsSetting();
    rooms.add(DungeonRoom.PIT.newSingleRoomSetting());
    assertThat(new RoomIterator(rooms, random).getDungeonRoom()).isInstanceOf(DungeonsPit.class);
  }
}
