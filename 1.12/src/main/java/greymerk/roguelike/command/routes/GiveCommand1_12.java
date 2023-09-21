package greymerk.roguelike.command.routes;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.List;

import greymerk.roguelike.command.CommandBase;
import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.util.ArgumentParser;

public class GiveCommand1_12 extends CommandRouteBase {

  public GiveCommand1_12(CommandBase commandBase) {
    super(commandBase);
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      context.sendInfo("notif.roguelike.usage_", "roguelike give novelty_name");
      return;
    }

    String name = ap.get(0);
    RldItemStack item = ItemNovelty.getItemByName(name);
    if (item == null) {
      context.sendFailure("nosuchitem");
      return;
    }

    context.give(item);
    context.sendSuccess("given", name);
  }
}
