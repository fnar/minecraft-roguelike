package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonMess extends DungeonBase {

  public DungeonMess(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

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
    cursor.translate(Cardinal.UP, 4);
    BlockType.GLOWSTONE.getBrush().stroke(worldEditor, cursor);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end = start.copy();
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      for (Cardinal d : Cardinal.DIRECTIONS) {
        cursor = end.copy();
        cursor.translate(d);
        stair.setUpsideDown(true).setFacing(d).stroke(worldEditor, cursor);
      }

      start = origin.copy();
      start.translate(dir, 3);
      start.translate(Cardinal.UP, 4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, wall);


      Cardinal[] corner = new Cardinal[]{dir, dir.antiClockwise()};
      if (entrances.size() == 4 && dir == entrances.get(0)) {
        supplyCorner(worldEditor, levelSettings, corner, origin);
      } else {
        corner(worldEditor, levelSettings, corner, origin);
      }

      doorway(worldEditor, levelSettings, dir, origin);

      cursor = origin.copy();
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    }

    List<Cardinal> nonDoors = new ArrayList<>();
    for (Cardinal dir : Cardinal.DIRECTIONS) {
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

  private void supplyCorner(WorldEditor editor, LevelSettings settings, Cardinal[] entrances, Coord origin) {
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
    end.translate(Cardinal.UP, 4);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = origin.copy();
    start.translate(entrances[0], 4);
    start.translate(entrances[1], 4);
    start.translate(Cardinal.UP, 4);
    RectSolid.newRect(start, end).fill(editor, panel);

    cursor = origin.copy();
    cursor.translate(entrances[0], 5);
    cursor.translate(entrances[1], 5);
    cursor.translate(entrances[0], 2);
    cursor.translate(Cardinal.UP);

    editor.getTreasureChestEditor().createChest(settings.getDifficulty(cursor), cursor, false, ChestType.FOOD);

    cursor = origin.copy();
    cursor.translate(entrances[0], 5);
    cursor.translate(entrances[1], 5);
    cursor.translate(entrances[1], 2);
    BlockType.FURNACE.getBrush().setFacing(entrances[1].reverse()).stroke(editor, cursor);

    for (Cardinal dir : entrances) {
      cursor = origin.copy();
      cursor.translate(entrances[0], 3);
      cursor.translate(entrances[1], 3);
      cursor.translate(dir, 4);
      start = cursor.copy();
      end = cursor.copy();
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, pillar);
      cursor.translate(Cardinal.UP, 3);
      cursor.translate(dir.reverse());
      wall.stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
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
      start.translate(Cardinal.UP, 2);
      end.translate(Cardinal.UP, 2);
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, start);
      stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, end);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.newRect(start, end).fill(editor, wall);
      start.translate(dir);
      end.translate(dir);
      end.translate(Cardinal.DOWN, 3);
      RectSolid.newRect(start, end).fill(editor, panel, false, true);
    }
  }

  private void corner(WorldEditor editor, LevelSettings settings, Cardinal[] entrances, Coord origin) {
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
    end.translate(Cardinal.UP, 4);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = origin.copy();
    start.translate(entrances[0], 4);
    start.translate(entrances[1], 4);
    start.translate(Cardinal.UP, 4);
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


    for (Cardinal dir : entrances) {
      cursor = origin.copy();
      cursor.translate(entrances[0], 3);
      cursor.translate(entrances[1], 3);
      cursor.translate(dir, 4);
      start = cursor.copy();
      end = cursor.copy();
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, pillar);
      cursor.translate(Cardinal.UP, 3);
      cursor.translate(dir.reverse());
      wall.stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
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
      start.translate(Cardinal.UP, 2);
      end.translate(Cardinal.UP, 2);
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, start);
      stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, end);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.newRect(start, end).fill(editor, wall);
      start.translate(dir);
      end.translate(dir);
      end.translate(Cardinal.DOWN, 3);
      RectSolid.newRect(start, end).fill(editor, panel, false, true);
    }
  }

  private void doorway(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {
    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush panel = theme.getSecondary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(dir, 7);
    start.translate(Cardinal.UP, 3);
    end = start.copy();
    end.translate(Cardinal.UP);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, wall);

    for (Cardinal o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 7);
      cursor.translate(o, 2);
      cursor.translate(Cardinal.UP, 2);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);

      cursor.translate(dir.reverse());
      cursor.translate(Cardinal.UP, 2);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o).stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.translate(dir, 6);
    cursor.translate(Cardinal.UP, 3);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    wall.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

    start = origin.copy();
    start.translate(Cardinal.UP, 5);
    start.translate(dir, 4);
    end = start.copy();
    end.translate(dir);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, panel, false, true);


  }

  private void fireplace(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {
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
    end.translate(Cardinal.UP, 2);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = origin.copy();
    start.translate(dir, 7);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    cursor = origin.copy();
    cursor.translate(dir, 6);
    bars.stroke(editor, cursor);
    cursor.translate(dir);
    cursor.translate(Cardinal.DOWN);
    netherrack.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    fire.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 6);
      cursor.translate(o);
      bars.stroke(editor, cursor);
      cursor.translate(o);
      wall.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(o).stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 6);
      cursor.translate(o);
      bars.stroke(editor, cursor);
      cursor.translate(dir);
      cursor.translate(Cardinal.DOWN);
      netherrack.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      fire.stroke(editor, cursor);
    }
  }

  private void supplies(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {
    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;


    cursor = origin.copy();
    cursor.translate(dir, 7);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    editor.getTreasureChestEditor().createChest(settings.getDifficulty(origin), cursor, false, getRoomSetting().getChestType().orElse(ChestType.FOOD));
    cursor.translate(dir.antiClockwise());
    BlockType.FURNACE.getBrush().setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir.clockwise(), 2);
    BlockType.CRAFTING_TABLE.getBrush().stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 7);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
    }


  }

  private void sideTable(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {
    ThemeBase theme = settings.getTheme();
    StairsBlock table = theme.getSecondary().getStair();
    Coord cursor = origin.copy();

    cursor.translate(dir, 5);
    table.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir);
    table.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {
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
