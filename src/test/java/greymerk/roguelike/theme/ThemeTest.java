package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.blocks.BlockType;

import static org.assertj.core.api.Assertions.assertThat;

public class ThemeTest {

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void noBase() throws Exception {
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

    ITheme t = ThemeParser.parse(json);
    assert (t.getPrimary().getFloor().equals(BlockType.get(BlockType.DIRT)));

  }

  @Test
  public void noSecondary() throws Exception {
    JsonObject json = new JsonObject();
    JsonObject primary = new JsonObject();
    json.add("primary", primary);

    JsonObject floor = new JsonObject();
    primary.add("floor", floor);
    floor.addProperty("name", "minecraft:dirt");

    ITheme t = ThemeParser.parse(json);
    assert (t.getPrimary().getFloor().equals(BlockType.get(BlockType.DIRT)));
  }

  @Test
  public void noPrimary() throws Exception {
    JsonObject json = new JsonObject();
    JsonObject secondary = new JsonObject();
    json.add("secondary", secondary);

    JsonObject floor = new JsonObject();
    secondary.add("floor", floor);
    floor.addProperty("name", "minecraft:dirt");

    ITheme t = ThemeParser.parse(json);
    assert (t.getSecondary().getFloor().equals(BlockType.get(BlockType.DIRT)));
  }

  @Test
  public void themesInheritFromTheirParents() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.get(BlockType.DIRT), null, null);
    BlockSet grassBlockSet = new BlockSet(BlockType.get(BlockType.GRASS), null, null);

    ITheme parent = new ThemeBase(dirtBlockSet, null);
    ITheme child = new ThemeBase(null, grassBlockSet);

    ITheme actual = Theme.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
    assertThat(actual.getSecondary().getFloor()).isEqualTo(grassBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirPrimaryIfTheirSecondaryBlockSetIsAbsent() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.get(BlockType.DIRT), null, null);
    BlockSet grassBlockSet = new BlockSet(BlockType.get(BlockType.GRASS), null, null);

    ITheme parent = new ThemeBase(dirtBlockSet, null);
    ITheme child = new ThemeBase(grassBlockSet, null);

    ITheme actual = Theme.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(grassBlockSet.getFloor());
    assertThat(actual.getSecondary().getFloor()).isEqualTo(grassBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirPrimaryBlockSetFromTheirParentIfAbsent() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.get(BlockType.DIRT), null, null);

    ITheme parent = new ThemeBase(dirtBlockSet, null);
    ITheme child = new ThemeBase(null, null);

    ITheme actual = Theme.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirParentIfAbsent() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.get(BlockType.DIRT), null, null);

    ITheme parent = new ThemeBase(null, dirtBlockSet);
    ITheme child = new ThemeBase(null, null);

    ITheme actual = Theme.inherit(parent, child);
    assertThat(actual.getSecondary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirParentsPrimaryIfTheParentHasNoSecondary() {
    BlockSet dirtBlockSet = new BlockSet(BlockType.get(BlockType.DIRT), null, null);

    ITheme parent = new ThemeBase(dirtBlockSet, null);
    ITheme child = new ThemeBase(null, null);

    ITheme actual = Theme.inherit(parent, child);
    assertThat(actual.getSecondary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesGetADefaultStoneBrickThemeWhenThereIsNothingToInherit() {
    ITheme parent = new ThemeBase(null, null);
    ITheme child = new ThemeBase(null, null);

    ITheme actual = Theme.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(BlockType.get(BlockType.STONE_BRICK));
    assertThat(actual.getSecondary().getFloor()).isEqualTo(BlockType.get(BlockType.STONE_BRICK));
  }
}
