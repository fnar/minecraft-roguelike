package greymerk.roguelike.treasure.loot;

import com.google.common.base.Predicates;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;

import static com.google.common.collect.Lists.newArrayList;

public class LootTableRule {

  List<Integer> levels = newArrayList();
  private String table;
  private List<ChestType> chestTypes = newArrayList();

  public LootTableRule() {
  }

  public LootTableRule(
      List<Integer> levels,
      String table,
      List<ChestType> chestTypes
  ) {
    this.levels = levels;
    this.table = table;
    this.chestTypes = chestTypes;
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
      ChestType chestType
  ) {
    LootTableRule lootTableRule = new LootTableRule();
    lootTableRule.addLevel(level);
    lootTableRule.setTable(table);
    lootTableRule.addChestType(chestType);
    return lootTableRule;
  }

  public void addLevel(int level) {
    this.levels.add(level);
  }

  public void setTable(String table) {
    this.table = table;
  }

  public void addChestType(ChestType chestType) {
    this.chestTypes.add(chestType);
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

  private static String parseTable(JsonObject json) throws Exception {
    if (!json.has("table")) {
      throw new Exception("Loot table requires a table field");
    }
    return json.get("table").getAsString();
  }

  private static List<ChestType> parseType(JsonObject json) {
    List<ChestType> type = newArrayList();
    if (!json.has("type")) {
      return type;
    }
    JsonElement typeElement = json.get("type");
    if (!typeElement.isJsonArray()) {
      type.add(new ChestType(typeElement.getAsString()));
    } else {
      for (JsonElement treasure : typeElement.getAsJsonArray()) {
        if (treasure.isJsonNull()) {
          continue;
        }
        type.add(new ChestType(treasure.getAsString()));
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
    return treasureManager.findChests(
        Predicates.and(
            TreasureChest::isNotEmpty,
            this::isChestLevel,
            this::isChestType));
  }

  private boolean isChestType(TreasureChest chest) {
    return chestTypes.isEmpty() || chestTypes.contains(chest.getType());
  }

  private boolean isChestLevel(TreasureChest chest) {
    return levels.isEmpty() || levels.contains(chest.getLevel());
  }

}
