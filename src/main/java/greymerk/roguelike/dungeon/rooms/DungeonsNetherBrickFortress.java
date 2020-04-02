package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.Spawner;

import static greymerk.roguelike.treasure.Treasure.RARE_TREASURES;
import static greymerk.roguelike.treasure.Treasure.createChests;
import static greymerk.roguelike.worldgen.Cardinal.DOWN;
import static greymerk.roguelike.worldgen.Cardinal.UP;
import static greymerk.roguelike.worldgen.Cardinal.directions;
import static greymerk.roguelike.worldgen.spawners.Spawner.COMMON_MOBS;

public class DungeonsNetherBrickFortress extends DungeonBase {

  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory liquid = theme.getPrimary().getLiquid();
    MetaBlock air = BlockType.get(BlockType.AIR);
    BlockWeightedRandom netherwart = new BlockWeightedRandom();
    netherwart.addBlock(air, 3);
    netherwart.addBlock(Crops.get(Crops.NETHERWART), 1);

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-8, -1, -8));
    end.add(new Coord(8, 6, 8));
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-4, 6, -4));
    end.add(new Coord(4, 6, 4));
    RectSolid.fill(editor, rand, start, end, wall);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-3, 7, -3));
    end.add(new Coord(3, 7, 3));
    RectSolid.fill(editor, rand, start, end, wall);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-2, 7, -2));
    end.add(new Coord(2, 7, 2));
    RectSolid.fill(editor, rand, start, end, liquid);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-4, -1, -4));
    end.add(new Coord(4, -3, 4));
    RectSolid.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-3, -2, -3));
    end.add(new Coord(3, -2, 3));
    BlockType.get(BlockType.SOUL_SAND).fill(editor, rand, new RectSolid(start, end), false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-3, -1, -3));
    end.add(new Coord(3, -1, 3));
    RectSolid.fill(editor, rand, start, end, netherwart, false, true);
    List<Coord> chests = (new RectSolid(start, end).get());

    List<Coord> chestLocations = chooseRandomLocations(rand, rand.nextInt(3) + 1, chests);
    createChests(editor, rand, settings.getDifficulty(origin), chestLocations, false, RARE_TREASURES);

    for (Cardinal dir : directions) {

      start = new Coord(origin);
      start.add(UP, 5);
      start.add(dir, 4);
      end = new Coord(start);
      start.add(dir.left(), 6);
      end.add(dir.right(), 6);
      RectSolid.fill(editor, rand, start, end, wall);

      start = new Coord(origin);
      start.add(UP, 5);
      start.add(dir, 6);
      end = new Coord(start);
      start.add(dir.left(), 6);
      end.add(dir.right(), 6);
      RectSolid.fill(editor, rand, start, end, wall);

      start = new Coord(origin);
      start.add(DOWN);
      start.add(dir, 4);
      end = new Coord(start);
      start.add(dir.left(), 2);
      end.add(dir.right(), 2);
      stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.add(dir, 4);
      cursor.add(dir.left(), 4);
      supportPillar(editor, rand, settings, cursor);

      for (Cardinal o : dir.orthogonal()) {
        cursor = new Coord(origin);
        cursor.add(dir, 7);
        cursor.add(o, 2);
        pillar(editor, rand, settings, cursor);
        cursor.add(o);
        cursor.add(o);
        cursor.add(o);
        pillar(editor, rand, settings, cursor);
      }
    }

    return true;
  }

  private void supportPillar(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock lava = BlockType.get(BlockType.LAVA_FLOWING);

    Coord start;
    Coord end;
    Coord cursor;

    for (Cardinal dir : directions) {
      start = new Coord(origin);
      start.add(dir);
      end = new Coord(start);
      end.add(UP, 5);
      RectSolid.fill(editor, rand, start, end, pillar);

      cursor = new Coord(origin);
      cursor.add(dir, 2);
      cursor.add(UP, 4);
      stair.setOrientation(dir, true).set(editor, cursor);
    }

    start = new Coord(origin);
    end = new Coord(start);
    end.add(UP, 5);
    RectSolid.fill(editor, rand, start, end, lava);
    List<Coord> core = new RectSolid(start, end).get();
    Spawner.generate(editor, rand, settings, core.get(rand.nextInt(core.size())), settings.getDifficulty(core.get(rand.nextInt(core.size()))), COMMON_MOBS);
  }

  private void pillar(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    end.add(UP, 5);
    RectSolid.fill(editor, rand, start, end, pillar);

    for (Cardinal dir : directions) {
      cursor = new Coord(origin);
      cursor.add(UP, 4);
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, rand, cursor, true, false);
      cursor.add(UP);
      wall.set(editor, rand, cursor);
    }


  }

  public int getSize() {
    return 10;
  }


}
