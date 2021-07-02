package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.Wood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.Line;
import greymerk.roguelike.worldgen.shapes.MultiShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Sphere;

public class TreeTower implements ITower {

  public static final Wood WOOD_TYPE = Wood.OAK;

  @Override
  public void generate(WorldEditor editor, Random rand, Theme theme, Coord origin) {

    Coord start;
    Coord end;
    Coord ground = Tower.getBaseCoord(editor, origin);
    Coord upstairs = ground.copy();
    upstairs.up(7);

    BlockBrush log = WOOD_TYPE.getLog();

    start = ground.copy();
    start.down(10);

    // generate the tree
    Branch tree = new Branch(rand, start);
    tree.genWood(editor);
    tree.genLeaves(editor, rand);


    start = ground.copy();
    start.translate(new Coord(-3, -3, -3));
    end = ground.copy();
    end.translate(new Coord(3, 3, 3));
    RectSolid.newRect(start, end).fill(editor, log);

    carveRoom(editor, ground);
    carveRoom(editor, upstairs);

    Direction dir = Direction.randomCardinal(rand);
    start = ground.copy();
    end = ground.copy();
    end.up();
    end.translate(dir, 8);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = ground.copy();
    end = ground.copy();
    end.translate(new Coord(8, 8, 8));
    new Sphere(start, end).fill(editor, log, false, true);

    start = upstairs.copy();
    start.down();
    for (Coord p : new RectSolid(start, origin)) {
      editor.spiralStairStep(rand, p, theme.getPrimary().getStair(), theme.getPrimary().getPillar());
    }
  }

  private void carveRoom(WorldEditor editor, Coord origin) {
    Coord start;
    Coord end;
    BlockBrush log = WOOD_TYPE.getLog();
    int size = 4;

    start = origin.copy();
    start.translate(new Coord(-(size - 1), 0, -(size - 1)));
    end = origin.copy();
    end.translate(new Coord(size - 1, 2, size - 1));
    SingleBlockBrush.AIR.fill(editor, new RectSolid(start, end));

    start = origin.copy();
    end = start.copy();
    start.up(2);
    end.translate(new Coord(size - 1, size - 1, size - 1));
    new Sphere(start, end).fill(editor, SingleBlockBrush.AIR);

    for (Direction dir : Direction.CARDINAL) {
      start = origin.copy();
      start.translate(dir, size - 1);
      start.translate(dir.antiClockwise(), size - 1);
      end = start.copy();
      end.up(size + 1);
      new RectSolid(start, end).fill(editor, log);
    }

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-(size - 1), -1, -(size - 1)));
    end.translate(new Coord(size - 1, -1, size - 1));
    new RectSolid(start, end).fill(editor, log);
  }

  private static class Branch {

    Coord start;
    Coord end;
    List<Branch> branches;
    double thickness;

    public Branch(Random rand, Coord start) {
      this.start = start.copy();
      branches = new ArrayList<>();
      int counter = 7;
      double length = 12;
      thickness = 7;
      int mainBranches = 5;
      int density = 3;
      double noise = 0.15;
      double pitch = 0;
      double yaw = Math.PI / 2;
      end = getEnd(start, 4, pitch, yaw);

      for (int i = 0; i < mainBranches; ++i) {
        branches.add(
            new Branch(
                rand,
                end.copy(),
                counter,
                length,
                thickness,
                4,
                noise,
                pitch + ((Math.PI * 2 / density) * i),
                yaw + (rand.nextDouble() - 0.5) * noise
            )
        );
      }
    }

    public Branch(Random rand, Coord start, int counter, double length, double thickness, int density, double noise, double pitch, double yaw) {

      this.start = start.copy();
      this.thickness = thickness < 1 ? 1 : thickness;
      branches = new ArrayList<>();
      end = getEnd(start, length, pitch, yaw);

      if (counter <= 0) {
        return;
      }

      for (int i = 0; i < rand.nextInt(density) + 1; ++i) {
        branches.add(
            new Branch(
                rand,
                end.copy(),
                counter - (rand.nextInt(2) + 1),
                length < 1 ? 1 : length * 0.88,
                thickness * 0.72,
                density,
                noise + (thickness / 5) * 0.55,
                pitch + (rand.nextDouble() - 0.5) * noise,
                yaw + (rand.nextDouble() - 0.5) * noise
            )
        );
      }
    }

    public void genWood(WorldEditor editor) {
      BlockBrush log = WOOD_TYPE.getLog().setFacing(start.dirTo(end));
      for (Branch b : branches) {
        b.genWood(editor);
      }

      if (thickness == 1) {
        new Line(start, end).fill(editor, log, true, false);
      } else if (thickness > 1) {
        MultiShape shape = new MultiShape();
        for (Coord pos : new Line(start, end)) {
          Coord s = pos.copy();
          Coord e = s.copy();
          e.translate(new Coord((int) thickness, (int) thickness, (int) thickness));
          shape.addShape(new Sphere(s, e));
        }
        shape.fill(editor, log);
      }
    }

    public void genLeaves(WorldEditor editor, Random rand) {
      MultiShape leafShape = new MultiShape();
      getLeafShape(leafShape, rand);

      BlockWeightedRandom leaves = new BlockWeightedRandom();
      leaves.addBlock(WOOD_TYPE.getLeaves(), 5);
      leaves.addBlock(SingleBlockBrush.AIR, 1);

      leafShape.fill(editor, leaves, true, false);
    }

    public void getLeafShape(MultiShape leaves, Random rand) {
      if (!branches.isEmpty()) {
        for (Branch b : branches) {
          b.getLeafShape(leaves, rand);
        }
        return;
      }

      Coord s = end.copy();
      Coord e = s.copy();
      final int size = 2;
      final int noise = 2;
      e.translate(new Coord(size + rand.nextInt(noise), size + rand.nextInt(noise), size + rand.nextInt(noise)));
      leaves.addShape(new Sphere(s, e));
    }

    private Coord getEnd(Coord start, double length, double pitch, double yaw) {
      Coord offset = new Coord(
          (int) (Math.cos(pitch) * Math.cos(yaw) * length),
          (int) (Math.sin(yaw) * length),
          (int) (Math.sin(pitch) * Math.cos(yaw) * length)
      );

      Coord end = start.copy();
      end.translate(offset);
      return end;
    }
  }
}
