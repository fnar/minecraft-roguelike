package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.base.DungeonRoom;

public class RoomSetting {
  private DungeonRoom dungeonRoom;
  private String frequency;
  private int weight;

  public RoomSetting(DungeonRoom dungeonRoom, String frequency, int weight) {
    this.dungeonRoom = dungeonRoom;
    this.frequency = frequency;
    this.weight = weight;
  }

  public DungeonRoom getDungeonRoom() {
    return dungeonRoom;
  }

  public String getFrequency() {
    return frequency;
  }

  public int getWeight() {
    return weight;
  }
}
