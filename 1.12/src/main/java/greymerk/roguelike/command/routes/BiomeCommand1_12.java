package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.BiomeAtLocationCommand;
import com.github.fnar.roguelike.command.BiomeAtPlayerCommand;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.util.ArgumentParser;

public class BiomeCommand1_12 extends CommandRouteBase {

  public BiomeCommand1_12(greymerk.roguelike.command.CommandBase commandBase) {
    super(commandBase);
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    if (!new ArgumentParser(args).hasEntry(0)) {
      new BiomeAtPlayerCommand(context).run();
    } else {
      new BiomeAtLocationCommand(context).run();
    }
  }

}
