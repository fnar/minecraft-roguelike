package greymerk.roguelike.dungeon.segment.alcove;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.block.spawner.Spawner;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PrisonCell {

  private static final int RECESSED = 5;

  public static void generate(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Coord origin) {
    if (isValidLocation(editor, origin.copy(), dir)) {
      generate(editor, rand, level.getSettings(), origin.copy(), dir);
    }
  }

  private static boolean isValidLocation(WorldEditor editor, Coord origin, Direction dir) {
    Coord centre = origin.copy();
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

  private static void generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Direction dir) {
    Theme theme = settings.getTheme();
    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush plate = BlockType.PRESSURE_PLATE_STONE.getBrush();

    Coord start = origin.copy();
    start.translate(dir, RECESSED);
    Coord end = start.copy();
    start.translate(new Coord(-2, -1, -2));
    end.translate(new Coord(2, 3, 2));
    RectHollow.newRect(start, end).fill(editor, walls);

    start = origin.copy();
    end = origin.copy();
    end.translate(dir, RECESSED);
    end.up();
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    Coord cursor = origin.copy();
    cursor.translate(dir, RECESSED - 1);
    plate.stroke(editor, cursor);
    cursor.down();
    if (rand.nextBoolean()) {
      Spawner spawner = settings.getSpawnerSettings().isEmpty()
          ? MobType.ZOMBIE.asSpawner()
          : settings.getSpawnerSettings().getSpawners().get(editor.getRandom());
      BaseRoom.generateSpawnerSafe(editor, spawner, cursor, settings.getDifficulty(cursor));
    }

    cursor = origin.copy();
    cursor.translate(dir, 3);
    theme.getSecondary().getDoor().setFacing(dir.reverse()).stroke(editor, cursor);
  }
}
