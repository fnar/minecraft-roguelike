package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandBase1_12;
import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;

public class CommandRouteRoguelike1_12 extends CommandRouteBase {

  public CommandRouteRoguelike1_12() {
    super(new CommandBase1_12());
    this.addRoute("dungeon", new CommandRouteDungeon(commandBase));
    this.addRoute("give", new CommandRouteGive1_12(commandBase));
    this.addRoute("config", new CommandRouteConfig(commandBase));
    this.addRoute("settings", new CommandRouteSettings(commandBase));
    this.addRoute("tower", new CommandRouteTower(commandBase));
    this.addRoute("biome", new CommandRouteBiome1_12(commandBase));
    this.addRoute("citadel", new CommandRouteCitadel(commandBase));
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    if (args.size() == 0) {
      //context.sendInfo("Usage: roguelike [dungeon | give | config | settings | tower]");
      context.sendInfo("notif.roguelike.usage_", "roguelike [dungeon | give | config | settings | tower]");
    }

    super.execute(context, args);
  }
}
