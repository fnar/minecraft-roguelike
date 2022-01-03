package greymerk.roguelike.dungeon.settings;

public class DungeonSettingParseException extends RuntimeException {

  public DungeonSettingParseException(String message) {
    super(message + " Check the wiki at https://www.github.com/fnar/minecraft-roguelike/wiki/.");
  }
}
