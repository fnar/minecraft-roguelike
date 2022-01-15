package greymerk.roguelike.worldgen;

import com.google.gson.JsonObject;

import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MetaBlock1_12Test {

  @Before
  public void setup() {
    Bootstrap.register();
  }

  @Test
  public void jsonArgs() {

    MetaBlock1_12 dirt = new MetaBlock1_12(Blocks.DIRT);
    MetaBlock1_12 stone = new MetaBlock1_12(Blocks.STONE);

    JsonObject json = new JsonObject();
    json.addProperty("name", "minecraft:dirt");
    MetaBlock1_12 test = new MetaBlock1_12(json);
    assert (test.equals(dirt));

    MetaBlock1_12 podzol = new MetaBlock1_12(Blocks.DIRT);
    podzol.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);

    json = new JsonObject();
    json.addProperty("name", "minecraft:dirt");
    json.addProperty("meta", 2);

    test = new MetaBlock1_12(json);

    assertFalse(test.equals(stone));
    assertFalse(test.equals(dirt));
    assertEquals(test, podzol);
  }


  @Test
  public void testEquals() {
    MetaBlock1_12 dirt = new MetaBlock1_12(Blocks.DIRT);
    MetaBlock1_12 dirt2 = new MetaBlock1_12(Blocks.DIRT);
    assert (dirt.equals(dirt2));
  }
}
