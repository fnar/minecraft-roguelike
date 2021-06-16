package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonMess extends DungeonBase {

  public DungeonMess(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    ThemeBase theme = levelSettings.getTheme();
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


      Direction[] corner = new Direction[]{dir, dir.antiClockwise()};
      if (entrances.size() == 4 && dir == entrances.get(0)) {
        supplyCorner(worldEditor, levelSettings, corner, origin, dir);
      } else {
        corner(worldEditor, levelSettings, corner, origin);
      }

      doorway(worldEditor, levelSettings, dir, origin);

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir);
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


    return this;
  }

  private void supplyCorner(WorldEditor editor, LevelSettings settings, Direction[] entrances, Coord origin, Direction entranceDir) {
    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush panel = theme.getSecondary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(entrances[0], 7);
    start.translate(entrances[1], 7);
    end = start.copy();
    end.up(4);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = origin.copy();
    start.translate(entrances[0], 4);
    start.translate(entrances[1], 4);
    start.up(4);
    RectSolid.newRect(start, end).fill(editor, panel);

    cursor = origin.copy();
    cursor.translate(entrances[0], 5);
    cursor.translate(entrances[1], 5);
    cursor.translate(entrances[0], 2);
    cursor.up();

    editor.getTreasureChestEditor().createChest(cursor, false, settings.getDifficulty(cursor), entranceDir.reverse(), ChestType.FOOD);

    cursor = origin.copy();
    cursor.translate(entrances[0], 5);
    cursor.translate(entrances[1], 5);
    cursor.translate(entrances[1], 2);
    BlockType.FURNACE.getBrush().setFacing(entrances[1].reverse()).stroke(editor, cursor);

    for (Direction dir : entrances) {
      cursor = origin.copy();
      cursor.translate(entrances[0], 3);
      cursor.translate(entrances[1], 3);
      cursor.translate(dir, 4);
      start = cursor.copy();
      end = cursor.copy();
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
      stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
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

  private void corner(WorldEditor editor, LevelSettings settings, Direction[] entrances, Coord origin) {
    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush panel = theme.getSecondary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    StairsBlock table = theme.getSecondary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(entrances[0], 7);
    start.translate(entrances[1], 7);
    end = start.copy();
    end.up(4);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = origin.copy();
    start.translate(entrances[0], 4);
    start.translate(entrances[1], 4);
    start.up(4);
    RectSolid.newRect(start, end).fill(editor, panel);

    cursor = origin.copy();
    cursor.translate(entrances[0], 4);
    cursor.translate(entrances[1], 4);
    table.setUpsideDown(true).setFacing(entrances[0].reverse()).stroke(editor, cursor);
    cursor.translate(entrances[1]);
    table.setUpsideDown(true).setFacing(entrances[1]).stroke(editor, cursor);
    cursor.translate(entrances[0]);
    table.setUpsideDown(true).setFacing(entrances[0]).stroke(editor, cursor);
    cursor.translate(entrances[1].reverse());
    table.setUpsideDown(true).setFacing(entrances[1].reverse()).stroke(editor, cursor);


    for (Direction dir : entrances) {
      cursor = origin.copy();
      cursor.translate(entrances[0], 3);
      cursor.translate(entrances[1], 3);
      cursor.translate(dir, 4);
      start = cursor.copy();
      end = cursor.copy();
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
      stair.setUpsideDown(false).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
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

  private void doorway(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {
    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush panel = theme.getSecondary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(dir, 7);
    start.up(3);
    end = start.copy();
    end.up();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, wall);

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
    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();
    BlockBrush netherrack = BlockType.NETHERRACK.getBrush();
    BlockBrush fire = BlockType.FIRE.getBrush();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(dir, 7);
    end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.up(2);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = origin.copy();
    start.translate(dir, 7);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.up();
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    cursor = origin.copy();
    cursor.translate(dir, 6);
    bars.stroke(editor, cursor);
    cursor.translate(dir);
    cursor.down();
    netherrack.stroke(editor, cursor);
    cursor.up();
    fire.stroke(editor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 6);
      cursor.translate(o);
      bars.stroke(editor, cursor);
      cursor.translate(o);
      wall.stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.up();
      cursor.translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 6);
      cursor.translate(o);
      bars.stroke(editor, cursor);
      cursor.translate(dir);
      cursor.down();
      netherrack.stroke(editor, cursor);
      cursor.up();
      fire.stroke(editor, cursor);
    }
  }

  private void supplies(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {
    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;


    cursor = origin.copy();
    cursor.translate(dir, 7);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.up();
    editor.getTreasureChestEditor().createChest(cursor, false, settings.getDifficulty(origin), dir, getRoomSetting().getChestType().orElse(ChestType.FOOD));
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
    ThemeBase theme = settings.getTheme();
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
