package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.HashSet;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

import static greymerk.roguelike.treasure.Treasure.RARE_TREASURES;


public class ObsidianRoom extends DungeonBase {

  public ObsidianRoom(RoomSetting roomSetting) {
    super(roomSetting);
  }

  private static void outerPillars(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {
    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {
        Coord pillarLocation = new Coord(x, y, z);
        pillarLocation.translate(dir, 10);

        pillarLocation.translate(orth, 2);
        outerPillar(editor, rand, theme, pillarLocation, dir);

        pillarLocation.translate(orth, 3);
        outerPillar(editor, rand, theme, pillarLocation, dir);

        pillarLocation.translate(orth, 3);
        outerPillar(editor, rand, theme, pillarLocation, dir);
      }
    }
  }

  private static void outerPillar(WorldEditor editor, Random rand, ITheme theme, Coord pillarLocation, Cardinal dir) {

    IBlockFactory secondaryWall = theme.getSecondary().getPillar();

    int x = pillarLocation.getX();
    int y = pillarLocation.getY();
    int z = pillarLocation.getZ();

    RectSolid.fill(editor, rand, new Coord(x, y - 2, z), new Coord(x, y + 3, z), secondaryWall);
    Coord blockLocation = new Coord(x, y + 3, z);

    blockLocation.translate(dir, 1);
    secondaryWall.set(editor, rand, blockLocation);

    for (int i = 0; i < 3; ++i) {
      blockLocation.translate(dir.reverse(), 1);
      blockLocation.translate(Cardinal.UP, 1);
      secondaryWall.set(editor, rand, blockLocation);
    }
  }

  private static void innerPillars(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {

    IBlockFactory secondaryWall = theme.getSecondary().getPillar();

    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {
        Coord pillar = new Coord(x, y, z);
        pillar.translate(dir, 2);
        pillar.translate(orth, 2);
        RectSolid.fill(editor, rand, new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ()), secondaryWall);
        pillar.translate(dir, 4);
        RectSolid.fill(editor, rand, new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ()), secondaryWall);
        pillar.translate(orth, 3);
        RectSolid.fill(editor, rand, new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ()), secondaryWall);

        Coord start = new Coord(x, y, z);
        start.translate(Cardinal.DOWN, 1);
        start.translate(orth, 2);
        start.translate(dir, 2);
        Coord end = new Coord(start);
        end.translate(dir, 5);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());

        start = new Coord(x, y, z);
        start.translate(Cardinal.DOWN, 1);
        start.translate(dir, 7);
        start.translate(orth, 5);
        secondaryWall.set(editor, rand, start);
        start.translate(Cardinal.DOWN, 1);
        end = new Coord(start);
        end.translate(dir.reverse(), 1);
        end.translate(orth, 1);
        end.translate(Cardinal.DOWN, 1);
        RectSolid.fill(editor, rand, start, end, secondaryWall);
      }
    }
  }

  private static void liquidWindow(WorldEditor editor, Coord cursor, Cardinal orth, ITheme theme, Random random) {
    IBlockFactory liquid = theme.getPrimary().getLiquid();
    RectSolid.fill(editor, random, cursor, cursor, liquid);
    cursor.translate(Cardinal.DOWN, 1);
    RectSolid.fill(editor, random, cursor, cursor, liquid);

    MetaBlock fence = BlockType.get(BlockType.FENCE_NETHER_BRICK);
    cursor.translate(orth, 1);
    fence.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    fence.set(editor, cursor);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ITheme theme = settings.getTheme();

    HashSet<Coord> spawners = new HashSet<>();
    MetaBlock air = BlockType.get(BlockType.AIR);
    IBlockFactory primaryWall = theme.getPrimary().getWall();
    IBlockFactory secondaryWall = theme.getSecondary().getWall();

    // space
    RectSolid.fill(editor, rand, new Coord(x - 10, y - 3, z - 10), new Coord(x + 10, y + 3, z + 10), air);


    // roof
    RectSolid.fill(editor, rand, new Coord(x - 7, y + 6, z - 7), new Coord(x + 7, y + 6, z + 7), secondaryWall);
    RectSolid.fill(editor, rand, new Coord(x - 8, y + 5, z - 8), new Coord(x + 8, y + 5, z + 8), secondaryWall);
    RectSolid.fill(editor, rand, new Coord(x - 9, y + 4, z - 9), new Coord(x + 9, y + 4, z + 9), secondaryWall);
    RectSolid.fill(editor, rand, new Coord(x - 1, y + 3, z - 1), new Coord(x + 1, y + 5, z + 1), air);
    secondaryWall.set(editor, rand, new Coord(x, y + 5, z));
    spawners.add(new Coord(x, y + 4, z));


    // foundation
    RectSolid.fill(editor, rand, new Coord(x - 10, y - 4, z - 10), new Coord(x + 10, y - 4, z + 10), secondaryWall);

    // ceiling holes
    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {
        Coord start = new Coord(x, y, z);
        start.translate(Cardinal.UP, 3);
        start.translate(dir, 3);
        start.translate(orth, 3);
        Coord end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        end.translate(dir, 2);
        end.translate(orth, 2);
        RectSolid.fill(editor, rand, start, end, air);

        start = new Coord(x, y, z);
        start.translate(dir, 3);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(dir, 2);
        start.translate(orth, 1);
        end.translate(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, air);

        Coord cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 4);
        cursor.translate(dir, 4);
        spawners.add(new Coord(cursor));
        cursor.translate(orth, 4);
        spawners.add(new Coord(cursor));

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 5);
        cursor.translate(dir, 4);
        secondaryWall.set(editor, rand, cursor);
        cursor.translate(orth, 4);
        secondaryWall.set(editor, rand, cursor);
      }
    }


    // ceiling trims and outer walls
    for (Cardinal dir : Cardinal.directions) {

      // outer wall trim
      Coord start = new Coord(x, y, z);
      start.translate(dir, 10);
      Coord end = new Coord(start);
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);

      start.translate(Cardinal.DOWN, 4);
      end.translate(Cardinal.DOWN, 1);
      RectSolid.fill(editor, rand, start, end, secondaryWall);

      start.translate(Cardinal.UP, 4 + 3);
      end.translate(Cardinal.UP, 1 + 3);
      RectSolid.fill(editor, rand, start, end, secondaryWall);

      // mid
      start = new Coord(x, y, z);
      start.translate(dir, 6);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);
      RectSolid.fill(editor, rand, start, end, secondaryWall);

      // inner
      start = new Coord(x, y, z);
      start.translate(dir, 2);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);
      RectSolid.fill(editor, rand, start, end, secondaryWall);

      // outer shell
      start = new Coord(x, y, z);
      start.translate(dir, 11);
      end = new Coord(start);
      start.translate(Cardinal.DOWN, 3);
      end.translate(Cardinal.UP, 3);
      start.translate(dir.antiClockwise(), 11);
      end.translate(dir.clockwise(), 11);
      RectSolid.fill(editor, rand, start, end, secondaryWall, false, true);
    }

    outerPillars(editor, rand, theme, x, y, z);

    // upper mid floor
    for (Cardinal dir : Cardinal.directions) {
      Coord start = new Coord(x, y, z);
      start.translate(Cardinal.DOWN, 1);
      Coord end = new Coord(start);
      end.translate(Cardinal.DOWN, 3);
      start.translate(dir, 9);
      start.translate(dir.antiClockwise(), 1);
      end.translate(dir.clockwise(), 1);
      RectSolid.fill(editor, rand, start, end, primaryWall);
    }

    // mid outer floors
    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {
        Coord start = new Coord(x, y, z);
        Coord end = new Coord(start);
        start.translate(dir, 9);
        start.translate(orth, 2);
        start.translate(Cardinal.DOWN, 3);
        end.translate(dir, 8);
        end.translate(orth, 9);
        end.translate(Cardinal.DOWN, 2);

        RectSolid.fill(editor, rand, start, end, primaryWall);
        IStair stair = theme.getPrimary().getStair();
        Coord stepCoord = new Coord(x, y, z);
        stepCoord.translate(dir, 8);
        stepCoord.translate(Cardinal.DOWN, 1);
        stepCoord.translate(orth, 2);
        stair.setOrientation(orth, false);
        stair.set(editor, rand, stepCoord);
        stepCoord.translate(dir, 1);
        stair.set(editor, rand, stepCoord);

        stair.setOrientation(dir.reverse(), false);
        stepCoord = new Coord(x, y, z);
        stepCoord.translate(Cardinal.DOWN, 2);
        stepCoord.translate(dir, 7);
        stepCoord.translate(orth, 3);
        stair.set(editor, rand, stepCoord);
        stepCoord.translate(orth, 1);
        stair.set(editor, rand, stepCoord);
        stepCoord.translate(Cardinal.DOWN, 1);
        stepCoord.translate(dir.reverse(), 1);
        stair.set(editor, rand, stepCoord);
        stepCoord.translate(orth.reverse(), 1);
        stair.set(editor, rand, stepCoord);
        stepCoord.translate(dir, 1);
        primaryWall.set(editor, rand, stepCoord);
        stepCoord.translate(orth, 1);
        primaryWall.set(editor, rand, stepCoord);

        Coord corner = new Coord(x, y, z);
        corner.translate(dir, 7);
        corner.translate(orth, 7);
        corner.translate(Cardinal.DOWN, 2);
        primaryWall.set(editor, rand, corner);
        corner.translate(Cardinal.DOWN, 1);
        primaryWall.set(editor, rand, corner);
      }
    }

    // chests areas
    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {
        Coord cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.DOWN, 2);
        cursor.translate(dir, 3);
        liquidWindow(editor, new Coord(cursor), orth, theme, rand);
        cursor.translate(dir, 2);
        liquidWindow(editor, new Coord(cursor), orth, theme, rand);

        Coord chestPos = new Coord(x, y, z);
        chestPos.translate(dir, 4);
        chestPos.translate(orth, 2);
        chestPos.translate(Cardinal.DOWN, 3);

        editor.treasureChestEditor.createChest(rand, Dungeon.getLevel(chestPos.getY()), chestPos, false, RARE_TREASURES);
      }
    }

    innerPillars(editor, rand, theme, x, y, z);

    for (Coord space : spawners) {
      SpawnerSettings spawners1 = settings.getSpawners();
      int difficulty = settings.getDifficulty(space);
      generateSpawner(editor, rand, space, difficulty, settings.getSpawners());
    }

    BlockJumble crap = new BlockJumble();
    crap.addBlock(theme.getPrimary().getLiquid());
    crap.addBlock(BlockType.get(BlockType.SOUL_SAND));
    crap.addBlock(BlockType.get(BlockType.OBSIDIAN));

    Coord start = new Coord(origin);
    Coord end = new Coord(start);
    start.translate(Cardinal.DOWN, 5);
    end.translate(Cardinal.DOWN, 8);
    start.translate(Cardinal.NORTH, 6);
    start.translate(Cardinal.EAST, 6);
    end.translate(Cardinal.SOUTH, 6);
    end.translate(Cardinal.WEST, 6);
    RectSolid.fill(editor, rand, start, end, crap);

    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }

}
