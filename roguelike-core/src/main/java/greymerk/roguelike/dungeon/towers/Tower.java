package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.Themes;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public enum Tower {

  BUMBO,
  BUNKER,
  ENIKO,
  ETHO,
  HOLE,
  HOUSE,
  JUNGLE,
  PYRAMID,
  ROGUE,
  RUIN,
  TREE,
  VILLAGER_HOUSE,
  WITCH
  ;

  public static ITower get(Tower type) {

    switch (type) {
      default:
      case ROGUE:
        return new RogueTower();
      case ENIKO:
        return new EniTower();
      case ETHO:
        return new EthoTower();
      case PYRAMID:
        return new PyramidTower();
      case JUNGLE:
        return new JungleTower();
      case WITCH:
        return new WitchTower();
      case HOUSE:
        return new HouseTower();
      case BUNKER:
        return new BunkerTower();
      case RUIN:
        return new RuinTower();
      case HOLE:
        return new HoleTower();
      case TREE:
        return new TreeTower();
      case BUMBO:
        return new BumboTower();
      case VILLAGER_HOUSE:
        return new VillagerHouseTower();
    }
  }

  public static Themes getDefaultTheme(Tower type) {
    switch (type) {
      case ETHO:
        return Themes.ETHOTOWER;
      case PYRAMID:
        return Themes.PYRAMID;
      case JUNGLE:
        return Themes.JUNGLE;
      case WITCH:
        return Themes.DARKHALL;
      case HOUSE:
        return Themes.HOUSE;
      case BUMBO:
        return Themes.BUMBO;
      default:
        return Themes.OAK;
    }
  }

  public static Coord getBaseCoord(WorldEditor editor, Coord pos) {

    Coord cursor = new Coord(pos.getX(), 128, pos.getZ());

    while (cursor.getY() > 60) {
      if (editor.validGroundBlock(cursor)) {
        break;
      }
      cursor.down();
    }

    cursor.up();

    int yOffset = cursor.getY() - pos.getY();

    if (yOffset < 14) {
      yOffset = 14;
    }

    return new Coord(pos.getX(), pos.getY() + yOffset, pos.getZ());
  }

  public static Tower get(String name) throws Exception {
    if (!contains(name.toUpperCase())) {
      throw new Exception("No such tower type: " + name);
    }

    return valueOf(name.toUpperCase());
  }

  public static boolean contains(String name) {
    for (Tower value : Tower.values()) {
      if (value.toString().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public static Tower randomTower(Random rand) {
    return Tower.values()[rand.nextInt(Tower.values().length)];
  }


}
