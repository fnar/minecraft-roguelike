package greymerk.roguelike.dungeon.rooms;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.rooms.prototype.BrickRoom;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonAshlea;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonAvidya;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonBTeam;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonBedRoom;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonBlaze;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonCorner;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonDarkHall;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonEniko;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonEtho;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonFirework;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonLab;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonLibrary;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonLinker;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonLinkerTop;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonMess;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonOssuary;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonPyramidCorner;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonPyramidSpawner;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonPyramidTomb;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonReward;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonStorage;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonTreetho;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsCreeperDen;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsCrypt;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsEnchant;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsEnder;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsFire;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsMusic;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsNetherBrick;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsPit;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsPrison;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsSlime;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsSmithy;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsSpiderNest;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsWood;
import greymerk.roguelike.dungeon.rooms.prototype.FortressRoom;
import greymerk.roguelike.dungeon.rooms.prototype.ObsidianRoom;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class RoomSetting {

  private RoomType roomType;
  private String spawnerId;
  private Frequency frequency;
  private int weight;
  private int count;
  private List<Integer> levels;

  public boolean isRandom() {
    return frequency.isRandom();
  }

  public boolean isSecret() {
    return frequency.isSecret();
  }

  public boolean isSingle() {
    return frequency.isSingle();
  }

  public boolean isOnFloorLevel(int floorLevel) {
    return getLevels() != null && getLevels().contains(floorLevel);
  }

  public DungeonBase instantiate() {
    switch (roomType) {
      case ASHLEA:
        return new DungeonAshlea(this);
      case AVIDYA:
        return new DungeonAvidya(this);
      case BEDROOM:
        return new DungeonBedRoom(this);
      case BLAZE:
        return new DungeonBlaze(this);
      case BTEAM:
        return new DungeonBTeam(this);
      case CAKE:
        return new DungeonsWood(this);
      case CORNER:
        return new DungeonCorner(this);
      case CREEPER:
        return new DungeonsCreeperDen(this);
      case CRYPT:
        return new DungeonsCrypt(this);
      case DARKHALL:
        return new DungeonDarkHall(this);
      case ENCHANT:
        return new DungeonsEnchant(this);
      case ENDER:
        return new DungeonsEnder(this);
      case ENIKO:
        return new DungeonEniko(this);
      case ETHO:
        return new DungeonEtho(this);
      case FIRE:
        return new DungeonsFire(this);
      case FIREWORK:
        return new DungeonFirework(this);
      case FORTRESS:
        return new FortressRoom(this);
      case LAB:
        return new DungeonLab(this);
      case LIBRARY:
        return new DungeonLibrary(this);
      case LINKER:
        return new DungeonLinker(this);
      case LINKERTOP:
        return new DungeonLinkerTop(this);
      case MESS:
        return new DungeonMess(this);
      case MUSIC:
        return new DungeonsMusic(this);
      case NETHER:
        return new DungeonsNetherBrick(this);
      case OBSIDIAN:
        return new ObsidianRoom(this);
      case OSSUARY:
        return new DungeonOssuary(this);
      case PIT:
        return new DungeonsPit(this);
      case PRISON:
        return new DungeonsPrison(this);
      case PYRAMIDCORNER:
        return new DungeonPyramidCorner(this);
      case PYRAMIDSPAWNER:
        return new DungeonPyramidSpawner(this);
      case PYRAMIDTOMB:
        return new DungeonPyramidTomb(this);
      case REWARD:
        return new DungeonReward(this);
      case SLIME:
        return new DungeonsSlime(this);
      case SMITH:
        return new DungeonsSmithy(this);
      case SPIDER:
        return new DungeonsSpiderNest(this);
      case STORAGE:
        return new DungeonStorage(this);
      case TREETHO:
        return new DungeonTreetho(this);
      case BRICK:
      default:
        return new BrickRoom(this);
    }
  }
}
