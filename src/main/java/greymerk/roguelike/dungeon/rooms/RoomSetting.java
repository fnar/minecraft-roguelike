package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.worldgen.spawners.Spawner;

public class RoomSetting {

  private DungeonRoom dungeonRoom;
  private Spawner spawner;
  private String frequency;
  private int weight;
  private List<Integer> levels;

  public RoomSetting(DungeonRoom dungeonRoom, Spawner spawner, String frequency, int weight, List<Integer> levels) {
    this.dungeonRoom = dungeonRoom;
    this.spawner = spawner;
    this.frequency = frequency;
    this.weight = weight;
    this.levels = levels;
  }

  public DungeonRoom getDungeonRoom() {
    return dungeonRoom;
  }

  public Optional<Spawner> getSpawner() {
    return Optional.ofNullable(spawner);
  }

  public String getFrequency() {
    return frequency;
  }

  public boolean isSecret() {
    return "secret".equals(getFrequency());
  }

  public int getWeight() {
    return weight;
  }

  public List<Integer> getLevels() {
    return levels;
  }

  public boolean isOnFloorLevel(int floorLevel) {
    return getLevels() != null && getLevels().contains(floorLevel);
  }

  @Override
  public boolean equals(Object o) {
    // I think this is only for tests, which means the behaviour isn't being tested, just the state
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomSetting that = (RoomSetting) o;
    return weight == that.weight &&
        dungeonRoom == that.dungeonRoom &&
        spawner == that.spawner &&
        Objects.equals(frequency, that.frequency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dungeonRoom, spawner, frequency, weight);
  }

}
