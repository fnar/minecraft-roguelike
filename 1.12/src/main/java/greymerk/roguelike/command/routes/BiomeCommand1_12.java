package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.BiomeAtLocationCommand;
import com.github.fnar.roguelike.command.commands.BiomeAtPlayerCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.util.ArgumentParser;

public class BiomeCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext context, List<String> args) {
    if (!new ArgumentParser(args).hasEntry(0)) {
      new BiomeAtPlayerCommand(context).run();
    } else {
      new BiomeAtLocationCommand(context).run();
    }
  }

}
