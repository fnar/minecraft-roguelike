package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class BumboTower extends Tower {

  public BumboTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord origin) {
    Coord ground = TowerType.getBaseCoord(editor, origin);

    Direction dir = Direction.randomCardinal(editor.getRandom());

    stem(editor, theme, ground, dir);

    Coord pos = ground.copy();
    pos.translate(dir.clockwise(), 5);
    pos.up(9);
    arm(editor, theme, pos, dir.clockwise());

    pos = ground.copy();
    pos.translate(dir.antiClockwise(), 5);
    pos.up(10);
    arm(editor, theme, pos, dir.antiClockwise());

    pos = ground.copy();
    pos.up(16);
    hat(editor, theme, pos);

    pos = ground.copy();
    pos.up(10);
    pos.translate(dir, 4);
    face(editor, theme, pos, dir);

    rooms(editor, theme, ground);

    Coord topStep = new Coord(ground).up(12);
    generateStaircase(editor, theme, topStep, origin);
  }

  private void stem(WorldEditor editor, Theme theme, Coord origin, Direction dir) {
    BlockBrush green = getPillar(theme);

    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    start.translate(dir, 4);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.reverse(), 4);
    end.translate(dir.clockwise(), 3);
    start.down(10);
    end.up(16);

    RectSolid.newRect(start, end).fill(editor, green);

    start = origin.copy();
    end = origin.copy();
    start.translate(dir.antiClockwise(), 4);
    start.translate(dir, 3);
    end.translate(dir.antiClockwise(), 4);
    end.translate(dir.reverse(), 3);
    start.down(10);
    end.up(16);

    RectSolid.newRect(start, end).fill(editor, green);

    start.translate(dir.clockwise(), 8);
    end.translate(dir.clockwise(), 8);

    RectSolid.newRect(start, end).fill(editor, green);


  }

  private void arm(WorldEditor editor, Theme theme, Coord origin, Direction dir) {
    BlockBrush green = getPillar(theme);

    Coord start;
    Coord end;
    start = origin.copy();
    end = origin.copy();

    start.translate(dir, 5);
    start.down(4);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.down();
    RectSolid.newRect(start, end).fill(editor, green);

    start = origin.copy();
    end = origin.copy();
    start.translate(dir, 5);
    end.translate(dir, 3);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, green);

    start = origin.copy();
    end = origin.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(dir, 2);
    RectSolid.newRect(start, end).fill(editor, green);

    start.translate(dir, 2);
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, green);

    start.down(3);
    start.translate(dir, 3);
    end.translate(dir, 3);
    RectSolid.newRect(start, end).fill(editor, green);

    start = origin.copy();
    start.translate(dir, 6);
    end = start.copy();
    start.up(2);
    end.down(4);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, green);

    start = origin.copy();
    start.down(5);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(dir, 5);
    RectSolid.newRect(start, end).fill(editor, green);

    start = origin.copy();
    start.translate(dir, 3);
    start.up(3);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(dir, 2);
    RectSolid.newRect(start, end).fill(editor, green);
  }

  private void hat(WorldEditor editor, Theme theme, Coord origin) {
    BlockBrush yellow = theme.getSecondary().getWall();
    BlockBrush red = theme.getSecondary().getFloor();

    Coord offset = new Coord(origin.getX(), 0, origin.getZ());
    BlockBrush rim = new BlockCheckers(red, yellow, offset);
    BlockBrush rim2 = new BlockCheckers(yellow, red, offset);

    Coord start;
    Coord end;
    Coord pos;

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-2, 0, -2));
    end.translate(new Coord(2, 8, 2));
    RectSolid.newRect(start, end).fill(editor, yellow);

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(dir, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      end.up(5);
      RectSolid.newRect(start, end).fill(editor, yellow);

      start = origin.copy();
      start.translate(dir, 4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      end.up(2);
      RectSolid.newRect(start, end).fill(editor, yellow);

      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end = start.copy();
      end.up(2);
      RectSolid.newRect(start, end).fill(editor, yellow);

      for (Direction o : dir.orthogonals()) {
        start = origin.copy();
        start.translate(dir, 3);
        end = start.copy();
        end.translate(dir, 3);
        end.translate(o, 5);
        RectSolid.newRect(start, end).fill(editor, yellow);
      }


      start = origin.copy();
      start.translate(dir, 7);
      end = start.copy();
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      RectSolid.newRect(start, end).fill(editor, rim2);
      start.translate(dir);
      end.translate(dir);
      start.up();
      end.up();
      RectSolid.newRect(start, end).fill(editor, rim);

      for (Direction o : dir.orthogonals()) {
        pos = origin.copy();
        pos.translate(dir, 5);
        pos.translate(o, 5);
        rim.stroke(editor, pos);
        pos.translate(dir);
        rim.stroke(editor, pos);
        pos.up();
        pos.translate(dir);
        rim2.stroke(editor, pos);
        pos.translate(dir.reverse());
        pos.translate(o);
        rim.stroke(editor, pos);
      }
    }
  }

  private void face(WorldEditor editor, Theme theme, Coord origin, Direction dir) {
    BlockBrush black = theme.getSecondary().getPillar();
    BlockBrush white = theme.getPrimary().getPillar();
    BlockBrush moustache = theme.getPrimary().getFloor();
    BlockBrush green = getPillar(theme);

    Coord start;
    Coord end;
    Coord pos;


    // mouth
    start = origin.copy();
    end = origin.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(dir);
    end.down(3);
    RectSolid.newRect(start, end).fill(editor, green);
    start.translate(dir.clockwise());
    start.down();
    end.translate(dir.antiClockwise());
    end.up();
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    pos = origin.copy();
    pos.translate(dir);
    pos.translate(dir.clockwise(), 2);
    pos.down(3);
    SingleBlockBrush.AIR.stroke(editor, pos);

    start = origin.copy();
    end = origin.copy();
    start.translate(dir.reverse());
    end.translate(dir.reverse());
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    start.down();
    end.down(2);
    RectSolid.newRect(start, end).fill(editor, black);

    for (Direction o : dir.orthogonals()) {
      pos = origin.copy();
      pos.down();
      pos.translate(o);
      white.stroke(editor, pos);
    }

    pos = origin.copy();
    pos.translate(dir, 2);
    moustache.stroke(editor, pos);

    for (Direction o : dir.orthogonals()) {
      start = origin.copy();
      start.translate(dir, 2);
      end = start.copy();
      end.translate(o, 2);
      start.translate(o);
      start.up();
      RectSolid.newRect(start, end).fill(editor, moustache);
      start.translate(o, 4);
      start.down(2);
      RectSolid.newRect(start, end).fill(editor, moustache);
      pos = origin.copy();
      pos.translate(dir, 2);
      pos.translate(o, 4);
      SingleBlockBrush.AIR.stroke(editor, pos);
    }

    for (Direction o : dir.orthogonals()) {
      pos = origin.copy();
      pos.translate(o, 2);
      if (o == dir.clockwise()) {
        pos.up();
      }
      pos.up(2);
      SingleBlockBrush.AIR.stroke(editor, pos);
      pos.up();
      SingleBlockBrush.AIR.stroke(editor, pos);
    }
  }

  private void rooms(WorldEditor editor, Theme theme, Coord origin) {

    Coord start;
    Coord end;

    start = origin.copy();
    start.up(7);
    end = start.copy();
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 3, 3));
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.up(5);
    end.up(5);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    for (Direction d : Direction.CARDINAL) {
      start = origin.copy();
      start.up(5);
      start.translate(d, 3);
      start.translate(d.antiClockwise(), 3);
      end = start.copy();
      end.up(10);
      RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getFloor());
    }
  }
}
