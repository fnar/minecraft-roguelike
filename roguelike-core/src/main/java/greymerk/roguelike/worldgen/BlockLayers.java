package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockLayers implements BlockBrush {

  private final List<BlockBrush> blocks = new ArrayList<>();

  public BlockLayers(List<BlockBrush> blocks) {
    this.blocks.addAll(blocks);
  }

  public BlockLayers(JsonElement data) {
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

  @Override
  public BlockLayers copy() {
    return new BlockLayers(blocks.stream().map(BlockBrush::copy).collect(Collectors.toList()));
  }

}
