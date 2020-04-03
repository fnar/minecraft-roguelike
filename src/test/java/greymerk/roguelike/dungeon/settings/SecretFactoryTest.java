package greymerk.roguelike.dungeon.settings;

import org.junit.Ignore;
import org.junit.Test;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.SecretFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class SecretFactoryTest {

  @Test
  @Ignore // ugh, I'm being bad
  public void testOverride() {
    SecretFactory secrets1 = new SecretFactory();
    secrets1.add(RoomType.FIREWORK.newSingleRoomSetting());
    secrets1.add(RoomType.BEDROOM.newSingleRoomSetting());

    SecretFactory secrets2 = new SecretFactory();
    secrets2.add(RoomType.BEDROOM.newSingleRoomSetting());
    secrets2.add(RoomType.PRISON.newSingleRoomSetting());

    SecretFactory test = new SecretFactory();
    test.add(RoomType.FIREWORK.newSingleRoomSetting());
    test.add(RoomType.BEDROOM.newSingleRoomSetting());
    test.add(RoomType.BEDROOM.newSingleRoomSetting());
    test.add(RoomType.PRISON.newSingleRoomSetting());

    SecretFactory expected = new SecretFactory(secrets1, secrets2);
    assertThat(test).isEqualTo(expected);
  }

  @Test
  @Ignore // ugh, I'm being bad
  public void testAdd() {
    SecretFactory threeBeds = new SecretFactory();
    threeBeds.add(RoomType.BEDROOM.newSingleRoomSetting());
    threeBeds.add(RoomType.BEDROOM.newSingleRoomSetting());
    threeBeds.add(RoomType.BEDROOM.newSingleRoomSetting());

    SecretFactory test = new SecretFactory();
    test.add(RoomType.BEDROOM.newSingleRoomSetting());
    test.add(RoomType.BEDROOM.newSingleRoomSetting());
    test.add(RoomType.BEDROOM.newSingleRoomSetting());

    assertThat(test).isEqualTo(threeBeds);
  }

  @Test
  @Ignore // ugh, I'm being bad
  public void testEquals() {
    SecretFactory secrets1 = new SecretFactory();
    secrets1.add(RoomType.BEDROOM.newSingleRoomSetting());

    SecretFactory secrets2 = new SecretFactory();
    secrets2.add(RoomType.BEDROOM.newSingleRoomSetting());

    assertThat(secrets1).isEqualTo(secrets2);

    SecretFactory secrets3 = new SecretFactory();
    secrets3.add(RoomType.BTEAM.newSingleRoomSetting());

    assertThat(secrets1).isNotEqualTo(secrets3);

    secrets1.add(RoomType.BTEAM.newSingleRoomSetting());
    secrets3.add(RoomType.BEDROOM.newSingleRoomSetting());

    assertThat(secrets1).isEqualTo(secrets3);
  }
}
