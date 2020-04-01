package greymerk.roguelike.dungeon.rooms;

import java.util.Objects;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.worldgen.spawners.Spawner;

public class RoomSetting {
  private DungeonRoom dungeonRoom;
  private Spawner spawner;
  private String frequency;
  private int weight;

  public RoomSetting(DungeonRoom dungeonRoom, Spawner spawner, String frequency, int weight) {
    this.dungeonRoom = dungeonRoom;
    this.spawner = spawner;
    this.frequency = frequency;
    this.weight = weight;
  }

  public DungeonRoom getDungeonRoom() {
    return dungeonRoom;
  }

  public Spawner getSpawner() {
    return spawner;
  }

  public String getFrequency() {
    return frequency;
  }

  public int getWeight() {
    return weight;
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

  @Override
  public String toString() {
    return "RoomSetting{" +
        "dungeonRoom=" + dungeonRoom +
        ", spawner=" + spawner +
        ", frequency='" + frequency + '\'' +
        ", weight=" + weight +
        '}';
  }
}
