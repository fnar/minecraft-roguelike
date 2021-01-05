package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.HashSet;
import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class ObsidianRoom extends DungeonBase {

  public ObsidianRoom(RoomSetting roomSetting) {
    super(roomSetting);
  }

  private static void outerPillars(WorldEditor editor, ThemeBase theme, int x, int y, int z) {
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonal : dir.orthogonals()) {
        Coord pillarLocation = new Coord(x, y, z);
        pillarLocation.translate(dir, 10);

        pillarLocation.translate(orthogonal, 2);
        outerPillar(editor, theme, pillarLocation, dir);

        pillarLocation.translate(orthogonal, 3);
        outerPillar(editor, theme, pillarLocation, dir);

        pillarLocation.translate(orthogonal, 3);
        outerPillar(editor, theme, pillarLocation, dir);
      }
    }
  }

  private static void outerPillar(WorldEditor editor, ThemeBase theme, Coord pillarLocation, Cardinal dir) {

    BlockBrush secondaryWall = theme.getSecondary().getPillar();

    int x = pillarLocation.getX();
    int y = pillarLocation.getY();
    int z = pillarLocation.getZ();

    RectSolid.fill(editor, new Coord(x, y - 2, z), new Coord(x, y + 3, z), secondaryWall);
    Coord blockLocation = new Coord(x, y + 3, z);

    blockLocation.translate(dir, 1);
    secondaryWall.stroke(editor, blockLocation);

    for (int i = 0; i < 3; ++i) {
      blockLocation.translate(dir.reverse(), 1);
      blockLocation.translate(Cardinal.UP, 1);
      secondaryWall.stroke(editor, blockLocation);
    }
  }

  private static void innerPillars(WorldEditor editor, ThemeBase theme, int x, int y, int z) {

    BlockBrush secondaryWall = theme.getSecondary().getPillar();

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonal : dir.orthogonals()) {
        Coord pillar = new Coord(x, y, z);
        pillar.translate(dir, 2);
        pillar.translate(orthogonal, 2);
        RectSolid.fill(editor, new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ()), secondaryWall);
        pillar.translate(dir, 4);
        RectSolid.fill(editor, new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ()), secondaryWall);
        pillar.translate(orthogonal, 3);
        RectSolid.fill(editor, new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ()), secondaryWall);

        Coord start = new Coord(x, y, z);
        start.translate(Cardinal.DOWN, 1);
        start.translate(orthogonal, 2);
        start.translate(dir, 2);
        Coord end = new Coord(start);
        end.translate(dir, 5);
        RectSolid.fill(editor, start, end, theme.getPrimary().getPillar());

        start = new Coord(x, y, z);
        start.translate(Cardinal.DOWN, 1);
        start.translate(dir, 7);
        start.translate(orthogonal, 5);
        secondaryWall.stroke(editor, start);
        start.translate(Cardinal.DOWN, 1);
        end = new Coord(start);
        end.translate(dir.reverse(), 1);
        end.translate(orthogonal, 1);
        end.translate(Cardinal.DOWN, 1);
        RectSolid.fill(editor, start, end, secondaryWall);
      }
    }
  }

  private static void liquidWindow(WorldEditor editor, Coord cursor, Cardinal orthogonal, ThemeBase theme) {
    BlockBrush liquid = theme.getPrimary().getLiquid();
    RectSolid.fill(editor, cursor, cursor, liquid);
    cursor.translate(Cardinal.DOWN, 1);
    RectSolid.fill(editor, cursor, cursor, liquid);

    BlockBrush fence = BlockType.FENCE_NETHER_BRICK.getBrush();
    cursor.translate(orthogonal, 1);
    fence.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    fence.stroke(editor, cursor);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ThemeBase theme = settings.getTheme();

    HashSet<Coord> spawnerLocations = new HashSet<>();
    BlockBrush primaryWall = theme.getPrimary().getWall();
    BlockBrush secondaryWall = theme.getSecondary().getWall();

    // space
    RectSolid.fill(editor, new Coord(x - 10, y - 3, z - 10), new Coord(x + 10, y + 3, z + 10), SingleBlockBrush.AIR);


    // roof
    RectSolid.fill(editor, new Coord(x - 7, y + 6, z - 7), new Coord(x + 7, y + 6, z + 7), secondaryWall);
    RectSolid.fill(editor, new Coord(x - 8, y + 5, z - 8), new Coord(x + 8, y + 5, z + 8), secondaryWall);
    RectSolid.fill(editor, new Coord(x - 9, y + 4, z - 9), new Coord(x + 9, y + 4, z + 9), secondaryWall);
    RectSolid.fill(editor, new Coord(x - 1, y + 3, z - 1), new Coord(x + 1, y + 5, z + 1), SingleBlockBrush.AIR);
    secondaryWall.stroke(editor, new Coord(x, y + 5, z));
    spawnerLocations.add(new Coord(x, y + 4, z));


    // foundation
    RectSolid.fill(editor, new Coord(x - 10, y - 4, z - 10), new Coord(x + 10, y - 4, z + 10), secondaryWall);

    // ceiling holes
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonal : dir.orthogonals()) {
        Coord start = new Coord(x, y, z);
        start.translate(Cardinal.UP, 3);
        start.translate(dir, 3);
        start.translate(orthogonal, 3);
        Coord end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        end.translate(dir, 2);
        end.translate(orthogonal, 2);
        RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

        start = new Coord(x, y, z);
        start.translate(dir, 3);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(dir, 2);
        start.translate(orthogonal, 1);
        end.translate(Cardinal.UP, 2);
        RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

        Coord cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 4);
        cursor.translate(dir, 4);
        spawnerLocations.add(new Coord(cursor));
        cursor.translate(orthogonal, 4);
        spawnerLocations.add(new Coord(cursor));

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 5);
        cursor.translate(dir, 4);
        secondaryWall.stroke(editor, cursor);
        cursor.translate(orthogonal, 4);
        secondaryWall.stroke(editor, cursor);
      }
    }


    // ceiling trims and outer walls
    for (Cardinal dir : Cardinal.DIRECTIONS) {

      // outer wall trim
      Coord start = new Coord(x, y, z);
      start.translate(dir, 10);
      Coord end = new Coord(start);
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);

      start.translate(Cardinal.DOWN, 4);
      end.translate(Cardinal.DOWN, 1);
      RectSolid.fill(editor, start, end, secondaryWall);

      start.translate(Cardinal.UP, 4 + 3);
      end.translate(Cardinal.UP, 1 + 3);
      RectSolid.fill(editor, start, end, secondaryWall);

      // mid
      start = new Coord(x, y, z);
      start.translate(dir, 6);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);
      RectSolid.fill(editor, start, end, secondaryWall);

      // inner
      start = new Coord(x, y, z);
      start.translate(dir, 2);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);
      RectSolid.fill(editor, start, end, secondaryWall);

      // outer shell
      start = new Coord(x, y, z);
      start.translate(dir, 11);
      end = new Coord(start);
      start.translate(Cardinal.DOWN, 3);
      end.translate(Cardinal.UP, 3);
      start.translate(dir.antiClockwise(), 11);
      end.translate(dir.clockwise(), 11);
      RectSolid.fill(editor, start, end, secondaryWall, false, true);
    }

    outerPillars(editor, theme, x, y, z);

    // upper mid floor
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      Coord start = new Coord(x, y, z);
      start.translate(Cardinal.DOWN, 1);
      Coord end = new Coord(start);
      end.translate(Cardinal.DOWN, 3);
      start.translate(dir, 9);
      start.translate(dir.antiClockwise(), 1);
      end.translate(dir.clockwise(), 1);
      RectSolid.fill(editor, start, end, primaryWall);
    }

    // mid outer floors
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonal : dir.orthogonals()) {
        Coord start = new Coord(x, y, z);
        Coord end = new Coord(start);
        start.translate(dir, 9);
        start.translate(orthogonal, 2);
        start.translate(Cardinal.DOWN, 3);
        end.translate(dir, 8);
        end.translate(orthogonal, 9);
        end.translate(Cardinal.DOWN, 2);

        RectSolid.fill(editor, start, end, primaryWall);
        StairsBlock stair = theme.getPrimary().getStair();
        Coord stepCoord = new Coord(x, y, z);
        stepCoord.translate(dir, 8);
        stepCoord.translate(Cardinal.DOWN, 1);
        stepCoord.translate(orthogonal, 2);
        stair.setUpsideDown(false).setFacing(orthogonal);
        stair.stroke(editor, stepCoord);
        stepCoord.translate(dir, 1);
        stair.stroke(editor, stepCoord);

        stair.setUpsideDown(false).setFacing(dir.reverse());
        stepCoord = new Coord(x, y, z);
        stepCoord.translate(Cardinal.DOWN, 2);
        stepCoord.translate(dir, 7);
        stepCoord.translate(orthogonal, 3);
        stair.stroke(editor, stepCoord);
        stepCoord.translate(orthogonal, 1);
        stair.stroke(editor, stepCoord);
        stepCoord.translate(Cardinal.DOWN, 1);
        stepCoord.translate(dir.reverse(), 1);
        stair.stroke(editor, stepCoord);
        stepCoord.translate(orthogonal.reverse(), 1);
        stair.stroke(editor, stepCoord);
        stepCoord.translate(dir, 1);
        primaryWall.stroke(editor, stepCoord);
        stepCoord.translate(orthogonal, 1);
        primaryWall.stroke(editor, stepCoord);

        Coord corner = new Coord(x, y, z);
        corner.translate(dir, 7);
        corner.translate(orthogonal, 7);
        corner.translate(Cardinal.DOWN, 2);
        primaryWall.stroke(editor, corner);
        corner.translate(Cardinal.DOWN, 1);
        primaryWall.stroke(editor, corner);
      }
    }

    // chests areas
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonal : dir.orthogonals()) {
        Coord cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.DOWN, 2);
        cursor.translate(dir, 3);
        liquidWindow(editor, new Coord(cursor), orthogonal, theme);
        cursor.translate(dir, 2);
        liquidWindow(editor, new Coord(cursor), orthogonal, theme);

        Coord chestPos = new Coord(x, y, z);
        chestPos.translate(dir, 4);
        chestPos.translate(orthogonal, 2);
        chestPos.translate(Cardinal.DOWN, 3);

        editor.getTreasureChestEditor().createChest(Dungeon.getLevel(chestPos.getY()), chestPos, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(editor.getRandom(), ChestType.RARE_TREASURES)));
      }
    }

    innerPillars(editor, theme, x, y, z);

    for (Coord space : spawnerLocations) {
      int difficulty = settings.getDifficulty(space);
      generateSpawner(editor, space, difficulty, settings.getSpawners());
    }

    BlockJumble crap = new BlockJumble();
    crap.addBlock(theme.getPrimary().getLiquid());
    crap.addBlock(BlockType.SOUL_SAND.getBrush());
    crap.addBlock(BlockType.OBSIDIAN.getBrush());

    Coord start = new Coord(origin);
    Coord end = new Coord(start);
    start.translate(Cardinal.DOWN, 5);
    end.translate(Cardinal.DOWN, 8);
    start.translate(Cardinal.NORTH, 6);
    start.translate(Cardinal.EAST, 6);
    end.translate(Cardinal.SOUTH, 6);
    end.translate(Cardinal.WEST, 6);
    RectSolid.fill(editor, start, end, crap);

    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }

}
