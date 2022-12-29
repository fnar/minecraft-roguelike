package greymerk.roguelike.dungeon.rooms;

import com.github.fnar.roguelike.dungeon.rooms.NetherPortalRoom;
import com.github.fnar.roguelike.dungeon.rooms.PlatformsRoom;
import com.github.fnar.roguelike.dungeon.rooms.SmallLiquidPitRoom;

import java.util.List;
import java.util.Optional;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.rooms.prototype.AshleaRoom;
import greymerk.roguelike.dungeon.rooms.prototype.AvidyaRoom;
import greymerk.roguelike.dungeon.rooms.prototype.BTeamRoom;
import greymerk.roguelike.dungeon.rooms.prototype.BedRoomRoom;
import greymerk.roguelike.dungeon.rooms.prototype.BlazeRoom;
import greymerk.roguelike.dungeon.rooms.prototype.BrickRoom;
import greymerk.roguelike.dungeon.rooms.prototype.CakeRoom;
import greymerk.roguelike.dungeon.rooms.prototype.CornerRoom;
import greymerk.roguelike.dungeon.rooms.prototype.DarkHallRoom;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsCreeperDen;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsCrypt;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsEnchant;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsEnder;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsFire;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsMusic;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsNetherBrick;
import greymerk.roguelike.dungeon.rooms.prototype.PitRoom;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsPrison;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsSlime;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsSmithy;
import greymerk.roguelike.dungeon.rooms.prototype.DungeonsSpiderNest;
import greymerk.roguelike.dungeon.rooms.prototype.EnikoRoom;
import greymerk.roguelike.dungeon.rooms.prototype.EthoRoom;
import greymerk.roguelike.dungeon.rooms.prototype.FireworkRoom;
import greymerk.roguelike.dungeon.rooms.prototype.LabRoom;
import greymerk.roguelike.dungeon.rooms.prototype.LibraryRoom;
import greymerk.roguelike.dungeon.rooms.prototype.LinkerRoom;
import greymerk.roguelike.dungeon.rooms.prototype.LinkerTopRoom;
import greymerk.roguelike.dungeon.rooms.prototype.MessRoom;
import greymerk.roguelike.dungeon.rooms.prototype.NetherFortressRoom;
import greymerk.roguelike.dungeon.rooms.prototype.ObsidianRoom;
import greymerk.roguelike.dungeon.rooms.prototype.OssuaryRoom;
import greymerk.roguelike.dungeon.rooms.prototype.PyramidCornerRoom;
import greymerk.roguelike.dungeon.rooms.prototype.PyramidSpawnerRoom;
import greymerk.roguelike.dungeon.rooms.prototype.PyramidTombRoom;
import greymerk.roguelike.dungeon.rooms.prototype.RewardRoom;
import greymerk.roguelike.dungeon.rooms.prototype.StorageRoom;
import greymerk.roguelike.dungeon.rooms.prototype.TreethoRoom;
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

  public BaseRoom instantiate(LevelSettings levelSettings, WorldEditor worldEditor) {
    switch (getRoomType()) {
      default:
      case BRICK:
        return new BrickRoom(this, levelSettings, worldEditor);
      case ASHLEA:
        return new AshleaRoom(this, levelSettings, worldEditor);
      case AVIDYA:
        return new AvidyaRoom(this, levelSettings, worldEditor);
      case BEDROOM:
        return new BedRoomRoom(this, levelSettings, worldEditor);
      case BLAZE:
        return new BlazeRoom(this, levelSettings, worldEditor);
      case BTEAM:
        return new BTeamRoom(this, levelSettings, worldEditor);
      case CAKE:
        return new CakeRoom(this, levelSettings, worldEditor);
      case CORNER:
        return new CornerRoom(this, levelSettings, worldEditor);
      case CREEPER:
        return new DungeonsCreeperDen(this, levelSettings, worldEditor);
      case CRYPT:
        return new DungeonsCrypt(this, levelSettings, worldEditor);
      case DARKHALL:
        return new DarkHallRoom(this, levelSettings, worldEditor);
      case ENCHANT:
        return new DungeonsEnchant(this, levelSettings, worldEditor);
      case ENDER:
        return new DungeonsEnder(this, levelSettings, worldEditor);
      case ENIKO:
        return new EnikoRoom(this, levelSettings, worldEditor);
      case ETHO:
        return new EthoRoom(this, levelSettings, worldEditor);
      case FIRE:
        return new DungeonsFire(this, levelSettings, worldEditor);
      case FIREWORK:
        return new FireworkRoom(this, levelSettings, worldEditor);
      case LAB:
        return new LabRoom(this, levelSettings, worldEditor);
      case LIBRARY:
        return new LibraryRoom(this, levelSettings, worldEditor);
      case LINKER:
        return new LinkerRoom(this, levelSettings, worldEditor);
      case LINKERTOP:
        return new LinkerTopRoom(this, levelSettings, worldEditor);
      case MESS:
        return new MessRoom(this, levelSettings, worldEditor);
      case MUSIC:
        return new DungeonsMusic(this, levelSettings, worldEditor);
      case NETHER:
        return new DungeonsNetherBrick(this, levelSettings, worldEditor);
      case NETHERFORT:
        return new NetherFortressRoom(this, levelSettings, worldEditor);
      case NETHER_PORTAL:
        return new NetherPortalRoom(this, levelSettings, worldEditor);
      case OBSIDIAN:
        return new ObsidianRoom(this, levelSettings, worldEditor);
      case OSSUARY:
        return new OssuaryRoom(this, levelSettings, worldEditor);
      case PIT:
        return new PitRoom(this, levelSettings, worldEditor);
      case PLATFORMS:
        return new PlatformsRoom(this, levelSettings, worldEditor);
      case PRISON:
        return new DungeonsPrison(this, levelSettings, worldEditor);
      case PYRAMIDCORNER:
        return new PyramidCornerRoom(this, levelSettings, worldEditor);
      case PYRAMIDSPAWNER:
        return new PyramidSpawnerRoom(this, levelSettings, worldEditor);
      case PYRAMIDTOMB:
        return new PyramidTombRoom(this, levelSettings, worldEditor);
      case REWARD:
        return new RewardRoom(this, levelSettings, worldEditor);
      case SLIME:
        return new DungeonsSlime(this, levelSettings, worldEditor);
      case SMALL_LIQUID_PIT:
        return new SmallLiquidPitRoom(this, levelSettings, worldEditor);
      case SMITH:
        return new DungeonsSmithy(this, levelSettings, worldEditor);
      case SPIDER:
        return new DungeonsSpiderNest(this, levelSettings, worldEditor);
      case STORAGE:
        return new StorageRoom(this, levelSettings, worldEditor);
      case TREETHO:
        return new TreethoRoom(this, levelSettings, worldEditor);
    }
  }

}
