package greymerk.roguelike.dungeon.segment.alcove;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.minecraft.block.spawner.Spawner;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PrisonCell {

  private static final int RECESSED = 5;

  public static void generate(WorldEditor editor, LevelSettings levelSettings, Coord origin, Direction dir) {
    if (!isValidLocation(editor, origin.copy(), dir)) {
      return;
    }
    BlockBrush walls = levelSettings.getTheme().getPrimary().getWall();
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
    if (editor.getRandom().nextBoolean()) {
      Spawner spawner = levelSettings.getSpawnerSettings().isEmpty()
          ? MobType.ZOMBIE.asSpawner()
          : levelSettings.getSpawnerSettings().getSpawners().get(editor.getRandom());
      BaseRoom.generateSpawnerSafe(editor, spawner, cursor);
    }

    cursor = origin.copy();
    cursor.translate(dir, 3);
    levelSettings.getTheme().getSecondary().getDoor().setFacing(dir.reverse()).stroke(editor, cursor);
  }

  private static boolean isValidLocation(WorldEditor editor, Coord origin, Direction dir) {
    Coord centre = origin.copy();
    centre.translate(dir, RECESSED);
    int x = centre.getX();
    int y = centre.getY();
    int z = centre.getZ();

    for (Coord c : RectSolid.newRect(new Coord(x - 2, y, z - 2), new Coord(x + 2, y, z + 2))) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }

}
