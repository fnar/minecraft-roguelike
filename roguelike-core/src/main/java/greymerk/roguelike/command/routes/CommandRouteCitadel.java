package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.command.CommandBase;
import greymerk.roguelike.command.CommandContext1_12;
import greymerk.roguelike.worldgen.Coord;

public class CommandRouteCitadel extends CommandRouteDungeon {

  public CommandRouteCitadel(CommandBase commandBase) {
    super(commandBase);
  }

  @Override
  public void execute(CommandContext1_12 context, List<String> args) {
    try {
      Coord pos = getLocation(context, args);
      Citadel.generate(context.createEditor(), pos.getX(), pos.getZ());
    } catch (Exception ignored) {
    }
  }
}
