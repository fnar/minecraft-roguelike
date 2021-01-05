package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.decorative.AnvilBlock;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.SlabBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
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

public class DungeonsSmithy extends DungeonBase {

  public DungeonsSmithy(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = settings.getTheme();

    Coord cursor;

    Cardinal dir = entrances.get(0);

    clearBoxes(editor, theme, dir, origin);

    cursor = new Coord(origin);
    cursor.translate(dir, 6);
    sideRoom(editor, settings, dir, cursor);
    anvilRoom(editor, settings, dir, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 6);
    sideRoom(editor, settings, dir, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 9);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    mainRoom(editor, settings, dir, origin);

    return this;
  }

  private void sideRoom(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {

    ThemeBase theme = settings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();

    for (Cardinal side : dir.orthogonals()) {

      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(side, 2);
      start.translate(dir.reverse(), 2);
      end.translate(side, 3);
      end.translate(dir, 2);
      RectSolid.newRect(start, end).fill(editor, wall);

      start.translate(dir);
      end = new Coord(start);
      end.translate(dir, 2);
      RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(true).setFacing(side.reverse()));

      for (Cardinal o : side.orthogonals()) {
        start = new Coord(origin);
        start.translate(side, 3);
        start.translate(o, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        RectSolid.newRect(start, end).fill(editor, pillar);

        cursor = new Coord(end);
        cursor.translate(side.reverse());
        stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        cursor.translate(side.reverse());
        stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);

        cursor = new Coord(end);
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      }
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    overheadLight(editor, settings, cursor);
  }

  private void clearBoxes(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord origin) {

    BlockBrush wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    // end room
    cursor = new Coord(origin);
    cursor.translate(dir, 6);

    start = new Coord(cursor);
    start.translate(Cardinal.DOWN);
    start.translate(dir, 3);
    start.translate(dir.antiClockwise(), 4);

    end = new Coord(cursor);
    end.translate(Cardinal.UP, 4);
    end.translate(dir.reverse(), 3);
    end.translate(dir.clockwise(), 4);

    RectHollow.newRect(start, end).fill(editor, wall);

    // entrance
    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 6);

    start = new Coord(cursor);
    start.translate(Cardinal.DOWN);
    start.translate(dir, 3);
    start.translate(dir.antiClockwise(), 4);

    end = new Coord(cursor);
    end.translate(Cardinal.UP, 4);
    end.translate(dir.reverse(), 3);
    end.translate(dir.clockwise(), 4);

    RectHollow.newRect(start, end).fill(editor, wall);

    // middle

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    start.translate(dir.antiClockwise(), 6);
    start.translate(dir.reverse(), 4);

    end = new Coord(origin);
    end.translate(Cardinal.UP, 6);
    end.translate(dir.clockwise(), 6);
    end.translate(dir, 4);

    RectHollow.newRect(start, end).fill(editor, wall, false, true);

  }

  private void mainRoom(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {

    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(dir, 3);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, wall);
    start.translate(dir.reverse(), 6);
    end.translate(dir.reverse(), 6);
    RectSolid.newRect(start, end).fill(editor, wall);

    for (Cardinal side : dir.orthogonals()) {
      for (Cardinal o : side.orthogonals()) {
        cursor = new Coord(origin);
        cursor.translate(side, 2);
        cursor.translate(o, 3);
        mainPillar(editor, theme, o, cursor);
        cursor.translate(side, 3);
        mainPillar(editor, theme, o, cursor);
      }
    }

    smelterSide(editor, settings, dir.antiClockwise(), origin);
    fireplace(editor, dir.clockwise(), origin);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 6);
    overheadLight(editor, settings, cursor);

  }

  private void mainPillar(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord origin) {
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(editor, pillar);
    cursor = new Coord(end);
    cursor.translate(dir.antiClockwise());
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor = new Coord(end);
    cursor.translate(dir.clockwise());
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
    cursor = new Coord(end);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    wall.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.translate(dir, 2);
    RectSolid.newRect(start, end).fill(editor, wall);
    cursor = new Coord(end);
    cursor.translate(dir.antiClockwise());
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor = new Coord(end);
    cursor.translate(dir.clockwise());
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
  }


  private void smelterSide(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {

    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(dir, 5);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, wall);
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getStair().setUpsideDown(false).setFacing(dir.reverse()));


    for (Cardinal o : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(o);
      smelter(editor, dir, cursor);

      cursor.translate(o, 2);
      wall.stroke(editor, cursor);
      cursor.translate(dir);
      wall.stroke(editor, cursor);
    }
  }

  private void smelter(WorldEditor editor, Cardinal dir, Coord origin) {
    editor.getTreasureChestEditor().createChest(1, origin, false, ChestType.EMPTY);

    Coord cursor;
    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    cursor.translate(Cardinal.UP, 2);
    editor.getTreasureChestEditor().createChest(1, cursor, false, ChestType.EMPTY);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir.reverse());
    editor.getTreasureChestEditor().createChest(1, cursor, false, ChestType.EMPTY);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir);
    BlockType.FURNACE.getBrush().setFacing(dir).stroke(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir);
    BlockType.HOPPER.getBrush().setFacing(dir).stroke(editor, cursor);

    cursor.translate(dir);
    cursor.translate(Cardinal.UP);
    BlockType.HOPPER.getBrush().setFacing(dir).stroke(editor, cursor);

    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP);
    BlockType.HOPPER.getBrush().setFacing(Cardinal.DOWN).stroke(editor, cursor);
  }

  private void fireplace(WorldEditor editor, Cardinal dir, Coord origin) {

    StairsBlock stair = StairsBlock.brick();
    BlockBrush brick = BlockType.BRICK.getBrush();
    BlockBrush brickSlab = SlabBlock.brick();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(dir, 4);
    end = new Coord(start);
    start.translate(Cardinal.DOWN);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(dir, 2);
    end.translate(Cardinal.UP, 5);

    RectSolid.newRect(start, end).fill(editor, brick);

    start = new Coord(origin);
    start.translate(dir, 5);
    end = new Coord(start);
    end.translate(Cardinal.UP, 5);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir, 4);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Cardinal side : dir.orthogonals()) {

      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(side);

      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      bars.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      bars.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(side).stroke(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(side);
      brick.stroke(editor, cursor);
      cursor.translate(dir);
      brick.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(side, 2);
      brick.stroke(editor, cursor);
      cursor.translate(dir);
      brick.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      brick.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(Cardinal.UP, 5);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    }

    BlockBrush netherrack = BlockType.NETHERRACK.getBrush();
    BlockBrush fire = BlockType.FIRE.getBrush();

    start = new Coord(origin);
    start.translate(dir, 5);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, netherrack);
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, fire);

    cursor = new Coord(origin);
    cursor.translate(dir, 3);
    brickSlab.stroke(editor, cursor);
    cursor.translate(dir);
    brickSlab.stroke(editor, cursor);

  }

  private void anvilRoom(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {

    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush anvil = AnvilBlock.anvil().setFacing(dir.antiClockwise());
    cursor = new Coord(origin);
    cursor.translate(dir);
    anvil.stroke(editor, cursor);

    start = new Coord(origin);
    start.translate(dir.clockwise(), 2);
    end = new Coord(start);
    start.translate(dir, 2);
    end.translate(dir.reverse(), 2);
    stair.setUpsideDown(false).setFacing(dir.antiClockwise());
    RectSolid.newRect(start, end).fill(editor, stair);

    cursor = new Coord(origin);
    cursor.translate(dir.clockwise(), 3);
    wall.stroke(editor, cursor);
    cursor.translate(dir);
    BlockType.WATER_FLOWING.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    BlockType.LAVA_FLOWING.getBrush().stroke(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.antiClockwise(), 3);
    start = new Coord(cursor);
    end = new Coord(start);
    start.translate(dir);
    end.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, stair);
    cursor.translate(Cardinal.UP);
    editor.getTreasureChestEditor().createChest(Dungeon.getLevel(cursor.getY()), cursor, false, getRoomSetting().getChestType().orElse(ChestType.SMITH));
  }


  private void overheadLight(WorldEditor editor, LevelSettings settings, Coord origin) {

    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord cursor;

    SingleBlockBrush.AIR.stroke(editor, origin);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(dir.antiClockwise());
      stair.stroke(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 2);
    BlockType.REDSTONE_BLOCK.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.DOWN);
    BlockType.REDSTONE_LAMP_LIT.getBrush().stroke(editor, cursor);
  }

  public int getSize() {
    return 9;
  }

}
