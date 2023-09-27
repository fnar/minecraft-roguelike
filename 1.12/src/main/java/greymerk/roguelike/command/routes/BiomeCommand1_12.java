package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.BiomeCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.worldgen.Coord;

public class BiomeCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext context, List<String> args) {
    Coord position = context.getArgumentAsCoord(1).orElse(context.getSenderCoord());
    new BiomeCommand(context, position).run();
  }

}
