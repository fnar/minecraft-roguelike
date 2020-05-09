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
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

class BlockSetParser {

  public static BlockSet parseBlockSet(JsonObject json, BlockSet baseBlockSet) throws Exception {
    return new BlockSet(
        parseFloor(json).orElse(baseBlockSet.getFloor()),
        parseWalls(json).orElse(baseBlockSet.getWall()),
        parseStair(json).orElse(baseBlockSet.getStair()),
        parsePillar(json).orElse(baseBlockSet.getPillar()),
        parseDoor(json).orElse(baseBlockSet.getDoor()),
        parseLightBlock(json).orElse(baseBlockSet.getLightBlock()),
        parseLiquid(json).orElse(baseBlockSet.getLiquid())
    );
  }

  private static Optional<IBlockFactory> parseFloor(JsonObject json) throws Exception {
    return json.has("floor")
        ? ofNullable(BlockProvider.create(json.get("floor").getAsJsonObject()))
        : empty();
  }

  private static Optional<IBlockFactory> parseWalls(JsonObject json) throws Exception {
    return json.has("walls")
        ? ofNullable(BlockProvider.create(json.get("walls").getAsJsonObject()))
        : empty();
  }

  private static Optional<IStair> parseStair(JsonObject json) throws Exception {
    return json.has("stair")
        ? of(somethingAboutStairWithData(json))
        : empty();
  }

  private static MetaStair somethingAboutStairWithData(JsonObject json) throws Exception {
    JsonObject stairData = json.get("stair").getAsJsonObject();
    return stairData.has("data")
        ? new MetaStair(new MetaBlock(stairData.get("data").getAsJsonObject()))
        : new MetaStair(new MetaBlock(stairData));
  }

  private static Optional<IBlockFactory> parsePillar(JsonObject json) throws Exception {
    return json.has("pillar")
        ? ofNullable(BlockProvider.create(json.get("pillar").getAsJsonObject()))
        : empty();
  }

  private static Optional<IDoor> parseDoor(JsonObject json) throws Exception {
    return json.has("door")
        ? of(new Door(json.get("door")))
        : empty();
  }

  private static Optional<IBlockFactory> parseLightBlock(JsonObject json) throws Exception {
    return json.has("lightblock")
        ? ofNullable(BlockProvider.create(json.get("lightblock").getAsJsonObject()))
        : empty();
  }

  private static Optional<IBlockFactory> parseLiquid(JsonObject json) throws Exception {
    return json.has("liquid")
        ? ofNullable(BlockProvider.create(json.get("liquid").getAsJsonObject()))
        : empty();
  }
}
