package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.ListSettingsCommand;
import com.github.fnar.roguelike.command.commands.ReloadSettingsCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.util.ArgumentParser;

public class SettingsCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    ArgumentParser argumentParser = new ArgumentParser(args);

    if (!argumentParser.hasEntry(0)) {
      ListSettingsCommand.sendUsage(commandContext);
      return;
    }

    if (argumentParser.match(0, "reload")) {
      new ReloadSettingsCommand(commandContext).run();
    }

    if (argumentParser.match(0, "list")) {
      String namespace = argumentParser.hasEntry(1) ? argumentParser.get(1) : "";
      new ListSettingsCommand(commandContext, namespace).run();
    }
  }

}
