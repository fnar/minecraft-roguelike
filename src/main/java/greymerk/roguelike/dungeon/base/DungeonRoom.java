package greymerk.roguelike.dungeon.base;


import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import lombok.Getter;

@Getter
public enum DungeonRoom {

  ASHLEA(false, false),
  AVIDYA(false, false),
  BEDROOM(false, true),
  BLAZE(true, false),
  BRICK(true, false),
  BTEAM(false, false),
  CAKE(true, true),
  CORNER(false, false),
  CREEPER(true, false),
  CRYPT(true, false),
  DARKHALL(true, false),
  ENCHANT(false, true),
  ENDER(true, false),
  ENIKO(false, false),
  ETHO(false, false),
  FIRE(true, false),
  FIREWORK(false, false),
  FORTRESS(true, false),
  LAB(true, false),
  LIBRARY(false, false),
  LINKER(false, false),
  LINKERTOP(false, false),
  MESS(true, false),
  MUSIC(true, false),
  NETHER(true, false),
  OBSIDIAN(true, false),
  OSSUARY(true, false),
  PIT(true, false),
  PRISON(true, false),
  PYRAMIDCORNER(true, false),
  PYRAMIDSPAWNER(false, false),
  PYRAMIDTOMB(true, false),
  REWARD(false, false),
  SLIME(true, false),
  SMITH(false, true),
  SPIDER(true, false),
  STORAGE(true, false),
  TREETHO(false, false);

  private boolean isIntersection;
  private boolean isSecret;

  DungeonRoom(
      boolean isIntersection,
      boolean isSecret
  ) {
    this.isIntersection = isIntersection;
    this.isSecret = isSecret;
  }

  public RoomSetting newRandomRoomSetting(int weight) {
    return new RoomSetting(this, null, "builtin:spawner", "random", weight, 1, Lists.newArrayList(0, 1, 2, 3, 4));
  }

  public RoomSetting newSingleRoomSetting() {
    return new RoomSetting(this, null, "builtin:spawner", "single", 1, 1, Lists.newArrayList(0, 1, 2, 3, 4));
  }

  public static DungeonRoom getRandomIntersection(Random random) {
    List<DungeonRoom> intersections = getIntersections();
    return intersections.get(random.nextInt(intersections.size()));
  }

  private static List<DungeonRoom> getSecrets() {
    return Arrays.stream(values())
        .filter(DungeonRoom::isSecret)
        .collect(Collectors.toList());
  }

  private static List<DungeonRoom> getIntersections() {
    return Arrays.stream(values())
        .filter(DungeonRoom::isIntersection)
        .collect(Collectors.toList());
  }

  public static DungeonRoom getRandomSecret(Random random) {
    List<DungeonRoom> secrets = getSecrets();
    return secrets.get(random.nextInt(secrets.size()));
  }

}
