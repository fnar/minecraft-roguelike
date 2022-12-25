package greymerk.roguelike.dungeon.segment.alcove;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.InfestedBlock;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.block.spawner.Spawner;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SilverfishNest {

  private static final int RECESSED = 6;

  public static void generate(SilverfishNest silverfishNest, WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Coord origin) {
    if (isValidLocation(editor, origin.copy(), dir)) {
      generate(silverfishNest, editor, rand, level.getSettings(), origin.copy(), dir);
    }
  }

  private static boolean isValidLocation(WorldEditor editor, Coord origin, Direction dir) {

    Coord centre = origin.copy();
    centre.translate(dir, RECESSED);
    int x = centre.getX();
    int y = centre.getY();
    int z = centre.getZ();

    List<Coord> toCheck = RectSolid.newRect(new Coord(x - 2, y + 1, z - 2), new Coord(x + 2, y + 1, z + 2)).get();

    for (Coord c : toCheck) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }

  private static void generate(SilverfishNest silverfishNest, WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Direction dir) {

    Coord corridor = origin.copy();
    Coord centre = origin.copy();
    centre.translate(dir, RECESSED);

    silverfishNest.nest(editor, rand, centre.getX(), centre.getY(), centre.getZ());

    Coord start = corridor.copy();
    start.up();

    Coord end = centre.copy();
    end.up();
    end.translate(dir.reverse(), 1);

    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    Spawner spawner = settings.getSpawnerSettings().isEmpty()
            ? MobType.SILVERFISH.asSpawner()
            : settings.getSpawnerSettings().getSpawners().get(editor.getRandom());
    BaseRoom.generateSpawnerSafe(editor, spawner, centre, settings.getLevel(centre));
  }

  private void nest(WorldEditor editor, Random rand, int x, int y, int z) {
    BlockWeightedRandom fish = new BlockWeightedRandom();
    BlockBrush egg = InfestedBlock.getJumble();
    fish.addBlock(egg, 20);
    fish.addBlock(BlockType.SOUL_SAND.getBrush(), 5);
    RectHollow.newRect(new Coord(x - 2, y, z - 2), new Coord(x + 2, y + 3, z + 2)).fill(editor, fish);

    fish.stroke(editor, new Coord(x - 1, y + 2, z));
    fish.stroke(editor, new Coord(x + 1, y + 2, z));
    fish.stroke(editor, new Coord(x, y + 2, z - 1));
    fish.stroke(editor, new Coord(x, y + 2, z + 1));
    fish.stroke(editor, new Coord(x, y + 1, z));

  }
}
