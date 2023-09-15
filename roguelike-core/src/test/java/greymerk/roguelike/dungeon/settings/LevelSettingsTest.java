package greymerk.roguelike.dungeon.settings;

import com.google.common.collect.Sets;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.layout.LayoutGenerator;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.worldgen.filter.Filter;

import static org.assertj.core.api.Assertions.assertThat;

public class LevelSettingsTest {

  public static final Set<SettingsType> EMPTY_OVERRIDES = Collections.unmodifiableSet(Sets.newHashSet());

  @Test
  public void testEquals() {
    LevelSettings base = new LevelSettings(0);
    base.setGenerator(LayoutGenerator.Type.CLASSIC);
    LevelSettings other = new LevelSettings(0);
    assertThat(base).isNotEqualTo(other);

    other.setGenerator(LayoutGenerator.Type.CLASSIC);
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
  public void levelSettingsInheritRooms() {
    LevelSettings parent = new LevelSettings(0);
    RoomSetting cakeRoomSetting = RoomType.CAKE.newSingleRoomSetting();
    parent.getRooms().add(cakeRoomSetting);

    List<RoomSetting> actual = new LevelSettings(0).inherit(parent, EMPTY_OVERRIDES).getRooms().getSingleRoomSettings();

    assertThat(actual).containsOnly(cakeRoomSetting);
  }

  @Test
  public void levelSettingsInheritSecretsFromTheirParents() {
    RoomSetting bedroomSecret0 = RoomType.BEDROOM.newSingleRoomSetting();
    RoomSetting bedroomSecret1 = RoomType.BEDROOM.newSingleRoomSetting();

    LevelSettings parent = new LevelSettings(0);
    parent.getSecrets().add(bedroomSecret0);
    parent.getSecrets().add(bedroomSecret1);

    LevelSettings actual = new LevelSettings(0).inherit(parent, EMPTY_OVERRIDES);

    assertThat(actual.getSecrets().getSecretRoomSettings()).containsOnly(bedroomSecret0, bedroomSecret1);
  }

  @Test
  public void testThemeMerge() {
    // TODO
  }

  @Test
  public void testSegmentsMerge() {
    // TODO
  }

  @Test
  public void testSpawnerMerge() {
    // TODO
  }

  @Test
  public void childRetainsLevelGeneratorType_WhenChildHasLevelGenerator() {
    LevelSettings parent = new LevelSettings(0);
    LevelSettings child = new LevelSettings(0);

    child.setGenerator(LayoutGenerator.Type.CLASSIC);

    child.inherit(parent, EMPTY_OVERRIDES);
    assertThat(child.getGeneratorType()).isEqualTo(LayoutGenerator.Type.CLASSIC);
  }

  @Test
  public void childInheritsLevelGenerator_WhenChildHasNoLevelGenerator() {
    LevelSettings parent = new LevelSettings(0);
    LevelSettings child = new LevelSettings(0);

    parent.setGenerator(LayoutGenerator.Type.CLASSIC);

    child.inherit(parent, EMPTY_OVERRIDES);
    assertThat(child.getGeneratorType()).isEqualTo(LayoutGenerator.Type.CLASSIC);
  }

  @Test
  public void childrenPreferTheirOwnLevelGenerator_EvenWhenAParentHasALevelGenerator() {
    LevelSettings parent = new LevelSettings(0);
    LevelSettings child = new LevelSettings(0);

    parent.setGenerator(LayoutGenerator.Type.MST);
    child.setGenerator(LayoutGenerator.Type.CLASSIC);

    child.inherit(parent, EMPTY_OVERRIDES);
    assertThat(child.getGeneratorType()).isEqualTo(LayoutGenerator.Type.CLASSIC);
  }

  @Test
  public void levelSettingsInheritLevelGeneratorsFromTheirParents_WhenParentHasLevelGenerator() {
    LevelSettings expected = new LevelSettings(0);
    expected.setGenerator(LayoutGenerator.Type.CLASSIC);

    LevelSettings parent = new LevelSettings(0);
    LevelSettings child = new LevelSettings(0);

    assertThat(child).isNotEqualTo(expected);

    parent.setGenerator(LayoutGenerator.Type.CLASSIC);

    assertThat(parent).isEqualTo(expected);
    assertThat(child).isNotEqualTo(expected);
    assertThat(child.inherit(parent, EMPTY_OVERRIDES)).isEqualTo(expected);
  }

  @Test
  public void levelSettingsRetainTheirLevelGeneratorDespiteInheritance_WhenParentHasNone() {
    LevelSettings expected = new LevelSettings(0);
    expected.setGenerator(LayoutGenerator.Type.CLASSIC);

    LevelSettings parent = new LevelSettings(0);
    LevelSettings child = new LevelSettings(0);

    assertThat(child).isNotEqualTo(expected);

    child.setGenerator(LayoutGenerator.Type.CLASSIC);

    assertThat(parent).isNotEqualTo(expected);
    assertThat(child).isEqualTo(expected);
    assertThat(child.inherit(parent, EMPTY_OVERRIDES)).isEqualTo(expected);
  }

  @Test
  public void levelSettingsRetainTheirLevelGeneratorDespiteInheritance_EvenWhenParentHasALevelGenerator() {
    LevelSettings expected = new LevelSettings(0);
    expected.setGenerator(LayoutGenerator.Type.CLASSIC);

    LevelSettings parent = new LevelSettings(0);
    LevelSettings child = new LevelSettings(0);

    assertThat(child).isNotEqualTo(expected);

    parent.setGenerator(LayoutGenerator.Type.MST);
    child.setGenerator(LayoutGenerator.Type.CLASSIC);

    assertThat(parent).isNotEqualTo(expected);
    assertThat(child).isEqualTo(expected);
    assertThat(child.inherit(parent, EMPTY_OVERRIDES)).isEqualTo(expected);
  }

  @Test
  public void filtersAreInheritedFromParents_WhenParentsHaveFilters() {
    LevelSettings child = new LevelSettings(0);
    LevelSettings parent = new LevelSettings(0);
    parent.addFilter(Filter.VINE);

    LevelSettings actual = child.inherit(parent, EMPTY_OVERRIDES);

    assertThat(actual.getFilters()).containsOnly(Filter.VINE);
  }

  @Test
  public void filtersAreKept_WhenParentsHaveNoFilters() {
    LevelSettings child = new LevelSettings(0);
    LevelSettings parent = new LevelSettings(0);
    child.addFilter(Filter.VINE);

    LevelSettings actual = child.inherit(parent, EMPTY_OVERRIDES);

    assertThat(actual.getFilters()).containsOnly(Filter.VINE);
  }

  @Test
  public void childrenMergeFiltersFromTheirParentsWithTheirOwnFilters() {
    LevelSettings child = new LevelSettings(0);
    child.addFilter(Filter.VINE);
    LevelSettings parent = new LevelSettings(0);
    parent.addFilter(Filter.COBWEB);

    LevelSettings actual = child.inherit(parent, EMPTY_OVERRIDES);

    assertThat(actual.getFilters()).containsOnly(Filter.VINE, Filter.COBWEB);
  }
}
