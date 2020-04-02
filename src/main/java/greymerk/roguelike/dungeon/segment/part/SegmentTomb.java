package greymerk.roguelike.dungeon.segment.part;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

import static greymerk.roguelike.treasure.Treasure.COMMON_TREASURES;
import static greymerk.roguelike.treasure.Treasure.createChest;
import static greymerk.roguelike.worldgen.spawners.Spawner.UNDEAD_MOBS;

public class SegmentTomb extends SegmentBase {

  private static void tomb(IWorldEditor editor, Random rand, LevelSettings level, ITheme theme, Cardinal dir, Coord pos) {

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();
    start = new Coord(pos);
    start.add(dir, 3);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    end.add(Cardinal.UP, 3);
    end.add(dir, 3);
    List<Coord> box = new RectHollow(start, end).get();

    // make sure the box is solid wall
    for (Coord c : box) {
      if (!editor.getBlock(c).getMaterial().isSolid()) {
        return;
      }
    }

    RectHollow.fill(editor, rand, start, end, theme.getPrimary().getWall());
    if (!(rand.nextInt(3) == 0)) {
      return;
    }
    cursor = new Coord(pos);
    cursor.add(Cardinal.UP);
    cursor.add(dir, 4);
    SpawnerSettings spawners = level.getSpawners();
    SpawnerSettings.generate(editor, rand, cursor, level.getDifficulty(cursor), spawners, UNDEAD_MOBS);
    cursor.add(dir);
    createChest(editor, rand, Dungeon.getLevel(cursor.getY()), cursor, false, COMMON_TREASURES);
  }

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    cursor.add(dir, 2);
    start = new Coord(cursor);
    start.add(orth[0], 1);
    end = new Coord(cursor);
    end.add(orth[1], 1);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    start.add(dir, 1);
    end.add(dir, 1);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);

    cursor.add(Cardinal.UP, 2);
    for (Cardinal d : orth) {
      Coord c = new Coord(cursor);
      c.add(d, 1);
      stair.setOrientation(d.reverse(), true);
      stair.set(editor, rand, c);
    }

    tomb(editor, rand, level.getSettings(), theme, dir, new Coord(origin));

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    cursor.add(dir, 3);
    BlockType.get(BlockType.QUARTZ).set(editor, cursor);

  }
}
