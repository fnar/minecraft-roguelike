package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.routes.RoomCommand1_12;

import java.util.List;
import java.util.stream.Collectors;

import greymerk.roguelike.command.BaseCommandRoute;

public class RoguelikeCommand1_12 extends BaseCommandRoute {

  public RoguelikeCommand1_12() {
    this.addRoute("biome", new BiomeCommand1_12());
    this.addRoute("citadel", new CitadelCommand1_12());
    this.addRoute("config", new ConfigCommand1_12());
    this.addRoute("dungeon", new DungeonCommand1_12());
    this.addRoute("give", new GiveCommand1_12());
    this.addRoute("room", new RoomCommand1_12());
    this.addRoute("rooms", new RoomCommand1_12());
    this.addRoute("settings", new SettingsCommand1_12());
    this.addRoute("tower", new TowerCommand1_12());
  }

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    if (args.isEmpty()) {
      commandContext.sendInfo("notif.roguelike.usage_", "/roguelike [" + subcommands() + "]");
    }
    super.execute(commandContext, args);
  }

  private String subcommands() {
    return getRoutes().stream()
        .sorted()
        .collect(Collectors.joining(" | "));
  }
}
