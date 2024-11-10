package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.GenerateCitadelCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.worldgen.Coord;

public class CitadelCommand1_12 extends BaseCommandRoute {

  private static final int coordArgumentIndex = 1;

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    Coord coord = commandContext.parseNearbyOrXZCoord(coordArgumentIndex);
    new GenerateCitadelCommand(commandContext, coord).run();
  }

}
