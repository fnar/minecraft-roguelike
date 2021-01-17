package greymerk.roguelike.dungeon.settings.base;

import com.google.common.collect.Lists;

import java.util.List;

import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

import static greymerk.roguelike.dungeon.base.RoomType.BLAZE;
import static greymerk.roguelike.dungeon.base.RoomType.BRICK;
import static greymerk.roguelike.dungeon.base.RoomType.CAKE;
import static greymerk.roguelike.dungeon.base.RoomType.CORNER;
import static greymerk.roguelike.dungeon.base.RoomType.CREEPER;
import static greymerk.roguelike.dungeon.base.RoomType.CRYPT;
import static greymerk.roguelike.dungeon.base.RoomType.DARKHALL;
import static greymerk.roguelike.dungeon.base.RoomType.ENDER;
import static greymerk.roguelike.dungeon.base.RoomType.FIRE;
import static greymerk.roguelike.dungeon.base.RoomType.FORTRESS;
import static greymerk.roguelike.dungeon.base.RoomType.NETHER;
import static greymerk.roguelike.dungeon.base.RoomType.OBSIDIAN;
import static greymerk.roguelike.dungeon.base.RoomType.OSSUARY;
import static greymerk.roguelike.dungeon.base.RoomType.PIT;
import static greymerk.roguelike.dungeon.base.RoomType.PRISON;
import static greymerk.roguelike.dungeon.base.RoomType.REWARD;
import static greymerk.roguelike.dungeon.base.RoomType.SLIME;
import static greymerk.roguelike.dungeon.base.RoomType.SPIDER;

public class SettingsRooms extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "rooms");

  public SettingsRooms() {
    super(ID);

    addRoomsToLevel(0, getLevel0Rooms());
    addRoomsToLevel(1, getLevel1Rooms());
    addRoomsToLevel(2, getLevel2Rooms());
    addRoomsToLevel(3, getLevel3Rooms());
    addRoomsToLevel(4, getLevel4Rooms());
  }

  private void addRoomsToLevel(int i, List<RoomSetting> rooms) {
    RoomsSetting roomSetting = new RoomsSetting();
    rooms.forEach(roomSetting::add);

    LevelSettings level = new LevelSettings();
    level.setRooms(roomSetting);

    getLevels().put(i, level);
  }

  private List<RoomSetting> getLevel0Rooms() {
    return Lists.newArrayList(
        CAKE.newSingleRoomSetting(),
        FIRE.newSingleRoomSetting(),
        BRICK.newRandomRoomSetting(4),
        CORNER.newRandomRoomSetting(2),
        REWARD.newSingleRoomSetting()
    );
  }

  private List<RoomSetting> getLevel1Rooms() {
    return Lists.newArrayList(
        PIT.newSingleRoomSetting(),
        CORNER.newRandomRoomSetting(10),
        BRICK.newRandomRoomSetting(3),
        REWARD.newSingleRoomSetting()
    );
  }

  private List<RoomSetting> getLevel2Rooms() {
    return Lists.newArrayList(
        OSSUARY.newSingleRoomSetting(),
        CRYPT.newSingleRoomSetting(),
        CREEPER.newSingleRoomSetting(),
        FIRE.newSingleRoomSetting(),
        SPIDER.newSingleRoomSetting(),
        PRISON.newSingleRoomSetting(),
        CRYPT.newRandomRoomSetting(5),
        CORNER.newRandomRoomSetting(5),
        BRICK.newRandomRoomSetting(3),
        REWARD.newSingleRoomSetting()
    );
  }

  private List<RoomSetting> getLevel3Rooms() {
    return Lists.newArrayList(
        OSSUARY.newSingleRoomSetting(),
        ENDER.newSingleRoomSetting(),
        CRYPT.newSingleRoomSetting(),
        PRISON.newRandomRoomSetting(3),
        SLIME.newRandomRoomSetting(5),
        CREEPER.newRandomRoomSetting(1),
        SPIDER.newRandomRoomSetting(1),
        PIT.newRandomRoomSetting(1),
        REWARD.newSingleRoomSetting()
    );
  }

  private List<RoomSetting> getLevel4Rooms() {
    return Lists.newArrayList(
        OBSIDIAN.newSingleRoomSetting(),
        BLAZE.newSingleRoomSetting(),
        PRISON.newSingleRoomSetting(),
        DARKHALL.newSingleRoomSetting(),
        FORTRESS.newSingleRoomSetting(),
        SLIME.newRandomRoomSetting(10),
        BLAZE.newRandomRoomSetting(3),
        NETHER.newRandomRoomSetting(3),
        SPIDER.newRandomRoomSetting(2),
        REWARD.newSingleRoomSetting()
    );
  }
}
