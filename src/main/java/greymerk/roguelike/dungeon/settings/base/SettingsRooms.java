package greymerk.roguelike.dungeon.settings.base;

import greymerk.roguelike.dungeon.base.DungeonFactory;
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

      DungeonFactory factory;

      switch (i) {
        case 0:
          factory = new DungeonFactory();
          factory.addSingle(CAKE.newSingleRoomSetting());
          factory.addSingle(FIRE.newSingleRoomSetting());
          factory.addRandom(BRICK.newRandomRoomSetting(4));
          factory.addRandom(CORNER.newRandomRoomSetting(2));
          break;
        case 1:
          factory = new DungeonFactory();
          factory.addSingle(PIT.newSingleRoomSetting());
          factory.addRandom(CORNER.newRandomRoomSetting(10));
          factory.addRandom(BRICK.newRandomRoomSetting(3));
          break;
        case 2:
          factory = new DungeonFactory();
          factory.addSingle(OSSUARY.newSingleRoomSetting());
          factory.addSingle(CRYPT.newSingleRoomSetting());
          factory.addSingle(CREEPER.newSingleRoomSetting());
          factory.addSingle(FIRE.newSingleRoomSetting());
          factory.addSingle(SPIDER.newSingleRoomSetting());
          factory.addSingle(PRISON.newSingleRoomSetting());
          factory.addRandom(CRYPT.newRandomRoomSetting(5));
          factory.addRandom(CORNER.newRandomRoomSetting(5));
          factory.addRandom(BRICK.newRandomRoomSetting(3));
          break;
        case 3:
          factory = new DungeonFactory();
          factory.addSingle(OSSUARY.newSingleRoomSetting());
          factory.addSingle(ENDER.newSingleRoomSetting());
          factory.addSingle(CRYPT.newSingleRoomSetting());
          factory.addRandom(PRISON.newRandomRoomSetting(3));
          factory.addRandom(SLIME.newRandomRoomSetting(5));
          factory.addRandom(CREEPER.newRandomRoomSetting(1));
          factory.addRandom(SPIDER.newRandomRoomSetting(1));
          factory.addRandom(PIT.newRandomRoomSetting(1));
          break;
        case 4:
          factory = new DungeonFactory();
          factory.addSingle(OBSIDIAN.newSingleRoomSetting());
          factory.addSingle(BLAZE.newSingleRoomSetting());
          factory.addSingle(PRISON.newSingleRoomSetting());
          factory.addSingle(DARKHALL.newSingleRoomSetting());
          factory.addSingle(FORTRESS.newSingleRoomSetting());
          factory.addRandom(SLIME.newRandomRoomSetting(10));
          factory.addRandom(BLAZE.newRandomRoomSetting(3));
          factory.addRandom(NETHER.newRandomRoomSetting(3));
          factory.addRandom(SPIDER.newRandomRoomSetting(2));
          break;
        default:
          factory = new DungeonFactory();
          break;
      }

      LevelSettings level = new LevelSettings();
      level.setRooms(factory);
      getLevels().put(i, level);
    }
  }
}
