package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.BedBlock;
import com.github.srwaggon.minecraft.block.decorative.FlowerPotBlock;
import com.github.srwaggon.minecraft.block.decorative.TorchBlock;
import com.github.srwaggon.minecraft.block.normal.SlabBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.carpet;
import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.stainedGlassPane;

public class HouseTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush mainFloor = theme.getPrimary().getFloor();
    StairsBlock stair = theme.getPrimary().getStair();

    Direction dir = Direction.CARDINAL.get((floor.getY() + 2) % 4);

    Coord cursor;
    Coord start;
    Coord end;

    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    floor.translate(Direction.UP);

    start = floor.copy();
    start.translate(Direction.UP, 4);
    end = start.copy();
    start.translate(dir.clockwise(), 3);
    start.translate(dir, 3);
    end.translate(Direction.UP, 8);
    end.translate(dir.reverse(), 7);
    end.translate(dir.antiClockwise(), 10);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = floor.copy();
    start.translate(dir.clockwise(), 2);
    start.translate(Direction.DOWN);
    end = floor.copy();
    end.translate(Direction.UP, 3);
    end.translate(dir.antiClockwise(), 8);
    end.translate(dir.reverse(), 5);
    RectSolid.newRect(new Coord(x - 2, floor.getY() + 3, z - 2), new Coord(x + 2, y + 10, z + 2)).fill(editor, walls);
    RectHollow.newRect(start, end).fill(editor, walls);

    cursor = floor.copy();
    cursor.translate(dir.antiClockwise(), 6);
    cursor.translate(dir.reverse(), 6);
    door(editor, theme, dir, cursor);

    start = floor.copy();
    start.translate(Direction.DOWN);
    start.translate(dir.clockwise());
    start.translate(dir.reverse());
    end = floor.copy();
    end.translate(Direction.DOWN);
    end.translate(dir.reverse(), 4);
    end.translate(dir.antiClockwise(), 7);
    RectSolid.newRect(start, end).fill(editor, mainFloor);

    start = floor.copy();
    start.translate(Direction.DOWN, 2);
    start.translate(dir.clockwise(), 2);
    start.translate(dir.reverse(), 2);
    end = new Coord(floor.getX(), y + 10, floor.getZ());
    end.translate(dir.reverse(), 5);
    end.translate(dir.antiClockwise(), 8);
    RectSolid.newRect(start, end).fill(editor, walls);

    cursor = floor.copy();
    cursor.translate(dir.reverse(), 5);
    cursor.translate(dir.clockwise(), 2);
    support(editor, theme, new Direction[]{dir.reverse(), dir.clockwise()}, cursor);
    cursor.translate(dir, 7);
    support(editor, theme, new Direction[]{dir, dir.clockwise()}, cursor);
    cursor.translate(dir.antiClockwise(), 4);
    support(editor, theme, new Direction[]{dir, dir.antiClockwise()}, cursor);
    cursor.translate(dir.antiClockwise(), 6);
    cursor.translate(dir.reverse(), 2);
    support(editor, theme, new Direction[]{dir, dir.antiClockwise()}, cursor);

    upperFloor(editor, theme, dir, new Coord(x, floor.getY() + 3, z));
    roof(editor, theme, dir, new Coord(x, floor.getY() + 4, z));
    upperWalls(editor, theme, dir, new Coord(x, floor.getY() + 4, z));
    windows(editor, dir, floor);
    decor(editor, rand, dir, floor);

    cursor = floor.copy();
    cursor.translate(Direction.UP, 3);
    for (int i = floor.getY() + 3; i >= y; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getSecondary().getPillar());
    }
  }

  private void decor(WorldEditor editor, Random rand, Direction dir, Coord origin) {

    StairsBlock stair = StairsBlock.oak();
    BlockBrush slab = SlabBlock.oak().setTop(true).setFullBlock(false).setSeamless(false);
    Direction[] orthogonals = dir.orthogonals();
    Coord cursor;
    Coord start;
    Coord end;

    // downstairs table
    cursor = origin.copy();
    cursor.translate(dir.reverse(), 4);
    stair.setUpsideDown(true).setFacing(orthogonals[1]).stroke(editor, cursor);
    cursor.translate(orthogonals[0]);
    slab.stroke(editor, cursor);
    cursor.translate(orthogonals[0]);
    stair.setUpsideDown(true).setFacing(orthogonals[0]).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(orthogonals[0], 4);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(orthogonals[1]).stroke(editor, cursor);
    cursor.translate(orthogonals[0]);
    slab.stroke(editor, cursor);
    cursor.translate(orthogonals[0]);
    stair.setUpsideDown(true).setFacing(orthogonals[0]).stroke(editor, cursor);
    cursor.translate(orthogonals[1]);
    cursor.translate(Direction.UP);
    BlockType.CAKE.getBrush().stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(orthogonals[0], 7);
    cursor.translate(dir.reverse());
    slab.stroke(editor, cursor);
    cursor.translate(Direction.UP);
    TorchBlock.torch().setFacing(Direction.UP).stroke(editor, cursor);
    cursor.translate(Direction.DOWN);
    cursor.translate(dir.reverse());
    BlockType.CRAFTING_TABLE.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse());
    BlockType.FURNACE.getBrush().setFacing(orthogonals[1]).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(Direction.UP, 4);
    cursor.translate(orthogonals[1], 2);
    cursor.translate(dir.reverse(), 3);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.translate(Direction.UP);
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);
    cursor.translate(dir);
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(Direction.UP, 4);
    cursor.translate(orthogonals[0]);
    cursor.translate(dir.reverse(), 5);
    stair.setUpsideDown(true).setFacing(orthogonals[1]).stroke(editor, cursor);
    cursor.translate(orthogonals[0]);
    slab.stroke(editor, cursor);
    cursor.translate(orthogonals[0]);
    stair.setUpsideDown(true).setFacing(orthogonals[0]).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(Direction.UP, 4);
    cursor.translate(orthogonals[0], 8);
    editor.getTreasureChestEditor().createChest(0, cursor, false, ChestType.STARTER);
    cursor.translate(dir.reverse());
    BlockType.BOOKSHELF.getBrush().stroke(editor, cursor);
    cursor.translate(Direction.UP);
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);
    cursor.translate(Direction.DOWN);
    cursor.translate(dir.reverse());
    BedBlock.bed().setColor(DyeColor.RED).setFacing(orthogonals[1]).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(Direction.UP, 4);
    cursor.translate(dir.reverse());
    cursor.translate(orthogonals[0]);
    start = cursor.copy();
    end = start.copy();
    end.translate(orthogonals[0], 5);
    end.translate(dir.reverse(), 3);
    BlockStripes carpet = new BlockStripes();
    carpet.addBlock(carpet().setColor(DyeColor.chooseRandom(rand)));
    carpet.addBlock(carpet().setColor(DyeColor.chooseRandom(rand)));
    carpet.addBlock(carpet().setColor(DyeColor.chooseRandom(rand)));
    RectSolid.newRect(start, end).fill(editor, carpet);
  }


  private void windows(WorldEditor editor, Direction dir, Coord origin) {
    BlockBrush pane = stainedGlassPane().setColor(DyeColor.LIGHT_GRAY);
    Direction[] orth = dir.orthogonals();
    Coord cursor;
    Coord start;
    Coord end;

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 5);
    cursor.translate(Direction.UP);
    pane.stroke(editor, cursor);
    cursor.translate(orth[0], 2);
    pane.stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(Direction.UP);
    cursor.translate(orth[0], 8);
    cursor.translate(dir.reverse(), 2);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    pane.stroke(editor, cursor);

    // upstairs
    cursor = origin.copy();
    cursor.translate(Direction.UP, 5);
    cursor.translate(orth[0]);
    cursor.translate(dir, 3);
    pane.stroke(editor, cursor);
    cursor.translate(orth[1], 2);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    cursor.translate(orth[1], 2);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 3);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(Direction.UP, 5);
    cursor.translate(orth[0], 9);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);
    cursor.translate(Direction.UP, 2);
    cursor.translate(dir);
    start = cursor.copy();
    end = start.copy();
    end.translate(Direction.UP);
    end.translate(dir, 2);
    RectSolid.newRect(start, end).fill(editor, pane);

    cursor = origin.copy();
    cursor.translate(Direction.UP, 4);
    cursor.translate(orth[0], 5);
    cursor.translate(dir.reverse(), 7);
    start = cursor.copy();
    end = start.copy();
    end.translate(orth[0], 2);
    end.translate(Direction.UP);
    RectSolid.newRect(start, end).fill(editor, pane);
  }

  private void roof(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {
    BlockBrush walls = theme.getSecondary().getWall();
    StairsBlock stair = theme.getSecondary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    cursor = origin.copy();
    cursor.translate(dir.clockwise(), 4);
    cursor.translate(dir, 4);
    cursor.translate(Direction.UP, 2);
    start = cursor.copy();
    end = cursor.copy();
    end.translate(dir.reverse(), 10);
    stair.setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, new RectSolid(start, end));

    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    RectSolid.newRect(start, end).fill(editor, walls);

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 3);
    cursor.translate(Direction.UP, 5);
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor.translate(Direction.DOWN);
    cursor.translate(dir.antiClockwise());
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
    cursor.translate(Direction.DOWN);
    cursor.translate(dir.antiClockwise());
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);

    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir, 5);
    stair.setUpsideDown(false).setFacing(dir.antiClockwise()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.DOWN);
    end.translate(Direction.DOWN);
    stair.setUpsideDown(true).setFacing(dir.clockwise()).fill(editor, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    stair.setUpsideDown(false).setFacing(dir.antiClockwise()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.DOWN);
    end.translate(Direction.DOWN);
    stair.setUpsideDown(true).setFacing(dir.clockwise()).fill(editor, new RectSolid(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    stair.setUpsideDown(false).setFacing(dir.antiClockwise()).fill(editor, new RectSolid(start, end));

    cursor = origin.copy();
    cursor.translate(Direction.UP, 2);
    cursor.translate(dir, 2);
    cursor.translate(dir.antiClockwise(), 10);
    start = cursor.copy();
    end = cursor.copy();
    end.translate(dir.clockwise(), 6);
    stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    end.translate(dir.clockwise());
    stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    end.translate(dir.clockwise());
    stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    end.translate(dir.clockwise());
    stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir).fill(editor, new RectSolid(start, end));

    start.translate(dir.reverse());
    end.translate(dir.reverse());
    RectSolid.newRect(start, end).fill(editor, walls);

    start = end.copy();
    end.translate(dir.reverse(), 2);
    start.translate(dir.clockwise());
    end.translate(dir.clockwise());
    start.translate(dir);
    end.translate(dir);
    stair.setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, new RectSolid(start, end));

    cursor.translate(dir.reverse(), 10);
    start = cursor.copy();
    end = cursor.copy();
    end.translate(dir.clockwise(), 7);
    stair.setUpsideDown(false).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    end.translate(dir.clockwise(), 5);
    stair.setUpsideDown(false).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));
    end.translate(dir.antiClockwise());
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));
    end.translate(dir.antiClockwise());
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));
    end.translate(dir.antiClockwise());
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
  }

  private void upperFloor(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {
    BlockBrush floor = theme.getPrimary().getFloor();
    Direction[] orth = dir.orthogonals();
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(orth[1], 3);
    start.translate(dir, 3);
    end = origin.copy();
    end.translate(orth[0], 3);
    end.translate(dir.reverse(), 6);
    RectSolid.newRect(start, end).fill(editor, floor);

    start = origin.copy();
    start.translate(orth[0], 3);
    start.translate(dir);
    end = origin.copy();
    end.translate(dir.reverse(), 7);
    end.translate(orth[0], 9);
    RectSolid.newRect(start, end).fill(editor, floor);
  }

  private void upperWalls(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {
    BlockBrush walls = theme.getPrimary().getWall();
    Direction[] orth = dir.orthogonals();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(orth[1], 3);
    start.translate(dir, 2);
    end = start.copy();
    end.translate(dir.reverse(), 7);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, walls);

    start = origin.copy();
    start.translate(orth[1], 2);
    start.translate(dir, 3);
    end = start.copy();
    end.translate(orth[0], 4);
    end.translate(Direction.UP, 3);
    RectSolid.newRect(start, end).fill(editor, walls);
    end.translate(Direction.UP);
    end.translate(orth[1]);
    start = end.copy();
    start.translate(orth[1], 2);
    RectSolid.newRect(start, end).fill(editor, walls);

    start = origin.copy();
    start.translate(orth[0], 3);
    start.translate(dir, 2);
    end = start.copy();
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, walls);

    start = origin.copy();
    start.translate(orth[0], 4);
    start.translate(dir);
    end = start.copy();
    end.translate(orth[0], 4);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, walls);

    start = origin.copy();
    start.translate(orth[0], 9);
    end = start.copy();
    end.translate(dir.reverse(), 6);
    end.translate(Direction.UP, 3);
    RectSolid.newRect(start, end).fill(editor, walls);
    end.translate(Direction.UP);
    end.translate(dir);
    start = end.copy();
    start.translate(dir, 4);
    RectSolid.newRect(start, end).fill(editor, walls);
    end.translate(Direction.UP);
    end.translate(dir);
    start = end.copy();
    start.translate(dir, 2);
    RectSolid.newRect(start, end).fill(editor, walls);


    start = origin.copy();
    start.translate(dir.reverse(), 7);
    start.translate(orth[0], 4);
    end = start.copy();
    end.translate(orth[0], 4);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, walls);

    start = origin.copy();
    start.translate(dir.reverse(), 6);
    start.translate(orth[1], 2);
    end = start.copy();
    end.translate(orth[0], 4);
    end.translate(Direction.UP, 3);
    RectSolid.newRect(start, end).fill(editor, walls);

    cursor = origin.copy();
    cursor.translate(orth[1], 3);
    cursor.translate(dir, 3);
    pillar(editor, theme, 3, cursor);
    cursor.translate(orth[0], 6);
    pillar(editor, theme, 3, cursor);
    cursor.translate(dir.reverse(), 2);
    pillar(editor, theme, 3, cursor);
    cursor.translate(orth[0], 6);
    pillar(editor, theme, 3, cursor);
    cursor.translate(dir.reverse(), 8);
    pillar(editor, theme, 3, cursor);
    cursor.translate(orth[1], 6);
    pillar(editor, theme, 3, cursor);
    cursor.translate(dir);
    pillar(editor, theme, 3, cursor);
    cursor.translate(orth[1], 6);
    pillar(editor, theme, 3, cursor);
  }

  private void pillar(WorldEditor editor, ThemeBase theme, int height, Coord start) {
    BlockBrush pillar = theme.getPrimary().getPillar();
    Coord end;
    end = start.copy();
    end.translate(Direction.UP, height - 1);
    RectSolid.newRect(start, end).fill(editor, pillar);
  }

  private void support(WorldEditor editor, ThemeBase theme, Direction[] dirs, Coord origin) {
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, pillar);
    cursor = origin.copy();
    cursor.translate(Direction.DOWN);
    editor.fillDown(cursor, pillar);

    for (Direction dir : dirs) {
      cursor = origin.copy();
      cursor.translate(Direction.UP, 2);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        stair.setUpsideDown(true).setFacing(o).stroke(editor, c, true, false);
      }
    }

  }

  private void door(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {

    BlockBrush floor = theme.getPrimary().getFloor();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    Direction[] orth = dir.orthogonals();

    start = origin.copy();
    start.translate(dir.reverse());
    end = start.copy();
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(dir.reverse(), 2);
    end.translate(Direction.UP, 6);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = start.copy();
    start.translate(Direction.DOWN);
    start.translate(orth[0]);
    end.translate(Direction.UP, 2);
    end.translate(orth[1]);
    RectSolid.newRect(start, end).fill(editor, floor);

    start = origin.copy();
    start.translate(Direction.DOWN);
    end = start.copy();
    start.translate(dir.reverse());
    end.translate(dir);
    start.translate(orth[0]);
    end.translate(orth[1]);
    RectSolid.newRect(start, end).fill(editor, floor);

    start = origin.copy();
    start.translate(Direction.DOWN, 2);
    end = start.copy();
    start.translate(dir.reverse());
    end.translate(dir);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.newRect(start, end).fill(editor, floor, true, false);

    theme.getPrimary().getDoor().setFacing(dir.reverse()).stroke(editor, origin);

    for (Direction o : orth) {

      cursor = origin.copy();
      cursor.translate(o, 2);
      cursor.translate(Direction.UP, 2);
      editor.fillDown(cursor, pillar);

      cursor = end.copy();
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(o).stroke(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(o).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.translate(Direction.UP, 2);
    cursor.translate(orth[0], 3);
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

    start = origin.copy();
    start.translate(dir);
    end = start.copy();
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    cursor = origin.copy();
    cursor.translate(Direction.DOWN);
    cursor.translate(dir.reverse(), 2);
    step(editor, theme, dir.reverse(), cursor);
  }

  private void step(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {

    Coord start;
    Coord end;
    Coord cursor;

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush blocks = theme.getPrimary().getWall();

    cursor = origin.copy();
    cursor.translate(Direction.DOWN);
    cursor.translate(dir);
    if (editor.validGroundBlock(cursor)) {
      return;
    }
    if (cursor.getY() <= 60) {
      return;
    }

    Direction[] orth = dir.orthogonals();

    start = origin.copy();
    end = origin.copy();
    start.translate(orth[0]);
    end.translate(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.newRect(start, end).fill(editor, blocks);

    start = origin.copy();
    end = origin.copy();
    start.translate(orth[0]);
    end.translate(orth[1]);
    stair.setUpsideDown(false).setFacing(dir);
    RectSolid.newRect(start, end).fill(editor, stair);

    origin.translate(Direction.DOWN);
    origin.translate(dir);
    step(editor, theme, dir, origin);
  }
}
