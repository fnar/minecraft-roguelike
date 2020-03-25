package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import java.util.Optional;

import greymerk.roguelike.worldgen.BlockProvider;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.door.Door;
import greymerk.roguelike.worldgen.blocks.door.IDoor;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

class BlockSetParser {

  public static BlockSet parseBlockSet(JsonObject json, Optional<IBlockSet> base) throws Exception {
    return new BlockSet(
        parseFloor(json).orElse(base.map(IBlockSet::getFloor).orElse(null)),
        parseWalls(json, base),
        parseStair(json, base),
        parsePillar(json, base),
        parseDoor(json, base),
        parseLightBlock(json, base),
        parseLiquid(json, base)
    );
  }

  private static Optional<IBlockFactory> parseFloor(JsonObject json) throws Exception {
    if (!json.has("floor")) {
      return empty();
    }
    return ofNullable(BlockProvider.create(json.get("floor").getAsJsonObject()));
  }

  private static IStair parseStair(JsonObject json, Optional<IBlockSet> base) throws Exception {
    return json.has("stair")
        ? somethingAboutStairWithData(json)
        : base
            .map(IBlockSet::getStair)
            .orElse(null);
  }

  private static IBlockFactory parseLiquid(JsonObject json, Optional<IBlockSet> base) throws Exception {
    return json.has("liquid")
        ? BlockProvider.create(json.get("liquid").getAsJsonObject())
        : base
            .map(IBlockSet::getLiquid)
            .orElse(null);
  }

  private static IBlockFactory parseLightBlock(JsonObject json, Optional<IBlockSet> base) throws Exception {
    return json.has("lightblock")
        ? BlockProvider.create(json.get("lightblock").getAsJsonObject())
        : base
            .map(IBlockSet::getLightBlock)
            .orElse(null);
  }

  private static IDoor parseDoor(JsonObject json, Optional<IBlockSet> base) throws Exception {
    return json.has("door") ? new Door(json.get("door")) : base
        .map(IBlockSet::getDoor)
        .orElse(null);
  }

  private static IBlockFactory parsePillar(JsonObject json, Optional<IBlockSet> base) throws Exception {
    return json.has("pillar")
        ? BlockProvider.create(json.get("pillar").getAsJsonObject())
        : base
            .map(IBlockSet::getPillar)
            .orElse(null);
  }

  private static IBlockFactory parseWalls(JsonObject json, Optional<IBlockSet> base) throws Exception {
    return json.has("walls")
        ? BlockProvider.create(json.get("walls").getAsJsonObject())
        : base
            .map(IBlockSet::getWall)
            .orElse(null);
  }

  private static MetaStair somethingAboutStairWithData(JsonObject json) throws Exception {
    JsonObject stairData = json.get("stair").getAsJsonObject();
    return stairData.has("data")
        ? new MetaStair(new MetaBlock(stairData.get("data").getAsJsonObject()))
        : new MetaStair(new MetaBlock(stairData));
  }
}
