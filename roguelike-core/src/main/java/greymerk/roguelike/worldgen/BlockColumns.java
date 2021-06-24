package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockColumns implements BlockBrush {

  private final List<BlockBrush> blocks = new ArrayList<>();

  public BlockColumns(List<BlockBrush> blocks) {
    this.blocks.addAll(blocks);
  }

  public BlockColumns(JsonElement data) {
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
    int size = blocks.size();
    int choice = Math.abs((pos.getX() % size + pos.getZ() % size)) % size;
    BlockBrush block = blocks.get(choice);
    return block.stroke(editor, pos, fillAir, replaceSolid);
  }

  @Override
  public BlockColumns copy() {
    return new BlockColumns(blocks.stream().map(BlockBrush::copy).collect(Collectors.toList()));
  }

}
