package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import greymerk.roguelike.config.RogueConfig;

import static org.assertj.core.api.Assertions.assertThat;

public class ThemeTest {

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void noBase() {
    JsonObject json = new JsonObject();
    JsonObject primary = new JsonObject();
    json.add("primary", primary);

    JsonObject secondary = new JsonObject();
    json.add("secondary", secondary);

    JsonObject floor = new JsonObject();
    primary.add("floor", floor);
    floor.addProperty("name", "minecraft:dirt");

    JsonObject walls = new JsonObject();
    secondary.add("walls", walls);
    walls.addProperty("name", "minecraft:stone");

    ThemeBase t = ThemeParser.parse(json);
    SingleBlockBrush floorBrush = (SingleBlockBrush) t.getPrimary().getFloor();
    assertThat(floorBrush.getJson()).isEqualTo(floor);
  }

  @Test
  public void noSecondary() {
    JsonObject json = new JsonObject();
    JsonObject primary = new JsonObject();
    json.add("primary", primary);

    JsonObject floor = new JsonObject();
    primary.add("floor", floor);
    floor.addProperty("name", "minecraft:dirt");

    ThemeBase t = ThemeParser.parse(json);
    SingleBlockBrush floorBrush = (SingleBlockBrush) t.getPrimary().getFloor();
    assertThat(floorBrush.getJson()).isEqualTo(floor);
  }

  @Test
  public void noPrimary() {
    JsonObject json = new JsonObject();
    JsonObject secondary = new JsonObject();
    json.add("secondary", secondary);

    JsonObject floor = new JsonObject();
    secondary.add("floor", floor);
    floor.addProperty("name", "minecraft:dirt");

    ThemeBase t = ThemeParser.parse(json);
    SingleBlockBrush floorBrush = (SingleBlockBrush) t.getSecondary().getFloor();
    assertThat(floorBrush.getJson()).isEqualTo(floor);
  }

  @Test
  public void themesInheritFromTheirParents() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.DIRT.getBrush(), null, null);
    BlockSet grassBlockSet = new BlockSet(BlockType.GRASS.getBrush(), null, null);

    ThemeBase parent = new ThemeBase(dirtBlockSet, null);
    ThemeBase child = new ThemeBase(null, grassBlockSet);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
    assertThat(actual.getSecondary().getFloor()).isEqualTo(grassBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirPrimaryIfTheirSecondaryBlockSetIsAbsent() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.DIRT.getBrush(), null, null);
    BlockSet grassBlockSet = new BlockSet(BlockType.GRASS.getBrush(), null, null);

    ThemeBase parent = new ThemeBase(dirtBlockSet, null);
    ThemeBase child = new ThemeBase(grassBlockSet, null);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(grassBlockSet.getFloor());
    assertThat(actual.getSecondary().getFloor()).isEqualTo(grassBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirPrimaryBlockSetFromTheirParentIfAbsent() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.DIRT.getBrush(), null, null);

    ThemeBase parent = new ThemeBase(dirtBlockSet, null);
    ThemeBase child = new ThemeBase(null, null);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirParentIfAbsent() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.DIRT.getBrush(), null, null);

    ThemeBase parent = new ThemeBase(null, dirtBlockSet);
    ThemeBase child = new ThemeBase(null, null);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getSecondary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirParentsPrimaryIfTheParentHasNoSecondary() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.DIRT.getBrush(), null, null);

    ThemeBase parent = new ThemeBase(dirtBlockSet, null);
    ThemeBase child = new ThemeBase(null, null);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getSecondary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesGetADefaultStoneBrickThemeWhenThereIsNothingToInherit() {
    ThemeBase parent = new ThemeBase(null, null);
    ThemeBase child = new ThemeBase(null, null);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(BlockType.STONE_BRICK.getBrush());
    assertThat(actual.getSecondary().getFloor()).isEqualTo(BlockType.STONE_BRICK.getBrush());
  }
}
