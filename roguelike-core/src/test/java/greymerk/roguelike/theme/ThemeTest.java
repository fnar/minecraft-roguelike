package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

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
    JsonObject primaryFloor = new JsonObject();
    primaryFloor.addProperty("name", "minecraft:dirt");
    JsonObject primary = new JsonObject();
    primary.add("floor", primaryFloor);

    JsonObject walls = new JsonObject();
    walls.addProperty("name", "minecraft:stone");
    JsonObject secondary = new JsonObject();
    secondary.add("walls", walls);

    JsonObject json = new JsonObject();
    json.add("primary", primary);
    json.add("secondary", secondary);

    ThemeBase t = ThemeParser.parse(json);
    SingleBlockBrush floorBrush = (SingleBlockBrush) t.getPrimary().getFloor();
    assertThat(floorBrush.getJson()).isEqualTo(primaryFloor);
  }

  @Test
  public void noSecondary() {
    JsonObject primaryFloor = new JsonObject();
    primaryFloor.addProperty("name", "minecraft:dirt");

    JsonObject primary = new JsonObject();
    primary.add("floor", primaryFloor);

    JsonObject json = new JsonObject();
    json.add("primary", primary);

    ThemeBase t = ThemeParser.parse(json);
    SingleBlockBrush floorBrush = (SingleBlockBrush) t.getPrimary().getFloor();
    assertThat(floorBrush.getJson()).isEqualTo(primaryFloor);
  }

  @Test
  public void noPrimary() {
    JsonObject secondaryFloor = new JsonObject();
    secondaryFloor.addProperty("name", "minecraft:dirt");

    JsonObject secondary = new JsonObject();
    secondary.add("floor", secondaryFloor);

    JsonObject json = new JsonObject();
    json.add("secondary", secondary);

    ThemeBase t = ThemeParser.parse(json);
    SingleBlockBrush floorBrush = (SingleBlockBrush) t.getSecondary().getFloor();
    assertThat(floorBrush.getJson()).isEqualTo(secondaryFloor);
  }

  @Test
  public void themesInheritFromTheirParents() {
    BlockSet dirtBlockSet = new BlockSet(
        BlockType.DIRT.getBrush(),
        BlockType.DIRT.getBrush(),
        StairsBlock.oak(),
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    BlockSet grassBlockSet = new BlockSet(
        BlockType.GRASS.getBrush(),
        BlockType.GRASS.getBrush(),
        StairsBlock.oak(),
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    ThemeBase parent = new ThemeBase(dirtBlockSet, null);
    ThemeBase child = new ThemeBase(null, grassBlockSet);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
    assertThat(actual.getSecondary().getFloor()).isEqualTo(grassBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirPrimaryIfTheirSecondaryBlockSetIsAbsent() {
    BlockSet dirtBlockSet = new BlockSet(
        BlockType.DIRT.getBrush(),
        BlockType.DIRT.getBrush(),
        StairsBlock.oak(),
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    BlockSet grassBlockSet = new BlockSet(
        BlockType.GRASS.getBrush(),
        BlockType.GRASS.getBrush(),
        StairsBlock.oak(),
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    ThemeBase dirtTheme = new ThemeBase(dirtBlockSet, null);
    ThemeBase grassTheme = new ThemeBase(grassBlockSet, null);

    ThemeBase actual = ThemeBase.inherit(dirtTheme, grassTheme);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(grassBlockSet.getFloor());
    assertThat(actual.getSecondary().getFloor()).isEqualTo(grassBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirPrimaryBlockSetFromTheirParentIfAbsent() {
    BlockSet dirtBlockSet = new BlockSet(
        BlockType.DIRT.getBrush(),
        BlockType.DIRT.getBrush(),
        null,
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    ThemeBase parent = new ThemeBase(dirtBlockSet, null);
    ThemeBase child = new ThemeBase(null, null);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirParentIfAbsent() {
    BlockSet dirtBlockSet = new BlockSet(
        BlockType.DIRT.getBrush(),
        BlockType.DIRT.getBrush(),
        null,
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    ThemeBase parent = new ThemeBase(null, dirtBlockSet);
    ThemeBase child = new ThemeBase(null, null);

    ThemeBase actual = ThemeBase.inherit(parent, child);
    assertThat(actual.getSecondary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesInheritTheirSecondaryBlockSetFromTheirParentsPrimaryIfTheParentHasNoSecondary() {
    BlockSet dirtBlockSet = new BlockSet(
        BlockType.DIRT.getBrush(),
        BlockType.DIRT.getBrush(),
        null,
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

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
