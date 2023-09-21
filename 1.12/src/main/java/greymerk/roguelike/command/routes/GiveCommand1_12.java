package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.GiveCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.util.ArgumentParser;

public class GiveCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      context.sendInfo("notif.roguelike.usage_", "roguelike give novelty_name");
      return;
    }

    String itemName = ap.get(0);
    new GiveCommand(context, itemName).run();
  }

}
