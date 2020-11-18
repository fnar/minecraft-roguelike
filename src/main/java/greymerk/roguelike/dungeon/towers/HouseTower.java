package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.Bed;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Cake;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.Stair;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.treasure.Treasure.STARTER;


public class HouseTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    IBlockFactory walls = theme.getPrimary().getWall();
    IBlockFactory mainFloor = theme.getPrimary().getFloor();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Cardinal dir = Cardinal.directions[(floor.getY() + 2) % 4];

    Coord cursor;
    Coord start;
    Coord end;

    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    floor.translate(Cardinal.UP);

    start = new Coord(floor);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir.clockwise(), 3);
    start.translate(dir, 3);
    end.translate(Cardinal.UP, 8);
    end.translate(dir.reverse(), 7);
    end.translate(dir.antiClockwise(), 10);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(floor);
    start.translate(dir.clockwise(), 2);
    start.translate(Cardinal.DOWN);
    end = new Coord(floor);
    end.translate(Cardinal.UP, 3);
    end.translate(dir.antiClockwise(), 8);
    end.translate(dir.reverse(), 5);
    RectSolid.fill(editor, rand, new Coord(x - 2, floor.getY() + 3, z - 2), new Coord(x + 2, y + 10, z + 2), walls);
    RectHollow.fill(editor, rand, start, end, walls);

    cursor = new Coord(floor);
    cursor.translate(dir.antiClockwise(), 6);
    cursor.translate(dir.reverse(), 6);
    door(editor, rand, theme, dir, cursor);

    start = new Coord(floor);
    start.translate(Cardinal.DOWN);
    start.translate(dir.clockwise());
    start.translate(dir.reverse());
    end = new Coord(floor);
    end.translate(Cardinal.DOWN);
    end.translate(dir.reverse(), 4);
    end.translate(dir.antiClockwise(), 7);
    RectSolid.fill(editor, rand, start, end, mainFloor);

    start = new Coord(floor);
    start.translate(Cardinal.DOWN, 2);
    start.translate(dir.clockwise(), 2);
    start.translate(dir.reverse(), 2);
    end = new Coord(floor.getX(), y + 10, floor.getZ());
    end.translate(dir.reverse(), 5);
    end.translate(dir.antiClockwise(), 8);
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(floor);
    cursor.translate(dir.reverse(), 5);
    cursor.translate(dir.clockwise(), 2);
    support(editor, rand, theme, new Cardinal[]{dir.reverse(), dir.clockwise()}, cursor);
    cursor.translate(dir, 7);
    support(editor, rand, theme, new Cardinal[]{dir, dir.clockwise()}, cursor);
    cursor.translate(dir.antiClockwise(), 4);
    support(editor, rand, theme, new Cardinal[]{dir, dir.antiClockwise()}, cursor);
    cursor.translate(dir.antiClockwise(), 6);
    cursor.translate(dir.reverse(), 2);
    support(editor, rand, theme, new Cardinal[]{dir, dir.antiClockwise()}, cursor);

    upperFloor(editor, rand, theme, dir, new Coord(x, floor.getY() + 3, z));
    roof(editor, rand, theme, dir, new Coord(x, floor.getY() + 4, z));
    upperWalls(editor, rand, theme, dir, new Coord(x, floor.getY() + 4, z));
    windows(editor, rand, theme, dir, floor);
    decor(editor, rand, theme, dir, floor);

    cursor = new Coord(floor);
    cursor.translate(Cardinal.UP, 3);
    for (int i = floor.getY() + 3; i >= y; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getSecondary().getPillar());
    }
  }

  private void decor(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    IStair stair = Stair.get(StairType.OAK);
    MetaBlock slab = Slab.get(Slab.OAK, true, false, false);
    Cardinal[] orth = dir.orthogonal();
    Coord cursor;
    Coord start;
    Coord end;

    // downstairs table
    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 4);
    stair.setOrientation(orth[1], true).set(editor, cursor);
    cursor.translate(orth[0]);
    slab.set(editor, cursor);
    cursor.translate(orth[0]);
    stair.setOrientation(orth[0], true).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(orth[0], 4);
    cursor.translate(dir.reverse());
    stair.setOrientation(orth[1], true).set(editor, cursor);
    cursor.translate(orth[0]);
    slab.set(editor, cursor);
    cursor.translate(orth[0]);
    stair.setOrientation(orth[0], true).set(editor, cursor);
    cursor.translate(orth[1]);
    cursor.translate(Cardinal.UP);
    Cake.get().set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(orth[0], 7);
    cursor.translate(dir.reverse());
    slab.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir.reverse());
    BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);
    cursor.translate(dir.reverse());
    Furnace.generate(editor, true, orth[1], cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    cursor.translate(orth[1], 2);
    cursor.translate(dir.reverse(), 3);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    FlowerPot.generate(editor, rand, cursor);
    cursor.translate(dir);
    FlowerPot.generate(editor, rand, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    cursor.translate(orth[0]);
    cursor.translate(dir.reverse(), 5);
    stair.setOrientation(orth[1], true).set(editor, cursor);
    cursor.translate(orth[0]);
    slab.set(editor, cursor);
    cursor.translate(orth[0]);
    stair.setOrientation(orth[0], true).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    cursor.translate(orth[0], 8);
    editor.treasureChestEditor.createChest(0, cursor, false, STARTER);
    cursor.translate(dir.reverse());
    BlockType.get(BlockType.SHELF).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    FlowerPot.generate(editor, rand, cursor);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir.reverse());
    Bed.generate(editor, orth[1], cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    cursor.translate(dir.reverse());
    cursor.translate(orth[0]);
    start = new Coord(cursor);
    end = new Coord(start);
    end.translate(orth[0], 5);
    end.translate(dir.reverse(), 3);
    BlockStripes carpet = new BlockStripes();
    carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
    carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
    carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
    RectSolid.fill(editor, rand, start, end, carpet);


  }


  private void windows(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    MetaBlock pane = ColorBlock.get(ColorBlock.PANE, DyeColor.LIGHT_GRAY);
    Cardinal[] orth = dir.orthogonal();
    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 5);
    cursor.translate(Cardinal.UP);
    pane.set(editor, cursor);
    cursor.translate(orth[0], 2);
    pane.set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(orth[0], 8);
    cursor.translate(dir.reverse(), 2);
    pane.set(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    pane.set(editor, cursor);

    // upstairs
    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 5);
    cursor.translate(orth[0]);
    cursor.translate(dir, 3);
    pane.set(editor, cursor);
    cursor.translate(orth[1], 2);
    pane.set(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    cursor.translate(orth[1], 2);
    pane.set(editor, cursor);
    cursor.translate(dir.reverse());
    pane.set(editor, cursor);
    cursor.translate(dir.reverse(), 3);
    pane.set(editor, cursor);
    cursor.translate(dir.reverse());
    pane.set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 5);
    cursor.translate(orth[0], 9);
    cursor.translate(dir.reverse());
    pane.set(editor, cursor);
    cursor.translate(dir.reverse());
    pane.set(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    pane.set(editor, cursor);
    cursor.translate(dir.reverse());
    pane.set(editor, cursor);
    cursor.translate(Cardinal.UP, 2);
    cursor.translate(dir);
    start = new Coord(cursor);
    end = new Coord(start);
    end.translate(Cardinal.UP);
    end.translate(dir, 2);
    RectSolid.fill(editor, rand, start, end, pane);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    cursor.translate(orth[0], 5);
    cursor.translate(dir.reverse(), 7);
    start = new Coord(cursor);
    end = new Coord(start);
    end.translate(orth[0], 2);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, pane);
  }

  private void roof(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory walls = theme.getSecondary().getWall();
    IStair stair = theme.getSecondary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    cursor.translate(dir.clockwise(), 4);
    cursor.translate(dir, 4);
    cursor.translate(Cardinal.UP, 2);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.translate(dir.reverse(), 10);
    stair.setOrientation(dir.clockwise(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    stair.setOrientation(dir.antiClockwise(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    stair.setOrientation(dir.clockwise(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    stair.setOrientation(dir.antiClockwise(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    stair.setOrientation(dir.clockwise(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    stair.setOrientation(dir.antiClockwise(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    stair.setOrientation(dir.clockwise(), false).fill(editor, rand, new RectSolid(start, end));

    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 3);
    cursor.translate(Cardinal.UP, 5);
    stair.setOrientation(dir.antiClockwise(), true).set(editor, cursor);
    cursor.translate(dir);
    stair.setOrientation(dir.antiClockwise(), true).set(editor, cursor);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir.antiClockwise());
    cursor.translate(dir);
    stair.setOrientation(dir.clockwise(), true).set(editor, cursor);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir.antiClockwise());
    cursor.translate(dir);
    stair.setOrientation(dir.clockwise(), true).set(editor, cursor);

    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir, 5);
    stair.setOrientation(dir.antiClockwise(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    stair.setOrientation(dir.clockwise(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    stair.setOrientation(dir.antiClockwise(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    stair.setOrientation(dir.clockwise(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    stair.setOrientation(dir.antiClockwise(), false).fill(editor, rand, new RectSolid(start, end));

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 2);
    cursor.translate(dir, 2);
    cursor.translate(dir.antiClockwise(), 10);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.translate(dir.clockwise(), 6);
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    end.translate(dir.clockwise());
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    end.translate(dir.clockwise());
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    end.translate(dir.clockwise());
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));

    start.translate(dir.reverse());
    end.translate(dir.reverse());
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(end);
    end.translate(dir.reverse(), 2);
    start.translate(dir.clockwise());
    end.translate(dir.clockwise());
    start.translate(dir);
    end.translate(dir);
    stair.setOrientation(dir.clockwise(), false).fill(editor, rand, new RectSolid(start, end));

    cursor.translate(dir.reverse(), 10);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.translate(dir.clockwise(), 7);
    stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    end.translate(dir.clockwise(), 5);
    stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    end.translate(dir.antiClockwise());
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    end.translate(dir.antiClockwise());
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    end.translate(dir.antiClockwise());
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));
  }

  private void upperFloor(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory floor = theme.getPrimary().getFloor();
    Cardinal[] orth = dir.orthogonal();
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(orth[1], 3);
    start.translate(dir, 3);
    end = new Coord(origin);
    end.translate(orth[0], 3);
    end.translate(dir.reverse(), 6);
    RectSolid.fill(editor, rand, start, end, floor);

    start = new Coord(origin);
    start.translate(orth[0], 3);
    start.translate(dir);
    end = new Coord(origin);
    end.translate(dir.reverse(), 7);
    end.translate(orth[0], 9);
    RectSolid.fill(editor, rand, start, end, floor);
  }

  private void upperWalls(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory walls = theme.getPrimary().getWall();
    Cardinal[] orth = dir.orthogonal();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(orth[1], 3);
    start.translate(dir, 2);
    end = new Coord(start);
    end.translate(dir.reverse(), 7);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.translate(orth[1], 2);
    start.translate(dir, 3);
    end = new Coord(start);
    end.translate(orth[0], 4);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, walls);
    end.translate(Cardinal.UP);
    end.translate(orth[1]);
    start = new Coord(end);
    start.translate(orth[1], 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.translate(orth[0], 3);
    start.translate(dir, 2);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.translate(orth[0], 4);
    start.translate(dir);
    end = new Coord(start);
    end.translate(orth[0], 4);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.translate(orth[0], 9);
    end = new Coord(start);
    end.translate(dir.reverse(), 6);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, walls);
    end.translate(Cardinal.UP);
    end.translate(dir);
    start = new Coord(end);
    start.translate(dir, 4);
    RectSolid.fill(editor, rand, start, end, walls);
    end.translate(Cardinal.UP);
    end.translate(dir);
    start = new Coord(end);
    start.translate(dir, 2);
    RectSolid.fill(editor, rand, start, end, walls);


    start = new Coord(origin);
    start.translate(dir.reverse(), 7);
    start.translate(orth[0], 4);
    end = new Coord(start);
    end.translate(orth[0], 4);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, walls);

    start = new Coord(origin);
    start.translate(dir.reverse(), 6);
    start.translate(orth[1], 2);
    end = new Coord(start);
    end.translate(orth[0], 4);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.translate(orth[1], 3);
    cursor.translate(dir, 3);
    pillar(editor, rand, theme, 3, cursor);
    cursor.translate(orth[0], 6);
    pillar(editor, rand, theme, 3, cursor);
    cursor.translate(dir.reverse(), 2);
    pillar(editor, rand, theme, 3, cursor);
    cursor.translate(orth[0], 6);
    pillar(editor, rand, theme, 3, cursor);
    cursor.translate(dir.reverse(), 8);
    pillar(editor, rand, theme, 3, cursor);
    cursor.translate(orth[1], 6);
    pillar(editor, rand, theme, 3, cursor);
    cursor.translate(dir);
    pillar(editor, rand, theme, 3, cursor);
    cursor.translate(orth[1], 6);
    pillar(editor, rand, theme, 3, cursor);
  }

  private void pillar(WorldEditor editor, Random rand, ITheme theme, int height, Coord start) {
    IBlockFactory pillar = theme.getPrimary().getPillar();
    Coord end;
    end = new Coord(start);
    end.translate(Cardinal.UP, height - 1);
    RectSolid.fill(editor, rand, start, end, pillar);
  }

  private void support(WorldEditor editor, Random rand, ITheme theme, Cardinal[] dirs, Coord origin) {
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, pillar);
    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    editor.fillDown(rand, cursor, pillar);

    for (Cardinal dir : dirs) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 2);
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, cursor);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setOrientation(o, true).set(editor, rand, c, true, false);
      }
    }

  }

  private void door(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    IBlockFactory floor = theme.getPrimary().getFloor();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(origin);
    start.translate(dir.reverse());
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(dir.reverse(), 2);
    end.translate(Cardinal.UP, 6);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(Cardinal.DOWN);
    start.translate(orth[0]);
    end.translate(Cardinal.UP, 2);
    end.translate(orth[1]);
    RectSolid.fill(editor, rand, start, end, floor);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir.reverse());
    end.translate(dir);
    start.translate(orth[0]);
    end.translate(orth[1]);
    RectSolid.fill(editor, rand, start, end, floor);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN, 2);
    end = new Coord(start);
    start.translate(dir.reverse());
    end.translate(dir);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.fill(editor, rand, start, end, floor, true, false);

    theme.getPrimary().getDoor().generate(editor, origin, dir.reverse(), false);

    for (Cardinal o : orth) {

      cursor = new Coord(origin);
      cursor.translate(o, 2);
      cursor.translate(Cardinal.UP, 2);
      editor.fillDown(rand, cursor, pillar);

      cursor = new Coord(end);
      cursor.translate(o);
      stair.setOrientation(o, true).set(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setOrientation(o, true).set(editor, cursor);
      cursor.translate(o.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(o.reverse());
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 2);
    cursor.translate(orth[0], 3);
    cursor.translate(dir);
    stair.setOrientation(dir, true).set(editor, cursor);

    start = new Coord(origin);
    start.translate(dir);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir.reverse(), 2);
    step(editor, rand, theme, dir.reverse(), cursor);
  }

  private void step(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    Coord start;
    Coord end;
    Coord cursor;

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory blocks = theme.getPrimary().getWall();

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir);
    if (editor.validGroundBlock(cursor)) {
      return;
    }
    if (cursor.getY() <= 60) {
      return;
    }

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.fill(editor, rand, start, end, blocks, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(orth[0]);
    end.translate(orth[1]);
    stair.setOrientation(dir, false);
    RectSolid.fill(editor, rand, start, end, stair, true, true);

    origin.translate(Cardinal.DOWN);
    origin.translate(dir);
    step(editor, rand, theme, dir, origin);
  }
}
