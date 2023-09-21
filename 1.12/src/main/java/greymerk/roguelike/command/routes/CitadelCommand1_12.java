package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.GenerateCitadelCommand;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.worldgen.Coord;

public class CitadelCommand1_12 extends DungeonCommand1_12 {

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    Coord coord = parseCoord(commandContext, args);
    new GenerateCitadelCommand(commandContext, coord).run();
  }

}
