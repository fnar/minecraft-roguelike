package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.roguelike.worldgen.generatables.SpiralStaircase;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class EthoTower extends Tower {

  public EthoTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord dungeon) {
    Coord floor = TowerType.getBaseCoord(editor, dungeon);
    Coord start = floor.copy();
    Coord end = start.copy();

    start.north(3);
    start.west(3);
    end.south(3);
    end.east(3);
    end.up(4);

    //WorldGenPrimitive.fillRectSolid(rand, start, end, air);

    start.north();
    start.west();
    start.down();
    end.south();
    end.east();
    end.up();

    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonals = dir.orthogonals();
      start = floor.copy();
      start.translate(dir, 3);
      start.translate(orthogonals[0], 3);
      end = start.copy();
      end.up(6);

      getSecondaryPillar().fill(editor, RectSolid.newRect(start, end));

      for (Direction o : orthogonals) {
        start = floor.copy();
        start.translate(dir, 5);
        start.translate(o, 4);
        end = start.copy();
        end.up(4);
        start.down(10);
        getSecondaryPillar().fill(editor, RectSolid.newRect(start, end));

        end.up();
        getSecondaryStair().setUpsideDown(false).setFacing(dir).stroke(editor, end);

        end.translate(dir.reverse());
        end.translate(o.reverse());
        getSecondaryStair().setUpsideDown(false).setFacing(o.reverse()).stroke(editor, end);
        end.translate(o.reverse());
        start = end.copy();
        start.translate(o.reverse(), 2);
        getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));

        end.translate(dir.reverse());
        end.up();
        start.translate(dir.reverse());
        start.up();
        getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
        getSecondaryStair().setUpsideDown(false).setFacing(o.reverse()).stroke(editor, end);

        start = floor.copy();
        start.translate(dir, 3);
        start.up(4);
        end = start.copy();
        end.translate(o, 2);
        getSecondaryStair().setUpsideDown(true).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
        start.translate(dir.reverse());
        start.up();
        end = start.copy();
        end.translate(o, 2);
        getSecondaryStair().setUpsideDown(true).setFacing(dir.reverse()).fill(editor, RectSolid.newRect(start, end));
        start.up();
        end.up();
        getSecondaryPillar().fill(editor, RectSolid.newRect(start, end));
        cursor = end.copy();
        start = end.copy();
        start.up(3);
        getSecondaryPillar().fill(editor, RectSolid.newRect(start, end));
        cursor.translate(o.reverse());
        cursor.up();
        getSecondaryStair().setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
        cursor.up(2);
        getSecondaryStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        start.up();
        end = start.copy();
        end.translate(o.reverse(), 2);
        getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
        cursor = end.copy();
        cursor.translate(dir.reverse());
        getSecondaryStair().setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
        cursor.translate(o);
        getSecondaryStair().setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
        cursor.up();
        getSecondaryStair().setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
        cursor.translate(o.reverse());
        getSecondaryStair().setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
      }
    }

    Direction front = Direction.NORTH;

    for (Direction dir : Direction.CARDINAL) {
      cursor = floor.copy();
      cursor.translate(dir, 6);
      if (editor.isAirBlock(cursor)) {
        front = dir;
        break;
      }
    }

    for (Direction dir : Direction.CARDINAL) {

      if (dir == front) {

        for (Direction o : dir.orthogonals()) {
          cursor = floor.copy();
          cursor.translate(dir, 5);
          cursor.translate(o, 2);
          getPrimaryWall().stroke(editor, cursor);
          cursor.translate(o);
          getSecondaryStair().setUpsideDown(false).setFacing(o).stroke(editor, cursor);
          cursor.translate(dir);
          getSecondaryStair().setUpsideDown(false).setFacing(o).stroke(editor, cursor);
          cursor.translate(o.reverse());
          getSecondaryStair().setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(dir.reverse());
          cursor.up();
          getSecondaryStair().setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.up();
          getSecondaryStair().setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          getSecondaryStair().setUpsideDown(false).setFacing(o).stroke(editor, cursor);
          cursor.translate(o.reverse());
          cursor.up();
          getSecondaryStair().setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.translate(o.reverse());
          getSecondaryStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
          cursor.translate(o.reverse());
          cursor.up();
          getSecondaryStair().setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          getSecondaryStair().setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
          cursor.translate(o);
          getSecondaryStair().setUpsideDown(false).setFacing(o).stroke(editor, cursor);
        }

        // carve doorway
        Direction[] orthogonals = dir.orthogonals();
        cursor = floor.copy();
        cursor.translate(dir, 4);
        start = cursor.copy();
        end = start.copy();
        start.translate(orthogonals[0]);
        end.up(2);
        end.translate(orthogonals[1]);
        SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

        cursor = floor.copy();
        cursor.translate(dir, 6);
        cursor.down();
        step(dir, cursor);

        continue;
      }


      for (Direction o : dir.orthogonals()) {
        start = floor.copy();
        start.up(4);
        start.translate(dir, 5);
        end = start.copy();
        start.translate(o, 2);
        getSecondaryStair().setUpsideDown(false).setFacing(dir).fill(editor, RectSolid.newRect(start, end));
        start.translate(o);
        getSecondaryStair().setUpsideDown(false).setFacing(o.reverse()).stroke(editor, start);
        start.down();
        getSecondaryStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, start);
      }

    }

    SpiralStaircase.newStaircase(editor).withHeight(floor.getY() - dungeon.getY()).withStairs(getPrimaryStair()).withPillar(getPrimaryWall()).generate(dungeon);
  }

  private void step(Direction dir, Coord origin) {

    if (editor.isOpaqueCubeBlock(origin)) {
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
    StairsBlock stair = getPrimaryStair();
    stair.setUpsideDown(false).setFacing(dir);
    stair.fill(editor, RectSolid.newRect(start, end));

    origin.down();
    origin.translate(dir);
    step(dir, origin);
  }

}
