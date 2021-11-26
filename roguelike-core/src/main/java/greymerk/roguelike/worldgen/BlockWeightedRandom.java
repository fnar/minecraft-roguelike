package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class BlockWeightedRandom implements BlockBrush {

  private WeightedRandomizer<BlockBrush> blocks = new WeightedRandomizer<>();

  public BlockWeightedRandom() {
  }

  public BlockWeightedRandom(JsonElement data) {
    for (JsonElement jsonElement : (JsonArray) data) {
      if (jsonElement.isJsonNull()) {
        continue;
      }
      JsonObject d = jsonElement.getAsJsonObject();
      int weight = d.get("weight").getAsInt();
      BlockBrush toAdd = BlockProvider.create(d);
      this.addBlock(toAdd, weight);
    }
  }

  public BlockWeightedRandom(final WeightedRandomizer<BlockBrush> blocks) {
    this.blocks = blocks;
  }

  public void addBlock(BlockBrush toAdd, int weight) {
    blocks.add(new WeightedChoice<>(toAdd, weight));
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord origin, boolean fillAir, boolean replaceSolid) {
    BlockBrush block = blocks.get(editor.getRandom());
    return block.stroke(editor, origin, fillAir, replaceSolid);
  }

  @Override
  public BlockWeightedRandom copy() {
    return new BlockWeightedRandom(blocks.copy());
  }
}
