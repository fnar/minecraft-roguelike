package greymerk.roguelike.theme;

import com.github.fnar.roguelike.theme.ThemeBases;

import java.util.Random;

public enum Theme {

  BLING(ThemeBases.BLING),
  BRICK(ThemeBases.BRICK),
  BUMBO(ThemeBases.BUMBO),
  CAVE(ThemeBases.CAVE),
  CHECKER(ThemeBases.CHECKER),
  CRYPT(ThemeBases.CRYPT),
  DARKHALL(ThemeBases.DARK_HALL),
  DARKOAK(ThemeBases.DARK_OAK),
  ENDER(ThemeBases.ENDER),
  ENIICE(ThemeBases.ENI_ICE),
  ENIKO(ThemeBases.ENIKO),
  ENIKO2(ThemeBases.ENIKO2),
  ENIQUARTZ(ThemeBases.ENI_QUARTZ),
  ETHO(ThemeBases.ETHO),
  ETHOTOWER(ThemeBases.ETHO_TOWER),
  GREY(ThemeBases.GREY),
  HELL(ThemeBases.HELL),
  HOUSE(ThemeBases.HOUSE),
  ICE(ThemeBases.ICE),
  JUNGLE(ThemeBases.JUNGLE),
  MINESHAFT(ThemeBases.MINESHAFT),
  MOSSY(ThemeBases.MOSSY),
  MUDDY(ThemeBases.MUDDY),
  NETHER(ThemeBases.NETHER),
  NETHER_FORTRESS(ThemeBases.NETHER_FORTRESS),
  OAK(ThemeBases.OAK),
  PURPUR(ThemeBases.PURPUR),
  PYRAMID(ThemeBases.PYRAMID),
  QUARTZ(ThemeBases.QUARTZ),
  RAINBOW(ThemeBases.RAINBOW),
  SANDSTONE(ThemeBases.SANDSTONE),
  SANDSTONERED(ThemeBases.SANDSTONE_RED),
  SEWER(ThemeBases.SEWER),
  SNOW(ThemeBases.SNOW),
  SPRUCE(ThemeBases.SPRUCE),
  STONE(ThemeBases.STONE),
  TEMPLE(ThemeBases.TEMPLE),
  TERRACOTTA(ThemeBases.TERRACOTTA),
  TOWER(ThemeBases.TOWER);

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
