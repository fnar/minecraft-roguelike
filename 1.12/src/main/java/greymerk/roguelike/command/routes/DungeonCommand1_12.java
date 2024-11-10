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
    ArgumentParser argumentParser = new ArgumentParser(args);
    if (!argumentParser.hasEntry(0)) {
      commandContext.sendInfo("notif.roguelike.usage_", "/roguelike dungeon {X Z | here | nearby} [setting]");
      return;
    }
    Coord coord = argumentParser.parseNearbyOrXZCoord(commandContext);
    boolean isNearby = argumentParser.isNearby();
    String settingName = argumentParser.get(isNearby ? 1 : 2);
    new DungeonCommand(commandContext, coord, settingName).run();
  }

}
