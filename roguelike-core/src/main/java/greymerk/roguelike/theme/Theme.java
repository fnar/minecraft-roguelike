package greymerk.roguelike.theme;

import com.github.fnar.roguelike.theme.NetherFortressTheme;

import java.util.Random;

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
  NETHER_FORTRESS(new NetherFortressTheme()),
  OAK(ThemeBases.OAK),
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

  public static Theme randomTheme() {
    return values()[random.nextInt(values().length)];
  }

}
