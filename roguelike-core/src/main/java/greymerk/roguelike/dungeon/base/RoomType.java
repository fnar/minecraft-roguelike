package greymerk.roguelike.dungeon.base;


import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.rooms.Frequency;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import lombok.Getter;

@Getter
public enum RoomType {

  AVIDYA(false, false),
  BEDROOM(false, true),
  BLAZE(true, false),
  BRICK(true, false),
  BTEAM(false, true),
  CAKE(true, true),
  CORNER(false, false),
  CREEPER(true, false),
  CRYPT(true, false),
  DARKHALL(true, false),
  ENCHANT(false, true),
  ENDER(true, false),
  ENIKO(false, false),
  FIRE(true, false),
  FIREWORK(false, true),
  FOUNTAIN(true, false),
  LAB(true, false),
  LIBRARY(false, false),
  LINKER(false, false),
  LINKERTOP(false, false),
  MESS(true, false),
  MUSIC(true, false),
  NETHER(true, false),
  NETHERFORT(true, false),
  NETHER_PORTAL(true, false),
  OBSIDIAN(true, false),
  OSSUARY(true, false),
  PIT(true, false),
  PLATFORMS(true, false),
  PLATFORMS_2(true, false),
  PRISON(true, false),
  PYRAMIDCORNER(true, false),
  PYRAMIDSPAWNER(false, false),
  PYRAMIDTOMB(true, false),
  REWARD(false, false),
  SMALL_LIQUID_PIT(true, false),
  SLIME(true, false),
  SMITH(false, true),
  SPIDER(true, false),
  STORAGE(true, false),
  TREETHO(false, false)
  ;

  private final boolean isIntersection;
  private final boolean isSecret;

  RoomType(
      boolean isIntersection,
      boolean isSecret
  ) {
    this.isIntersection = isIntersection;
    this.isSecret = isSecret;
  }

  public static RoomType fromString(String string) {
    return valueOf(string.toUpperCase());
  }

  public RoomSetting newRandomRoomSetting(int weight) {
    return new RoomSetting(
        this,
        null,
        Frequency.RANDOM,
        weight,
        1,
        Lists.newArrayList(0, 1, 2, 3, 4),
        Optional.empty()
    );
  }

  public RoomSetting newSingleRoomSetting() {
    return new RoomSetting(
        this,
        null,
        Frequency.SINGLE,
        1,
        1,
        Lists.newArrayList(0, 1, 2, 3, 4),
        Optional.empty());
  }

  public static RoomType getRandomIntersection(Random random) {
    List<RoomType> intersections = getIntersections();
    return intersections.get(random.nextInt(intersections.size()));
  }

  public static List<RoomType> getSecrets() {
    return Arrays.stream(values())
        .filter(RoomType::isSecret)
        .collect(Collectors.toList());
  }

  public static List<RoomType> getIntersections() {
    return Arrays.stream(values())
        .filter(RoomType::isIntersection)
        .collect(Collectors.toList());
  }

  public static RoomType getRandomSecret(Random random) {
    List<RoomType> secrets = getSecrets();
    return secrets.get(random.nextInt(secrets.size()));
  }

}
