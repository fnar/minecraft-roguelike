package greymerk.roguelike.worldgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public enum BlockProvider {

  METABLOCK,
  WEIGHTED,
  CHECKERS,
  JUMBLE,
  STRIPES,
  LAYERS,
  COLUMNS;

  public static BlockBrush create(JsonObject block) throws DungeonSettingParseException {
    BlockProvider type = block.has("type")
        ? BlockProvider.valueOf(block.get("type").getAsString())
        : METABLOCK;

    JsonElement data = block.has("data")
        ? block.get("data")
        : block;

    switch (type) {
      case METABLOCK:
        return new SingleBlockBrush(data);
      case WEIGHTED:
        return new BlockWeightedRandom(data);
      case CHECKERS:
        return new BlockCheckers(data);
      case JUMBLE:
        return new BlockJumble(data);
      case STRIPES:
        return new BlockStripes(data);
      case LAYERS:
        return new BlockLayers(data);
      case COLUMNS:
        return new BlockColumns(data);
      default:
        return null;
    }
  }
}
