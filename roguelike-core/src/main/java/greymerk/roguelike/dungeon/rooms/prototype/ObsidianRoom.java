package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

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
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class ObsidianRoom extends DungeonBase {

  public ObsidianRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  private static void outerPillars(WorldEditor editor, ThemeBase theme, int x, int y, int z) {
    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonal : dir.orthogonals()) {
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

  private static void outerPillar(WorldEditor editor, ThemeBase theme, Coord pillarLocation, Direction dir) {

    BlockBrush secondaryWall = theme.getSecondary().getPillar();

    int x = pillarLocation.getX();
    int y = pillarLocation.getY();
    int z = pillarLocation.getZ();

    RectSolid.newRect(new Coord(x, y - 2, z), new Coord(x, y + 3, z)).fill(editor, secondaryWall);
    Coord blockLocation = new Coord(x, y + 3, z);

    blockLocation.translate(dir, 1);
    secondaryWall.stroke(editor, blockLocation);

    for (int i = 0; i < 3; ++i) {
      blockLocation.translate(dir.reverse(), 1);
      blockLocation.translate(Direction.UP, 1);
      secondaryWall.stroke(editor, blockLocation);
    }
  }

  private static void innerPillars(WorldEditor editor, ThemeBase theme, int x, int y, int z) {

    BlockBrush secondaryWall = theme.getSecondary().getPillar();

    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonal : dir.orthogonals()) {
        Coord pillar = new Coord(x, y, z);
        pillar.translate(dir, 2);
        pillar.translate(orthogonal, 2);
        RectSolid.newRect(new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ())).fill(editor, secondaryWall);
        pillar.translate(dir, 4);
        RectSolid.newRect(new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ())).fill(editor, secondaryWall);
        pillar.translate(orthogonal, 3);
        RectSolid.newRect(new Coord(pillar.getX(), y - 4, pillar.getZ()), new Coord(pillar.getX(), y + 4, pillar.getZ())).fill(editor, secondaryWall);

        Coord start = new Coord(x, y, z);
        start.translate(Direction.DOWN, 1);
        start.translate(orthogonal, 2);
        start.translate(dir, 2);
        Coord end = start.copy();
        end.translate(dir, 5);
        RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getPillar());

        start = new Coord(x, y, z);
        start.translate(Direction.DOWN, 1);
        start.translate(dir, 7);
        start.translate(orthogonal, 5);
        secondaryWall.stroke(editor, start);
        start.translate(Direction.DOWN, 1);
        end = start.copy();
        end.translate(dir.reverse(), 1);
        end.translate(orthogonal, 1);
        end.translate(Direction.DOWN, 1);
        RectSolid.newRect(start, end).fill(editor, secondaryWall);
      }
    }
  }

  private static void liquidWindow(WorldEditor editor, Coord cursor, Direction orthogonal, ThemeBase theme) {
    BlockBrush liquid = theme.getPrimary().getLiquid();
    RectSolid.newRect(cursor, cursor).fill(editor, liquid);
    cursor.translate(Direction.DOWN, 1);
    RectSolid.newRect(cursor, cursor).fill(editor, liquid);

    BlockBrush fence = BlockType.FENCE_NETHER_BRICK.getBrush();
    cursor.translate(orthogonal, 1);
    fence.stroke(editor, cursor);
    cursor.translate(Direction.UP, 1);
    fence.stroke(editor, cursor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ThemeBase theme = levelSettings.getTheme();

    HashSet<Coord> spawnerLocations = new HashSet<>();
    BlockBrush primaryWall = theme.getPrimary().getWall();
    BlockBrush secondaryWall = theme.getSecondary().getWall();

    // space
    RectSolid.newRect(new Coord(x - 10, y - 3, z - 10), new Coord(x + 10, y + 3, z + 10)).fill(worldEditor, SingleBlockBrush.AIR);


    // roof
    RectSolid.newRect(new Coord(x - 7, y + 6, z - 7), new Coord(x + 7, y + 6, z + 7)).fill(worldEditor, secondaryWall);
    RectSolid.newRect(new Coord(x - 8, y + 5, z - 8), new Coord(x + 8, y + 5, z + 8)).fill(worldEditor, secondaryWall);
    RectSolid.newRect(new Coord(x - 9, y + 4, z - 9), new Coord(x + 9, y + 4, z + 9)).fill(worldEditor, secondaryWall);
    RectSolid.newRect(new Coord(x - 1, y + 3, z - 1), new Coord(x + 1, y + 5, z + 1)).fill(worldEditor, SingleBlockBrush.AIR);
    secondaryWall.stroke(worldEditor, new Coord(x, y + 5, z));
    spawnerLocations.add(new Coord(x, y + 4, z));


    // foundation
    RectSolid.newRect(new Coord(x - 10, y - 4, z - 10), new Coord(x + 10, y - 4, z + 10)).fill(worldEditor, secondaryWall);

    // ceiling holes
    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonal : dir.orthogonals()) {
        Coord start = new Coord(x, y, z);
        start.translate(Direction.UP, 3);
        start.translate(dir, 3);
        start.translate(orthogonal, 3);
        Coord end = start.copy();
        end.translate(Direction.UP, 2);
        end.translate(dir, 2);
        end.translate(orthogonal, 2);
        RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

        start = new Coord(x, y, z);
        start.translate(dir, 3);
        start.translate(Direction.UP, 3);
        end = start.copy();
        end.translate(dir, 2);
        start.translate(orthogonal, 1);
        end.translate(Direction.UP, 2);
        RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

        Coord cursor = new Coord(x, y, z);
        cursor.translate(Direction.UP, 4);
        cursor.translate(dir, 4);
        spawnerLocations.add(cursor.copy());
        cursor.translate(orthogonal, 4);
        spawnerLocations.add(cursor.copy());

        cursor = new Coord(x, y, z);
        cursor.translate(Direction.UP, 5);
        cursor.translate(dir, 4);
        secondaryWall.stroke(worldEditor, cursor);
        cursor.translate(orthogonal, 4);
        secondaryWall.stroke(worldEditor, cursor);
      }
    }


    // ceiling trims and outer walls
    for (Direction dir : Direction.CARDINAL) {

      // outer wall trim
      Coord start = new Coord(x, y, z);
      start.translate(dir, 10);
      Coord end = start.copy();
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);

      start.translate(Direction.DOWN, 4);
      end.translate(Direction.DOWN, 1);
      RectSolid.newRect(start, end).fill(worldEditor, secondaryWall);

      start.translate(Direction.UP, 4 + 3);
      end.translate(Direction.UP, 1 + 3);
      RectSolid.newRect(start, end).fill(worldEditor, secondaryWall);

      // mid
      start = new Coord(x, y, z);
      start.translate(dir, 6);
      start.translate(Direction.UP, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);
      RectSolid.newRect(start, end).fill(worldEditor, secondaryWall);

      // inner
      start = new Coord(x, y, z);
      start.translate(dir, 2);
      start.translate(Direction.UP, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);
      RectSolid.newRect(start, end).fill(worldEditor, secondaryWall);

      // outer shell
      start = new Coord(x, y, z);
      start.translate(dir, 11);
      end = start.copy();
      start.translate(Direction.DOWN, 3);
      end.translate(Direction.UP, 3);
      start.translate(dir.antiClockwise(), 11);
      end.translate(dir.clockwise(), 11);
      RectSolid.newRect(start, end).fill(worldEditor, secondaryWall, false, true);
    }

    outerPillars(worldEditor, theme, x, y, z);

    // upper mid floor
    for (Direction dir : Direction.CARDINAL) {
      Coord start = new Coord(x, y, z);
      start.translate(Direction.DOWN, 1);
      Coord end = start.copy();
      end.translate(Direction.DOWN, 3);
      start.translate(dir, 9);
      start.translate(dir.antiClockwise(), 1);
      end.translate(dir.clockwise(), 1);
      RectSolid.newRect(start, end).fill(worldEditor, primaryWall);
    }

    // mid outer floors
    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonal : dir.orthogonals()) {
        Coord start = new Coord(x, y, z);
        Coord end = start.copy();
        start.translate(dir, 9);
        start.translate(orthogonal, 2);
        start.translate(Direction.DOWN, 3);
        end.translate(dir, 8);
        end.translate(orthogonal, 9);
        end.translate(Direction.DOWN, 2);

        RectSolid.newRect(start, end).fill(worldEditor, primaryWall);
        StairsBlock stair = theme.getPrimary().getStair();
        Coord stepCoord = new Coord(x, y, z);
        stepCoord.translate(dir, 8);
        stepCoord.translate(Direction.DOWN, 1);
        stepCoord.translate(orthogonal, 2);
        stair.setUpsideDown(false).setFacing(orthogonal);
        stair.stroke(worldEditor, stepCoord);
        stepCoord.translate(dir, 1);
        stair.stroke(worldEditor, stepCoord);

        stair.setUpsideDown(false).setFacing(dir.reverse());
        stepCoord = new Coord(x, y, z);
        stepCoord.translate(Direction.DOWN, 2);
        stepCoord.translate(dir, 7);
        stepCoord.translate(orthogonal, 3);
        stair.stroke(worldEditor, stepCoord);
        stepCoord.translate(orthogonal, 1);
        stair.stroke(worldEditor, stepCoord);
        stepCoord.translate(Direction.DOWN, 1);
        stepCoord.translate(dir.reverse(), 1);
        stair.stroke(worldEditor, stepCoord);
        stepCoord.translate(orthogonal.reverse(), 1);
        stair.stroke(worldEditor, stepCoord);
        stepCoord.translate(dir, 1);
        primaryWall.stroke(worldEditor, stepCoord);
        stepCoord.translate(orthogonal, 1);
        primaryWall.stroke(worldEditor, stepCoord);

        Coord corner = new Coord(x, y, z);
        corner.translate(dir, 7);
        corner.translate(orthogonal, 7);
        corner.translate(Direction.DOWN, 2);
        primaryWall.stroke(worldEditor, corner);
        corner.translate(Direction.DOWN, 1);
        primaryWall.stroke(worldEditor, corner);
      }
    }

    // chests areas
    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonal : dir.orthogonals()) {
        Coord cursor = new Coord(x, y, z);
        cursor.translate(Direction.DOWN, 2);
        cursor.translate(dir, 3);
        liquidWindow(worldEditor, cursor.copy(), orthogonal, theme);
        cursor.translate(dir, 2);
        liquidWindow(worldEditor, cursor.copy(), orthogonal, theme);

        Coord chestPos = new Coord(x, y, z);
        chestPos.translate(dir, 4);
        chestPos.translate(orthogonal, 2);
        chestPos.translate(Direction.DOWN, 3);

        worldEditor.getTreasureChestEditor().createChest(Dungeon.getLevel(chestPos.getY()), chestPos, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(worldEditor.getRandom(), ChestType.RARE_TREASURES)));
      }
    }

    innerPillars(worldEditor, theme, x, y, z);

    for (Coord space : spawnerLocations) {
      generateSpawner(space);
    }

    BlockJumble crap = new BlockJumble();
    crap.addBlock(theme.getPrimary().getLiquid());
    crap.addBlock(BlockType.SOUL_SAND.getBrush());
    crap.addBlock(BlockType.OBSIDIAN.getBrush());

    Coord start = origin.copy();
    Coord end = start.copy();
    start.translate(Direction.DOWN, 5);
    end.translate(Direction.DOWN, 8);
    start.translate(Direction.NORTH, 6);
    start.translate(Direction.EAST, 6);
    end.translate(Direction.SOUTH, 6);
    end.translate(Direction.WEST, 6);
    RectSolid.newRect(start, end).fill(worldEditor, crap);

    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }

}
