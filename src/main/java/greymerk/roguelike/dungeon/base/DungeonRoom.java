package greymerk.roguelike.dungeon.base;


import java.util.Random;


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

  public static DungeonRoom getRandomRoom(Random rand) {
    return intersectionRooms[rand.nextInt(intersectionRooms.length)];
  }

  public static DungeonRoom getRandomSecret(Random rand) {
    return secrets[rand.nextInt(secrets.length)];
  }

}
