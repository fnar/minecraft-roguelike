package greymerk.roguelike.theme;

import java.util.Random;

import static java.util.Optional.ofNullable;

public enum Theme {

  BLING(new ThemeBling()),
  BRICK(new ThemeBrick()),
  BUMBO(new ThemeBumbo()),
  CAVE(new ThemeCave()),
  CHECKER(new ThemeChecker()),
  CRYPT(new ThemeCrypt()),
  DARKHALL(new ThemeDarkHall()),
  DARKOAK(new ThemeDarkOak()),
  ENDER(new ThemeEnder()),
  ENIICE(new ThemeEniIce()),
  ENIKO(new ThemeEniko()),
  ENIKO2(new ThemeEniko2()),
  ENIQUARTZ(new ThemeEniQuartz()),
  ETHO(new ThemeEtho()),
  ETHOTOWER(new ThemeEthoTower()),
  GREY(new ThemeGrey()),
  HELL(new ThemeHell()),
  HOUSE(new ThemeHouse()),
  ICE(new ThemeIce()),
  JUNGLE(new ThemeJungle()),
  MINESHAFT(new ThemeMineShaft()),
  MOSSY(new ThemeMossy()),
  MUDDY(new ThemeMuddy()),
  NETHER(new ThemeNether()),
  OAK(new ThemeOak()),
  PURPUR(new ThemePurpur()),
  PYRAMID(new ThemePyramid()),
  QUARTZ(new ThemeQuartz()),
  RAINBOW(new ThemeRainbow()),
  SANDSTONE(new ThemeSandstone()),
  SANDSTONERED(new ThemeSandstoneRed()),
  SEWER(new ThemeSewer()),
  SNOW(new ThemeSnow()),
  SPRUCE(new ThemeSpruce()),
  STONE(new ThemeStone()),
  TEMPLE(new ThemeTemple()),
  TERRACOTTA(new ThemeTerracotta()),
  TOWER(new ThemeTower());

  private static final Random random = new Random();
  private final ThemeBase themeBase;

  Theme(ThemeBase themeBase) {
    this.themeBase = themeBase;
  }

  public ThemeBase getThemeBase() {
    return themeBase;
  }

  public static ITheme inherit(ITheme parent, ITheme child) {
    return new ThemeBase(
        getPrimaryBlockSet(parent, child),
        getSecondaryBlockSet(parent, child));
  }

  private static IBlockSet getPrimaryBlockSet(ITheme parent, ITheme child) {
    return inherit(
        parent.getPrimary(),
        child.getPrimary());
  }

  private static IBlockSet getSecondaryBlockSet(ITheme parent, ITheme child) {
    return inherit(
        parent.getSecondary(),
        child.getSecondary());
  }

  private static IBlockSet inherit(
      IBlockSet parentBlockSet,
      IBlockSet childBlockSet
  ) {
    if (parentBlockSet == null && childBlockSet == null) {
      return new BlockSet();
    }
    if (parentBlockSet == null) {
      return childBlockSet;
    }
    if (childBlockSet == null) {
      return parentBlockSet;
    }
    return new BlockSet(
        ofNullable(childBlockSet.getFloor()).orElse(parentBlockSet.getFloor()),
        ofNullable(childBlockSet.getWall()).orElse(parentBlockSet.getWall()),
        ofNullable(childBlockSet.getStair()).orElse(parentBlockSet.getStair()),
        ofNullable(childBlockSet.getPillar()).orElse(parentBlockSet.getPillar()),
        ofNullable(childBlockSet.getDoor()).orElse(parentBlockSet.getDoor()),
        ofNullable(childBlockSet.getLightBlock()).orElse(parentBlockSet.getLightBlock()),
        ofNullable(childBlockSet.getLiquid()).orElse(parentBlockSet.getLiquid()));
  }

  public static Theme randomTheme() {
    return values()[random.nextInt(values().length)];
  }

}
