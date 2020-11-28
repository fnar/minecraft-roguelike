package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockLayers extends BlockBase {

  private List<IBlockFactory> blocks;

  public BlockLayers() {
    blocks = new ArrayList<>();
  }

  public BlockLayers(JsonElement data) {
    this();
    for (JsonElement jsonElement : (JsonArray) data) {
      if (jsonElement.isJsonNull()) {
        continue;
      }
      this.addBlock(BlockProvider.create(jsonElement.getAsJsonObject()));
    }
  }

  public void addBlock(IBlockFactory toAdd) {
    blocks.add(toAdd);
  }

  @Override
  public boolean set(WorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
    IBlockFactory block = this.blocks.get(pos.getY() % this.blocks.size());
    return block.set(editor, rand, pos, fillAir, replaceSolid);
  }

}
