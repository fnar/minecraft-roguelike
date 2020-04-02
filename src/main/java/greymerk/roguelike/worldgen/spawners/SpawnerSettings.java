package greymerk.roguelike.worldgen.spawners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;


public class SpawnerSettings {

  private Map<Spawner, WeightedRandomizer<Spawnable>> spawners = new HashMap<>();

  public SpawnerSettings() {
  }

  public SpawnerSettings(SpawnerSettings toCopy) {
    for (Spawner type : toCopy.spawners.keySet()) {
      if (spawners.get(type) == null) {
        spawners.put(type, new WeightedRandomizer<>());
      }
      spawners.get(type).merge(toCopy.spawners.get(type));
    }
  }

  public SpawnerSettings(SpawnerSettings base, SpawnerSettings other) {
    for (Spawner type : base.spawners.keySet()) {
      if (spawners.get(type) == null) {
        spawners.put(type, new WeightedRandomizer<>());
      }
      spawners.get(type).merge(base.spawners.get(type));
    }

    for (Spawner type : other.spawners.keySet()) {
      if (spawners.get(type) == null) {
        spawners.put(type, new WeightedRandomizer<>());
      }
      spawners.get(type).merge(other.spawners.get(type));
    }
  }

  public static void generate(IWorldEditor editor, Random rand, Coord cursor, int difficulty, SpawnerSettings spawnerSettings, Spawner... types) {
    Spawner type = types[rand.nextInt(types.length)];
    Spawnable toSpawn = spawnerSettings.spawners.containsKey(type)
        ? spawnerSettings.spawners.get(type).get(rand)
        : new Spawnable(type);
    toSpawn.generate(editor, rand, cursor, difficulty);
  }

  public void parse(JsonObject entry) throws Exception {
    if (!entry.has("type")) {
      throw new Exception("Spawners entry missing type");
    }
    add(
        parseType(entry),
        parseSpawnPotentials(entry),
        parseWeight(entry));
  }

  private Spawner parseType(JsonObject entry) {
    return Spawner.valueOf(entry.get("type").getAsString().toUpperCase());
  }

  private Spawnable parseSpawnPotentials(JsonObject entry) throws Exception {
    // todo: Check for potentials before getting
    JsonElement potentials = entry.get("potentials");
    return new Spawnable(potentials);
  }

  private int parseWeight(JsonObject entry) {
    return entry.has("weight")
        ? entry.get("weight").getAsInt()
        : 1;
  }

  private void add(Spawner type, Spawnable spawnable, int weight) {
    if (!spawners.containsKey(type)) {
      spawners.put(type, new WeightedRandomizer<>());
    }
    spawners.get(type).add(new WeightedChoice<>(spawnable, weight));
  }

  @Override
  public String toString() {
    return spawners.keySet().toString();
  }
}
