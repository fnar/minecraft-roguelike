package greymerk.roguelike.dungeon.segment.alcove;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

import java.util.Random;

import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class PrisonCell implements IAlcove {

  private static int RECESSED = 5;
  private ThemeBase theme;

  @Override
  public void generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    theme = settings.getTheme();
    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush plate = BlockType.PRESSURE_PLATE_STONE.getBrush();

    Coord start = new Coord(origin);
    start.translate(dir, RECESSED);
    Coord end = new Coord(start);
    start.translate(new Coord(-2, -1, -2));
    end.translate(new Coord(2, 3, 2));
    RectHollow.newRect(start, end).fill(editor, walls);

    start = new Coord(origin);
    end = new Coord(origin);
    end.translate(dir, RECESSED);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    Coord cursor = new Coord(origin);
    cursor.translate(dir, RECESSED - 1);
    plate.stroke(editor, cursor);
    cursor.translate(Cardinal.DOWN);
    if (rand.nextBoolean()) {
      SpawnerSettings spawners = settings.getSpawners().isEmpty()
          ? MobType.ZOMBIE.newSpawnerSetting()
          : settings.getSpawners();
      spawners.generateSpawner(editor, cursor, settings.getDifficulty(cursor));
    }

    cursor = new Coord(origin);
    cursor.translate(dir, 3);
    theme.getSecondary().getDoor().setFacing(dir.reverse()).stroke(editor, cursor);
  }

  @Override
  public boolean isValidLocation(WorldEditor editor, Coord origin, Cardinal dir) {

    Coord centre = new Coord(origin);
    centre.translate(dir, RECESSED);
    int x = centre.getX();
    int y = centre.getY();
    int z = centre.getZ();

    for (Coord c : new RectSolid(new Coord(x - 2, y, z - 2), new Coord(x + 2, y, z + 2))) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }
}
