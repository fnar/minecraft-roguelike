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
import greymerk.roguelike.dungeon.rooms.ObsidianRoom;
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
        return new DungeonAshlea(roomSetting);
      case AVIDYA:
        return new DungeonAvidya(roomSetting);
      case BEDROOM:
        return new DungeonBedRoom();
      case BLAZE:
        return new DungeonBlaze(roomSetting);
      case BTEAM:
        return new DungeonBTeam();
      case CAKE:
        return new DungeonsWood();
      case CORNER:
        return new DungeonCorner(roomSetting);
      case CREEPER:
        return new DungeonsCreeperDen(roomSetting);
      case CRYPT:
        return new DungeonsCrypt(roomSetting);
      case DARKHALL:
        return new DungeonDarkHall(roomSetting);
      case ENCHANT:
        return new DungeonsEnchant();
      case ENDER:
        return new DungeonsEnder(roomSetting);
      case ENIKO:
        return new DungeonEniko(roomSetting);
      case ETHO:
        return new DungeonEtho(roomSetting);
      case FIRE:
        return new DungeonsFire(roomSetting);
      case FIREWORK:
        return new DungeonFirework();
      case LAB:
        return new DungeonLab(roomSetting);
      case LIBRARY:
        return new DungeonLibrary(roomSetting);
      case LINKER:
        return new DungeonLinker(roomSetting);
      case LINKERTOP:
        return new DungeonLinkerTop(roomSetting);
      case MESS:
        return new DungeonMess(roomSetting);
      case MUSIC:
        return new DungeonsMusic(roomSetting);
      case NETHER:
        return new DungeonsNetherBrick(roomSetting);
      case NETHERFORT:
        return new DungeonsNetherBrickFortress(roomSetting);
      case OBSIDIAN:
        return new ObsidianRoom(roomSetting);
      case OSSUARY:
        return new DungeonOssuary(roomSetting);
      case PIT:
        return new DungeonsPit(roomSetting);
      case PRISON:
        return new DungeonsPrison();
      case PYRAMIDCORNER:
        return new DungeonPyramidCorner(roomSetting);
      case PYRAMIDSPAWNER:
        return new DungeonPyramidSpawner(roomSetting);
      case PYRAMIDTOMB:
        return new DungeonPyramidTomb();
      case REWARD:
        return new DungeonReward(roomSetting);
      case SLIME:
        return new DungeonsSlime(roomSetting);
      case SMITH:
        return new DungeonsSmithy();
      case SPIDER:
        return new DungeonsSpiderNest(roomSetting);
      case STORAGE:
        return new DungeonStorage(roomSetting);
      case TREETHO:
        return new DungeonTreetho(roomSetting);
      case BRICK:
      default:
        return new BrickRoom(roomSetting);
    }
  }
}
