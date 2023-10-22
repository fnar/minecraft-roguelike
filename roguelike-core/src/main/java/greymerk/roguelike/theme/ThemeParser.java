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

  public static Theme parse(JsonObject json) throws DungeonSettingParseException {
    Theme theme = parseThemeBase(json);

    BlockSet primaryBlockSet = ofNullable(parsePrimaryBlockSet(json, theme))
        .orElse(ofNullable(theme).map(Theme::getPrimary)
            .orElse(null));


    BlockSet secondaryBlockSet = ofNullable(parseSecondaryBlockSet(json, theme))
        .orElse(ofNullable(theme).map(Theme::getSecondary)
            .orElse(null));

    return new Theme(primaryBlockSet, secondaryBlockSet);
  }

  private static Theme parseThemeBase(JsonObject json) {
    if (!json.has(THEME_BASE_KEY)) {
      return Theme.Type.OAK.getThemeBase();
    }

    JsonElement baseElement = json.get(THEME_BASE_KEY);
    if (baseElement.isJsonNull()) {
      return Theme.Type.OAK.getThemeBase();
    }

    String baseString = baseElement.getAsString();
    if (baseString.isEmpty()) {
      return Theme.Type.OAK.getThemeBase();
    }

    return get(baseString).getThemeBase();
  }

  private static BlockSet parsePrimaryBlockSet(JsonObject json, Theme base) throws DungeonSettingParseException {
    return parseBlockSet(json, base, PRIMARY_KEY, Theme::getPrimary);
  }

  private static BlockSet parseSecondaryBlockSet(JsonObject json, Theme base) throws DungeonSettingParseException {
    return parseBlockSet(json, base, SECONDARY_KEY, Theme::getSecondary);
  }

  private static BlockSet parseBlockSet(JsonObject json, Theme baseTheme, String key, Function<Theme, BlockSet> getBlockSetFunction) throws DungeonSettingParseException {
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

  public static Theme.Type get(String name) throws DungeonSettingParseException {
    if (!contains(name.toUpperCase())) {
      throw new DungeonSettingParseException("No such theme: " + name);
    }
    return Theme.Type.valueOf(name.toUpperCase());
  }

  public static boolean contains(String name) {
    return Arrays.stream(Theme.Type.values())
        .anyMatch(value -> value.toString().equals(name));
  }
}
