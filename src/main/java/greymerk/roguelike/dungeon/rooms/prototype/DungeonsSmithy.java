package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.Anvil;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Hopper;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsSmithy extends DungeonBase {

  public DungeonsSmithy(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();

    Coord cursor;

    Cardinal dir = entrances[0];

    clearBoxes(editor, rand, theme, dir, origin);

    cursor = new Coord(origin);
    cursor.translate(dir, 6);
    sideRoom(editor, rand, settings, dir, cursor);
    anvilRoom(editor, rand, settings, dir, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 6);
    sideRoom(editor, rand, settings, dir, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 9);
    MetaBlock air = BlockType.get(BlockType.AIR);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    air.set(editor, cursor);

    mainRoom(editor, rand, settings, dir, origin);

    return this;
  }

  private void sideRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();

    for (Cardinal side : dir.orthogonal()) {

      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(side, 2);
      start.translate(dir.reverse(), 2);
      end.translate(side, 3);
      end.translate(dir, 2);
      RectSolid.fill(editor, rand, start, end, wall);

      start.translate(dir);
      end = new Coord(start);
      end.translate(dir, 2);
      RectSolid.fill(editor, rand, start, end, stair.setOrientation(side.reverse(), true));

      for (Cardinal o : side.orthogonal()) {
        start = new Coord(origin);
        start.translate(side, 3);
        start.translate(o, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor = new Coord(end);
        cursor.translate(side.reverse());
        stair.setOrientation(side.reverse(), true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        cursor.translate(side.reverse());
        stair.setOrientation(side.reverse(), true).set(editor, cursor);

        cursor = new Coord(end);
        cursor.translate(o.reverse());
        stair.setOrientation(o.reverse(), true).set(editor, cursor);
      }
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    overheadLight(editor, settings, cursor);
  }

  private void clearBoxes(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    IBlockFactory wall = theme.getPrimary().getWall();


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

    RectHollow.fill(editor, rand, start, end, wall);

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

    RectHollow.fill(editor, rand, start, end, wall);

    // middle

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    start.translate(dir.antiClockwise(), 6);
    start.translate(dir.reverse(), 4);

    end = new Coord(origin);
    end.translate(Cardinal.UP, 6);
    end.translate(dir.clockwise(), 6);
    end.translate(dir, 4);

    RectHollow.fill(editor, rand, start, end, wall, false, true);

  }

  private void mainRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
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
    RectSolid.fill(editor, rand, start, end, wall);
    start.translate(dir.reverse(), 6);
    end.translate(dir.reverse(), 6);
    RectSolid.fill(editor, rand, start, end, wall);

    for (Cardinal side : dir.orthogonal()) {
      for (Cardinal o : side.orthogonal()) {
        cursor = new Coord(origin);
        cursor.translate(side, 2);
        cursor.translate(o, 3);
        mainPillar(editor, rand, theme, o, cursor);
        cursor.translate(side, 3);
        mainPillar(editor, rand, theme, o, cursor);
      }
    }

    cursor = new Coord(origin);
    smelterSide(editor, rand, settings, dir.antiClockwise(), origin);
    fireplace(editor, rand, dir.clockwise(), origin);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 6);
    overheadLight(editor, settings, cursor);

  }

  private void mainPillar(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, pillar);
    cursor = new Coord(end);
    cursor.translate(dir.antiClockwise());
    stair.setOrientation(dir.antiClockwise(), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.translate(dir.clockwise());
    stair.setOrientation(dir.clockwise(), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    wall.set(editor, rand, cursor);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.translate(dir, 2);
    RectSolid.fill(editor, rand, start, end, wall);
    cursor = new Coord(end);
    cursor.translate(dir.antiClockwise());
    stair.setOrientation(dir.antiClockwise(), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.translate(dir.clockwise());
    stair.setOrientation(dir.clockwise(), true).set(editor, cursor);
  }


  private void smelterSide(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(dir, 5);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, rand, start, end, wall);
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    IStair stair = theme.getPrimary().getStair();
    stair = stair.setOrientation(dir.reverse(), false);
    RectSolid.fill(editor, rand, start, end, stair);


    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(o);
      smelter(editor, dir, cursor);

      cursor.translate(o, 2);
      wall.set(editor, rand, cursor);
      cursor.translate(dir);
      wall.set(editor, rand, cursor);
    }
  }

  private void smelter(WorldEditor editor, Cardinal dir, Coord origin) {
    editor.treasureChestEditor.createChest(1, origin, false, ChestType.EMPTY);

    Coord cursor;
    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    cursor.translate(Cardinal.UP, 2);
    editor.treasureChestEditor.createChest(1, cursor, false, ChestType.EMPTY);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir.reverse());
    editor.treasureChestEditor.createChest(1, cursor, false, ChestType.EMPTY);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir);
    Furnace.generate(editor, dir.reverse(), cursor);

    cursor = new Coord(origin);
    cursor.translate(dir);
    Hopper.generate(editor, dir.reverse(), cursor);

    cursor.translate(dir);
    cursor.translate(Cardinal.UP);
    Hopper.generate(editor, dir.reverse(), cursor);

    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP);
    Hopper.generate(editor, Cardinal.DOWN, cursor);
  }

  private void fireplace(WorldEditor editor, Random rand, Cardinal dir, Coord origin) {

    IStair stair = new MetaStair(StairType.BRICK);
    MetaBlock brick = BlockType.get(BlockType.BRICK);
    MetaBlock brickSlab = Slab.get(Slab.BRICK);
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    MetaBlock air = BlockType.get(BlockType.AIR);

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

    RectSolid.fill(editor, rand, start, end, brick);

    start = new Coord(origin);
    start.translate(dir, 5);
    end = new Coord(start);
    end.translate(Cardinal.UP, 5);
    RectSolid.fill(editor, rand, start, end, air);
    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir, 4);
    air.set(editor, cursor);

    for (Cardinal side : dir.orthogonal()) {

      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(side);

      stair.setOrientation(side.reverse(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(side.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(side, false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      bars.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      bars.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(side, true).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(side);
      stair.setOrientation(dir.reverse(), false).set(editor, cursor);
      cursor.translate(side);
      stair.setOrientation(dir.reverse(), false).set(editor, cursor);
      cursor.translate(side);
      brick.set(editor, cursor);
      cursor.translate(dir);
      brick.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(side.reverse(), false).set(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setOrientation(side.reverse(), false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(side, 2);
      brick.set(editor, cursor);
      cursor.translate(dir);
      brick.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      brick.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(dir.reverse(), false).set(editor, cursor);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir.reverse());
      stair.setOrientation(dir.reverse(), false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(Cardinal.UP, 5);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    }

    MetaBlock netherrack = BlockType.get(BlockType.NETHERRACK);
    MetaBlock fire = BlockType.get(BlockType.FIRE);

    start = new Coord(origin);
    start.translate(dir, 5);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.fill(editor, rand, start, end, netherrack);
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, fire);

    cursor = new Coord(origin);
    cursor.translate(dir, 3);
    brickSlab.set(editor, cursor);
    cursor.translate(dir);
    brickSlab.set(editor, cursor);

  }

  private void anvilRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    MetaBlock anvil = Anvil.get(
        (RogueConfig.getBoolean(RogueConfig.GENEROUS)
            ? Anvil.NEW_ANVIL
            : Anvil.DAMAGED_ANVIL), dir.antiClockwise());
    cursor = new Coord(origin);
    cursor.translate(dir);
    anvil.set(editor, cursor);

    start = new Coord(origin);
    start.translate(dir.clockwise(), 2);
    end = new Coord(start);
    start.translate(dir, 2);
    end.translate(dir.reverse(), 2);
    stair.setOrientation(dir.antiClockwise(), false);
    RectSolid.fill(editor, rand, start, end, stair);

    cursor = new Coord(origin);
    cursor.translate(dir.clockwise(), 3);
    wall.set(editor, rand, cursor);
    cursor.translate(dir);
    BlockType.get(BlockType.WATER_FLOWING).set(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    BlockType.get(BlockType.LAVA_FLOWING).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.antiClockwise(), 3);
    start = new Coord(cursor);
    end = new Coord(start);
    start.translate(dir);
    end.translate(dir.reverse());
    stair.setOrientation(dir.clockwise(), true);
    RectSolid.fill(editor, rand, start, end, stair);
    cursor.translate(Cardinal.UP);
    editor.treasureChestEditor.createChest(Dungeon.getLevel(cursor.getY()), cursor, false, getRoomSetting().getChestType().orElse(ChestType.SMITH));
    cursor = new Coord(origin);
  }


  private void overheadLight(WorldEditor editor, LevelSettings settings, Coord origin) {

    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;

    BlockType.get(BlockType.AIR).set(editor, origin);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(dir.antiClockwise());
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 2);
    BlockType.get(BlockType.REDSTONE_BLOCK).set(editor, cursor);
    cursor.translate(Cardinal.DOWN);
    BlockType.get(BlockType.REDSTONE_LAMP_LIT).set(editor, cursor);
  }

  public int getSize() {
    return 9;
  }

}
