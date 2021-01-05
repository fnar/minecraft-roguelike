package greymerk.roguelike.command.routes;

import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;

public class CommandRouteBiome extends CommandRouteBase {

  @Override
  public void execute(CommandContext context, List<String> args) {

    Coord pos = getCoord(context, args);
    if (pos == null) {
      return;
    }

    GetBiome.getBiome(context, pos);
  }

  private Coord getCoord(CommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    Coord pos;
    if (!ap.hasEntry(0)) {
      pos = context.getPos();
    } else {
      int x;
      int z;
      try {
        x = CommandBase.parseInt(ap.get(0));
        z = CommandBase.parseInt(ap.get(1));
      } catch (NumberInvalidException e) {
        context.sendFailure("Invalid Coords: X Z");
        return null;
      }
      pos = new Coord(x, 0, z);
    }
    return pos;
  }
}

