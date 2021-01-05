package greymerk.roguelike.command.routes;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static java.util.stream.Collectors.joining;

public class GetBiome {

  public static void getBiome(CommandContext context, Coord pos) {
    context.sendSpecial("Biome Information for " + pos.toString());

    WorldEditor editor = context.createEditor();
    Biome biome = editor.getInfo(pos).getBiome();
    context.sendSpecial(biome.getBiomeName());

    Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
    String types = biomeTypes.stream()
        .map(type -> type.getName() + " ")
        .collect(joining());

    context.sendSpecial(types);
  }
}
