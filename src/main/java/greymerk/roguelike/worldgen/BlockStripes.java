package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockStripes extends BlockBase {

  private List<IBlockFactory> blocks;

  public BlockStripes() {
    blocks = new ArrayList<>();
  }

  public BlockStripes(JsonElement data) throws Exception {
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
  public boolean set(WorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
    int size = blocks.size();
    int choice = Math.abs((origin.getX() % size + origin.getY() % size + origin.getZ() % size)) % size;
    IBlockFactory block = blocks.get(choice);
    return block.set(editor, rand, origin, fillAir, replaceSolid);
  }
}
