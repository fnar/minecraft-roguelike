package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockSetTest {

  @Test
  public void jsonNoBase() {

    JsonObject json = new JsonObject();
    JsonObject floor = new JsonObject();
    json.add("floor", floor);

    floor.addProperty("name", "minecraft:dirt");

    BlockSet test = BlockSetParser.parseBlockSet(json, new BlockSet());

    SingleBlockBrush floorBrush = (SingleBlockBrush) test.getFloor();
    assertThat(floorBrush.getJson()).isEqualTo(floor);
  }

}
