package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.Themes;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public enum TowerType {

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

  public static ITower get(TowerType type) {

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

  public static Themes getDefaultTheme(TowerType type) {
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

  public static Coord getBaseCoord(WorldEditor editor, Coord origin) {

    Coord cursor = new Coord(origin.getX(), 128, origin.getZ());

    while (cursor.getY() > 60 && !editor.validGroundBlock(cursor)) {
      cursor.down();
    }

    cursor.up();

    int yOffset = cursor.getY() - origin.getY();

    if (yOffset < 14) {
      yOffset = 14;
    }

    return new Coord(origin.getX(), origin.getY() + yOffset, origin.getZ());
  }

  public static TowerType get(String name) throws Exception {
    if (!contains(name.toUpperCase())) {
      throw new Exception("No such tower type: " + name);
    }

    return valueOf(name.toUpperCase());
  }

  public static boolean contains(String name) {
    for (TowerType value : TowerType.values()) {
      if (value.toString().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public static TowerType randomTower(Random rand) {
    return TowerType.values()[rand.nextInt(TowerType.values().length)];
  }


}
