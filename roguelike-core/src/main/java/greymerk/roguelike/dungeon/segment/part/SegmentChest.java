package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.Cardinal.UP;

public class SegmentChest extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orthogonals = dir.orthogonals();

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.translate(UP, 2);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.fill(editor, start, end, theme.getSecondary().getWall());

    for (Cardinal d : orthogonals) {
      cursor = new Coord(origin);
      cursor.translate(UP, 2);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setUpsideDown(true).setFacing(dir.reverse());
      stair.stroke(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setUpsideDown(false).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(UP, 1);
    cursor.translate(dir, 3);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(UP, 1);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);

    Coord shelf = new Coord(origin);
    shelf.translate(dir, 3);
    Coord below = new Coord(shelf);
    shelf.translate(UP, 1);

    if (editor.isAirBlock(below)) {
      return;
    }

    boolean isTrapped = rand.nextInt(20) == 0;
    editor.getTreasureChestEditor().createChest(Dungeon.getLevel(origin.getY()), shelf, isTrapped, ChestType.COMMON_TREASURES);
    if (isTrapped) {
      BlockType.TNT.getBrush().stroke(editor, new Coord(shelf.getX(), shelf.getY() - 2, shelf.getZ()));
      if (rand.nextBoolean()) {
        BlockType.TNT.getBrush().stroke(editor, new Coord(shelf.getX(), shelf.getY() - 3, shelf.getZ()));
      }
    }
  }
}
