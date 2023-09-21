package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;

public class RoguelikeCommand1_12 extends BaseCommandRoute {

  public RoguelikeCommand1_12() {
    this.addRoute("dungeon", new DungeonCommand1_12());
    this.addRoute("give", new GiveCommand1_12());
    this.addRoute("config", new ConfigCommand1_12());
    this.addRoute("settings", new SettingsCommand1_12());
    this.addRoute("tower", new TowerCommand1_12());
    this.addRoute("biome", new BiomeCommand1_12());
    this.addRoute("citadel", new CitadelCommand1_12());
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
