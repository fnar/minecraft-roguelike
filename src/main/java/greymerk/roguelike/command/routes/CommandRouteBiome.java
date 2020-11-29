package greymerk.roguelike.command.routes;

import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.Set;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static java.util.stream.Collectors.joining;

public class CommandRouteBiome extends CommandRouteBase {

  @Override
  public void execute(CommandContext context, List<String> args) {

    ArgumentParser ap = new ArgumentParser(args);

    WorldEditor editor = context.createEditor();
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
        return;
      }
      pos = new Coord(x, 0, z);
    }

    context.sendSpecial("Biome Information for " + pos.toString());

    Biome biome = editor.getInfo(pos).getBiome();
    context.sendSpecial(biome.getBiomeName());

    Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
    String types = biomeTypes.stream()
        .map(type -> type.getName() + " ")
        .collect(joining());

    context.sendSpecial(types);
  }
}
