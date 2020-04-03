package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.RoomIterator;
import greymerk.roguelike.dungeon.rooms.DungeonCorner;
import greymerk.roguelike.dungeon.rooms.DungeonsPit;
import greymerk.roguelike.dungeon.rooms.DungeonsWood;

import static org.assertj.core.api.Assertions.assertThat;

public class DungeonFactoryTest {

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void testEquals() {
    DungeonFactory base = new DungeonFactory();
    DungeonFactory other = new DungeonFactory();
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

    DungeonFactory base = new DungeonFactory();
    DungeonFactory other = new DungeonFactory();

    DungeonFactory third = new DungeonFactory();
    base.add(DungeonRoom.BLAZE.newRandomRoomSetting(5));
    base.add(DungeonRoom.CAKE.newRandomRoomSetting(1));
    base.add(DungeonRoom.SLIME.newRandomRoomSetting(2));

    DungeonFactory merge = new DungeonFactory(base, other);
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
    DungeonFactory base = new DungeonFactory();
    base.add(DungeonRoom.BLAZE.newRandomRoomSetting(5));
    base.add(DungeonRoom.CAKE.newRandomRoomSetting(1));
    base.add(DungeonRoom.SLIME.newRandomRoomSetting(2));

    DungeonFactory third = new DungeonFactory(base);
    base.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    base.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    base.add(DungeonRoom.CREEPER.newSingleRoomSetting());
    DungeonFactory merge = new DungeonFactory(base, new DungeonFactory());
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
    DungeonFactory rooms = new DungeonFactory();
    assertThat(new RoomIterator(rooms, random).getDungeonRoom()).isInstanceOf(DungeonCorner.class);

    rooms = new DungeonFactory();
    rooms.add(DungeonRoom.CAKE.newSingleRoomSetting());
    rooms.add(DungeonRoom.CAKE.newSingleRoomSetting());
    RoomIterator roomIterator = new RoomIterator(rooms, random);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(DungeonsWood.class);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(DungeonsWood.class);
    assertThat(roomIterator.getDungeonRoom()).isInstanceOf(DungeonCorner.class);

    rooms = new DungeonFactory();
    rooms.add(DungeonRoom.PIT.newSingleRoomSetting());
    assertThat(new RoomIterator(rooms, random).getDungeonRoom()).isInstanceOf(DungeonsPit.class);
  }
}
