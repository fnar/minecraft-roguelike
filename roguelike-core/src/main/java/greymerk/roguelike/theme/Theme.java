package greymerk.roguelike.theme;

import com.github.fnar.roguelike.theme.NetherFortressTheme;

import java.util.Optional;
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
import lombok.ToString;

@ToString
public class Theme {

  public static final ThemeBling BLING = new ThemeBling();
  public static final ThemeBrick BRICK = new ThemeBrick();
  public static final ThemeBumbo BUMBO = new ThemeBumbo();
  public static final ThemeCave CAVE = new ThemeCave();
  public static final ThemeChecker CHECKER = new ThemeChecker();
  public static final ThemeCrypt CRYPT = new ThemeCrypt();
  public static final ThemeDarkHall DARKHALL = new ThemeDarkHall();
  public static final ThemeDarkOak DARKOAK = new ThemeDarkOak();
  public static final ThemeEnder ENDER = new ThemeEnder();
  public static final ThemeEniIce ENIICE = new ThemeEniIce();
  public static final ThemeEniko ENIKO = new ThemeEniko();
  public static final ThemeEniko2 ENIKO2 = new ThemeEniko2();
  public static final ThemeEniQuartz ENIQUARTZ = new ThemeEniQuartz();
  public static final ThemeEtho ETHO = new ThemeEtho();
  public static final ThemeEthoTower ETHOTOWER = new ThemeEthoTower();
  public static final ThemeGrey GREY = new ThemeGrey();
  public static final ThemeHell HELL = new ThemeHell();
  public static final ThemeHouse HOUSE = new ThemeHouse();
  public static final ThemeIce ICE = new ThemeIce();
  public static final ThemeJungle JUNGLE = new ThemeJungle();
  public static final ThemeMineShaft MINESHAFT = new ThemeMineShaft();
  public static final ThemeMossy MOSSY = new ThemeMossy();
  public static final ThemeMuddy MUDDY = new ThemeMuddy();
  public static final ThemeNether NETHER = new ThemeNether();
  public static final NetherFortressTheme NETHER_FORTRESS = new NetherFortressTheme();
  public static final ThemeOak OAK = new ThemeOak();
  public static final ThemePurpur PURPUR = new ThemePurpur();
  public static final ThemePyramid PYRAMID = new ThemePyramid();
  public static final ThemeQuartz QUARTZ = new ThemeQuartz();
  public static final ThemeRainbow RAINBOW = new ThemeRainbow();
  public static final ThemeSandstone SANDSTONE = new ThemeSandstone();
  public static final ThemeSandstoneRed SANDSTONERED = new ThemeSandstoneRed();
  public static final ThemeSewer SEWER = new ThemeSewer();
  public static final ThemeSnow SNOW = new ThemeSnow();
  public static final ThemeSpruce SPRUCE = new ThemeSpruce();
  public static final ThemeStone STONE = new ThemeStone();
  public static final ThemeTemple TEMPLE = new ThemeTemple();
  public static final ThemeTerracotta TERRACOTTA = new ThemeTerracotta();
  public static final ThemeTower TOWER = new ThemeTower();


  protected BlockSet primary;
  protected BlockSet secondary;

  public Theme() {
  }

  public Theme(BlockSet primary, BlockSet secondary) {
    this.primary = primary;
    this.secondary = secondary;
  }

  public static Theme inherit(Theme parent, Theme child) {
    return new Theme(
        BlockSet.inherit(parent.getPrimary(), child.getPrimary()),
        BlockSet.inherit(parent.getSecondary(), child.getSecondary()));
  }

  public BlockSet getPrimary() {
    return primary;
  }

  public BlockSet getSecondary() {
    return Optional.ofNullable(secondary).orElse(getPrimary());
  }

  public static Theme random(Random random) {
    return Theme.Type.random(random).asTheme();
  }

  public enum Type {

    BLING(Theme.BLING),
    BRICK(Theme.BRICK),
    BUMBO(Theme.BUMBO),
    CAVE(Theme.CAVE),
    CHECKER(Theme.CHECKER),
    CRYPT(Theme.CRYPT),
    DARKHALL(Theme.DARKHALL),
    DARKOAK(Theme.DARKOAK),
    ENDER(Theme.ENDER),
    ENIICE(Theme.ENIICE),
    ENIKO(Theme.ENIKO),
    ENIKO2(Theme.ENIKO2),
    ENIQUARTZ(Theme.ENIQUARTZ),
    ETHO(Theme.ETHO),
    ETHOTOWER(Theme.ETHOTOWER),
    GREY(Theme.GREY),
    HELL(Theme.HELL),
    HOUSE(Theme.HOUSE),
    ICE(Theme.ICE),
    JUNGLE(Theme.JUNGLE),
    MINESHAFT(Theme.MINESHAFT),
    MOSSY(Theme.MOSSY),
    MUDDY(Theme.MUDDY),
    NETHER(Theme.NETHER),
    NETHER_FORTRESS(Theme.NETHER_FORTRESS),
    OAK(Theme.OAK),
    PURPUR(Theme.PURPUR),
    PYRAMID(Theme.PYRAMID),
    QUARTZ(Theme.QUARTZ),
    RAINBOW(Theme.RAINBOW),
    SANDSTONE(Theme.SANDSTONE),
    SANDSTONERED(Theme.SANDSTONERED),
    SEWER(Theme.SEWER),
    SNOW(Theme.SNOW),
    SPRUCE(Theme.SPRUCE),
    STONE(Theme.STONE),
    TEMPLE(Theme.TEMPLE),
    TERRACOTTA(Theme.TERRACOTTA),
    TOWER(Theme.TOWER);

    private final Theme theme;

    Type(Theme theme) {
      this.theme = theme;
    }

    public Theme asTheme() {
      return theme;
    }

    private static Type random(Random random) {
      Type[] values = values();
      return values[random.nextInt(values.length)];
    }

  }
}
