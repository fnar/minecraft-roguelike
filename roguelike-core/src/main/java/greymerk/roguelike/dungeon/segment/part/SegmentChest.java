package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.Direction.UP;

public class SegmentChest extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Theme theme, Coord origin) {
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    Direction[] orthogonals = dir.orthogonals();

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.translate(UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall());

    for (Direction d : orthogonals) {
      cursor = origin.copy();
      cursor.translate(UP, 2);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setUpsideDown(true).setFacing(dir.reverse());
      stair.stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setUpsideDown(false).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.translate(UP, 1);
    cursor.translate(dir, 3);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(UP, 1);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);

    Coord shelf = origin.copy();
    shelf.translate(dir, 3);
    Coord below = shelf.copy();
    shelf.translate(UP, 1);

    if (editor.isAirBlock(below)) {
      return;
    }

    int difficulty = level.getSettings().getDifficulty(shelf);
    boolean isTrapped = rand.nextInt(30 / (Math.max(1, difficulty))) == 0;
    editor.getTreasureChestEditor().createChest(shelf, isTrapped, Dungeon.getLevel(origin.getY()), dir, ChestType.COMMON_TREASURES);
    if (isTrapped) {
      Coord tntCoord = shelf.copy().down(2);
      BlockType.TNT.getBrush().stroke(editor, tntCoord);
      if (rand.nextBoolean()) {
        BlockType.TNT.getBrush().stroke(editor, tntCoord.down());
      }
    }
  }
}
