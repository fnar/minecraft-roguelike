package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class SegmentSpawner extends SegmentBase {


  @Override
  protected void genWall(IWorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();


    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orth[0], 1);
    end.translate(orth[1], 1);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());

    for (Cardinal d : orth) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 2);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setOrientation(dir.reverse(), true);
      stair.set(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setOrientation(d.reverse(), false);
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 1);
    cursor.translate(dir, 3);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    stair.setOrientation(dir.reverse(), true);
    stair.set(editor, cursor);

    Coord shelf = new Coord(origin);
    shelf.translate(dir, 3);
    shelf.translate(Cardinal.UP, 1);

    SpawnerSettings spawners = level.getSettings().getSpawners().isEmpty()
        ? MobType.newSpawnerSetting(MobType.COMMON_MOBS)
        : level.getSettings().getSpawners();
    spawners.generateSpawner(editor, rand, shelf, level.getSettings().getDifficulty(shelf));
  }
}
