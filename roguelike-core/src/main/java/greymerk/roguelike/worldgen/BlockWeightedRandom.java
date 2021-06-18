package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class BlockWeightedRandom implements BlockBrush {

  private WeightedRandomizer<BlockBrush> blocks;

  public BlockWeightedRandom() {
    blocks = new WeightedRandomizer<>();
  }

  public BlockWeightedRandom(JsonElement data) {
    this();
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

  public void addBlock(BlockBrush toAdd, int weight) {
    blocks.add(new WeightedChoice<>(toAdd, weight));
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord origin, boolean fillAir, boolean replaceSolid) {
    BlockBrush block = blocks.get(editor.getRandom());
    return block.stroke(editor, origin, fillAir, replaceSolid);
  }
}
