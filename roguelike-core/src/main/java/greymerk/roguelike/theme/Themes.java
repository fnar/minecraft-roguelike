package greymerk.roguelike.theme;

import com.github.fnar.roguelike.theme.NetherFortressTheme;

import java.util.Random;

import greymerk.roguelike.theme.builtin.ThemeBling;
import greymerk.roguelike.theme.builtin.ThemeBrick;
import greymerk.roguelike.theme.builtin.ThemeBumbo;
import greymerk.roguelike.theme.builtin.ThemeCave;
import greymerk.roguelike.theme.builtin.ThemeChecker;
import greymerk.roguelike.theme.builtin.ThemeCrypt;
import greymerk.roguelike.theme.builtin.ThemeDarkHall;
import greymerk.roguelike.theme.builtin.ThemeDarkOak;
import greymerk.roguelike.theme.builtin.ThemeEnder;
import greymerk.roguelike.theme.builtin.ThemeEniIce;
import greymerk.roguelike.theme.builtin.ThemeEniQuartz;
import greymerk.roguelike.theme.builtin.ThemeEniko;
import greymerk.roguelike.theme.builtin.ThemeEniko2;
import greymerk.roguelike.theme.builtin.ThemeEtho;
import greymerk.roguelike.theme.builtin.ThemeEthoTower;
import greymerk.roguelike.theme.builtin.ThemeGrey;
import greymerk.roguelike.theme.builtin.ThemeHell;
import greymerk.roguelike.theme.builtin.ThemeHouse;
import greymerk.roguelike.theme.builtin.ThemeIce;
import greymerk.roguelike.theme.builtin.ThemeJungle;
import greymerk.roguelike.theme.builtin.ThemeMineShaft;
import greymerk.roguelike.theme.builtin.ThemeMossy;
import greymerk.roguelike.theme.builtin.ThemeMuddy;
import greymerk.roguelike.theme.builtin.ThemeNether;
import greymerk.roguelike.theme.builtin.ThemeOak;
import greymerk.roguelike.theme.builtin.ThemePurpur;
import greymerk.roguelike.theme.builtin.ThemePyramid;
import greymerk.roguelike.theme.builtin.ThemeQuartz;
import greymerk.roguelike.theme.builtin.ThemeRainbow;
import greymerk.roguelike.theme.builtin.ThemeSandstone;
import greymerk.roguelike.theme.builtin.ThemeSandstoneRed;
import greymerk.roguelike.theme.builtin.ThemeSewer;
import greymerk.roguelike.theme.builtin.ThemeSnow;
import greymerk.roguelike.theme.builtin.ThemeSpruce;
import greymerk.roguelike.theme.builtin.ThemeStone;
import greymerk.roguelike.theme.builtin.ThemeTemple;
import greymerk.roguelike.theme.builtin.ThemeTerracotta;
import greymerk.roguelike.theme.builtin.ThemeTower;

public enum Themes {

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

  private final Theme theme;

  Themes(Theme theme) {
    this.theme = theme;
  }

  public Theme getThemeBase() {
    return theme;
  }

  public static Themes random(Random random) {
    Themes[] values = values();
    return values[random.nextInt(values.length)];
  }

}
