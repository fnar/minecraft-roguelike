package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
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

    base.addSingle(DungeonRoom.BRICK.newSingleRoomSetting());
    assertThat(base).isNotEqualTo(other);

    other.addSingle(DungeonRoom.BRICK.newSingleRoomSetting());
    assertThat(base).isEqualTo(other);

    base.addRandom(DungeonRoom.CRYPT.newRandomRoomSetting(2));
    assertThat(base).isNotEqualTo(other);

    other.addRandom(DungeonRoom.CRYPT.newRandomRoomSetting(2));
    assertThat(base).isEqualTo(other);

    base.addRandom(DungeonRoom.DARKHALL.newRandomRoomSetting(3));
    assertThat(base).isNotEqualTo(other);

    other.addRandom(DungeonRoom.DARKHALL.newRandomRoomSetting(1));
    assertThat(base).isNotEqualTo(other);

    other.addRandom(DungeonRoom.DARKHALL.newRandomRoomSetting(3));
    assertThat(base).isNotEqualTo(other);

    base.addRandom(DungeonRoom.DARKHALL.newRandomRoomSetting(1));
    assertThat(base).isNotEqualTo(other);
  }

  @Test
  public void testMerge() {

    DungeonFactory base = new DungeonFactory();
    DungeonFactory other = new DungeonFactory();

    DungeonFactory third = new DungeonFactory();
    base.addRandom(DungeonRoom.BLAZE.newRandomRoomSetting(5));
    base.addRandom(DungeonRoom.CAKE.newRandomRoomSetting(1));
    base.addRandom(DungeonRoom.SLIME.newRandomRoomSetting(2));

    DungeonFactory merge = new DungeonFactory(base, other);
    assertThat(third).isNotEqualTo(merge);

    third.addRandom(DungeonRoom.CAKE.newRandomRoomSetting(1));
    assertThat(third).isNotEqualTo(merge);

    third.addRandom(DungeonRoom.SLIME.newRandomRoomSetting(2));
    assertThat(third).isNotEqualTo(merge);

    third.addRandom(DungeonRoom.BLAZE.newRandomRoomSetting(1));
    assertThat(third).isNotEqualTo(merge);

    third.addRandom(DungeonRoom.BLAZE.newRandomRoomSetting(5));
    assertThat(third).isNotEqualTo(merge);
  }

  @Test
  public void testMerge1() {
    DungeonFactory base = new DungeonFactory();
    base.addRandom(DungeonRoom.BLAZE.newRandomRoomSetting(5));
    base.addRandom(DungeonRoom.CAKE.newRandomRoomSetting(1));
    base.addRandom(DungeonRoom.SLIME.newRandomRoomSetting(2));

    DungeonFactory third = new DungeonFactory(base);
    base.addSingle(DungeonRoom.CREEPER.newSingleRoomSetting());
    base.addSingle(DungeonRoom.CREEPER.newSingleRoomSetting());
    base.addSingle(DungeonRoom.CREEPER.newSingleRoomSetting());
    DungeonFactory merge = new DungeonFactory(base, new DungeonFactory());
    assertThat(third).isNotEqualTo(merge);

    third.addSingle(DungeonRoom.CREEPER.newSingleRoomSetting());
    assertThat(third).isNotEqualTo(merge);

    third.addSingle(DungeonRoom.CREEPER.newSingleRoomSetting());
    third.addSingle(DungeonRoom.CREEPER.newSingleRoomSetting());
    assertThat(third).isEqualTo(merge);
  }

  @Test
  public void testGetSingle() {
    Dungeon.settingsResolver = new SettingsResolver(new SettingsContainer());


    Random rand = new Random();

    DungeonFactory rooms = new DungeonFactory(DungeonRoom.CORNER);
    assertThat(rooms.get(rand)).isInstanceOf(DungeonCorner.class);

    rooms = new DungeonFactory(DungeonRoom.CORNER);
    rooms.addSingle(DungeonRoom.CAKE.newSingleRoomSetting());
    rooms.addSingle(DungeonRoom.CAKE.newSingleRoomSetting());
    assertThat(rooms.get(rand)).isInstanceOf(DungeonsWood.class);
    assertThat(rooms.get(rand)).isInstanceOf(DungeonsWood.class);
    assertThat(rooms.get(rand)).isInstanceOf(DungeonCorner.class);

    rooms = new DungeonFactory(DungeonRoom.CORNER);
    rooms.addSingle(DungeonRoom.PIT.newSingleRoomSetting());
    assertThat(rooms.get(rand)).isInstanceOf(DungeonsPit.class);
  }
}
