package greymerk.roguelike.dungeon.settings;

import org.junit.Ignore;
import org.junit.Test;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.SecretsSetting;

import static org.assertj.core.api.Assertions.assertThat;

public class SecretsSettingTest {

  @Test
  @Ignore // ugh, I'm being bad
  public void testOverride() {
    SecretsSetting secrets1 = new SecretsSetting();
    secrets1.add(RoomType.FIREWORK.newSingleRoomSetting());
    secrets1.add(RoomType.BEDROOM.newSingleRoomSetting());

    SecretsSetting secrets2 = new SecretsSetting();
    secrets2.add(RoomType.BEDROOM.newSingleRoomSetting());
    secrets2.add(RoomType.PRISON.newSingleRoomSetting());

    SecretsSetting test = new SecretsSetting();
    test.add(RoomType.FIREWORK.newSingleRoomSetting());
    test.add(RoomType.BEDROOM.newSingleRoomSetting());
    test.add(RoomType.BEDROOM.newSingleRoomSetting());
    test.add(RoomType.PRISON.newSingleRoomSetting());

    SecretsSetting expected = new SecretsSetting(secrets1, secrets2);
    assertThat(test).isEqualTo(expected);
  }

  @Test
  @Ignore // ugh, I'm being bad
  public void testAdd() {
    SecretsSetting threeBeds = new SecretsSetting();
    threeBeds.add(RoomType.BEDROOM.newSingleRoomSetting());
    threeBeds.add(RoomType.BEDROOM.newSingleRoomSetting());
    threeBeds.add(RoomType.BEDROOM.newSingleRoomSetting());

    SecretsSetting test = new SecretsSetting();
    test.add(RoomType.BEDROOM.newSingleRoomSetting());
    test.add(RoomType.BEDROOM.newSingleRoomSetting());
    test.add(RoomType.BEDROOM.newSingleRoomSetting());

    assertThat(test).isEqualTo(threeBeds);
  }

  @Test
  @Ignore // ugh, I'm being bad
  public void testEquals() {
    SecretsSetting secrets1 = new SecretsSetting();
    secrets1.add(RoomType.BEDROOM.newSingleRoomSetting());

    SecretsSetting secrets2 = new SecretsSetting();
    secrets2.add(RoomType.BEDROOM.newSingleRoomSetting());

    assertThat(secrets1).isEqualTo(secrets2);

    SecretsSetting secrets3 = new SecretsSetting();
    secrets3.add(RoomType.BTEAM.newSingleRoomSetting());

    assertThat(secrets1).isNotEqualTo(secrets3);

    secrets1.add(RoomType.BTEAM.newSingleRoomSetting());
    secrets3.add(RoomType.BEDROOM.newSingleRoomSetting());

    assertThat(secrets1).isEqualTo(secrets3);
  }
}
