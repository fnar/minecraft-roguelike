package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockJumble implements BlockBrush {

  private final List<BlockBrush> blocks = new ArrayList<>();

  public BlockJumble() {
  }

  public BlockJumble(List<BlockBrush> blocks) {
    this.blocks.addAll(blocks);
  }

  public BlockJumble(JsonElement data) {
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

  public BlockJumble with(BlockBrush toAdd) {
    addBlock(toAdd);
    return this;
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord origin, boolean fillAir, boolean replaceSolid) {
    BlockBrush block = blocks.get(editor.getRandom().nextInt(blocks.size()));
    return block.stroke(editor, origin, fillAir, replaceSolid);
  }

  @Override
  public BlockJumble copy() {
    return new BlockJumble(blocks.stream().map(BlockBrush::copy).collect(Collectors.toList()));
  }

}
