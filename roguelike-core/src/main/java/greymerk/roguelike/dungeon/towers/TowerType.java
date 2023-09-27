package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.Theme;
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

    while (cursor.getY() > 60 && !editor.isValidGroundBlock(cursor)) {
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

  public static TowerType random(Random random) {
    TowerType[] values = TowerType.values();
    return values[random.nextInt(values.length)];
  }


  public Tower instantiate(WorldEditor worldEditor, Theme theme) {

    switch (this) {
      default:
      case ROGUE:
        return new RogueTower(worldEditor, theme);
      case ENIKO:
        return new EniTower(worldEditor, theme);
      case ETHO:
        return new EthoTower(worldEditor, theme);
      case PYRAMID:
        return new PyramidTower(worldEditor, theme);
      case JUNGLE:
        return new JungleTower(worldEditor, theme);
      case WITCH:
        return new WitchTower(worldEditor, theme);
      case HOUSE:
        return new HouseTower(worldEditor, theme);
      case BUNKER:
        return new BunkerTower(worldEditor, theme);
      case RUIN:
        return new RuinTower(worldEditor, theme);
      case HOLE:
        return new HoleTower(worldEditor, theme);
      case TREE:
        return new TreeTower(worldEditor, theme);
      case BUMBO:
        return new BumboTower(worldEditor, theme);
      case VILLAGER_HOUSE:
        return new VillagerHouseTower(worldEditor, theme);
    }
  }
}
