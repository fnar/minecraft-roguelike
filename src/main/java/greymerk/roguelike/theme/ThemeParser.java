package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class ThemeParser {

  public static final String THEME_BASE_KEY = "base";
  public static final String PRIMARY_KEY = "primary";
  public static final String SECONDARY_KEY = "secondary";

  public static ITheme parse(JsonObject json) throws Exception {
    ThemeBase themeBase = json.has(THEME_BASE_KEY)
        ? get(json.get(THEME_BASE_KEY).getAsString()).getThemeBase()
        : null;

    IBlockSet primaryBlockSet = ofNullable(parsePrimaryBlockSet(json, themeBase))
        .orElse(ofNullable(themeBase).map(ThemeBase::getPrimary)
            .orElse(null));


    IBlockSet secondaryBlockSet = ofNullable(parseSecondaryBlockSet(json, themeBase))
        .orElse(ofNullable(themeBase).map(ThemeBase::getSecondary)
            .orElse(null));

    return new ThemeBase(primaryBlockSet, secondaryBlockSet);
  }

  private static IBlockSet parsePrimaryBlockSet(JsonObject json, ThemeBase base) throws Exception {
    return parseBlockSet(json, base, PRIMARY_KEY, ITheme::getPrimary);
  }

  private static IBlockSet parseSecondaryBlockSet(JsonObject json, ThemeBase base) throws Exception {
    return parseBlockSet(json, base, SECONDARY_KEY, ITheme::getSecondary);
  }

  private static IBlockSet parseBlockSet(JsonObject json, ITheme base, String key, Function<ITheme, IBlockSet> getBlockSetFunction) throws Exception {
    if (!json.has(key)) {
      return null;
    } else {
      JsonObject data = json.get(key).getAsJsonObject();
      Optional<IBlockSet> baseBlockSet = ofNullable(base).map(getBlockSetFunction);
      return BlockSetParser.parseBlockSet(data, baseBlockSet);
    }
  }

  public static Theme get(String name) throws Exception {
    if (!contains(name.toUpperCase())) {
      throw new Exception("No such theme: " + name);
    }
    return Theme.valueOf(name.toUpperCase());
  }

  public static boolean contains(String name) {
    return Arrays.stream(Theme.values())
        .anyMatch(value -> value.toString().equals(name));
  }
}
