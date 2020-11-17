package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.Cardinal.UP;

public class SegmentChest extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();


    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orth[0], 1);
    end.translate(orth[1], 1);
    end.translate(UP, 2);
    RectSolid.fill(editor, rand, start, end, air);
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());

    for (Cardinal d : orth) {
      cursor = new Coord(origin);
      cursor.translate(UP, 2);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setOrientation(dir.reverse(), true);
      stair.set(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setOrientation(d.reverse(), false);
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(UP, 1);
    cursor.translate(dir, 3);
    air.set(editor, rand, cursor);
    cursor.translate(UP, 1);
    stair.setOrientation(dir.reverse(), true);
    stair.set(editor, cursor);

    Coord shelf = new Coord(origin);
    shelf.translate(dir, 3);
    Coord below = new Coord(shelf);
    shelf.translate(UP, 1);

    if (editor.isAirBlock(below)) {
      return;
    }

    boolean isTrapped = rand.nextInt(20) == 0;
    editor.treasureChestEditor.createChest(rand, Dungeon.getLevel(origin.getY()), shelf, isTrapped);
    if (isTrapped) {
      BlockType.get(BlockType.TNT).set(editor, new Coord(shelf.getX(), shelf.getY() - 2, shelf.getZ()));
      if (rand.nextBoolean()) {
        BlockType.get(BlockType.TNT).set(editor, new Coord(shelf.getX(), shelf.getY() - 3, shelf.getZ()));
      }
    }
  }
}
