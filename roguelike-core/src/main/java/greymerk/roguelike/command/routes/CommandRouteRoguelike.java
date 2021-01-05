package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;

public class CommandRouteRoguelike extends CommandRouteBase {

  public CommandRouteRoguelike() {
    super();
    this.addRoute("dungeon", new CommandRouteDungeon());
    this.addRoute("give", new CommandRouteGive());
    this.addRoute("config", new CommandRouteConfig());
    this.addRoute("settings", new CommandRouteSettings());
    this.addRoute("tower", new CommandRouteTower());
    this.addRoute("biome", new CommandRouteBiome());
    this.addRoute("citadel", new CommandRouteCitadel());
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    if (args.size() == 0) {
      context.sendInfo("Usage: roguelike [dungeon | give | config | settings | tower]");
    }

    super.execute(context, args);
  }
}
