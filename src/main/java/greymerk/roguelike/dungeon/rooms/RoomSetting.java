package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Optional;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.worldgen.spawners.Spawner;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class RoomSetting {

  private DungeonRoom dungeonRoom;
  private Spawner spawner;
  private String spawnerId;
  private String frequency;
  private int weight;
  private int count;
  private List<Integer> levels;

  public Optional<Spawner> getSpawner() {
    return Optional.ofNullable(spawner);
  }

  public boolean isSecret() {
    return "secret".equals(getFrequency());
  }

  public boolean isOnFloorLevel(int floorLevel) {
    return getLevels() != null && getLevels().contains(floorLevel);
  }
}
