package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

public class LootTableRule {

  List<Integer> levels = newArrayList();
  private ResourceLocation table;
  private List<Treasure> treasureTypes = newArrayList();

  public LootTableRule() { }

  public LootTableRule(
      List<Integer> levels,
      ResourceLocation table,
      List<Treasure> treasureTypes
  ) {
    this.levels = levels;
    this.table = table;
    this.treasureTypes = treasureTypes;
  }

  public LootTableRule(JsonObject json) throws Exception {
    this(
        parseLevels(json),
        parseTable(json),
        parseType(json)
    );
  }

  public static LootTableRule newLootTableRule(
      int level,
      String table,
      Treasure treasure
  ) {
    LootTableRule lootTableRule = new LootTableRule();
    lootTableRule.addLevel(level);
    lootTableRule.setTable(table);
    lootTableRule.addTreasureType(treasure);
    return lootTableRule;
  }

  public void addLevel(int level) {
    this.levels.add(level);
  }

  public void setTable(String table) {
    this.table = new ResourceLocation(table);
  }

  public void addTreasureType(Treasure type) {
    this.treasureTypes.add(type);
  }

  private static List<Integer> parseLevels(JsonObject json) {
    if (!json.has("level")) {
      return newArrayList();
    }
    JsonElement level = json.get("level");
    List<Integer> levels = new ArrayList<>();
    if (!level.isJsonArray()) {
      levels.add(level.getAsInt());
    } else {
      for (JsonElement jsonElement : level.getAsJsonArray()) {
        if (jsonElement.isJsonNull()) {
          continue;
        }
        levels.add(jsonElement.getAsInt());
      }
    }
    return levels;

  }

  private static ResourceLocation parseTable(JsonObject json) throws Exception {
    if (!json.has("table")) {
      throw new Exception("Loot table requires a table field");
    }
    return new ResourceLocation(json.get("table").getAsString());
  }

  private static List<Treasure> parseType(JsonObject json) {
    List<Treasure> type = newArrayList();
    if (!json.has("type")) {
      return type;
    }
    JsonElement typeElement = json.get("type");
    if (!typeElement.isJsonArray()) {
      type.add(Treasure.valueOf(typeElement.getAsString()));
    } else {
      for (JsonElement treasure : typeElement.getAsJsonArray()) {
        if (treasure.isJsonNull()) {
          continue;
        }
        type.add(Treasure.valueOf(treasure.getAsString()));
      }
    }
    return type;
  }

  public void process(TreasureManager treasure) {
    getMatching(treasure).stream()
        .filter(TreasureChest::isNotEmpty)
        .forEach(chest -> chest.setLootTable(table));
  }

  private List<TreasureChest> getMatching(TreasureManager treasureManager) {
    return treasureManager.findChests().stream()
        .filter(chest -> levels.isEmpty() || levels.contains(chest.getLevel()))
        .filter(chest -> treasureTypes.isEmpty() || treasureTypes.contains(chest.getType()))
        .collect(toList());
  }

}
