package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandBase1_12;
import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;

public class RoguelikeCommand1_12 extends CommandRouteBase {

  public RoguelikeCommand1_12() {
    super(new CommandBase1_12());
    this.addRoute("dungeon", new DungeonCommand(commandBase));
    this.addRoute("give", new GiveCommand1_12(commandBase));
    this.addRoute("config", new ConfigCommand(commandBase));
    this.addRoute("settings", new SettingsCommand(commandBase));
    this.addRoute("tower", new TowerCommand(commandBase));
    this.addRoute("biome", new BiomeCommand1_12(commandBase));
    this.addRoute("citadel", new CitadelCommand(commandBase));
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
