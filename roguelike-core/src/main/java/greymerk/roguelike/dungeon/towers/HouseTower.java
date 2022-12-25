package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.BedBlock;
import com.github.fnar.minecraft.block.decorative.FlowerPotBlock;
import com.github.fnar.minecraft.block.decorative.TorchBlock;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.roguelike.worldgen.generatables.SpiralStaircase;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.carpet;
import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedGlassPane;

public class HouseTower extends Tower {

  public HouseTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord dungeon) {

    Coord floor = TowerType.getBaseCoord(editor, dungeon);

    BlockBrush mainFloor = getPrimaryFloor();
    StairsBlock stair = getPrimaryStair();

    Direction dir = Direction.CARDINAL.get((floor.getY() + 2) % 4);

    Coord cursor;
    Coord start;
    Coord end;

    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    floor.up();

    start = floor.copy();
    start.up(4);
    end = start.copy();
    start.translate(dir.clockwise(), 3);
    start.translate(dir, 3);
    end.up(8);
    end.translate(dir.reverse(), 7);
    end.translate(dir.antiClockwise(), 10);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    start = floor.copy();
    start.translate(dir.clockwise(), 2);
    start.down();
    end = floor.copy();
    end.up(3);
    end.translate(dir.antiClockwise(), 8);
    end.translate(dir.reverse(), 5);
    getPrimaryWall().fill(editor, RectSolid.newRect(new Coord(x - 2, floor.getY() + 3, z - 2), new Coord(x + 2, y + 10, z + 2)));
    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    cursor = floor.copy();
    cursor.translate(dir.antiClockwise(), 6);
    cursor.translate(dir.reverse(), 6);
    door(dir, cursor);

    start = floor.copy();
    start.down();
    start.translate(dir.clockwise());
    start.translate(dir.reverse());
    end = floor.copy();
    end.down();
    end.translate(dir.reverse(), 4);
    end.translate(dir.antiClockwise(), 7);
    mainFloor.fill(editor, RectSolid.newRect(start, end));

    start = floor.copy();
    start.down(2);
    start.translate(dir.clockwise(), 2);
    start.translate(dir.reverse(), 2);
    end = new Coord(floor.getX(), y + 10, floor.getZ());
    end.translate(dir.reverse(), 5);
    end.translate(dir.antiClockwise(), 8);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    cursor = floor.copy();
    cursor.translate(dir.reverse(), 5);
    cursor.translate(dir.clockwise(), 2);
    support(new Direction[]{dir.reverse(), dir.clockwise()}, cursor);
    cursor.translate(dir, 7);
    support(new Direction[]{dir, dir.clockwise()}, cursor);
    cursor.translate(dir.antiClockwise(), 4);
    support(new Direction[]{dir, dir.antiClockwise()}, cursor);
    cursor.translate(dir.antiClockwise(), 6);
    cursor.translate(dir.reverse(), 2);
    support(new Direction[]{dir, dir.antiClockwise()}, cursor);

    upperFloor(dir, new Coord(x, floor.getY() + 3, z));
    roof(dir, new Coord(x, floor.getY() + 4, z));
    upperWalls(dir, new Coord(x, floor.getY() + 4, z));
    windows(dir, floor);
    decor(dir, floor);

    BlockBrush pillar = getSecondaryPillar();
    int secondStoryFloor = 4;
    int stairHeight = floor.getY() - y + secondStoryFloor;
    SpiralStaircase.newStaircase(editor).withHeight(stairHeight).withStairs(stair).withPillar(pillar).generate(dungeon);
  }

  private void decor(Direction dir, Coord origin) {

    // downstairs table
    Coord cursor = origin.copy();
    cursor.translate(dir.reverse(), 4);
    StairsBlock.oak().setUpsideDown(true).setFacing(dir.right()).stroke(editor, cursor);
    cursor.translate(dir.left());
    SlabBlock.oak().setTop(true).setFullBlock(false).setSeamless(false).stroke(editor, cursor);
    cursor.translate(dir.left());
    StairsBlock.oak().setUpsideDown(true).setFacing(dir.left()).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(dir.left(), 4);
    cursor.translate(dir.reverse());
    StairsBlock.oak().setUpsideDown(true).setFacing(dir.right()).stroke(editor, cursor);
    cursor.translate(dir.left());
    SlabBlock.oak().setTop(true).setFullBlock(false).setSeamless(false).stroke(editor, cursor);
    cursor.translate(dir.left());
    StairsBlock.oak().setUpsideDown(true).setFacing(dir.left()).stroke(editor, cursor);
    cursor.translate(dir.right());
    cursor.up();
    BlockType.CAKE.getBrush().stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(dir.left(), 7);
    cursor.translate(dir.reverse());
    SlabBlock.oak().setTop(true).setFullBlock(false).setSeamless(false).stroke(editor, cursor);
    cursor.up();
    TorchBlock.torch().setFacing(Direction.UP).stroke(editor, cursor);
    cursor.down();
    cursor.translate(dir.reverse());
    BlockType.CRAFTING_TABLE.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse());
    BlockType.FURNACE.getBrush().setFacing(dir.right()).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.up(4);
    cursor.translate(dir.right(), 2);
    cursor.translate(dir.reverse(), 3);
    StairsBlock.oak().setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    StairsBlock.oak().setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.up();
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);
    cursor.translate(dir);
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.up(4);
    cursor.translate(dir.left());
    cursor.translate(dir.reverse(), 5);
    StairsBlock.oak().setUpsideDown(true).setFacing(dir.right()).stroke(editor, cursor);
    cursor.translate(dir.left());
    SlabBlock.oak().setTop(true).setFullBlock(false).setSeamless(false).stroke(editor, cursor);
    cursor.translate(dir.left());
    StairsBlock.oak().setUpsideDown(true).setFacing(dir.left()).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.up(4);
    cursor.translate(dir.left(), 8);

    chest(editor, dir, cursor);

    cursor.translate(dir.reverse());
    BlockType.BOOKSHELF.getBrush().stroke(editor, cursor);
    cursor.up();
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);
    cursor.down();
    cursor.translate(dir.reverse());
    BedBlock.bed().setColor(DyeColor.RED).setFacing(dir.right()).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.up(4);
    cursor.translate(dir.reverse());
    cursor.translate(dir.left());
    Coord start = cursor.copy();
    Coord end = start.copy();
    end.translate(dir.left(), 5);
    end.translate(dir.reverse(), 3);
    BlockStripes carpet = new BlockStripes();
    carpet.addBlock(carpet().setColor(DyeColor.chooseRandom(editor.getRandom())));
    carpet.addBlock(carpet().setColor(DyeColor.chooseRandom(editor.getRandom())));
    carpet.addBlock(carpet().setColor(DyeColor.chooseRandom(editor.getRandom())));
    carpet.fill(editor, RectSolid.newRect(start, end));
  }

  private void windows(Direction dir, Coord origin) {
    BlockBrush pane = stainedGlassPane().setColor(DyeColor.LIGHT_GRAY);

    Coord cursor = origin.copy();
    cursor.translate(dir.reverse(), 5);
    cursor.up();
    pane.stroke(editor, cursor);
    cursor.translate(dir.left(), 2);
    pane.stroke(editor, cursor);

    cursor = origin.copy();
    cursor.up();
    cursor.translate(dir.left(), 8);
    cursor.translate(dir.reverse(), 2);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    pane.stroke(editor, cursor);

    // upstairs
    cursor = origin.copy();
    cursor.up(5);
    cursor.translate(dir.left());
    cursor.translate(dir, 3);
    pane.stroke(editor, cursor);
    cursor.translate(dir.right(), 2);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    cursor.translate(dir.right(), 2);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 3);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);

    cursor = origin.copy();
    cursor.up(5);
    cursor.translate(dir.left(), 9);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 2);
    pane.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    pane.stroke(editor, cursor);
    cursor.up(2);
    cursor.translate(dir);
    Coord start = cursor.copy();
    Coord end = start.copy();
    end.up();
    end.translate(dir, 2);
    pane.fill(editor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.up(4);
    cursor.translate(dir.left(), 5);
    cursor.translate(dir.reverse(), 7);
    start = cursor.copy();
    end = start.copy();
    end.translate(dir.left(), 2);
    end.up();
    pane.fill(editor, RectSolid.newRect(start, end));
  }

  private void roof(Direction dir, Coord origin) {

    Coord cursor = origin.copy();
    cursor.translate(dir.clockwise(), 4);
    cursor.translate(dir, 4);
    cursor.up(2);
    Coord start = cursor.copy();
    Coord end = cursor.copy();
    end.translate(dir.reverse(), 10);
    getSecondaryStair().setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    getSecondaryStair().setUpsideDown(true).setFacing(dir.antiClockwise()).fill(editor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    getSecondaryStair().setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    getSecondaryStair().setUpsideDown(true).setFacing(dir.antiClockwise()).fill(editor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    getSecondaryStair().setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    getSecondaryStair().setUpsideDown(true).setFacing(dir.antiClockwise()).fill(editor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    getSecondaryStair().setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, RectSolid.newRect(start, end));

    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    getSecondaryWall().fill(editor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 3);
    cursor.up(5);
    getSecondaryStair().setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor.translate(dir);
    getSecondaryStair().setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor.down();
    cursor.translate(dir.antiClockwise());
    cursor.translate(dir);
    getSecondaryStair().setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);
    cursor.down();
    cursor.translate(dir.antiClockwise());
    cursor.translate(dir);
    getSecondaryStair().setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);

    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir, 5);
    getSecondaryStair().setUpsideDown(false).setFacing(dir.antiClockwise()).fill(editor, RectSolid.newRect(start, end));
    start.down();
    end.down();
    getSecondaryStair().setUpsideDown(true).setFacing(dir.clockwise()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    getSecondaryStair().setUpsideDown(false).setFacing(dir.antiClockwise()).fill(editor, RectSolid.newRect(start, end));
    start.down();
    end.down();
    getSecondaryStair().setUpsideDown(true).setFacing(dir.clockwise()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    end.translate(dir);
    getSecondaryStair().setUpsideDown(false).setFacing(dir.antiClockwise()).fill(editor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.up(2);
    cursor.translate(dir, 2);
    cursor.translate(dir.antiClockwise(), 10);
    start = cursor.copy();
    end = cursor.copy();
    end.translate(dir.clockwise(), 6);
    getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    getSecondaryStair().setUpsideDown(true).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    end.translate(dir.clockwise());
    getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    getSecondaryStair().setUpsideDown(true).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    end.translate(dir.clockwise());
    getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    getSecondaryStair().setUpsideDown(true).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    end.translate(dir.clockwise());
    getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    getSecondaryStair().setUpsideDown(true).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));

    start.translate(dir.reverse());
    end.translate(dir.reverse());
    getSecondaryWall().fill(editor, RectSolid.newRect(start, end));

    start = end.copy();
    end.translate(dir.reverse(), 2);
    start.translate(dir.clockwise());
    end.translate(dir.clockwise());
    start.translate(dir);
    end.translate(dir);
    getSecondaryStair().setUpsideDown(false).setFacing(dir.clockwise()).fill(editor, RectSolid.newRect(start, end));

    cursor.translate(dir.reverse(), 10);
    start = cursor.copy();
    end = cursor.copy();
    end.translate(dir.clockwise(), 7);
    getSecondaryStair().setUpsideDown(false).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir);
    end.translate(dir);
    getSecondaryStair().setUpsideDown(true).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
    start.up();
    end.up();
    end.translate(dir.clockwise(), 5);
    getSecondaryStair().setUpsideDown(false).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir);
    end.translate(dir);
    getSecondaryStair().setUpsideDown(true).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
    end.translate(dir.antiClockwise());
    start.up();
    end.up();
    getSecondaryStair().setUpsideDown(false).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir);
    end.translate(dir);
    getSecondaryStair().setUpsideDown(true).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
    end.translate(dir.antiClockwise());
    start.up();
    end.up();
    getSecondaryStair().setUpsideDown(false).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
    start.translate(dir);
    end.translate(dir);
    getSecondaryStair().setUpsideDown(true).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
    end.translate(dir.antiClockwise());
    start.up();
    end.up();
    getSecondaryStair().setUpsideDown(false).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
  }

  private void upperFloor(Direction dir, Coord origin) {
    Coord start = origin.copy();
    start.translate(dir.right(), 3);
    start.translate(dir, 3);
    Coord end = origin.copy();
    end.translate(dir.left(), 3);
    end.translate(dir.reverse(), 6);
    getPrimaryFloor().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(dir.left(), 3);
    start.translate(dir);
    end = origin.copy();
    end.translate(dir.reverse(), 7);
    end.translate(dir.left(), 9);
    getPrimaryFloor().fill(editor, RectSolid.newRect(start, end));
  }

  private void upperWalls(Direction dir, Coord origin) {
    Coord start = origin.copy();
    start.translate(dir.right(), 3);
    start.translate(dir, 2);
    Coord end = start.copy();
    end.translate(dir.reverse(), 7);
    end.up(2);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(dir.right(), 2);
    start.translate(dir, 3);
    end = start.copy();
    end.translate(dir.left(), 4);
    end.up(3);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
    end.up();
    end.translate(dir.right());
    start = end.copy();
    start.translate(dir.right(), 2);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(dir.left(), 3);
    start.translate(dir, 2);
    end = start.copy();
    end.up(2);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(dir.left(), 4);
    start.translate(dir);
    end = start.copy();
    end.translate(dir.left(), 4);
    end.up(2);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(dir.left(), 9);
    end = start.copy();
    end.translate(dir.reverse(), 6);
    end.up(3);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
    end.up();
    end.translate(dir);
    start = end.copy();
    start.translate(dir, 4);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
    end.up();
    end.translate(dir);
    start = end.copy();
    start.translate(dir, 2);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));


    start = origin.copy();
    start.translate(dir.reverse(), 7);
    start.translate(dir.left(), 4);
    end = start.copy();
    end.translate(dir.left(), 4);
    end.up(2);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(dir.reverse(), 6);
    start.translate(dir.right(), 2);
    end = start.copy();
    end.translate(dir.left(), 4);
    end.up(3);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    Coord cursor = origin.copy();
    cursor.translate(dir.right(), 3);
    cursor.translate(dir, 3);
    pillar(cursor);
    cursor.translate(dir.left(), 6);
    pillar(cursor);
    cursor.translate(dir.reverse(), 2);
    pillar(cursor);
    cursor.translate(dir.left(), 6);
    pillar(cursor);
    cursor.translate(dir.reverse(), 8);
    pillar(cursor);
    cursor.translate(dir.right(), 6);
    pillar(cursor);
    cursor.translate(dir);
    pillar(cursor);
    cursor.translate(dir.right(), 6);
    pillar(cursor);
  }

  private void pillar(Coord start) {
    Coord end = start.copy().up(2);
    getPrimaryPillar().fill(editor, RectSolid.newRect(start, end));
  }

  private void support(Direction[] dirs, Coord origin) {
    BlockBrush pillar = getPrimaryPillar();
    StairsBlock stair = getPrimaryStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    end.up(2);
    pillar.fill(editor, RectSolid.newRect(start, end));
    cursor = origin.copy();
    cursor.down();
    editor.fillDown(cursor, pillar);

    for (Direction dir : dirs) {
      cursor = origin.copy();
      cursor.up(2);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        stair.setUpsideDown(true).setFacing(o).stroke(editor, c, true, false);
      }
    }

  }

  private void door(Direction dir, Coord origin) {

    StairsBlock stair = getPrimaryStair();
    Coord cursor;
    Coord start;
    Coord end;

    Direction[] orth = dir.orthogonals();

    start = origin.copy();
    start.translate(dir.reverse());
    end = start.copy();
    start.translate(dir.left());
    end.translate(dir.right());
    end.translate(dir.reverse(), 2);
    end.up(6);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    end = start.copy();
    start.down();
    start.translate(dir.left());
    end.up(2);
    end.translate(dir.right());
    getPrimaryFloor().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.down();
    end = start.copy();
    start.translate(dir.reverse());
    end.translate(dir);
    start.translate(dir.left());
    end.translate(dir.right());
    getPrimaryFloor().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.down(2);
    end = start.copy();
    start.translate(dir.reverse());
    end.translate(dir);
    start.translate(dir.left());
    end.translate(dir.right());
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.newRect(start, end).fill(editor, getPrimaryFloor(), true, false);

    getPrimaryDoor().setFacing(dir.reverse()).stroke(editor, origin);

    for (Direction o : orth) {

      cursor = origin.copy();
      cursor.translate(o, 2);
      cursor.up(2);
      editor.fillDown(cursor, getPrimaryPillar());

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
    cursor.up(2);
    cursor.translate(dir.left(), 3);
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

    start = origin.copy();
    start.translate(dir);
    end = start.copy();
    start.translate(dir.left());
    end.translate(dir.right());
    end.up(2);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.down();
    cursor.translate(dir.reverse(), 2);
    step(dir.reverse(), cursor);
  }

  private void step(Direction dir, Coord origin) {
    Coord cursor = origin.copy();
    cursor.down();
    cursor.translate(dir);
    if (editor.isValidGroundBlock(cursor)) {
      return;
    }
    if (cursor.getY() <= 60) {
      return;
    }

    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(dir.left());
    end.translate(dir.right());
    end = new Coord(end.getX(), 60, end.getZ());
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    end = origin.copy();
    start.translate(dir.left());
    end.translate(dir.right());
    getPrimaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));

    origin.down();
    origin.translate(dir);
    step(dir, origin);
  }
}
