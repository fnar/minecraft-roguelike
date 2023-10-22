package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThemeTest {

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

    Theme t = ThemeParser.parse(json);
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

    Theme t = ThemeParser.parse(json);
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

    Theme t = ThemeParser.parse(json);
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
        BlockType.GRASS_BLOCK.getBrush(),
        BlockType.GRASS_BLOCK.getBrush(),
        StairsBlock.oak(),
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    Theme parent = new Theme(dirtBlockSet, null);
    Theme child = new Theme(null, grassBlockSet);

    Theme actual = Theme.inherit(parent, child);
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
        BlockType.GRASS_BLOCK.getBrush(),
        BlockType.GRASS_BLOCK.getBrush(),
        StairsBlock.oak(),
        null,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    Theme dirtTheme = new Theme(dirtBlockSet, null);
    Theme grassTheme = new Theme(grassBlockSet, null);

    Theme actual = Theme.inherit(dirtTheme, grassTheme);
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

    Theme parent = new Theme(dirtBlockSet, null);
    Theme child = new Theme(null, null);

    Theme actual = Theme.inherit(parent, child);
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

    Theme parent = new Theme(null, dirtBlockSet);
    Theme child = new Theme(null, null);

    Theme actual = Theme.inherit(parent, child);
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

    Theme parent = new Theme(dirtBlockSet, null);
    Theme child = new Theme(null, null);

    Theme actual = Theme.inherit(parent, child);
    assertThat(actual.getSecondary().getFloor()).isEqualTo(dirtBlockSet.getFloor());
  }

  @Test
  public void themesGetADefaultStoneBrickThemeWhenThereIsNothingToInherit() {
    Theme parent = new Theme(null, null);
    Theme child = new Theme(null, null);

    Theme actual = Theme.inherit(parent, child);
    assertThat(actual.getPrimary().getFloor()).isEqualTo(BlockType.STONE_BRICKS.getBrush());
    assertThat(actual.getSecondary().getFloor()).isEqualTo(BlockType.STONE_BRICKS.getBrush());
  }
}
