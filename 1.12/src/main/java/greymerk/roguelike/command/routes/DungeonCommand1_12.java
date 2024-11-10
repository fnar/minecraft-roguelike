package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.DungeonCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.worldgen.Coord;

public class DungeonCommand1_12 extends BaseCommandRoute {

  private static final int coordArgumentIndex = 1;

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    if (commandContext.getArgument(coordArgumentIndex).isPresent()) {
      generateDungeon(commandContext);
    } else {
      sendUsage(commandContext);
    }
  }

  private static void generateDungeon(CommandContext commandContext) {
    Coord coord = commandContext.parseNearbyOrXZCoord(coordArgumentIndex);
    String settingName = getSettingName(commandContext);
    new DungeonCommand(commandContext, coord, settingName).run();
  }

  private static void sendUsage(CommandContext commandContext) {
    commandContext.sendInfo("notif.roguelike.usage_", "/roguelike dungeon {X Z | here | nearby} [setting]");
  }

  private static String getSettingName(CommandContext commandContext) {
    return commandContext
        .getArgument(getSettingNameArgumentIndex(commandContext))
        .orElse(null);
  }

  private static int getSettingNameArgumentIndex(CommandContext commandContext) {
    return commandContext.isNearby(coordArgumentIndex) ? 2 : 3;
  }

}
