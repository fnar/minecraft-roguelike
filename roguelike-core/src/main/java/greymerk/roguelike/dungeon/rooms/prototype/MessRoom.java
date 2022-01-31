package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class MessRoom extends BaseRoom {

  public MessRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    Theme theme = levelSettings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush panel = theme.getSecondary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    // clear air
    start = origin.copy();
    start.translate(-8, -1, -8);
    end = origin.copy();
    end.translate(8, 5, 8);
    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    start = origin.copy();
    start.translate(-2, 5, -2);
    end = origin.copy();
    end.translate(2, 5, 2);
    RectSolid.newRect(start, end).fill(worldEditor, panel, false, true);

    cursor = origin.copy();
    cursor.up(4);
    levelSettings.getTheme().getPrimary().getLightBlock().stroke(worldEditor, cursor);

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end = start.copy();
      end.up(3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      for (Direction d : Direction.CARDINAL) {
        cursor = end.copy();
        cursor.translate(d);
        stair.setUpsideDown(true).setFacing(d).stroke(worldEditor, cursor);
      }

      start = origin.copy();
      start.translate(dir, 3);
      start.up(4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, wall);


      Direction[] corners = new Direction[]{dir, dir.antiClockwise()};
      if (entrances.size() == 4 && dir == entrances.get(0)) {
        supplyCorner(worldEditor, levelSettings, corners, origin, dir);
      } else {
        corner(worldEditor, levelSettings, corners, origin);
      }

      generateWallDecor(worldEditor, levelSettings, dir, origin);

      cursor = origin.copy()
          .up(4)
          .translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    }

    List<Direction> nonDoors = new ArrayList<>();
    for (Direction dir : Direction.CARDINAL) {
      if (!entrances.contains(dir)) {
        nonDoors.add(dir);
      }
    }

    Collections.shuffle(nonDoors);

    switch (nonDoors.size()) {
      case 3:
        sideTable(worldEditor, levelSettings, nonDoors.get(2), origin);
      case 2:
        fireplace(worldEditor, levelSettings, nonDoors.get(1), origin);
      case 1:
        supplies(worldEditor, levelSettings, nonDoors.get(0), origin);
      default:
    }

    generateDoorways(origin, entrances);

    return this;
  }

  private void supplyCorner(WorldEditor editor, LevelSettings settings, Direction[] corners, Coord origin, Direction entranceDir) {
    Theme theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush panel = theme.getSecondary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy().translate(corners[0], 7).translate(corners[1], 7);
    end = start.copy().up(4);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = origin.copy().translate(corners[0], 4).translate(corners[1], 4).up(4);
    RectSolid.newRect(start, end).fill(editor, panel);

    cursor = origin.copy()
        .translate(corners[0], 7)
        .translate(corners[1], 5)
        .up();

    generateChest(cursor, entranceDir.reverse(), ChestType.FOOD);

    cursor = origin.copy()
        .translate(corners[0], 5)
        .translate(corners[1], 7);
    BlockType.FURNACE.getBrush().setFacing(corners[1].reverse()).stroke(editor, cursor);

    generateEntrywayWithDecor(editor, corners, origin, true);
  }

  private void corner(WorldEditor editor, LevelSettings settings, Direction[] corners, Coord origin) {
    Theme theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush panel = theme.getSecondary().getWall();
    StairsBlock table = theme.getSecondary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy()
        .translate(corners[0], 7)
        .translate(corners[1], 7);
    end = start.copy().up(4);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = origin.copy()
        .translate(corners[0], 4)
        .translate(corners[1], 4)
        .up(4);
    RectSolid.newRect(start, end).fill(editor, panel);

    cursor = origin.copy()
        .translate(corners[0], 4)
        .translate(corners[1], 4);
    table.setUpsideDown(true).setFacing(corners[0].reverse()).stroke(editor, cursor);
    cursor.translate(corners[1]);
    table.setUpsideDown(true).setFacing(corners[1]).stroke(editor, cursor);
    cursor.translate(corners[0]);
    table.setUpsideDown(true).setFacing(corners[0]).stroke(editor, cursor);
    cursor.translate(corners[1].reverse());
    table.setUpsideDown(true).setFacing(corners[1].reverse()).stroke(editor, cursor);
    generateEntrywayWithDecor(editor, corners, origin, false);
  }

  private void generateEntrywayWithDecor(WorldEditor editor, Direction[] entrances, Coord origin, boolean areSomeStairsUpsideDown) {
    Theme theme = levelSettings.getTheme();
    BlockSet primaryBlockSet = theme.getPrimary();
    BlockBrush wall = primaryBlockSet.getWall();
    BlockBrush pillar = primaryBlockSet.getPillar();
    StairsBlock stair = primaryBlockSet.getStair();
    BlockBrush panel = theme.getSecondary().getWall();

    for (Direction dir : entrances) {
      Coord cursor = origin.copy();
      cursor.translate(entrances[0], 3);
      cursor.translate(entrances[1], 3);
      cursor.translate(dir, 4);
      Coord start = cursor.copy();
      Coord end = cursor.copy();
      end.up(3);
      RectSolid.newRect(start, end).fill(editor, pillar);
      cursor.up(3);
      cursor.translate(dir.reverse());
      wall.stroke(editor, cursor);
      cursor.down();
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.up();
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.up();
      cursor.translate(dir.reverse());
      start = cursor.copy();
      end = cursor.copy();
      end.translate(dir, 3);
      RectSolid.newRect(start, end).fill(editor, wall);

      cursor = origin.copy();
      cursor.translate(entrances[0], 5);
      cursor.translate(entrances[1], 5);
      cursor.translate(dir, 2);
      start = cursor.copy();
      start.translate(dir.antiClockwise());
      end = cursor.copy();
      end.translate(dir.clockwise());
      stair.setUpsideDown(areSomeStairsUpsideDown).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
      start.up(2);
      end.up(2);
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, start);
      stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, end);
      start.up();
      end.up();
      RectSolid.newRect(start, end).fill(editor, wall);
      start.translate(dir);
      end.translate(dir);
      end.down(3);
      RectSolid.newRect(start, end).fill(editor, panel, false, true);
    }
  }

  private void generateWallDecor(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {
    Theme theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush panel = theme.getSecondary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord start = origin.copy().translate(dir, 7).up(3).translate(dir.left(), 2);
    Coord end = origin.copy().translate(dir, 7).up(4).translate(dir.right(), 2);
    RectSolid.newRect(start, end).fill(editor, wall);

    Coord cursor;
    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 7);
      cursor.translate(o, 2);
      cursor.up(2);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);

      cursor.translate(dir.reverse());
      cursor.up(2);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o).stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.translate(dir, 6);
    cursor.up(3);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.up();
    wall.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

    start = origin.copy();
    start.up(5);
    start.translate(dir, 4);
    end = start.copy();
    end.translate(dir);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, panel, false, true);
  }

  private void fireplace(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {
    StairsBlock stair = settings.getTheme().getPrimary().getStair();

    RectSolid behindMantle = RectSolid.newRect(
        origin.copy().translate(dir, 7).translate(dir.left(), 2),
        origin.copy().translate(dir, 8).translate(dir.right(), 2).up(2));
    levelSettings.getTheme().getPrimary().getWall().fill(editor, behindMantle);

    RectSolid fireArea = RectSolid.newRect(
        origin.copy().translate(dir, 7).translate(dir.left()),
        origin.copy().translate(dir, 7).translate(dir.right()).up());
    SingleBlockBrush.AIR.fill(editor, fireArea);

    Coord cursor = origin.copy();
    cursor.translate(dir, 6);
    BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
    cursor.translate(dir);
    cursor.down();
    BlockType.NETHERRACK.getBrush().stroke(editor, cursor);
    cursor.up();
    BlockType.FIRE.getBrush().stroke(editor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy().translate(dir, 6).translate(o);
      BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
      cursor.translate(o);
      settings.getTheme().getPrimary().getWall().stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.up().translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);

      cursor = origin.copy().translate(dir, 6).translate(o);
      BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
      cursor.translate(dir).down();
      BlockType.NETHERRACK.getBrush().stroke(editor, cursor);
      cursor.up();
      BlockType.FIRE.getBrush().stroke(editor, cursor);
    }
  }

  private void supplies(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {
    Theme theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;


    cursor = origin.copy();
    cursor.translate(dir, 7);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.up();
    generateChest(cursor, dir, ChestType.FOOD);
    cursor.translate(dir.antiClockwise());
    BlockType.FURNACE.getBrush().setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir.clockwise(), 2);
    BlockType.CRAFTING_TABLE.getBrush().stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 7);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
    }


  }

  private void sideTable(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {
    Theme theme = settings.getTheme();
    StairsBlock table = theme.getSecondary().getStair();
    Coord cursor = origin.copy();

    cursor.translate(dir, 5);
    table.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir);
    table.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.translate(o);
      table.setUpsideDown(true).setFacing(o).stroke(editor, cursor);
      cursor.translate(dir);
      table.setUpsideDown(true).setFacing(o).stroke(editor, cursor);
    }
  }

  public int getSize() {
    return 10;
  }
}
