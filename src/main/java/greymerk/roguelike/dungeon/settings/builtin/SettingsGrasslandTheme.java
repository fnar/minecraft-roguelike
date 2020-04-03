package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.base.SettingsBase;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.filter.Filter;

import static com.google.common.collect.Lists.newArrayList;
import static net.minecraftforge.common.BiomeDictionary.Type.PLAINS;

public class SettingsGrasslandTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "grassland");

  public SettingsGrasslandTheme() {

    super(ID);
    getInherit().add(SettingsBase.ID);
    getSpawnCriteria().setBiomeTypes(newArrayList(PLAINS));

    setTowerSettings(new TowerSettings(Tower.ROGUE, Theme.TOWER));

    for (int i = 0; i < 5; ++i) {

      LevelSettings level = new LevelSettings();
      SecretFactory secrets = new SecretFactory();
      RoomsSetting rooms = new RoomsSetting();

      switch (i) {
        case 0:
          secrets.addRoom(RoomType.BEDROOM);
          secrets.addRoom(RoomType.SMITH);
          secrets.addRoom(RoomType.FIREWORK);
          level.setSecrets(secrets);
          break;
        case 1:
          secrets.addRoom(RoomType.BTEAM);
          rooms.add(RoomType.MUSIC.newSingleRoomSetting());
          rooms.add(RoomType.PIT.newSingleRoomSetting());
          rooms.add(RoomType.MESS.newSingleRoomSetting());
          rooms.add(RoomType.LAB.newSingleRoomSetting());
          rooms.add(RoomType.CORNER.newRandomRoomSetting(10));
          rooms.add(RoomType.BRICK.newRandomRoomSetting(3));
          level.setSecrets(secrets);
          level.setRooms(rooms);
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:
          break;
        default:
          break;
      }
      getLevels().put(i, level);
    }

    getLevels().get(3).addFilter(Filter.VINE);
  }


}
