package greymerk.roguelike.dungeon.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greymerk.roguelike.dungeon.DungeonStage;

import static greymerk.roguelike.dungeon.DungeonStage.ENCASE;
import static greymerk.roguelike.dungeon.DungeonStage.FILTERS;
import static greymerk.roguelike.dungeon.DungeonStage.LAYOUT;
import static greymerk.roguelike.dungeon.DungeonStage.LINKS;
import static greymerk.roguelike.dungeon.DungeonStage.LOOT;
import static greymerk.roguelike.dungeon.DungeonStage.ROOMS;
import static greymerk.roguelike.dungeon.DungeonStage.SEGMENTS;
import static greymerk.roguelike.dungeon.DungeonStage.TOWER;
import static greymerk.roguelike.dungeon.DungeonStage.TUNNELS;

public class DungeonTaskRegistry implements IDungeonTaskRegistry {

  public static DungeonTaskRegistry registry;
  private final Map<DungeonStage, List<IDungeonTask>> tasks = new HashMap<>();

  public DungeonTaskRegistry() {
    addTask(new DungeonTaskLayout(), LAYOUT);
    addTask(new DungeonTaskEncase(), ENCASE);
    addTask(new DungeonTaskTunnels(), TUNNELS);
    addTask(new DungeonTaskRooms(), ROOMS);
    addTask(new DungeonTaskSegments(), SEGMENTS);
    addTask(new DungeonTaskLinks(), LINKS);
    addTask(new DungeonTaskTower(), TOWER);
    addTask(new DungeonTaskFilters(), FILTERS);
    addTask(new DungeonTaskLoot(), LOOT);
  }

  public static IDungeonTaskRegistry getTaskRegistry() {
    if (registry == null) {
      registry = new DungeonTaskRegistry();
    }

    return registry;
  }

  public void addTask(IDungeonTask task, DungeonStage stage) {
    if (!tasks.containsKey(stage)) {
      tasks.put(stage, new ArrayList<>());
    }

    tasks.get(stage).add(task);
  }

  public List<IDungeonTask> getTasks(DungeonStage stage) {
    if (!tasks.containsKey(stage)) {
      return new ArrayList<>();
    }
    return tasks.get(stage);
  }
}
