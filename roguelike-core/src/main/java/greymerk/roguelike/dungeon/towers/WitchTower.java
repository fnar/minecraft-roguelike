package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedGlass;

public class WitchTower extends Tower {

  public WitchTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord origin) {

    BlockBrush glass = stainedGlass().setColor(DyeColor.BLACK);

    Coord main = TowerType.getBaseCoord(editor, origin);

    Coord cursor;
    Coord start;
    Coord end;

    // main floor

    start = main.copy();
    start.north(3);
    start.west(3);
    start.down();
    end = main.copy();
    end.south(3);
    end.east(3);
    end.up(3);

    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonal = dir.orthogonals();

      start = main.copy();
      start.translate(dir, 3);
      start.translate(orthogonal[0], 3);
      start.down();
      end = start.copy();
      end.up(3);

      getPrimaryPillar().fill(editor, RectSolid.newRect(start, end));

      for (Direction o : orthogonal) {
        start = main.copy();
        start.translate(dir, 4);
        start.translate(o, 2);
        start.down();
        end = start.copy();
        end.up(3);

        getPrimaryPillar().fill(editor, RectSolid.newRect(start, end));
        cursor = end.copy();
        cursor.translate(dir);
        getPrimaryStair().setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
        for (Direction d : orthogonal) {
          cursor = end.copy();
          cursor.translate(d);
          getPrimaryStair().setUpsideDown(true).setFacing(d).stroke(editor, cursor);
        }
      }
    }

    // second floor

    Coord secondFloor = main.copy();
    secondFloor.up(4);

    start = secondFloor.copy();
    start.north(4);
    start.west(4);
    start.down();
    end = secondFloor.copy();
    end.south(4);
    end.east(4);
    end.up(6);

    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonal = dir.orthogonals();

      start = secondFloor.copy();
      start.translate(dir, 4);
      start.up();
      end = start.copy();
      start.translate(orthogonal[0]);
      end.translate(orthogonal[1]);
      end.up();
      glass.fill(editor, RectSolid.newRect(start, end));

      start = secondFloor.copy();
      start.translate(dir, 4);
      start.down();
      start.translate(orthogonal[0], 4);
      end = start.copy();
      end.up(4);
      SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

      for (Direction o : orthogonal) {

        start = secondFloor.copy();
        start.down();
        start.translate(dir, 4);
        start.translate(o, 3);
        end = start.copy();
        end.up(3);
        getPrimaryPillar().fill(editor, RectSolid.newRect(start, end));

        start = secondFloor.copy();
        start.down();
        start.translate(dir, 5);
        start.translate(o, 2);
        end = start.copy();
        end.up(4);
        getPrimaryPillar().fill(editor, RectSolid.newRect(start, end));

        cursor = end.copy();
        cursor.translate(dir);
        getPrimaryStair().setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
        for (Direction d : orthogonal) {
          cursor = end.copy();
          cursor.translate(d);
          getPrimaryStair().setUpsideDown(true).setFacing(d).stroke(editor, cursor);
        }
      }
    }

    // third floor

    Coord thirdFloor = secondFloor.copy();
    thirdFloor.up(7);

    start = thirdFloor.copy();
    start.north(3);
    start.west(3);
    start.down();
    end = thirdFloor.copy();
    end.south(3);
    end.east(3);
    end.up(4);

    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonal = dir.orthogonals();

      cursor = thirdFloor.copy();
      cursor.translate(dir, 3);
      cursor.up();
      window(editor, dir, cursor);

      start = thirdFloor.copy();
      start.translate(dir, 2);
      end = start.copy();
      end.translate(dir, 4);
      end.down();
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      start = thirdFloor.copy();
      start.translate(dir, 5);
      start.down(2);
      end = start.copy();
      start.translate(orthogonal[0]);
      end.translate(orthogonal[1]);
      end.down();
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      start = thirdFloor.copy();
      start.translate(dir, 3);
      start.translate(orthogonal[0], 3);
      start.down(2);
      end = start.copy();
      end.up(5);
      getPrimaryPillar().fill(editor, RectSolid.newRect(start, end));

      cursor = end.copy();
      cursor.translate(dir);
      getPrimaryStair().setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

      cursor = end.copy();
      cursor.translate(orthogonal[0]);
      getPrimaryStair().setUpsideDown(true).setFacing(orthogonal[0]).stroke(editor, cursor);

      for (Direction o : orthogonal) {

        start = thirdFloor.copy();
        start.translate(dir, 4);
        start.down();
        start.translate(o, 3);
        end = start.copy();
        end.translate(o);
        end.down();
        SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

        for (int i = 0; i < 4; ++i) {
          start = thirdFloor.copy();
          start.translate(dir, 4);
          start.translate(o, i + 1);
          start.down(i);
          end = start.copy();
          end.translate(dir, 2);
          getPrimaryStair().setUpsideDown(false).setFacing(o);
          getPrimaryStair().fill(editor, RectSolid.newRect(start, end));

          if (i < 3) {
            start = thirdFloor.copy();
            start.translate(dir, 4);
            start.translate(o, i + 1);
            start.down(i + 1);
            end = start.copy();
            end.translate(dir, 2);
            getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
          }

          start = thirdFloor.copy();
          start.translate(dir, 4);
          start.translate(o, 2);
          start.down(3);
          end = start.copy();
          end.translate(dir, 2);
          getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

          cursor = thirdFloor.copy();
          cursor.translate(dir, 6);
          cursor.translate(o);
          cursor.down(2);
          getPrimaryStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        }
      }
    }


    for (int i = thirdFloor.getY() - 1; i >= 50; --i) {
      editor.spiralStairStep(editor.getRandom(), new Coord(origin.getX(), i, origin.getZ()), getPrimaryStair(), getPrimaryPillar());
    }

    // attic

    Coord attic = thirdFloor.copy();
    attic.up(5);

    start = attic.copy();
    start.north(2);
    start.west(2);
    start.down();
    end = attic.copy();
    end.south(2);
    end.east(2);
    end.up(3);

    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    start = attic.copy();
    start.up(4);
    end = start.copy();
    start.north();
    start.west();
    end.south();
    end.east();
    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    start = attic.copy();
    start.up(5);
    end = start.copy();
    end.up(2);
    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonal = dir.orthogonals();

      cursor = attic.copy();
      cursor.translate(dir, 2);
      cursor.up();
      window(editor, dir, cursor);

      getPrimaryStair().setUpsideDown(false).setFacing(dir);

      start = attic.copy();
      start.translate(dir, 3);
      end = start.copy();
      start.translate(orthogonal[0], 3);
      end.translate(orthogonal[1], 3);

      getPrimaryStair().fill(editor, RectSolid.newRect(start, end));

      start = attic.copy();
      start.translate(dir, 4);
      start.down();
      end = start.copy();
      start.translate(orthogonal[0], 4);
      end.translate(orthogonal[1], 4);

      getPrimaryStair().fill(editor, RectSolid.newRect(start, end));

      start = attic.copy();
      start.translate(dir, 3);
      start.up(3);
      end = start.copy();
      start.translate(orthogonal[0], 3);
      end.translate(orthogonal[1], 3);

      getPrimaryStair().fill(editor, RectSolid.newRect(start, end));

      start = attic.copy();
      start.translate(dir, 2);
      start.up(4);
      end = start.copy();
      start.translate(orthogonal[0], 2);
      end.translate(orthogonal[1], 2);

      getPrimaryStair().fill(editor, RectSolid.newRect(start, end));

    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = main.copy();
      cursor.translate(dir, 4);
      if (editor.isAirBlock(cursor)) {
        cursor = main.copy();
        cursor.translate(dir, 3);
        getPrimaryDoor().setFacing(dir).stroke(editor, cursor);
        cursor.translate(dir);
        start = cursor.copy();
        end = start.copy();
        end.up();
        end.translate(dir, 3);
        SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

        cursor = main.copy();
        cursor.translate(dir, 4);
        cursor.down();
        step(editor, dir, cursor);
        break;
      }
    }
  }

  private void window(WorldEditor editor, Direction dir, Coord origin) {

    Coord cursor;

    StairsBlock stair = getPrimaryStair();

    cursor = origin.copy();
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }
  }

  private void step(WorldEditor editor, Direction dir, Coord origin) {

    if (editor.isOpaqueCubeBlock(origin)) {
      return;
    }

    Coord start;
    Coord end;

    StairsBlock stair = getPrimaryStair();
    BlockBrush blocks = getPrimaryWall();

    Direction[] orthogonal = dir.orthogonals();

    start = origin.copy();
    end = origin.copy();
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.newRect(start, end).fill(editor, blocks);

    start = origin.copy();
    end = origin.copy();
    start.translate(orthogonal[0]);
    end.translate(orthogonal[1]);
    stair.setUpsideDown(false).setFacing(dir);
    RectSolid.newRect(start, end).fill(editor, stair);

    origin.down();
    origin.translate(dir);
    step(editor, dir, origin);
  }
}
