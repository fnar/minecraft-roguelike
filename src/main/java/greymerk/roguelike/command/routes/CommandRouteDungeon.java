package greymerk.roguelike.command.routes;

import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;

import java.util.List;
import java.util.Optional;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.command.routes.exception.NoValidLocationException;
import greymerk.roguelike.command.routes.exception.SettingNameNotFoundException;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingsRandom;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandRouteDungeon extends CommandRouteBase {

  public static Coord getLocation(CommandContext context, List<String> args) throws NumberInvalidException {
    ArgumentParser argumentParser = new ArgumentParser(args);
    if (argumentParser.match(0, "here") || argumentParser.match(0, "nearby")) {
      Coord coord = context.getPos();
      return new Coord(coord.getX(), 0, coord.getZ());
    }
    try {
      int x = CommandBase.parseInt(argumentParser.get(0));
      int z = CommandBase.parseInt(argumentParser.get(1));
      return new Coord(x, 0, z);
    } catch (NumberInvalidException e) {
      context.sendFailure("Invalid Coords: X Z");
      throw (e);
    }
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    ArgumentParser argumentParser = new ArgumentParser(args);
    if (!argumentParser.hasEntry(0)) {
      context.sendInfo("Usage: roguelike dungeon {X Z | here} [setting]");
      return;
    }
    String settingName = getSettingName(argumentParser);

    try {
      Coord pos = getLocation(context, args);
      WorldEditor editor = context.createEditor();
      DungeonSettings dungeonSettings = chooseDungeonSettings(settingName, pos, editor);
      generateDungeon(context, pos, editor, dungeonSettings);
    } catch (Exception e) {
      context.sendFailure(e);
    }
  }

  private DungeonSettings chooseDungeonSettings(String settingName, Coord pos, WorldEditor editor) throws Exception {
    if (settingName == null) {
      return resolveAnyCustomDungeonSettings(pos, editor);
    } else if (settingName.equals("random")) {
      return resolveRandomDungeon(editor);
    } else {
      return resolveNamedDungeonSettings(settingName);
    }
  }

  private DungeonSettings resolveAnyCustomDungeonSettings(Coord pos, WorldEditor editor) throws Exception {
    SettingsResolver settingsResolver = SettingsResolver.initSettingsResolver();
    DungeonSettings dungeonSettings = settingsResolver.getAnyCustomDungeonSettings(editor, pos);
    return Optional.ofNullable(dungeonSettings)
        .orElseThrow(() -> new NoValidLocationException(pos));
  }

  private DungeonSettings resolveRandomDungeon(WorldEditor editor) throws Exception {
    Dungeon.initResolver();
    return new SettingsRandom(editor.getRandom());
  }

  private DungeonSettings resolveNamedDungeonSettings(String settingName) throws Exception {
    Dungeon.initResolver();
    DungeonSettings dungeonSettings = SettingsResolver.initSettingsResolver().getByName(settingName);
    return Optional.ofNullable(dungeonSettings)
        .orElseThrow(() -> new SettingNameNotFoundException(settingName));
  }

  private void generateDungeon(CommandContext context, Coord coord, WorldEditor editor, DungeonSettings dungeonSettings) {
    Dungeon dungeon = new Dungeon(editor);
    dungeon.generate(dungeonSettings, coord);
    context.sendSuccess("Dungeon generated at " + coord.toString());
  }

  private String getSettingName(ArgumentParser argumentParser) {
    int index = argumentParser.match(0, "here")
        || argumentParser.match(0, "nearby")
        ? 1
        : 2;
    return argumentParser.get(index);
  }
}
