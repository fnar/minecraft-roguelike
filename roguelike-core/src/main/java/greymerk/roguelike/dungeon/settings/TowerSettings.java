package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.ThemeBase;
import com.github.fnar.roguelike.theme.ThemeBases;
import greymerk.roguelike.theme.ThemeParser;

public class TowerSettings {

  private final Tower type;
  private final ThemeBase theme;

  public TowerSettings(Tower type, Theme theme) {
    this.type = type;
    this.theme = theme.getThemeBase();
  }

  public TowerSettings(JsonElement e) throws Exception {

    JsonObject data = e.getAsJsonObject();

    type = data.has("type") ? Tower.get(data.get("type").getAsString()) : null;
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

  public Tower getType() {
    if (type == null) {
      return Tower.ROGUE;
    }

    return type;
  }

  public ThemeBase getTheme() {
    if (theme == null) {
      return ThemeBases.TOWER;
    }

    return theme;
  }


}
