package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;

public class RoguelikeCommand1_12 extends CommandRouteBase {

  public RoguelikeCommand1_12() {
    this.addRoute("dungeon", new DungeonCommand());
    this.addRoute("give", new GiveCommand1_12());
    this.addRoute("config", new ConfigCommand());
    this.addRoute("settings", new SettingsCommand());
    this.addRoute("tower", new TowerCommand());
    this.addRoute("biome", new BiomeCommand1_12());
    this.addRoute("citadel", new CitadelCommand());
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    if (args.isEmpty()) {
      //context.sendInfo("Usage: roguelike [dungeon | give | config | settings | tower]");
      context.sendInfo("notif.roguelike.usage_", "roguelike [dungeon | give | config | settings | tower]");
    }
    super.execute(context, args);
  }
}
