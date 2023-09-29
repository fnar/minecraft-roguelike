package greymerk.roguelike.dungeon.settings.builtin;

import com.google.common.collect.Lists;

import java.util.List;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

import static greymerk.roguelike.dungeon.base.RoomType.BLAZE;
import static greymerk.roguelike.dungeon.base.RoomType.BRICK;
import static greymerk.roguelike.dungeon.base.RoomType.CAKE;
import static greymerk.roguelike.dungeon.base.RoomType.CORNER;
import static greymerk.roguelike.dungeon.base.RoomType.CREEPER;
import static greymerk.roguelike.dungeon.base.RoomType.CRYPT;
import static greymerk.roguelike.dungeon.base.RoomType.ENDER;
import static greymerk.roguelike.dungeon.base.RoomType.FIRE;
import static greymerk.roguelike.dungeon.base.RoomType.FOUNTAIN;
import static greymerk.roguelike.dungeon.base.RoomType.NETHER;
import static greymerk.roguelike.dungeon.base.RoomType.NETHERFORT;
import static greymerk.roguelike.dungeon.base.RoomType.NETHER_PORTAL;
import static greymerk.roguelike.dungeon.base.RoomType.OBSIDIAN;
import static greymerk.roguelike.dungeon.base.RoomType.OSSUARY;
import static greymerk.roguelike.dungeon.base.RoomType.PIT;
import static greymerk.roguelike.dungeon.base.RoomType.PLATFORMS;
import static greymerk.roguelike.dungeon.base.RoomType.PLATFORMS_2;
import static greymerk.roguelike.dungeon.base.RoomType.PRISON;
import static greymerk.roguelike.dungeon.base.RoomType.REWARD;
import static greymerk.roguelike.dungeon.base.RoomType.SLIME;
import static greymerk.roguelike.dungeon.base.RoomType.SMALL_LIQUID_PIT;
import static greymerk.roguelike.dungeon.base.RoomType.SPIDER;

public class BuiltinRoomsSettings extends DungeonSettings {

  public static final String ID = SettingsContainer.BUILTIN_NAMESPACE + "Rooms";

  public BuiltinRoomsSettings() {
    super(ID);

    addRoomsToLevel(0, getLevel0Rooms());
    addRoomsToLevel(1, getLevel1Rooms());
    addRoomsToLevel(2, getLevel2Rooms());
    addRoomsToLevel(3, getLevel3Rooms());
    addRoomsToLevel(4, getLevel4Rooms());
  }

  private void addRoomsToLevel(int level, List<RoomSetting> rooms) {
    rooms.forEach(getLevelSettings().get(level).getRooms()::add);
  }

  private List<RoomSetting> getLevel0Rooms() {
    return Lists.newArrayList(
        CAKE.newSingleRoomSetting(),
        FIRE.newSingleRoomSetting(),
        REWARD.newSingleRoomSetting(),

        BRICK.newRandomRoomSetting(2),
        CORNER.newRandomRoomSetting(2)
    );
  }

  private List<RoomSetting> getLevel1Rooms() {
    return Lists.newArrayList(
        PIT.newSingleRoomSetting(),
        REWARD.newSingleRoomSetting(),

        BRICK.newRandomRoomSetting(3),
        CORNER.newRandomRoomSetting(10),
        PLATFORMS_2.newRandomRoomSetting(1),
        SMALL_LIQUID_PIT.newRandomRoomSetting(1)
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
        REWARD.newSingleRoomSetting(),

        CRYPT.newRandomRoomSetting(5),
        CORNER.newRandomRoomSetting(5),
        BRICK.newRandomRoomSetting(3)
    );
  }

  private List<RoomSetting> getLevel3Rooms() {
    return Lists.newArrayList(
        OSSUARY.newSingleRoomSetting(),
        CRYPT.newSingleRoomSetting(),
        REWARD.newSingleRoomSetting(),

        SLIME.newRandomRoomSetting(5),
        PRISON.newRandomRoomSetting(3),
        PLATFORMS.newRandomRoomSetting(2),
        CREEPER.newRandomRoomSetting(1),
        FOUNTAIN.newRandomRoomSetting(1),
        SPIDER.newRandomRoomSetting(1),
        PIT.newRandomRoomSetting(1),
        ENDER.newRandomRoomSetting(1)
    );
  }

  private List<RoomSetting> getLevel4Rooms() {
    return Lists.newArrayList(
        REWARD.newSingleRoomSetting(),
        OBSIDIAN.newSingleRoomSetting(),
        NETHERFORT.newSingleRoomSetting(),
        NETHER_PORTAL.newSingleRoomSetting(),

        BLAZE.newRandomRoomSetting(1),
        BRICK.newRandomRoomSetting(1),
        CRYPT.newRandomRoomSetting(1),
        CORNER.newRandomRoomSetting(1),
        PLATFORMS.newRandomRoomSetting(1),
        PLATFORMS_2.newRandomRoomSetting(1),
        NETHER.newRandomRoomSetting(1),
        PRISON.newRandomRoomSetting(1),
        OBSIDIAN.newRandomRoomSetting(1),
        SLIME.newRandomRoomSetting(1)
    );
  }
}
