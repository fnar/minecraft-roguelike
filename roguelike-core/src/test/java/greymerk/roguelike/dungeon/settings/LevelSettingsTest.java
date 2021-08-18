package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretsSetting;
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

    RoomsSetting baseRooms = new RoomsSetting();
    RoomsSetting otherRooms = new RoomsSetting();
    baseRooms.add(RoomType.BRICK.newRandomRoomSetting(1));
    base.setRooms(baseRooms);
    assertThat(base).isNotEqualTo(other);

    otherRooms.add(RoomType.BRICK.newRandomRoomSetting(1));
    other.setRooms(otherRooms);
    assertThat(base).isEqualTo(other);

    baseRooms.add(RoomType.CAKE.newRandomRoomSetting(2));
    assertThat(base).isNotEqualTo(other);

    otherRooms.add(RoomType.CAKE.newRandomRoomSetting(2));
    assertThat(base).isEqualTo(other);
  }

  @Test
  public void testRoomsMerge() {

    LevelSettings base = new LevelSettings();
    LevelSettings other = new LevelSettings();

    RoomsSetting baseRooms = new RoomsSetting();
    RoomsSetting otherRooms = new RoomsSetting();

    base.setRooms(baseRooms);
    other.setRooms(otherRooms);

    LevelSettings control = new LevelSettings();
    RoomsSetting controlRooms = new RoomsSetting();
    control.setRooms(controlRooms);
    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings merge = new LevelSettings(base, other, overrides);
    assertThat(control).isEqualTo(merge);

    baseRooms.add(RoomType.CAKE.newSingleRoomSetting());
    merge = new LevelSettings(base, other, overrides);
    assertThat(control).isNotEqualTo(merge);

    controlRooms.add(RoomType.CAKE.newSingleRoomSetting());
    assertThat(control).isEqualTo(merge);
  }

  @Test
  public void testSecretsMerge() {

    LevelSettings base = new LevelSettings();
    LevelSettings other = new LevelSettings();

    SecretsSetting baseSecrets = new SecretsSetting();
    SecretsSetting otherSecrets = new SecretsSetting();

    base.setSecrets(baseSecrets);
    other.setSecrets(otherSecrets);

    SecretsSetting controlSecrets = new SecretsSetting();
    LevelSettings control = new LevelSettings();
    control.setSecrets(controlSecrets);

    LevelSettings merge;
    Set<SettingsType> overrides = new HashSet<>();

    merge = new LevelSettings(base, other, overrides);

    assert control.equals(merge);

    baseSecrets.add(RoomType.BEDROOM.newSingleRoomSetting());
    baseSecrets.add(RoomType.BEDROOM.newSingleRoomSetting());

    merge = new LevelSettings(base, other, overrides);
    assert !control.equals(merge);

    controlSecrets.add(RoomType.BEDROOM.newSingleRoomSetting());
    controlSecrets.add(RoomType.BEDROOM.newSingleRoomSetting());
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
  public void testFilterMerge0() {
    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings compare = new LevelSettings();
    assert compare.getFilters().isEmpty();
    compare.addFilter(Filter.VINE);
    assert compare.getFilters().contains(Filter.VINE);

    LevelSettings base = new LevelSettings();
    base.addFilter(Filter.VINE);

    assert new LevelSettings(base).getFilters().contains(Filter.VINE);

    LevelSettings other = new LevelSettings();

    assertThat(new LevelSettings(other, other, overrides).getFilters()).doesNotContain(Filter.VINE);
  }

  @Test
  public void testFilterMerge1() {
    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings compare = new LevelSettings();
    assert compare.getFilters().isEmpty();
    compare.addFilter(Filter.VINE);
    assert compare.getFilters().contains(Filter.VINE);

    LevelSettings base = new LevelSettings();
    base.addFilter(Filter.VINE);

    assert new LevelSettings(base).getFilters().contains(Filter.VINE);

    LevelSettings other = new LevelSettings();

    assertThat(new LevelSettings(base, other, overrides).getFilters()).contains(Filter.VINE);
  }

  @Test
  public void testFilterMerge2() {
    Set<SettingsType> overrides = new HashSet<>();

    LevelSettings compare = new LevelSettings();
    assert compare.getFilters().isEmpty();
    compare.addFilter(Filter.VINE);
    assert compare.getFilters().contains(Filter.VINE);

    LevelSettings base = new LevelSettings();
    base.addFilter(Filter.VINE);

    assert new LevelSettings(base).getFilters().contains(Filter.VINE);

    LevelSettings other = new LevelSettings();

    assertThat(new LevelSettings(other, base, overrides).getFilters()).contains(Filter.VINE);
  }
}
