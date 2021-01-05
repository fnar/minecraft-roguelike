package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class BlockJumble implements BlockBrush {

  private List<BlockBrush> blocks = new ArrayList<>();

  public BlockJumble() {
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

  @Override
  public boolean stroke(WorldEditor editor, Coord origin, boolean fillAir, boolean replaceSolid) {
    BlockBrush block = blocks.get(editor.getRandom().nextInt(blocks.size()));
    return block.stroke(editor, origin, fillAir, replaceSolid);
  }

}
