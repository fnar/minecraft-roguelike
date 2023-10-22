package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.ThemeParser;
import greymerk.roguelike.theme.builtin.ThemeTower;

public class TowerSettings {

  private final TowerType type;
  private final Theme theme;

  public TowerSettings(TowerType type, Theme.Type theme) {
    this.type = type;
    this.theme = theme.asTheme();
  }

  public TowerSettings(JsonElement e) throws Exception {

    JsonObject data = e.getAsJsonObject();

    type = data.has("type") ? TowerType.get(data.get("type").getAsString()).orElse(null) : null;
    try {
      theme = data.has("theme") ? ThemeParser.parse(data.get("theme").getAsJsonObject()) : null;
    } catch (DungeonSettingParseException exception) {
      throw new DungeonSettingParseException("While parsing tower: " + exception.getMessage());
    }
  }

  public TowerSettings(TowerSettings base, TowerSettings override) {
    if (base == null) {
      type = override.type;
      theme = override.theme;
      return;
    }

    if (override == null) {
      type = base.type;
      theme = base.theme;
      return;
    }

    type = override.type == null ? base.type : override.type;
    theme = override.theme == null ? base.theme : override.theme;

  }

  public TowerSettings(TowerSettings toCopy) {
    type = toCopy.type;
    theme = toCopy.theme;
  }

  public TowerType getType() {
    if (type == null) {
      return TowerType.ROGUE;
    }

    return type;
  }

  public Theme getTheme() {
    if (theme == null) {
      return new ThemeTower();
    }

    return theme;
  }


}
