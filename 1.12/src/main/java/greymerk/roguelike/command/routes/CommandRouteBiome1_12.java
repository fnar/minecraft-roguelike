package greymerk.roguelike.command.routes;

import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandRouteBiome1_12 extends CommandRouteBase {

  public CommandRouteBiome1_12(greymerk.roguelike.command.CommandBase commandBase) {
    super(commandBase);
  }

  @Override
  public void execute(CommandContext context, List<String> args) {
    Coord pos = getCoord(context, args);
    if (pos == null) {
      return;
    }

    context.sendSpecial("notif.roguelike.biomeinfo", pos.toString());

    WorldEditor editor = context.createEditor();
    context.sendSpecial(editor.getBiomeName(pos));

    List<String> typeNames = editor.getBiomeTagNames(pos);
    context.sendSpecial(String.join("", typeNames));
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
        context.sendFailure("invalidcoords", "X Z");
        return null;
      }
      pos = new Coord(x, 0, z);
    }
    return pos;
  }
}

