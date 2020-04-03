package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.worldgen.filter.Filter;

import static org.assertj.core.api.Assertions.assertThat;

public class LevelSettingsTest {

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void testEquals() {
    LevelSettings base = new LevelSettings();
    base.setGenerator(LevelGenerator.CLASSIC);
    LevelSettings other = new LevelSettings();
    assertThat(base).isNotEqualTo(other);

    other.setGenerator(LevelGenerator.CLASSIC);
    assertThat(base).isEqualTo(other);

    DungeonFactory baseRooms = new DungeonFactory();
    DungeonFactory otherRooms = new DungeonFactory();
    baseRooms.add(DungeonRoom.BRICK.newRandomRoomSetting(1));
    base.setRooms(baseRooms);
    assertThat(base).isNotEqualTo(other);

    otherRooms.add(DungeonRoom.BRICK.newRandomRoomSetting(1));
    other.setRooms(otherRooms);
    assertThat(base).isEqualTo(other);

    baseRooms.add(DungeonRoom.CAKE.newRandomRoomSetting(2));
    assertThat(base).isNotEqualTo(other);

    otherRooms.add(DungeonRoom.CAKE.newRandomRoomSetting(2));
    assertThat(base).isEqualTo(other);
  }

  @Test
  public void testRoomsMerge() {

    LevelSettings base = new LevelSettings();
    LevelSettings other = new LevelSettings();

    DungeonFactory baseRooms = new DungeonFactory();
    DungeonFactory otherRooms = new DungeonFactory();

    base.setRooms(baseRooms);
    other.setRooms(otherRooms);

    LevelSettings control = new LevelSettings();
    DungeonFactory controlRooms = new DungeonFactory();
    control.setRooms(controlRooms);
    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings merge = new LevelSettings(base, other, overrides);
    assertThat(control).isEqualTo(merge);

    baseRooms.add(DungeonRoom.CAKE.newSingleRoomSetting());
    merge = new LevelSettings(base, other, overrides);
    assertThat(control).isNotEqualTo(merge);

    controlRooms.add(DungeonRoom.CAKE.newSingleRoomSetting());
    assertThat(control).isEqualTo(merge);
  }

  @Test
  public void testSecretsMerge() {

    LevelSettings base = new LevelSettings();
    LevelSettings other = new LevelSettings();

    SecretFactory baseSecrets = new SecretFactory();
    SecretFactory otherSecrets = new SecretFactory();

    base.setSecrets(baseSecrets);
    other.setSecrets(otherSecrets);

    SecretFactory controlSecrets = new SecretFactory();
    LevelSettings control = new LevelSettings();
    control.setSecrets(controlSecrets);

    LevelSettings merge;
    Set<SettingsType> overrides = new HashSet<>();

    merge = new LevelSettings(base, other, overrides);

    assert control.equals(merge);

    baseSecrets.addRoom(DungeonRoom.BEDROOM, 2);

    merge = new LevelSettings(base, other, overrides);
    assert !control.equals(merge);

    controlSecrets.addRoom(DungeonRoom.BEDROOM, 2);
    assert control.equals(merge);
  }

  @Test
  public void testThemeMerge() {
  }

  @Test
  public void testSegmentsMerge() {
  }

  @Test
  public void testSpawnerMerge() {
  }

  @Test
  public void testGeneratorMerge() {

    LevelSettings compare = new LevelSettings();
    compare.setGenerator(LevelGenerator.CLASSIC);

    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings base = new LevelSettings();
    LevelSettings other = new LevelSettings();

    LevelSettings control = new LevelSettings(base, other, overrides);

    assert !control.equals(compare);

    other.setGenerator(LevelGenerator.CLASSIC);
    assert other.equals(compare);

    LevelSettings merge = new LevelSettings(base, other, overrides);

    assert merge.equals(compare);

    LevelSettings merge2 = new LevelSettings(other, base, overrides);

    assert merge2.equals(compare);

  }

  @Test
  public void testFilterMerge() {
    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings compare = new LevelSettings();
    assert compare.getFilters().isEmpty();
    compare.addFilter(Filter.VINE);
    assert compare.getFilters().contains(Filter.VINE);

    LevelSettings base = new LevelSettings();
    base.addFilter(Filter.VINE);

    assert new LevelSettings(base).getFilters().contains(Filter.VINE);

    LevelSettings other = new LevelSettings();

    assert !new LevelSettings(other, other, overrides).getFilters().contains(Filter.VINE);

    assert new LevelSettings(base, other, overrides).getFilters().contains(Filter.VINE);

    assert new LevelSettings(other, base, overrides).getFilters().contains(Filter.VINE);

  }
}
