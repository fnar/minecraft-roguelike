package greymerk.roguelike.dungeon.base;


import java.util.Random;

import greymerk.roguelike.dungeon.rooms.BrickRoom;
import greymerk.roguelike.dungeon.rooms.DungeonAshlea;
import greymerk.roguelike.dungeon.rooms.DungeonAvidya;
import greymerk.roguelike.dungeon.rooms.DungeonBTeam;
import greymerk.roguelike.dungeon.rooms.DungeonBedRoom;
import greymerk.roguelike.dungeon.rooms.DungeonBlaze;
import greymerk.roguelike.dungeon.rooms.DungeonCorner;
import greymerk.roguelike.dungeon.rooms.DungeonDarkHall;
import greymerk.roguelike.dungeon.rooms.DungeonEniko;
import greymerk.roguelike.dungeon.rooms.DungeonEtho;
import greymerk.roguelike.dungeon.rooms.DungeonFirework;
import greymerk.roguelike.dungeon.rooms.DungeonLab;
import greymerk.roguelike.dungeon.rooms.DungeonLibrary;
import greymerk.roguelike.dungeon.rooms.DungeonLinker;
import greymerk.roguelike.dungeon.rooms.DungeonLinkerTop;
import greymerk.roguelike.dungeon.rooms.DungeonMess;
import greymerk.roguelike.dungeon.rooms.DungeonObsidian;
import greymerk.roguelike.dungeon.rooms.DungeonOssuary;
import greymerk.roguelike.dungeon.rooms.DungeonPyramidCorner;
import greymerk.roguelike.dungeon.rooms.DungeonPyramidSpawner;
import greymerk.roguelike.dungeon.rooms.DungeonPyramidTomb;
import greymerk.roguelike.dungeon.rooms.DungeonReward;
import greymerk.roguelike.dungeon.rooms.DungeonStorage;
import greymerk.roguelike.dungeon.rooms.DungeonTreetho;
import greymerk.roguelike.dungeon.rooms.DungeonsCreeperDen;
import greymerk.roguelike.dungeon.rooms.DungeonsCrypt;
import greymerk.roguelike.dungeon.rooms.DungeonsEnchant;
import greymerk.roguelike.dungeon.rooms.DungeonsEnder;
import greymerk.roguelike.dungeon.rooms.DungeonsFire;
import greymerk.roguelike.dungeon.rooms.DungeonsMusic;
import greymerk.roguelike.dungeon.rooms.DungeonsNetherBrick;
import greymerk.roguelike.dungeon.rooms.DungeonsNetherBrickFortress;
import greymerk.roguelike.dungeon.rooms.DungeonsPit;
import greymerk.roguelike.dungeon.rooms.DungeonsPrison;
import greymerk.roguelike.dungeon.rooms.DungeonsSlime;
import greymerk.roguelike.dungeon.rooms.DungeonsSmithy;
import greymerk.roguelike.dungeon.rooms.DungeonsSpiderNest;
import greymerk.roguelike.dungeon.rooms.DungeonsWood;
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
  LAB,
  LIBRARY,
  LINKER,
  LINKERTOP,
  MESS,
  MUSIC,
  NETHER,
  NETHERFORT,
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
      BLAZE, BRICK, CAKE, CREEPER, CRYPT, DARKHALL, ENDER, FIRE, LAB, MESS,
      MUSIC, NETHER, NETHERFORT, OBSIDIAN, OSSUARY, PIT, PRISON, PYRAMIDCORNER, PYRAMIDSPAWNER,
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

  public IDungeonRoom instantiate(RoomSetting roomSetting) {
    switch (this) {
      case ASHLEA:
        return new DungeonAshlea();
      case AVIDYA:
        return new DungeonAvidya();
      case BEDROOM:
        return new DungeonBedRoom();
      case BLAZE:
        return new DungeonBlaze();
      case BTEAM:
        return new DungeonBTeam();
      case CAKE:
        return new DungeonsWood();
      case CORNER:
        return new DungeonCorner();
      case CREEPER:
        return new DungeonsCreeperDen();
      case CRYPT:
        return new DungeonsCrypt();
      case DARKHALL:
        return new DungeonDarkHall();
      case ENCHANT:
        return new DungeonsEnchant();
      case ENDER:
        return new DungeonsEnder();
      case ENIKO:
        return new DungeonEniko();
      case ETHO:
        return new DungeonEtho();
      case FIRE:
        return new DungeonsFire();
      case FIREWORK:
        return new DungeonFirework();
      case LAB:
        return new DungeonLab();
      case LIBRARY:
        return new DungeonLibrary();
      case LINKER:
        return new DungeonLinker();
      case LINKERTOP:
        return new DungeonLinkerTop();
      case MESS:
        return new DungeonMess();
      case MUSIC:
        return new DungeonsMusic();
      case NETHER:
        return new DungeonsNetherBrick();
      case NETHERFORT:
        return new DungeonsNetherBrickFortress();
      case OBSIDIAN:
        return new DungeonObsidian();
      case OSSUARY:
        return new DungeonOssuary();
      case PIT:
        return new DungeonsPit();
      case PRISON:
        return new DungeonsPrison();
      case PYRAMIDCORNER:
        return new DungeonPyramidCorner();
      case PYRAMIDSPAWNER:
        return new DungeonPyramidSpawner();
      case PYRAMIDTOMB:
        return new DungeonPyramidTomb();
      case REWARD:
        return new DungeonReward();
      case SLIME:
        return new DungeonsSlime();
      case SMITH:
        return new DungeonsSmithy();
      case SPIDER:
        return new DungeonsSpiderNest();
      case STORAGE:
        return new DungeonStorage();
      case TREETHO:
        return new DungeonTreetho();
      case BRICK:
      default:
        return new BrickRoom(roomSetting);
    }
  }
}
