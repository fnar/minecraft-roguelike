package greymerk.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.GenerateTowerCommand;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.EnumTools;
import greymerk.roguelike.worldgen.Coord;

import static greymerk.roguelike.dungeon.Dungeon.TOPLEVEL;

public class TowerCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext context, List<String> args) {
    ArgumentParser argumentParser = new ArgumentParser(args);

    if (!argumentParser.hasEntry(0)) {
      List<String> towers = EnumTools.valuesToStrings(TowerType.class)
          .stream()
          .map(String::toLowerCase)
          .collect(Collectors.toList());
      context.sendInfo("notif.roguelike.towertypes", StringUtils.join(towers, " "));

      return;
    }
    String towerName = argumentParser.get(0);
    Optional<TowerType> towerType = getTowerType(context, towerName);
    if (!towerType.isPresent()) {
      return;
    }
    TowerType type = towerType.get();

    Coord coord = context.getPos().setY(TOPLEVEL);

    new GenerateTowerCommand(context, type, coord).run();
    //context.sendSuccess(towerName + " Tower generated at " + here);
    context.sendSuccess(towerName + "_generated", coord.toString());
  }

  private static Optional<TowerType> getTowerType(CommandContext context, String towerName) {
    try {
      return Optional.of(TowerType.get(towerName.toUpperCase()));
    } catch (Exception e) {
      context.sendFailure("nosuchtower", towerName);
    }
    return Optional.empty();
  }

  @Override
  public List<String> getTabCompletion(List<String> args) {
    List<String> towers = EnumTools.valuesToStrings(TowerType.class)
        .stream()
        .map(String::toLowerCase)
        .collect(Collectors.toList());

    if (!args.isEmpty()) {
      return this.getListTabOptions(args.get(0).toLowerCase(), towers);
    }

    return towers;
  }

}
