package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class RuinTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord origin) {

    BlockBrush blocks = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord floor = Tower.getBaseCoord(editor, origin);

    Coord cursor;
    Coord start;
    Coord end;

    RectSolid.newRect(new Coord(origin.getX() - 4, floor.getY() + 1, origin.getZ() - 4), new Coord(origin.getX() + 4, floor.getY() + 3, origin.getZ() + 4)).fill(editor, SingleBlockBrush.AIR);

    RectSolid.newRect(new Coord(origin.getX() - 3, floor.getY() - 5, origin.getZ() - 3), new Coord(origin.getX() + 3, floor.getY(), origin.getZ() + 3)).fill(editor, blocks);

    RectSolid.newRect(new Coord(origin.getX() - 2, origin.getY() + 10, origin.getZ() - 2), new Coord(origin.getX() + 2, floor.getY() - 1, origin.getZ() + 2)).fill(editor, blocks, false, true);

    for (int i = floor.getY(); i >= origin.getY(); --i) {
      editor.spiralStairStep(rand, new Coord(origin.getX(), i, origin.getZ()), stair, theme.getPrimary().getPillar());
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonals : dir.orthogonals()) {
        cursor = new Coord(floor);
        cursor.translate(dir, 4);
        cursor.translate(orthogonals);
        RectSolid.newRect(new Coord(cursor), new Coord(cursor.getX(), cursor.getY() + 1 + rand.nextInt(3), cursor.getZ())).fill(editor, blocks);
        cursor.translate(orthogonals);
        RectSolid.newRect(new Coord(cursor), new Coord(cursor.getX(), cursor.getY() + 1 + rand.nextInt(2), cursor.getZ())).fill(editor, blocks);
      }

      start = new Coord(floor);
      start.translate(Cardinal.DOWN);
      start.translate(dir, 4);
      end = new Coord(start.getX(), origin.getY() + 10, start.getZ());
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      RectSolid.newRect(start, end).fill(editor, blocks, true, false);

      cursor = new Coord(floor);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      RectSolid.newRect(new Coord(cursor.getX(), origin.getY() + 20, cursor.getZ()), new Coord(cursor.getX(), floor.getY() + 2 + rand.nextInt(4), cursor.getZ())).fill(editor, blocks, true, false);
    }
  }
}
