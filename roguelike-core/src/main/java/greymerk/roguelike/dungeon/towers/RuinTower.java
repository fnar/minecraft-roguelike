package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class RuinTower extends Tower {

  public RuinTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord origin) {

    BlockBrush blocks = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    Coord floor = TowerType.getBaseCoord(editor, origin);

    Coord cursor;
    Coord start;
    Coord end;

    RectSolid.newRect(new Coord(origin.getX() - 4, floor.getY() + 1, origin.getZ() - 4), new Coord(origin.getX() + 4, floor.getY() + 3, origin.getZ() + 4)).fill(editor, SingleBlockBrush.AIR);

    RectSolid.newRect(new Coord(origin.getX() - 3, floor.getY() - 5, origin.getZ() - 3), new Coord(origin.getX() + 3, floor.getY(), origin.getZ() + 3)).fill(editor, blocks);

    RectSolid.newRect(new Coord(origin.getX() - 2, origin.getY() + 10, origin.getZ() - 2), new Coord(origin.getX() + 2, floor.getY() - 1, origin.getZ() + 2)).fill(editor, blocks, false, true);

    for (int i = floor.getY(); i >= origin.getY(); --i) {
      editor.spiralStairStep(editor.getRandom(), new Coord(origin.getX(), i, origin.getZ()), stair, theme.getPrimary().getPillar());
    }

    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonals : dir.orthogonals()) {
        cursor = floor.copy();
        cursor.translate(dir, 4);
        cursor.translate(orthogonals);
        RectSolid.newRect(cursor.copy(), new Coord(cursor.getX(), cursor.getY() + 1 + editor.getRandom().nextInt(3), cursor.getZ())).fill(editor, blocks);
        cursor.translate(orthogonals);
        RectSolid.newRect(cursor.copy(), new Coord(cursor.getX(), cursor.getY() + 1 + editor.getRandom().nextInt(2), cursor.getZ())).fill(editor, blocks);
      }

      start = floor.copy();
      start.down();
      start.translate(dir, 4);
      end = new Coord(start.getX(), origin.getY() + 10, start.getZ());
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      RectSolid.newRect(start, end).fill(editor, blocks, true, false);

      cursor = floor.copy();
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      RectSolid.newRect(new Coord(cursor.getX(), origin.getY() + 20, cursor.getZ()), new Coord(cursor.getX(), floor.getY() + 2 + editor.getRandom().nextInt(4), cursor.getZ())).fill(editor, blocks, true, false);
    }
  }
}
