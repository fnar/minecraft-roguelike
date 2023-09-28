package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.GenerateTowerCommand;

import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.dungeon.towers.TowerType;

public class TowerCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext context, List<String> args) {
    String towerName = context.getArgument(1).orElse(null);
    new GenerateTowerCommand(context, towerName).run();
  }

  @Override
  public List<String> getTabCompletion(List<String> args) {
    List<String> towers = TowerType.getTowerList();
    return args.isEmpty()
        ? towers
        : this.getListTabOptions(args.get(0).toLowerCase(), towers);
  }

}
