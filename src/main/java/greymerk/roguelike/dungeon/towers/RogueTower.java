package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class RogueTower implements ITower {

  public void generate(WorldEditor editor, Random rand, ITheme theme, Coord dungeon) {


    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    MetaBlock air = BlockType.get(BlockType.AIR);

    IBlockFactory blocks = theme.getPrimary().getWall();

    IStair stair = theme.getPrimary().getStair();

    Coord floor = Tower.getBaseCoord(editor, dungeon);
    int ground = floor.getY() - 1;
    int main = floor.getY() + 4;
    int roof = floor.getY() + 9;

    RectSolid.fill(editor, rand, new Coord(x - 3, ground, z - 3), new Coord(x + 3, floor.getY() + 12, z + 3), air);

    RectSolid.fill(editor, rand, new Coord(x - 2, y + 10, z - 2), new Coord(x + 2, floor.getY() - 1, z + 2), blocks, false, true);

    Coord start;
    Coord end;
    Coord cursor;

    RectSolid.fill(editor, rand, new Coord(x - 3, main, z - 3), new Coord(x + 3, main, z + 3), theme.getSecondary().getWall(), true, true);
    RectSolid.fill(editor, rand, new Coord(x - 3, roof, z - 3), new Coord(x + 3, roof, z + 3), blocks);

    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {
        // ground floor
        start = new Coord(floor);
        start.translate(Cardinal.DOWN, 1);
        start.translate(dir, 2);
        end = new Coord(start);
        end.translate(dir, 3);
        end.translate(orth, 1);
        RectSolid.fill(editor, rand, start, end, blocks, true, true);
        start.translate(orth, 2);
        end.translate(dir.reverse(), 2);
        end.translate(orth, 2);
        RectSolid.fill(editor, rand, start, end, blocks, true, true);

        cursor = new Coord(floor);
        cursor.translate(dir, 5);
        cursor.translate(orth, 1);
        start = new Coord(cursor);
        end = new Coord(cursor);
        end.translate(dir.reverse(), 1);
        end.translate(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, blocks);
        start = new Coord(end);
        start.translate(dir, 1);
        start.translate(orth.reverse(), 1);
        RectSolid.fill(editor, rand, start, end, blocks);
        cursor.translate(Cardinal.UP, 2);
        stair.setOrientation(orth, false);
        stair.set(editor, rand, cursor);

        start = new Coord(floor);
        start.translate(dir, 4);
        end = new Coord(start);
        end.translate(Cardinal.UP, 9);
        end.translate(orth, 2);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.translate(dir, 3);
        start.translate(orth, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 9);
        RectSolid.fill(editor, rand, start, end, blocks);

        start = new Coord(floor);
        start.translate(dir, 4);
        end = new Coord(start);
        end.translate(dir, 1);
        end.translate(Cardinal.UP, 1);
        RectSolid.fill(editor, rand, start, end, air);

        cursor = new Coord(floor);
        cursor.translate(dir, 3);
        cursor.translate(orth, 2);
        cursor.translate(Cardinal.UP, 3);
        stair.setOrientation(orth.reverse(), true);
        stair.set(editor, cursor);
        cursor.translate(Cardinal.UP, 5);
        stair.setOrientation(orth.reverse(), true);
        stair.set(editor, cursor);

        start = new Coord(floor);
        start.translate(dir, 4);
        start.translate(orth, 3);
        start.translate(Cardinal.UP, 4);
        stair.setOrientation(orth, true);
        stair.set(editor, rand, start);

        start.translate(Cardinal.UP, 1);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, blocks, true, true);

        start = new Coord(floor);
        start.translate(dir, 5);
        start.translate(Cardinal.UP, 4);
        stair.setOrientation(dir, true);
        stair.set(editor, rand, start);

        cursor = new Coord(start);
        cursor.translate(orth, 1);
        stair.setOrientation(orth, true);
        stair.set(editor, rand, cursor);

        start.translate(Cardinal.UP, 3);
        stair.setOrientation(dir, true);
        stair.set(editor, rand, start);

        cursor = new Coord(start);
        cursor.translate(orth, 1);
        stair.setOrientation(orth, true);
        stair.set(editor, rand, cursor);

        start.translate(Cardinal.UP, 1);
        end = new Coord(start);
        end.translate(orth, 1);
        end.translate(Cardinal.UP, 1);
        RectSolid.fill(editor, rand, start, end, blocks, true, true);

        cursor = new Coord(end);
        cursor.translate(orth, 1);
        cursor.translate(Cardinal.DOWN, 1);
        stair.setOrientation(orth, true);
        stair.set(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        cursor.translate(orth, 1);
        stair.set(editor, cursor);

        cursor.translate(orth.reverse(), 1);
        blocks.set(editor, rand, cursor);
        cursor.translate(Cardinal.UP, 1);
        blocks.set(editor, rand, cursor);
        cursor.translate(orth, 1);
        blocks.set(editor, rand, cursor);
        cursor.translate(Cardinal.UP, 1);
        addCrenellation(editor, rand, cursor, blocks);

        cursor.translate(Cardinal.DOWN, 2);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(orth, 1);
        blocks.set(editor, rand, cursor);
        cursor.translate(Cardinal.DOWN, 1);
        blocks.set(editor, rand, cursor);

        cursor = new Coord(floor);
        cursor.translate(dir, 6);
        cursor.translate(Cardinal.UP, 9);

        stair.setOrientation(dir, true);
        stair.set(editor, cursor);

        cursor.translate(orth, 1);
        stair.setOrientation(orth, true);
        stair.set(editor, rand, cursor);

        cursor.translate(orth.reverse(), 1);
        cursor.translate(Cardinal.UP, 1);
        blocks.set(editor, rand, cursor);
        cursor.translate(orth, 1);
        blocks.set(editor, rand, cursor);
        cursor.translate(Cardinal.UP, 1);
        addCrenellation(editor, rand, cursor, blocks);

        cursor = new Coord(floor);
        cursor.translate(dir, 4);
        cursor.translate(Cardinal.UP, 5);
        air.set(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        air.set(editor, cursor);
        cursor.translate(orth, 2);
        BlockType.get(BlockType.IRON_BAR).set(editor, rand, cursor);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {
        start = new Coord(x, ground, z);
        start.translate(dir, 4);
        end = new Coord(x, 60, z);
        end.translate(dir, 4);
        start.translate(orth.reverse(), 2);
        end.translate(orth, 2);

        RectSolid.fill(editor, rand, start, end, blocks, true, true);
        start = new Coord(x, ground, z);
        start.translate(dir, 3);
        start.translate(orth, 3);
        end = new Coord(x, 60, z);
        end.translate(dir, 3);
        end.translate(orth, 3);
        RectSolid.fill(editor, rand, start, end, blocks, true, true);

      }
    }

    for (int i = main; i >= y; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getPrimary().getPillar());
    }
  }


  private void addCrenellation(WorldEditor editor, Random rand, Coord cursor, IBlockFactory blocks) {

    blocks.set(editor, rand, cursor);

    if (editor.isAirBlock(cursor)) {
      return;
    }

    cursor.translate(Cardinal.UP, 1);
    Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
  }
}
