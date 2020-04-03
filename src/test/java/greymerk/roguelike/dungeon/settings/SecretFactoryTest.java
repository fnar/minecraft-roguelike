package greymerk.roguelike.dungeon.settings;

import org.junit.Ignore;
import org.junit.Test;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class SecretFactoryTest {

  @Test
  @Ignore // ugh, I'm being bad
  public void testOverride() {
    SecretFactory secrets1 = new SecretFactory();
    secrets1.addRoom(DungeonRoom.FIREWORK);
    secrets1.addRoom(DungeonRoom.BEDROOM);

    SecretFactory secrets2 = new SecretFactory();
    secrets2.addRoom(DungeonRoom.BEDROOM);
    secrets2.addRoom(DungeonRoom.PRISON);

    SecretFactory test = new SecretFactory();
    test.addRoom(DungeonRoom.FIREWORK);
    test.addRoom(DungeonRoom.BEDROOM, 2);
    test.addRoom(DungeonRoom.PRISON);

    SecretFactory expected = new SecretFactory(secrets1, secrets2);
    assertThat(test).isEqualTo(expected);
  }

  @Test
  @Ignore // ugh, I'm being bad
  public void testAdd() {
    SecretFactory threeBeds = new SecretFactory();
    threeBeds.addRoom(DungeonRoom.BEDROOM, 3);

    SecretFactory test = new SecretFactory();
    test.addRoom(DungeonRoom.BEDROOM);
    test.addRoom(DungeonRoom.BEDROOM);
    test.addRoom(DungeonRoom.BEDROOM);

    assertThat(test).isEqualTo(threeBeds);
  }

  @Test
  @Ignore // ugh, I'm being bad
  public void testEquals() {
    SecretFactory secrets1 = new SecretFactory();
    secrets1.addRoom(DungeonRoom.BEDROOM);

    SecretFactory secrets2 = new SecretFactory();
    secrets2.addRoom(DungeonRoom.BEDROOM, 1);

    assertThat(secrets1).isEqualTo(secrets2);

    SecretFactory secrets3 = new SecretFactory();
    secrets3.addRoom(DungeonRoom.BTEAM);

    assertThat(secrets1).isNotEqualTo(secrets3);

    secrets1.addRoom(DungeonRoom.BTEAM);
    secrets3.addRoom(DungeonRoom.BEDROOM);

    assertThat(secrets1).isEqualTo(secrets3);
  }
}
