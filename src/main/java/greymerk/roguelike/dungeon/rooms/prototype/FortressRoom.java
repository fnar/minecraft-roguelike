package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.treasure.Treasure.RARE_TREASURES;
import static greymerk.roguelike.treasure.Treasure.createChests;
import static greymerk.roguelike.worldgen.Cardinal.DOWN;
import static greymerk.roguelike.worldgen.Cardinal.UP;
import static greymerk.roguelike.worldgen.Cardinal.directions;

public class FortressRoom extends DungeonBase {

  public FortressRoom(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings levelSettings, Coord origin, Cardinal[] entrances) {
    ITheme theme = levelSettings.getTheme();
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
    start.translate(new Coord(-8, -1, -8));
    end.translate(new Coord(8, 6, 8));
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, 6, -4));
    end.translate(new Coord(4, 6, 4));
    RectSolid.fill(editor, rand, start, end, wall);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 7, -3));
    end.translate(new Coord(3, 7, 3));
    RectSolid.fill(editor, rand, start, end, wall);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-2, 7, -2));
    end.translate(new Coord(2, 7, 2));
    RectSolid.fill(editor, rand, start, end, liquid);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -3, 4));
    RectSolid.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -2, -3));
    end.translate(new Coord(3, -2, 3));
    BlockType.get(BlockType.SOUL_SAND).fill(editor, rand, new RectSolid(start, end), false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.fill(editor, rand, start, end, netherwart, false, true);
    List<Coord> chests = (new RectSolid(start, end).get());

    List<Coord> chestLocations = chooseRandomLocations(rand, rand.nextInt(3) + 1, chests);
    createChests(editor, rand, levelSettings.getDifficulty(origin), chestLocations, false, RARE_TREASURES);

    for (Cardinal dir : directions) {

      start = new Coord(origin);
      start.translate(UP, 5);
      start.translate(dir, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.fill(editor, rand, start, end, wall);

      start = new Coord(origin);
      start.translate(UP, 5);
      start.translate(dir, 6);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.fill(editor, rand, start, end, wall);

      start = new Coord(origin);
      start.translate(DOWN);
      start.translate(dir, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(dir.antiClockwise(), 4);
      supportPillar(editor, rand, levelSettings, cursor);

      for (Cardinal o : dir.orthogonal()) {
        cursor = new Coord(origin);
        cursor.translate(dir, 7);
        cursor.translate(o, 2);
        pillar(editor, rand, levelSettings, cursor);
        cursor.translate(o);
        cursor.translate(o);
        cursor.translate(o);
        pillar(editor, rand, levelSettings, cursor);
      }
    }

    return this;
  }

  private void supportPillar(WorldEditor editor, Random rand, LevelSettings levelSettings, Coord origin) {

    ITheme theme = levelSettings.getTheme();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock lava = BlockType.get(BlockType.LAVA_FLOWING);

    Coord start;
    Coord end;
    Coord cursor;

    for (Cardinal dir : directions) {
      start = new Coord(origin);
      start.translate(dir);
      end = new Coord(start);
      end.translate(UP, 5);
      RectSolid.fill(editor, rand, start, end, pillar);

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(UP, 4);
      stair.setOrientation(dir, true).set(editor, cursor);
    }

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(UP, 5);
    RectSolid.fill(editor, rand, start, end, lava);
    List<Coord> core = new RectSolid(start, end).get();
    Coord spawnerLocation = core.get(rand.nextInt(core.size()));
    int difficulty = levelSettings.getDifficulty(spawnerLocation);
    generateSpawner(editor, rand, spawnerLocation, difficulty, levelSettings.getSpawners());
  }

  private void pillar(WorldEditor editor, Random rand, LevelSettings settings, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(UP, 5);
    RectSolid.fill(editor, rand, start, end, pillar);

    for (Cardinal dir : directions) {
      cursor = new Coord(origin);
      cursor.translate(UP, 4);
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, rand, cursor, true, false);
      cursor.translate(UP);
      wall.set(editor, rand, cursor);
    }


  }

  public int getSize() {
    return 10;
  }


}
