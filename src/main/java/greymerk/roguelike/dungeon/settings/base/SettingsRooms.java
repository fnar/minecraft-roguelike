package greymerk.roguelike.dungeon.settings.base;

import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

import static greymerk.roguelike.dungeon.base.DungeonRoom.BLAZE;
import static greymerk.roguelike.dungeon.base.DungeonRoom.BRICK;
import static greymerk.roguelike.dungeon.base.DungeonRoom.CAKE;
import static greymerk.roguelike.dungeon.base.DungeonRoom.CORNER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.CREEPER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.CRYPT;
import static greymerk.roguelike.dungeon.base.DungeonRoom.DARKHALL;
import static greymerk.roguelike.dungeon.base.DungeonRoom.ENDER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.FIRE;
import static greymerk.roguelike.dungeon.base.DungeonRoom.FORTRESS;
import static greymerk.roguelike.dungeon.base.DungeonRoom.NETHER;
import static greymerk.roguelike.dungeon.base.DungeonRoom.OBSIDIAN;
import static greymerk.roguelike.dungeon.base.DungeonRoom.OSSUARY;
import static greymerk.roguelike.dungeon.base.DungeonRoom.PIT;
import static greymerk.roguelike.dungeon.base.DungeonRoom.PRISON;
import static greymerk.roguelike.dungeon.base.DungeonRoom.SLIME;
import static greymerk.roguelike.dungeon.base.DungeonRoom.SPIDER;

public class SettingsRooms extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "rooms");

  public SettingsRooms() {
    super(ID);
    for (int i = 0; i < 5; ++i) {

      RoomsSetting factory;

      switch (i) {
        case 0:
          factory = new RoomsSetting();
          factory.add(CAKE.newSingleRoomSetting());
          factory.add(FIRE.newSingleRoomSetting());
          factory.add(BRICK.newRandomRoomSetting(4));
          factory.add(CORNER.newRandomRoomSetting(2));
          break;
        case 1:
          factory = new RoomsSetting();
          factory.add(PIT.newSingleRoomSetting());
          factory.add(CORNER.newRandomRoomSetting(10));
          factory.add(BRICK.newRandomRoomSetting(3));
          break;
        case 2:
          factory = new RoomsSetting();
          factory.add(OSSUARY.newSingleRoomSetting());
          factory.add(CRYPT.newSingleRoomSetting());
          factory.add(CREEPER.newSingleRoomSetting());
          factory.add(FIRE.newSingleRoomSetting());
          factory.add(SPIDER.newSingleRoomSetting());
          factory.add(PRISON.newSingleRoomSetting());
          factory.add(CRYPT.newRandomRoomSetting(5));
          factory.add(CORNER.newRandomRoomSetting(5));
          factory.add(BRICK.newRandomRoomSetting(3));
          break;
        case 3:
          factory = new RoomsSetting();
          factory.add(OSSUARY.newSingleRoomSetting());
          factory.add(ENDER.newSingleRoomSetting());
          factory.add(CRYPT.newSingleRoomSetting());
          factory.add(PRISON.newRandomRoomSetting(3));
          factory.add(SLIME.newRandomRoomSetting(5));
          factory.add(CREEPER.newRandomRoomSetting(1));
          factory.add(SPIDER.newRandomRoomSetting(1));
          factory.add(PIT.newRandomRoomSetting(1));
          break;
        case 4:
          factory = new RoomsSetting();
          factory.add(OBSIDIAN.newSingleRoomSetting());
          factory.add(BLAZE.newSingleRoomSetting());
          factory.add(PRISON.newSingleRoomSetting());
          factory.add(DARKHALL.newSingleRoomSetting());
          factory.add(FORTRESS.newSingleRoomSetting());
          factory.add(SLIME.newRandomRoomSetting(10));
          factory.add(BLAZE.newRandomRoomSetting(3));
          factory.add(NETHER.newRandomRoomSetting(3));
          factory.add(SPIDER.newRandomRoomSetting(2));
          break;
        default:
          factory = new RoomsSetting();
          break;
      }

      LevelSettings level = new LevelSettings();
      level.setRooms(factory);
      getLevels().put(i, level);
    }
  }
}
