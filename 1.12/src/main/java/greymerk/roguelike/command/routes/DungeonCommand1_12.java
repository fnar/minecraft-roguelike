package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.DungeonCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;

public class DungeonCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    try {
      ArgumentParser argumentParser = new ArgumentParser(args);
      if (!argumentParser.hasEntry(0)) {
        commandContext.sendInfo("notif.roguelike.usage_", "roguelike dungeon {X Z | here} [setting]");
        return;
      }
      String settingName = parseSettingName(argumentParser);
      Coord coord = parseCoord(commandContext, args);
      new DungeonCommand(commandContext, coord, settingName).run();
    } catch (Exception e) {
      commandContext.sendFailure(e);
    }
  }

  private String parseSettingName(ArgumentParser argumentParser) {
    boolean isNearby = isNearby(argumentParser);
    return argumentParser.get(isNearby ? 1 : 2);
  }

  private static boolean isNearby(ArgumentParser argumentParser) {
    return argumentParser.match(0, "here")
        || argumentParser.match(0, "nearby");
  }

  protected Coord parseCoord(CommandContext context, List<String> args) {
    ArgumentParser argumentParser = new ArgumentParser(args);
    if (isNearby(argumentParser)) {
      return context.getPos().setY(0);
    }
    try {
      return argumentParser.getXZCoord(0);
    } catch (IllegalArgumentException e) {
      context.sendFailure("invalidcoords", "X Z");
      throw (e);
    }
  }

}
