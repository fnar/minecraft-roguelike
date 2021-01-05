package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class BlockLayers implements BlockBrush {

  private List<BlockBrush> blocks;

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

  public void addBlock(BlockBrush toAdd) {
    blocks.add(toAdd);
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {
    BlockBrush block = this.blocks.get(pos.getY() % this.blocks.size());
    return block.stroke(editor, pos, fillAir, replaceSolid);
  }

}
