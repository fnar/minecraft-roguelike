package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.AnvilBlock;
import com.github.srwaggon.minecraft.block.normal.SlabBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
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

public class DungeonsSmithy extends DungeonBase {

  public DungeonsSmithy(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    ThemeBase theme = levelSettings.getTheme();

    Coord cursor;

    Direction dir = entrances.get(0);

    clearBoxes(worldEditor, theme, dir, origin);

    cursor = origin.copy();
    cursor.translate(dir, 6);
    sideRoom(worldEditor, levelSettings, dir, cursor);
    anvilRoom(worldEditor, levelSettings, dir, cursor);

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 6);
    sideRoom(worldEditor, levelSettings, dir, cursor);

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 9);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    mainRoom(worldEditor, levelSettings, dir, origin);

    return this;
  }

  private void sideRoom(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {

    ThemeBase theme = settings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();

    for (Direction side : dir.orthogonals()) {

      start = origin.copy();
      start.up(3);
      end = start.copy();
      start.translate(side, 2);
      start.translate(dir.reverse(), 2);
      end.translate(side, 3);
      end.translate(dir, 2);
      RectSolid.newRect(start, end).fill(editor, wall);

      start.translate(dir);
      end = start.copy();
      end.translate(dir, 2);
      RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(true).setFacing(side.reverse()));

      for (Direction o : side.orthogonals()) {
        start = origin.copy();
        start.translate(side, 3);
        start.translate(o, 2);
        end = start.copy();
        end.up(2);
        RectSolid.newRect(start, end).fill(editor, pillar);

        cursor = end.copy();
        cursor.translate(side.reverse());
        stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
        cursor.up();
        cursor.translate(side.reverse());
        stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);

        cursor = end.copy();
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      }
    }

    cursor = origin.copy();
    cursor.up(4);
    overheadLight(editor, settings, cursor);
  }

  private void clearBoxes(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {

    BlockBrush wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    // end room
    cursor = origin.copy();
    cursor.translate(dir, 6);

    start = cursor.copy();
    start.down();
    start.translate(dir, 3);
    start.translate(dir.antiClockwise(), 4);

    end = cursor.copy();
    end.up(4);
    end.translate(dir.reverse(), 3);
    end.translate(dir.clockwise(), 4);

    RectHollow.newRect(start, end).fill(editor, wall);

    // entrance
    cursor = origin.copy();
    cursor.translate(dir.reverse(), 6);

    start = cursor.copy();
    start.down();
    start.translate(dir, 3);
    start.translate(dir.antiClockwise(), 4);

    end = cursor.copy();
    end.up(4);
    end.translate(dir.reverse(), 3);
    end.translate(dir.clockwise(), 4);

    RectHollow.newRect(start, end).fill(editor, wall);

    // middle

    start = origin.copy();
    start.down();
    start.translate(dir.antiClockwise(), 6);
    start.translate(dir.reverse(), 4);

    end = origin.copy();
    end.up(6);
    end.translate(dir.clockwise(), 6);
    end.translate(dir, 4);

    RectHollow.newRect(start, end).fill(editor, wall, false, true);

  }

  private void mainRoom(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {

    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(dir, 3);
    start.up(4);
    end = start.copy();
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    end.up();
    RectSolid.newRect(start, end).fill(editor, wall);
    start.translate(dir.reverse(), 6);
    end.translate(dir.reverse(), 6);
    RectSolid.newRect(start, end).fill(editor, wall);

    for (Direction side : dir.orthogonals()) {
      for (Direction o : side.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(side, 2);
        cursor.translate(o, 3);
        mainPillar(editor, theme, o, cursor);
        cursor.translate(side, 3);
        mainPillar(editor, theme, o, cursor);
      }
    }

    smelterSide(editor, settings, dir.antiClockwise(), origin);
    fireplace(editor, dir.clockwise(), origin);

    cursor = origin.copy();
    cursor.up(6);
    overheadLight(editor, settings, cursor);

  }

  private void mainPillar(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    end.up(3);
    RectSolid.newRect(start, end).fill(editor, pillar);
    cursor = end.copy();
    cursor.translate(dir.antiClockwise());
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor = end.copy();
    cursor.translate(dir.clockwise());
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
    cursor = end.copy();
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.up();
    wall.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    cursor.up();
    start = cursor.copy();
    end = cursor.copy();
    end.translate(dir, 2);
    RectSolid.newRect(start, end).fill(editor, wall);
    cursor = end.copy();
    cursor.translate(dir.antiClockwise());
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor = end.copy();
    cursor.translate(dir.clockwise());
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
  }


  private void smelterSide(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {

    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(dir, 5);
    end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, wall);
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getStair().setUpsideDown(false).setFacing(dir.reverse()));


    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.translate(o);
      smelter(editor, dir, cursor);

      cursor.translate(o, 2);
      wall.stroke(editor, cursor);
      cursor.translate(dir);
      wall.stroke(editor, cursor);
    }
  }

  private void smelter(WorldEditor editor, Direction dir, Coord origin) {
    editor.getTreasureChestEditor().createChest(origin, false, 1, dir, ChestType.EMPTY);

    Coord cursor;
    cursor = origin.copy();
    cursor.translate(dir, 2);
    cursor.up(2);
    editor.getTreasureChestEditor().createChest(cursor, false, 1, dir, ChestType.EMPTY);
    cursor.up();
    cursor.translate(dir.reverse());
    editor.getTreasureChestEditor().createChest(cursor, false, 1, dir, ChestType.EMPTY);

    cursor = origin.copy();
    cursor.up();
    cursor.translate(dir);
    BlockType.FURNACE.getBrush().setFacing(dir).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(dir);
    BlockType.HOPPER.getBrush().setFacing(dir).stroke(editor, cursor);

    cursor.translate(dir);
    cursor.up();
    BlockType.HOPPER.getBrush().setFacing(dir).stroke(editor, cursor);

    cursor.translate(dir.reverse());
    cursor.up();
    BlockType.HOPPER.getBrush().setFacing(Direction.DOWN).stroke(editor, cursor);
  }

  private void fireplace(WorldEditor editor, Direction dir, Coord origin) {

    StairsBlock stair = StairsBlock.brick();
    BlockBrush brick = BlockType.BRICK.getBrush();
    BlockBrush brickSlab = SlabBlock.brick();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(dir, 4);
    end = start.copy();
    start.down();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(dir, 2);
    end.up(5);

    RectSolid.newRect(start, end).fill(editor, brick);

    start = origin.copy();
    start.translate(dir, 5);
    end = start.copy();
    end.up(5);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    cursor = origin.copy();
    cursor.up();
    cursor.translate(dir, 4);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Direction side : dir.orthogonals()) {

      cursor = origin.copy();
      cursor.translate(dir, 4);
      cursor.translate(side);

      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
      cursor.up();
      bars.stroke(editor, cursor);
      cursor.up();
      bars.stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(side).stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(side);
      brick.stroke(editor, cursor);
      cursor.translate(dir);
      brick.stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 4);
      cursor.translate(side, 2);
      brick.stroke(editor, cursor);
      cursor.translate(dir);
      brick.stroke(editor, cursor);
      cursor.up();
      brick.stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.down();
      cursor.translate(dir.reverse());
      stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.up(5);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    }

    BlockBrush netherrack = BlockType.NETHERRACK.getBrush();
    BlockBrush fire = BlockType.FIRE.getBrush();

    start = origin.copy();
    start.translate(dir, 5);
    start.down();
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, netherrack);
    start.up();
    end.up();
    RectSolid.newRect(start, end).fill(editor, fire);

    cursor = origin.copy();
    cursor.translate(dir, 3);
    brickSlab.stroke(editor, cursor);
    cursor.translate(dir);
    brickSlab.stroke(editor, cursor);

  }

  private void anvilRoom(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {

    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush anvil = AnvilBlock.anvil().setFacing(dir.antiClockwise());
    cursor = origin.copy();
    cursor.translate(dir);
    anvil.stroke(editor, cursor);

    start = origin.copy();
    start.translate(dir.clockwise(), 2);
    end = start.copy();
    start.translate(dir, 2);
    end.translate(dir.reverse(), 2);
    stair.setUpsideDown(false).setFacing(dir.antiClockwise());
    RectSolid.newRect(start, end).fill(editor, stair);

    cursor = origin.copy();
    cursor.translate(dir.clockwise(), 3);
    wall.stroke(editor, cursor);
    cursor.translate(dir);
    BlockType.WATER_FLOWING.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    BlockType.LAVA_FLOWING.getBrush().stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(dir.antiClockwise(), 3);
    start = cursor.copy();
    end = start.copy();
    start.translate(dir);
    end.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, stair);
    cursor.up();
    editor.getTreasureChestEditor().createChest(cursor, false, Dungeon.getLevel(cursor.getY()), dir, getRoomSetting().getChestType().orElse(ChestType.SMITH));
  }


  private void overheadLight(WorldEditor editor, LevelSettings settings, Coord origin) {

    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord cursor;

    SingleBlockBrush.AIR.stroke(editor, origin);

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(dir.antiClockwise());
      stair.stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.up(2);
    BlockType.REDSTONE_BLOCK.getBrush().stroke(editor, cursor);
    cursor.down();
    BlockType.REDSTONE_LAMP_LIT.getBrush().stroke(editor, cursor);
  }

  public int getSize() {
    return 9;
  }

}
