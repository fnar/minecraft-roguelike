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

public final class DungeonTaskRegistry {

  private static final DungeonTaskRegistry singleton = new DungeonTaskRegistry();
  private final Map<DungeonStage, List<IDungeonTask>> tasks = new HashMap<>();

  private DungeonTaskRegistry() {
    addTask(LAYOUT, new DungeonTaskLayout());
    addTask(ENCASE, new DungeonTaskEncase());
    addTask(TUNNELS, new DungeonTaskTunnels());
    addTask(SEGMENTS, new DungeonTaskSegments());
    addTask(ROOMS, new DungeonTaskRooms());
    addTask(LINKS, new DungeonTaskLinks());
    addTask(TOWER, new DungeonTaskTower());
    addTask(FILTERS, new DungeonTaskFilters());
    addTask(LOOT, new DungeonTaskLoot());
  }

  public static DungeonTaskRegistry getInstance() {
    return singleton;
  }

  public void addTask(DungeonStage stage, IDungeonTask task) {
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
