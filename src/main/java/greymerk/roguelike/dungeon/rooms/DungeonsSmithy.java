package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.Anvil;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Hopper;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.treasure.Treasure.EMPTY;
import static greymerk.roguelike.treasure.Treasure.SMITH;
import static greymerk.roguelike.treasure.Treasure.createChest;

public class DungeonsSmithy extends DungeonBase {

  public IDungeonRoom generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();

    Coord cursor;

    Cardinal dir = entrances[0];

    clearBoxes(editor, rand, theme, dir, origin);

    cursor = new Coord(origin);
    cursor.add(dir, 6);
    sideRoom(editor, rand, settings, dir, cursor);
    anvilRoom(editor, rand, settings, dir, cursor);

    cursor = new Coord(origin);
    cursor.add(dir.reverse(), 6);
    sideRoom(editor, rand, settings, dir, cursor);

    cursor = new Coord(origin);
    cursor.add(dir.reverse(), 9);
    MetaBlock air = BlockType.get(BlockType.AIR);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);

    mainRoom(editor, rand, settings, dir, origin);

    return this;
  }

  private void sideRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();

    for (Cardinal side : dir.orthogonal()) {

      start = new Coord(origin);
      start.add(Cardinal.UP, 3);
      end = new Coord(start);
      start.add(side, 2);
      start.add(dir.reverse(), 2);
      end.add(side, 3);
      end.add(dir, 2);
      RectSolid.fill(editor, rand, start, end, wall);

      start.add(dir);
      end = new Coord(start);
      end.add(dir, 2);
      RectSolid.fill(editor, rand, start, end, stair.setOrientation(side.reverse(), true));

      for (Cardinal o : side.orthogonal()) {
        start = new Coord(origin);
        start.add(side, 3);
        start.add(o, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor = new Coord(end);
        cursor.add(side.reverse());
        stair.setOrientation(side.reverse(), true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        cursor.add(side.reverse());
        stair.setOrientation(side.reverse(), true).set(editor, cursor);

        cursor = new Coord(end);
        cursor.add(o.reverse());
        stair.setOrientation(o.reverse(), true).set(editor, cursor);
      }
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    overheadLight(editor, settings, cursor);
  }

  private void clearBoxes(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    IBlockFactory wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    // end room
    cursor = new Coord(origin);
    cursor.add(dir, 6);

    start = new Coord(cursor);
    start.add(Cardinal.DOWN);
    start.add(dir, 3);
    start.add(dir.left(), 4);

    end = new Coord(cursor);
    end.add(Cardinal.UP, 4);
    end.add(dir.reverse(), 3);
    end.add(dir.right(), 4);

    RectHollow.fill(editor, rand, start, end, wall);

    // entrance
    cursor = new Coord(origin);
    cursor.add(dir.reverse(), 6);

    start = new Coord(cursor);
    start.add(Cardinal.DOWN);
    start.add(dir, 3);
    start.add(dir.left(), 4);

    end = new Coord(cursor);
    end.add(Cardinal.UP, 4);
    end.add(dir.reverse(), 3);
    end.add(dir.right(), 4);

    RectHollow.fill(editor, rand, start, end, wall);

    // middle

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    start.add(dir.left(), 6);
    start.add(dir.reverse(), 4);

    end = new Coord(origin);
    end.add(Cardinal.UP, 6);
    end.add(dir.right(), 6);
    end.add(dir, 4);

    RectHollow.fill(editor, rand, start, end, wall, false, true);

  }

  private void mainRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 3);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    start.add(dir.left(), 5);
    end.add(dir.right(), 5);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, wall);
    start.add(dir.reverse(), 6);
    end.add(dir.reverse(), 6);
    RectSolid.fill(editor, rand, start, end, wall);

    for (Cardinal side : dir.orthogonal()) {
      for (Cardinal o : side.orthogonal()) {
        cursor = new Coord(origin);
        cursor.add(side, 2);
        cursor.add(o, 3);
        mainPillar(editor, rand, theme, o, cursor);
        cursor.add(side, 3);
        mainPillar(editor, rand, theme, o, cursor);
      }
    }

    cursor = new Coord(origin);
    smelterSide(editor, rand, settings, dir.left(), origin);
    fireplace(editor, rand, dir.right(), origin);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 6);
    overheadLight(editor, settings, cursor);

  }

  private void mainPillar(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, pillar);
    cursor = new Coord(end);
    cursor.add(dir.left());
    stair.setOrientation(dir.left(), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.add(dir.right());
    stair.setOrientation(dir.right(), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.add(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    wall.set(editor, rand, cursor);
    cursor.add(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.add(dir.reverse());
    cursor.add(Cardinal.UP);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.add(dir, 2);
    RectSolid.fill(editor, rand, start, end, wall);
    cursor = new Coord(end);
    cursor.add(dir.left());
    stair.setOrientation(dir.left(), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.add(dir.right());
    stair.setOrientation(dir.right(), true).set(editor, cursor);
  }


  private void smelterSide(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 5);
    end = new Coord(start);
    start.add(dir.left(), 2);
    end.add(dir.right(), 2);
    RectSolid.fill(editor, rand, start, end, wall);
    start.add(dir.reverse());
    end.add(dir.reverse());
    IStair stair = theme.getPrimary().getStair();
    stair = stair.setOrientation(dir.reverse(), false);
    RectSolid.fill(editor, rand, start, end, stair);


    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(o);
      smelter(editor, rand, dir, cursor);

      cursor.add(o, 2);
      wall.set(editor, rand, cursor);
      cursor.add(dir);
      wall.set(editor, rand, cursor);
    }
  }

  private void smelter(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {
    Treasure.createChest(editor, rand, 1, origin, false, EMPTY);

    Coord cursor;
    cursor = new Coord(origin);
    cursor.add(dir, 2);
    cursor.add(Cardinal.UP, 2);
    Treasure.createChest(editor, rand, 1, cursor, false, EMPTY);
    cursor.add(Cardinal.UP);
    cursor.add(dir.reverse());
    Treasure.createChest(editor, rand, 1, cursor, false, EMPTY);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    cursor.add(dir);
    Furnace.generate(editor, dir.reverse(), cursor);

    cursor = new Coord(origin);
    cursor.add(dir);
    Hopper.generate(editor, dir.reverse(), cursor);

    cursor.add(dir);
    cursor.add(Cardinal.UP);
    Hopper.generate(editor, dir.reverse(), cursor);

    cursor.add(dir.reverse());
    cursor.add(Cardinal.UP);
    Hopper.generate(editor, Cardinal.DOWN, cursor);
  }

  private void fireplace(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {

    IStair stair = new MetaStair(StairType.BRICK);
    MetaBlock brick = BlockType.get(BlockType.BRICK);
    MetaBlock brickSlab = Slab.get(Slab.BRICK);
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 4);
    end = new Coord(start);
    start.add(Cardinal.DOWN);
    start.add(dir.left());
    end.add(dir.right());
    end.add(dir, 2);
    end.add(Cardinal.UP, 5);

    RectSolid.fill(editor, rand, start, end, brick);

    start = new Coord(origin);
    start.add(dir, 5);
    end = new Coord(start);
    end.add(Cardinal.UP, 5);
    RectSolid.fill(editor, rand, start, end, air);
    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    cursor.add(dir, 4);
    air.set(editor, cursor);

    for (Cardinal side : dir.orthogonal()) {

      cursor = new Coord(origin);
      cursor.add(dir, 4);
      cursor.add(side);

      stair.setOrientation(side.reverse(), false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(side.reverse(), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(side, false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      bars.set(editor, cursor);
      cursor.add(Cardinal.UP);
      bars.set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(side, true).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(side);
      stair.setOrientation(dir.reverse(), false).set(editor, cursor);
      cursor.add(side);
      stair.setOrientation(dir.reverse(), false).set(editor, cursor);
      cursor.add(side);
      brick.set(editor, cursor);
      cursor.add(dir);
      brick.set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(side.reverse(), false).set(editor, cursor);
      cursor.add(dir.reverse());
      stair.setOrientation(side.reverse(), false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 4);
      cursor.add(side, 2);
      brick.set(editor, cursor);
      cursor.add(dir);
      brick.set(editor, cursor);
      cursor.add(Cardinal.UP);
      brick.set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(dir.reverse(), false).set(editor, cursor);
      cursor.add(Cardinal.DOWN);
      cursor.add(dir.reverse());
      stair.setOrientation(dir.reverse(), false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(Cardinal.UP, 5);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    }

    MetaBlock netherrack = BlockType.get(BlockType.NETHERRACK);
    MetaBlock fire = BlockType.get(BlockType.FIRE);

    start = new Coord(origin);
    start.add(dir, 5);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(dir.left());
    end.add(dir.right());
    RectSolid.fill(editor, rand, start, end, netherrack);
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, fire);

    cursor = new Coord(origin);
    cursor.add(dir, 3);
    brickSlab.set(editor, cursor);
    cursor.add(dir);
    brickSlab.set(editor, cursor);

  }

  private void anvilRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    MetaBlock anvil = Anvil.get(
        (RogueConfig.getBoolean(RogueConfig.GENEROUS)
            ? Anvil.NEW_ANVIL
            : Anvil.DAMAGED_ANVIL), dir.left());
    cursor = new Coord(origin);
    cursor.add(dir);
    anvil.set(editor, cursor);

    start = new Coord(origin);
    start.add(dir.right(), 2);
    end = new Coord(start);
    start.add(dir, 2);
    end.add(dir.reverse(), 2);
    stair.setOrientation(dir.left(), false);
    RectSolid.fill(editor, rand, start, end, stair);

    cursor = new Coord(origin);
    cursor.add(dir.right(), 3);
    wall.set(editor, rand, cursor);
    cursor.add(dir);
    BlockType.get(BlockType.WATER_FLOWING).set(editor, cursor);
    cursor.add(dir.reverse(), 2);
    BlockType.get(BlockType.LAVA_FLOWING).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(dir.left(), 3);
    start = new Coord(cursor);
    end = new Coord(start);
    start.add(dir);
    end.add(dir.reverse());
    stair.setOrientation(dir.right(), true);
    RectSolid.fill(editor, rand, start, end, stair);
    cursor.add(Cardinal.UP);
    createChest(editor, rand, Dungeon.getLevel(cursor.getY()), cursor, false, SMITH);
    cursor = new Coord(origin);
  }


  private void overheadLight(IWorldEditor editor, LevelSettings settings, Coord origin) {

    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;

    BlockType.get(BlockType.AIR).set(editor, origin);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.add(dir.left());
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 2);
    BlockType.get(BlockType.REDSTONE_BLOCK).set(editor, cursor);
    cursor.add(Cardinal.DOWN);
    BlockType.get(BlockType.REDSTONE_LAMP_LIT).set(editor, cursor);
  }

  public int getSize() {
    return 9;
  }

}
