package greymerk.roguelike.command.routes;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import greymerk.roguelike.command.CommandBase;
import greymerk.roguelike.command.CommandContext1_12;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.EnumTools;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static greymerk.roguelike.dungeon.Dungeon.TOPLEVEL;

public class CommandRouteTower extends CommandRouteBase {

  public CommandRouteTower(CommandBase commandBase) {
    super(commandBase);
  }

  @Override
  public void execute(CommandContext1_12 context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      List<String> towers = EnumTools.valuesToStrings(TowerType.class)
          .stream()
          .map(String::toLowerCase)
          .collect(Collectors.toList());
      context.sendInfo("Tower types: " + StringUtils.join(towers, " "));

      return;
    }
    String towerName = ap.get(0);
    TowerType type;
    try {
      type = TowerType.get(towerName.toUpperCase());
    } catch (Exception e) {
      context.sendFailure("No such tower type: " + towerName);
      return;
    }

    Coord here = new Coord(context.getPos().getX(), TOPLEVEL, context.getPos().getZ());
    Tower tower = TowerType.get(type);

    WorldEditor editor = context.createEditor();
    tower.generate(editor, editor.getRandom(), TowerType.getDefaultTheme(type).getThemeBase(), here);
    context.sendSuccess(towerName + " Tower generated at " + here.toString());
  }

  @Override
  public List<String> getTabCompletion(List<String> args) {
    List<String> towers = EnumTools.valuesToStrings(TowerType.class)
        .stream()
        .map(String::toLowerCase)
        .collect(Collectors.toList());

    if (args.size() > 0) {
      return this.getListTabOptions(args.get(0).toLowerCase(), towers);
    }

    return towers;
  }

}
