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
    secrets1.addRoom(RoomType.FIREWORK);
    secrets1.addRoom(RoomType.BEDROOM);

    SecretFactory secrets2 = new SecretFactory();
    secrets2.addRoom(RoomType.BEDROOM);
    secrets2.addRoom(RoomType.PRISON);

    SecretFactory test = new SecretFactory();
    test.addRoom(RoomType.FIREWORK);
    test.addRoom(RoomType.BEDROOM, 2);
    test.addRoom(RoomType.PRISON);

    SecretFactory expected = new SecretFactory(secrets1, secrets2);
    assertThat(test).isEqualTo(expected);
  }

  @Test
  @Ignore // ugh, I'm being bad
  public void testAdd() {
    SecretFactory threeBeds = new SecretFactory();
    threeBeds.addRoom(RoomType.BEDROOM, 3);

    SecretFactory test = new SecretFactory();
    test.addRoom(RoomType.BEDROOM);
    test.addRoom(RoomType.BEDROOM);
    test.addRoom(RoomType.BEDROOM);

    assertThat(test).isEqualTo(threeBeds);
  }

  @Test
  @Ignore // ugh, I'm being bad
  public void testEquals() {
    SecretFactory secrets1 = new SecretFactory();
    secrets1.addRoom(RoomType.BEDROOM);

    SecretFactory secrets2 = new SecretFactory();
    secrets2.addRoom(RoomType.BEDROOM, 1);

    assertThat(secrets1).isEqualTo(secrets2);

    SecretFactory secrets3 = new SecretFactory();
    secrets3.addRoom(RoomType.BTEAM);

    assertThat(secrets1).isNotEqualTo(secrets3);

    secrets1.addRoom(RoomType.BTEAM);
    secrets3.addRoom(RoomType.BEDROOM);

    assertThat(secrets1).isEqualTo(secrets3);
  }
}
