package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.treasure.Treasure.COMMON_TREASURES;
import static greymerk.roguelike.treasure.Treasure.createChest;
import static greymerk.roguelike.worldgen.Cardinal.UP;

public class SegmentChest extends SegmentBase {


  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();


    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(origin);
    start.add(dir, 2);
    end = new Coord(start);
    start.add(orth[0], 1);
    end.add(orth[1], 1);
    end.add(UP, 2);
    RectSolid.fill(editor, rand, start, end, air);
    start.add(dir, 1);
    end.add(dir, 1);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());

    for (Cardinal d : orth) {
      cursor = new Coord(origin);
      cursor.add(UP, 2);
      cursor.add(dir, 2);
      cursor.add(d, 1);
      stair.setOrientation(dir.reverse(), true);
      stair.set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 2);
      cursor.add(d, 1);
      stair.setOrientation(d.reverse(), false);
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(UP, 1);
    cursor.add(dir, 3);
    air.set(editor, rand, cursor);
    cursor.add(UP, 1);
    stair.setOrientation(dir.reverse(), true);
    stair.set(editor, cursor);

    Coord shelf = new Coord(origin);
    shelf.add(dir, 3);
    Coord below = new Coord(shelf);
    shelf.add(UP, 1);

    if (editor.isAirBlock(below)) {
      return;
    }

    boolean isTrapped = rand.nextInt(20) == 0;
    createChest(editor, rand, Dungeon.getLevel(origin.getY()), shelf, isTrapped, COMMON_TREASURES);
    if (isTrapped) {
      BlockType.get(BlockType.TNT).set(editor, new Coord(shelf.getX(), shelf.getY() - 2, shelf.getZ()));
      if (rand.nextBoolean()) {
        BlockType.get(BlockType.TNT).set(editor, new Coord(shelf.getX(), shelf.getY() - 3, shelf.getZ()));
      }
    }
  }
}
