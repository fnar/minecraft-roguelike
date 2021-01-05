package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.ArgumentParser;

public class CommandRouteConfig extends CommandRouteBase {

  @Override
  public void execute(CommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      context.sendInfo("Usage: roguelike config reload");
    } else if (ap.match(0, "reload")) {
      RogueConfig.reload(true);
      context.sendSuccess("Configurations Reloaded");
    }
  }

}
