package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.ReloadConfigCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;

public class ConfigCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext context, List<String> args) {
    new ReloadConfigCommand(context).run();
  }

}
