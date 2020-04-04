package greymerk.roguelike.worldgen.spawners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;


public class SpawnerSettings {

  private WeightedRandomizer<Spawnable> spawners = new WeightedRandomizer<>();

  public SpawnerSettings() {
  }

  public SpawnerSettings(SpawnerSettings toCopy) {
    spawners.merge(toCopy.spawners);
  }

  public SpawnerSettings(SpawnerSettings base, SpawnerSettings other) {
    spawners.merge(base.spawners);
    spawners.merge(other.spawners);
  }

  public void generateSpawner(IWorldEditor editor, Random rand, Coord cursor, int difficulty) {
    try {
      spawners.get(rand).generate(editor, rand, cursor, difficulty);
    } catch (Exception e) {
      throw new RuntimeException("Tried to spawn empty spawner", e);
    }
  }

  public boolean isEmpty() {
    return spawners.isEmpty();
  }

  public void parse(JsonObject spawnerJson) throws Exception {
    add(
        parseSpawnPotentials(spawnerJson),
        parseWeight(spawnerJson));
  }

  private Spawnable parseSpawnPotentials(JsonObject entry) throws Exception {
    // todo: Check for potentials before getting
    JsonElement spawnPotentialsJson = entry.get("potentials");
    List<SpawnPotential> spawnPotentials = SpawnPotentialParser.parse(spawnPotentialsJson);
    return new Spawnable(spawnPotentials);
  }

  private int parseWeight(JsonObject entry) {
    return entry.has("weight")
        ? entry.get("weight").getAsInt()
        : 1;
  }

  public void add(Spawnable spawnable, int weight) {
    spawners.add(new WeightedChoice<>(spawnable, weight));
  }

  @Override
  public String toString() {
    return spawners.toString();
  }
}
