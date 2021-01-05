package greymerk.roguelike.dungeon.segment.alcove;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.InfestedBlock;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class SilverfishNest implements IAlcove {

  private static int RECESSED = 6;

  @Override
  public void generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    Coord corridor = new Coord(origin);
    Coord centre = new Coord(origin);
    centre.translate(dir, RECESSED);

    nest(editor, rand, centre.getX(), centre.getY(), centre.getZ());

    Coord start = new Coord(corridor);
    start.translate(Cardinal.UP);

    Coord end = new Coord(centre);
    end.translate(Cardinal.UP);
    end.translate(dir.reverse(), 1);

    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);
    SpawnerSettings spawners = settings.getSpawners().isEmpty()
        ? MobType.SILVERFISH.newSpawnerSetting()
        : settings.getSpawners();
    spawners.generateSpawner(editor, centre, settings.getDifficulty(centre));
  }

  @Override
  public boolean isValidLocation(WorldEditor editor, Coord origin, Cardinal dir) {

    Coord centre = new Coord(origin);
    centre.translate(dir, RECESSED);
    int x = centre.getX();
    int y = centre.getY();
    int z = centre.getZ();

    List<Coord> toCheck = new RectSolid(new Coord(x - 2, y + 1, z - 2), new Coord(x + 2, y + 1, z + 2)).get();

    for (Coord c : toCheck) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }

  private void nest(WorldEditor editor, Random rand, int x, int y, int z) {
    BlockWeightedRandom fish = new BlockWeightedRandom();
    BlockBrush egg = InfestedBlock.getJumble();
    fish.addBlock(egg, 20);
    fish.addBlock(BlockType.SOUL_SAND.getBrush(), 5);
    RectHollow.fill(editor, new Coord(x - 2, y, z - 2), new Coord(x + 2, y + 3, z + 2), fish);

    fish.stroke(editor, new Coord(x - 1, y + 2, z));
    fish.stroke(editor, new Coord(x + 1, y + 2, z));
    fish.stroke(editor, new Coord(x, y + 2, z - 1));
    fish.stroke(editor, new Coord(x, y + 2, z + 1));
    fish.stroke(editor, new Coord(x, y + 1, z));

  }
}
