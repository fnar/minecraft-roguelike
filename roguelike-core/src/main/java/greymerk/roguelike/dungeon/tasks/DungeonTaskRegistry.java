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
    addTask(new DungeonTaskLayout(), LAYOUT);
    addTask(new DungeonTaskEncase(), ENCASE);
    addTask(new DungeonTaskTunnels(), TUNNELS);
    addTask(new DungeonTaskSegments(), SEGMENTS);
    addTask(new DungeonTaskRooms(), ROOMS);
    addTask(new DungeonTaskLinks(), LINKS);
    addTask(new DungeonTaskTower(), TOWER);
    addTask(new DungeonTaskFilters(), FILTERS);
    addTask(new DungeonTaskLoot(), LOOT);
  }

  public static DungeonTaskRegistry getInstance() {
    return singleton;
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
