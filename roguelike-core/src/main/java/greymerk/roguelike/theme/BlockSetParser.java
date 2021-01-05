package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import com.github.srwaggon.roguelike.worldgen.block.redstone.DoorBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Optional;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockProvider;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

class BlockSetParser {

  public static BlockSet parseBlockSet(JsonObject json, BlockSet baseBlockSet) throws DungeonSettingParseException {
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

  private static Optional<BlockBrush> parseFloor(JsonObject json) throws DungeonSettingParseException {
    return json.has("floor")
        ? ofNullable(BlockProvider.create(json.get("floor").getAsJsonObject()))
        : empty();
  }

  private static Optional<BlockBrush> parseWalls(JsonObject json) throws DungeonSettingParseException {
    return json.has("walls")
        ? ofNullable(BlockProvider.create(json.get("walls").getAsJsonObject()))
        : empty();
  }

  private static Optional<StairsBlock> parseStair(JsonObject json) throws DungeonSettingParseException {
    return json.has("stair")
        ? of(somethingAboutStairWithData(json))
        : empty();
  }

  private static StairsBlock somethingAboutStairWithData(JsonObject json) throws DungeonSettingParseException {
    // todo: review -- should this just use BlockProvider.create() instead?
    JsonObject stairData = json.get("stair").getAsJsonObject();
    JsonObject jsonObject = stairData.has("data")
        ? stairData.get("data").getAsJsonObject()
        : stairData;
    return new StairsBlock(jsonObject);
  }

  private static Optional<BlockBrush> parsePillar(JsonObject json) throws DungeonSettingParseException {
    return json.has("pillar")
        ? ofNullable(BlockProvider.create(json.get("pillar").getAsJsonObject()))
        : empty();
  }

  private static Optional<DoorBlock> parseDoor(JsonObject json) throws DungeonSettingParseException {
    return json.has("door")
        ? of(new DoorBlock(json.get("door")))
        : empty();
  }

  private static Optional<BlockBrush> parseLightBlock(JsonObject json) throws DungeonSettingParseException {
    return json.has("lightblock")
        ? ofNullable(BlockProvider.create(json.get("lightblock").getAsJsonObject()))
        : empty();
  }

  private static Optional<BlockBrush> parseLiquid(JsonObject json) throws DungeonSettingParseException {
    return json.has("liquid")
        ? ofNullable(BlockProvider.create(json.get("liquid").getAsJsonObject()))
        : empty();
  }
}
