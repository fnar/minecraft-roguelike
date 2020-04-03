package greymerk.roguelike.dungeon.base;


import com.google.common.collect.Lists;

import java.util.Random;

import greymerk.roguelike.dungeon.rooms.RoomSetting;


public enum DungeonRoom {

  ASHLEA,
  AVIDYA,
  BEDROOM,
  BLAZE,
  BRICK,
  BTEAM,
  CAKE,
  CORNER,
  CREEPER,
  CRYPT,
  DARKHALL,
  ENCHANT,
  ENDER,
  ENIKO,
  ETHO,
  FIRE,
  FIREWORK,
  FORTRESS,
  LAB,
  LIBRARY,
  LINKER,
  LINKERTOP,
  MESS,
  MUSIC,
  NETHER,
  OBSIDIAN,
  OSSUARY,
  PIT,
  PRISON,
  PYRAMIDCORNER,
  PYRAMIDSPAWNER,
  PYRAMIDTOMB,
  REWARD,
  SLIME,
  SMITH,
  SPIDER,
  STORAGE,
  TREETHO;

  public static DungeonRoom[] intersectionRooms = {
      BLAZE, BRICK, CAKE, CREEPER, CRYPT, DARKHALL, ENDER, FIRE, FORTRESS, LAB,
      MESS, MUSIC, NETHER, OBSIDIAN, OSSUARY, PIT, PRISON, PYRAMIDCORNER, PYRAMIDSPAWNER,
      PYRAMIDTOMB, SLIME, SPIDER, STORAGE
  };

  public static DungeonRoom[] secrets = {
      BEDROOM, CAKE, ENCHANT, SMITH
  };

  public RoomSetting newRandomRoomSetting(int weight) {
    return new RoomSetting(this, null, "builtin:spawner", "random", weight, 1, Lists.newArrayList(0, 1, 2, 3, 4));
  }

  public RoomSetting newSingleRoomSetting() {
    return new RoomSetting(this, null, "builtin:spawner", "single", 1, 1, Lists.newArrayList(0, 1, 2, 3, 4));
  }

  public static DungeonRoom getRandomRoom(Random rand) {
    return intersectionRooms[rand.nextInt(intersectionRooms.length)];
  }

  public static DungeonRoom getRandomSecret(Random rand) {
    return secrets[rand.nextInt(secrets.length)];
  }

}
