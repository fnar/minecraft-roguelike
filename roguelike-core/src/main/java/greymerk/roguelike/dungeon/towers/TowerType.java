package greymerk.roguelike.dungeon.towers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.theme.Theme;
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
  WITCH;

  public static Theme getDefaultTheme(TowerType type) {
    switch (type) {
      case ETHO:
        return Theme.ETHOTOWER;
      case PYRAMID:
        return Theme.PYRAMID;
      case JUNGLE:
        return Theme.JUNGLE;
      case WITCH:
        return Theme.DARKHALL;
      case HOUSE:
        return Theme.HOUSE;
      case BUMBO:
        return Theme.BUMBO;
      default:
        return Theme.OAK;
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

  public static Optional<TowerType> get(String name) {
    return contains(name) ? Optional.of(valueOf(name.toUpperCase())) : Optional.empty();
  }

  public static boolean contains(String name) {
    return Arrays.stream(TowerType.values())
        .anyMatch(value -> name.equalsIgnoreCase(value.toString()));
  }

  public static TowerType random(Random random) {
    TowerType[] values = TowerType.values();
    return values[random.nextInt(values.length)];
  }

  public static List<String> getTowerList() {
    return Arrays.stream(values())
        .map(TowerType::toString)
        .map(String::toLowerCase)
        .collect(Collectors.toList());
  }

  public Tower instantiate(WorldEditor worldEditor) {
    return instantiate(worldEditor, TowerType.getDefaultTheme(this));
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
