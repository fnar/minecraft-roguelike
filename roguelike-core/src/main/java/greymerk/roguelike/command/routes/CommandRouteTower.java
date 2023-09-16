package greymerk.roguelike.command.routes;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import greymerk.roguelike.command.CommandBase;
import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;
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
  public void execute(CommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      List<String> towers = EnumTools.valuesToStrings(TowerType.class)
          .stream()
          .map(String::toLowerCase)
          .collect(Collectors.toList());
      context.sendInfo("notif.roguelike.towertypes", StringUtils.join(towers, " "));

      return;
    }
    String towerName = ap.get(0);
    TowerType type;
    try {
      type = TowerType.get(towerName.toUpperCase());
    } catch (Exception e) {
      context.sendFailure("nosuchtower", towerName);
      return;
    }

    Coord here = new Coord(context.getPos().getX(), TOPLEVEL, context.getPos().getZ());

    WorldEditor worldEditor = context.createEditor();
    Theme theme = TowerType.getDefaultTheme(type).getThemeBase();
    Tower tower = TowerType.instantiate(type, worldEditor, theme);
    tower.generate(here);
    //context.sendSuccess(towerName + " Tower generated at " + here);
    context.sendSuccess(towerName + "_generated",here.toString());
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
