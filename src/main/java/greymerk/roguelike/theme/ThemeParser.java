package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.function.Function;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

import static java.util.Optional.ofNullable;

public class ThemeParser {

  public static final String THEME_BASE_KEY = "base";
  public static final String PRIMARY_KEY = "primary";
  public static final String SECONDARY_KEY = "secondary";

  public static ITheme parse(JsonObject json) throws DungeonSettingParseException {
    ThemeBase themeBase = json.has(THEME_BASE_KEY)
        ? get(json.get(THEME_BASE_KEY).getAsString()).getThemeBase()
        : null;

    BlockSet primaryBlockSet = ofNullable(parsePrimaryBlockSet(json, themeBase))
        .orElse(ofNullable(themeBase).map(ThemeBase::getPrimary)
            .orElse(null));


    BlockSet secondaryBlockSet = ofNullable(parseSecondaryBlockSet(json, themeBase))
        .orElse(ofNullable(themeBase).map(ThemeBase::getSecondary)
            .orElse(null));

    return new ThemeBase(primaryBlockSet, secondaryBlockSet);
  }

  private static BlockSet parsePrimaryBlockSet(JsonObject json, ThemeBase base) throws DungeonSettingParseException {
    return parseBlockSet(json, base, PRIMARY_KEY, ITheme::getPrimary);
  }

  private static BlockSet parseSecondaryBlockSet(JsonObject json, ThemeBase base) throws DungeonSettingParseException {
    return parseBlockSet(json, base, SECONDARY_KEY, ITheme::getSecondary);
  }

  private static BlockSet parseBlockSet(JsonObject json, ITheme baseTheme, String key, Function<ITheme, BlockSet> getBlockSetFunction) throws DungeonSettingParseException {
    if (!json.has(key)) {
      return null;
    } else {
      JsonObject data = json.get(key).getAsJsonObject();
      BlockSet baseBlockSet = ofNullable(baseTheme).map(getBlockSetFunction).orElse(getEmptyBlockSet());
      return BlockSetParser.parseBlockSet(data, baseBlockSet);
    }
  }

  private static BlockSet getEmptyBlockSet() {
    return new BlockSet(
        null,
        null,
        null,
        null,
        null,
        null,
        null);
  }

  public static Theme get(String name) throws DungeonSettingParseException {
    if (!contains(name.toUpperCase())) {
      throw new DungeonSettingParseException("No such theme: " + name);
    }
    return Theme.valueOf(name.toUpperCase());
  }

  public static boolean contains(String name) {
    return Arrays.stream(Theme.values())
        .anyMatch(value -> value.toString().equals(name));
  }
}
