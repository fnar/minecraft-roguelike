package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.GiveCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;

public class GiveCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    String itemName = commandContext.getArgument(1).orElse(null);
    new GiveCommand(commandContext, itemName).run();
  }

}
