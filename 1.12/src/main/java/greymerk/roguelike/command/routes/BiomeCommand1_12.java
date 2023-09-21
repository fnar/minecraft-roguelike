package greymerk.roguelike.command.routes;

import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class BiomeCommand1_12 extends CommandRouteBase {

  public BiomeCommand1_12(greymerk.roguelike.command.CommandBase commandBase) {
    super(commandBase);
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    Coord coord = getCoord(context, args);
    if (coord == null) {
      return;
    }
    sendBiomeDetails(context, coord);
  }

  private Coord getCoord(CommandContext context, List<String> args) {
    ArgumentParser argumentParser = new ArgumentParser(args);

    Coord coord;
    if (!argumentParser.hasEntry(0)) {
      coord = context.getPos();
    } else {
      int x;
      int z;
      try {
        x = CommandBase.parseInt(argumentParser.get(0));
        z = CommandBase.parseInt(argumentParser.get(1));
      } catch (NumberInvalidException e) {
        context.sendFailure("invalidcoords", "X Z");
        return null;
      }
      coord = new Coord(x, 0, z);
    }
    return coord;
  }

  private static void sendBiomeDetails(CommandContext commandContext, Coord coord) {
    commandContext.sendSpecial("notif.roguelike.biomeinfo", coord.toString());

    WorldEditor editor = commandContext.createEditor();
    commandContext.sendSpecial(editor.getBiomeName(coord));

    List<String> typeNames = editor.getBiomeTagNames(coord);
    commandContext.sendSpecial(String.join("", typeNames));
  }
}
