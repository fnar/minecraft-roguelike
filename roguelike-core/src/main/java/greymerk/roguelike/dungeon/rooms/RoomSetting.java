package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Optional;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.rooms.prototype.BrickRoom;
import greymerk.roguelike.dungeon.rooms.prototype.CornerRoom;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonAshlea;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonAvidya;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonBTeam;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonBedRoom;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonBlaze;
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
import greymerk.roguelike.dungeon.rooms.prototype.NetherFortressRoom;
import greymerk.roguelike.dungeon.rooms.prototype.ObsidianRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.WorldEditor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class RoomSetting {

  private final RoomType roomType;
  private final String spawnerId;
  private final Frequency frequency;
  private final int weight;
  private final int count;
  private final List<Integer> levels;
  private final Optional<ChestType> chestType;

  public RoomSetting(RoomType roomType, String spawnerId, Frequency frequency, int weight, int count, List<Integer> levels, Optional<ChestType> chestType) {
    this.roomType = roomType;
    this.spawnerId = spawnerId;
    this.frequency = frequency;
    this.weight = weight;
    this.count = count;
    this.levels = levels;
    this.chestType = chestType;
  }

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

  public DungeonBase instantiate(LevelSettings levelSettings, WorldEditor worldEditor) {
    switch (getRoomType()) {
      default:
      case BRICK:
        return new BrickRoom(this, levelSettings, worldEditor);
      case ASHLEA:
        return new DungeonAshlea(this, levelSettings, worldEditor);
      case AVIDYA:
        return new DungeonAvidya(this, levelSettings, worldEditor);
      case BEDROOM:
        return new DungeonBedRoom(this, levelSettings, worldEditor);
      case BLAZE:
        return new DungeonBlaze(this, levelSettings, worldEditor);
      case BTEAM:
        return new DungeonBTeam(this, levelSettings, worldEditor);
      case CAKE:
        return new DungeonsWood(this, levelSettings, worldEditor);
      case CORNER:
        return new CornerRoom(this, levelSettings, worldEditor);
      case CREEPER:
        return new DungeonsCreeperDen(this, levelSettings, worldEditor);
      case CRYPT:
        return new DungeonsCrypt(this, levelSettings, worldEditor);
      case DARKHALL:
        return new DungeonDarkHall(this, levelSettings, worldEditor);
      case ENCHANT:
        return new DungeonsEnchant(this, levelSettings, worldEditor);
      case ENDER:
        return new DungeonsEnder(this, levelSettings, worldEditor);
      case ENIKO:
        return new DungeonEniko(this, levelSettings, worldEditor);
      case ETHO:
        return new DungeonEtho(this, levelSettings, worldEditor);
      case FIRE:
        return new DungeonsFire(this, levelSettings, worldEditor);
      case FIREWORK:
        return new DungeonFirework(this, levelSettings, worldEditor);
      case NETHERFORT:
        return new NetherFortressRoom(this, levelSettings, worldEditor);
      case LAB:
        return new DungeonLab(this, levelSettings, worldEditor);
      case LIBRARY:
        return new DungeonLibrary(this, levelSettings, worldEditor);
      case LINKER:
        return new DungeonLinker(this, levelSettings, worldEditor);
      case LINKERTOP:
        return new DungeonLinkerTop(this, levelSettings, worldEditor);
      case MESS:
        return new DungeonMess(this, levelSettings, worldEditor);
      case MUSIC:
        return new DungeonsMusic(this, levelSettings, worldEditor);
      case NETHER:
        return new DungeonsNetherBrick(this, levelSettings, worldEditor);
      case OBSIDIAN:
        return new ObsidianRoom(this, levelSettings, worldEditor);
      case OSSUARY:
        return new DungeonOssuary(this, levelSettings, worldEditor);
      case PIT:
        return new DungeonsPit(this, levelSettings, worldEditor);
      case PRISON:
        return new DungeonsPrison(this, levelSettings, worldEditor);
      case PYRAMIDCORNER:
        return new DungeonPyramidCorner(this, levelSettings, worldEditor);
      case PYRAMIDSPAWNER:
        return new DungeonPyramidSpawner(this, levelSettings, worldEditor);
      case PYRAMIDTOMB:
        return new DungeonPyramidTomb(this, levelSettings, worldEditor);
      case REWARD:
        return new DungeonReward(this, levelSettings, worldEditor);
      case SLIME:
        return new DungeonsSlime(this, levelSettings, worldEditor);
      case SMITH:
        return new DungeonsSmithy(this, levelSettings, worldEditor);
      case SPIDER:
        return new DungeonsSpiderNest(this, levelSettings, worldEditor);
      case STORAGE:
        return new DungeonStorage(this, levelSettings, worldEditor);
      case TREETHO:
        return new DungeonTreetho(this, levelSettings, worldEditor);
    }
  }
}
