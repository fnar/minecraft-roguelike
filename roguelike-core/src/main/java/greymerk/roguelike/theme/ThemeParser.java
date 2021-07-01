package greymerk.roguelike.theme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.function.Function;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

import static java.util.Optional.ofNullable;

public class ThemeParser {

  public static final String THEME_BASE_KEY = "base";
  public static final String PRIMARY_KEY = "primary";
  public static final String SECONDARY_KEY = "secondary";

  public static ThemeBase parse(JsonObject json) throws DungeonSettingParseException {
    ThemeBase themeBase = parseThemeBase(json);

    BlockSet primaryBlockSet = ofNullable(parsePrimaryBlockSet(json, themeBase))
        .orElse(ofNullable(themeBase).map(ThemeBase::getPrimary)
            .orElse(null));


    BlockSet secondaryBlockSet = ofNullable(parseSecondaryBlockSet(json, themeBase))
        .orElse(ofNullable(themeBase).map(ThemeBase::getSecondary)
            .orElse(null));

    return new ThemeBase(primaryBlockSet, secondaryBlockSet);
  }

  private static ThemeBase parseThemeBase(JsonObject json) {
    if (!json.has(THEME_BASE_KEY)) {
      return Theme.OAK.getThemeBase();
    }

    JsonElement baseElement = json.get(THEME_BASE_KEY);
    if (baseElement.isJsonNull()) {
      return Theme.OAK.getThemeBase();
    }

    String baseString = baseElement.getAsString();
    if (baseString.isEmpty()) {
      return Theme.OAK.getThemeBase();
    }

    return get(baseString).getThemeBase();
  }

  private static BlockSet parsePrimaryBlockSet(JsonObject json, ThemeBase base) throws DungeonSettingParseException {
    return parseBlockSet(json, base, PRIMARY_KEY, ThemeBase::getPrimary);
  }

  private static BlockSet parseSecondaryBlockSet(JsonObject json, ThemeBase base) throws DungeonSettingParseException {
    return parseBlockSet(json, base, SECONDARY_KEY, ThemeBase::getSecondary);
  }

  private static BlockSet parseBlockSet(JsonObject json, ThemeBase baseTheme, String key, Function<ThemeBase, BlockSet> getBlockSetFunction) throws DungeonSettingParseException {
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
