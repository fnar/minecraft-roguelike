package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.worldgen.Coord;

public class CitadelCommand extends DungeonCommand {

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    try {
      Coord pos = getLocation(commandContext, args);
      Citadel.generate(commandContext.createEditor(), pos.getX(), pos.getZ());
    } catch (Exception ignored) {
    }
  }
}
